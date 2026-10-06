# Reto 13 — Resiliencia (mínimo 2: CircuitBreaker, Retry, Fallback, RateLimit, Bulkhead)

**Nivel que evalúa:** Senior

**Estado:** ✅ Cerrado — verificado el 2026-10-03 (4 tests de resiliencia + ArchitectureTest en verde, log de corrida real en LOG-CIRCUITO.txt).

## Para qué te sirve este reto

Cierra el concepto de qué hacer cuando una dependencia externa falla: reintentar, cortar el circuito, degradar la respuesta, limitar la concurrencia. Cualquier llamada externa de un microservicio tuyo en Bancolombia puede fallar así — este reto es la respuesta técnica formal a algo que probablemente ya resolviste de forma manual.

## Enunciado

Simulá una pasarela de pagos externa que falla 40% de las veces. Envolvela con Resilience4j: `@Retry` (3 intentos, backoff exponencial), `@CircuitBreaker` (se abre con >50% de fallos en ventana de 10), `@Bulkhead` (máximo 5 llamadas concurrentes), y `@Fallback` que responda "pago en proceso, se confirmará luego" en vez de error crudo.

## Qué debés entregar

Código + log de una corrida donde se vea el circuito pasando por los 3 estados.

## Cómo sabés que lo dominás

¿Podés explicar por qué combinaste Bulkhead con CircuitBreaker en vez de solo uno de los dos — qué problema distinto resuelve cada uno en este caso?

## Explicación técnica del concepto

Retry reintenta una operación que falla por una causa transitoria. CircuitBreaker detiene los intentos cuando la tasa de fallos supera un umbral, evitando insistir sobre una dependencia ya caída. Bulkhead limita la concurrencia hacia una dependencia para que su lentitud no consuma todos los recursos del sistema. Fallback define una respuesta alternativa cuando lo anterior no evita la falla. Son complementarios porque cada uno actúa en un momento distinto: antes, durante y después de que la falla ocurre.

<!-- ENTITLEMENT:13:START -->
## Ejemplo fácil de explicar

Un fusible: si la plancha falla una y otra vez, el fusible corta para no quemar la casa; pasado un rato prueba si ya se puede volver a conectar.

```java
// Retry con backoff solo para errores transitorios (5xx), nunca para 4xx
.retryWhen(Retry.backoff(3, Duration.ofMillis(200)).filter(e -> e instanceof WebClientResponseException w && w.getStatusCode().is5xxServerError()))
```
Cuándo NO: reintentar operaciones no idempotentes (cobros) sin llave de idempotencia; reintentar sin límite empeora una caída.

## Cómo lo trabajamos en Entitlement (micros)

Evidencia del código real de los micros (rutas relativas a `Bancolombia/Micros/`). Es lo que hace el equipo; cuenta qué parte hiciste tú y cuál es del equipo.

- **Retry con backoff reactivo filtrado:** `RetrieveClientLimitService` (`Monetary_Limits_MR/ms_limit_orchestration_services`): `Retry.backoff(MAX_ATTEMPTS, ...).filter(this::isRetryable)`, solo errores 5xx.
- **Retry de failover de BD:** `AuroraFailoverRetrySupport` (`Monetary_Transactions_MR/operational_transaction_management_ms`, `r2dbc-aurora-support`): `Retry.backoff(...).maxBackoff(...).filter(this::isFailoverError)`; lo usa `SaveInitTransactionAdapter`. Y el validador de rol del cluster (`AuroraConnectionRoleValidator`, espera `WRITER`).
- **Timeouts:** `RestConsumerConfig` (Netty `ConnectTimeout`, `ReadTimeoutHandler`, `WriteTimeoutHandler`); `ReactiveEventsGateway.emit` con `.timeout(Duration.ofSeconds(t)).retry(n)`.
- **Dead Letter Queue y reintentos del broker:** `AsyncProps.withDLQRetry(true).retryDelay(...).maxRetries(...)` en `RabbitMQConfiguration` (`ms_products`).
- **Infraestructura:** HPA, PodDisruptionBudget (`ms_retrieve_role`), readiness/liveness/startup probes, canary con `DestinationRule` (Istio).
- **Resilience4j:** declarado (`resilience4j-spring-boot3/4`, YAML con `circuitbreaker.instances.testGet/testPost`), pero **sin uso**: no hay `@CircuitBreaker`, `@Retry` ni `@Bulkhead` en el código de producción; las instancias son la plantilla del scaffold.

**No encontrado en los micros (no lo afirmes como experiencia del proyecto):**

- CircuitBreaker, Bulkhead y RateLimiter en código de producción: NO ENCONTRADO. No digas que usas Resilience4j en producción.

**Cómo contarlo en la entrevista:** Habla de lo real (retry con filtro 5xx, failover de Aurora, timeouts, DLQ). Los 3 estados del CircuitBreaker los defiendes con el reto 13, diciendo que ahí lo implementaste.
<!-- ENTITLEMENT:13:END -->

## Cómo cerré esta brecha (mi implementación)

Dejé `RealizarPagoUseCase` "tonto" a propósito: solo delega en el puerto `ProcesarPagoPort`. La resiliencia vive en el adaptador `PasarelaPagosAdapter`, que lleva las tres anotaciones de Resilience4j sobre `procesar()`. La pasarela simulada decide si falla a través de `FallaSimulada` (`FallaAleatoria` en producción, con 40% configurable en `pasarela.probabilidad-fallo`), lo que me permite controlarla en los tests. El `application.yaml` tiene los valores del reto: Retry de 3 intentos con backoff exponencial (x2), CircuitBreaker que abre con 50% de fallos en ventana de 10, y Bulkhead de 5 llamadas concurrentes.

Tuve cuatro hallazgos reales. Uno: el proyecto está en Spring Boot 4 y `resilience4j-spring-boot3` / `spring-boot-starter-aop` ya no resuelven; usé `resilience4j-spring-boot4` 2.4.0 y `spring-boot-starter-aspectj`. Dos: `@Fallback` no existe en Resilience4j, es el atributo `fallbackMethod`, y su ubicación importa: si lo pongo en `@CircuitBreaker` (que queda por dentro del Retry), el fallback devuelve una respuesta "exitosa" y el Retry nunca reintenta. Lo puse en `@Retry`, que es la capa más externa, así primero se reintenta y el fallback solo actúa al final. Tres: `minimum-number-of-calls` vale 100 por defecto, así que con ventana de 10 el circuito nunca abriría; lo fijé en 10. Cuatro: el Retry no debe reintentar contra un circuito abierto o un Bulkhead lleno, así que ignoro `CallNotPermittedException` y `BulkheadFullException`.

Probé todo con 4 tests de Spring que ejecutan las anotaciones de verdad: pasarela sana, pasarela caída (3 intentos y respuesta `EN_PROCESO`), el circuito pasando por CLOSED, OPEN, HALF_OPEN y CLOSED, y el Bulkhead lleno recibiendo el fallback sin tocar la pasarela. `LOG-CIRCUITO.txt` guarda una corrida real de 60 pagos.

Para la pregunta del evaluador: el CircuitBreaker protege del fallo (deja de insistir contra una pasarela que ya está caída y le da tiempo de recuperarse) y el Bulkhead protege de la lentitud (limita cuántas llamadas simultáneas puede consumir esa dependencia, para que no se lleve todos los hilos del servicio). Una pasarela lenta pero que no falla nunca abre el circuito, pero sí agotaría los hilos sin el Bulkhead.

## SDD — Spec-Driven Development

Antes de tocar código en este reto, escribí (alcanza con 3-5 líneas, en un comentario o en un README aparte) la especificación de lo que vas a construir: qué clases/métodos necesitás, el contrato de cada uno (entradas, salidas, casos borde) y la regla de negocio que cubre — el "qué" antes del "cómo". Es la misma disciplina que separa TDD (diseñás guiado por tests que escribís vos) de SDD (diseñás guiado por una spec escrita, para vos mismo o para que una IA la ejecute): la decisión de diseño se toma **antes** de escribir la primera línea, no se descubre a medida que tecleás. Encaja directo con el feedback de tus evaluadores: podés usar la IA para redactar o pulir esa spec, pero la decisión de qué debe hacer cada pieza es tuya, no de la IA — spec en mano, después sí generás o escribís el código.

---
