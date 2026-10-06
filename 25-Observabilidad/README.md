# Reto 25 — Observabilidad — métricas, logs y trazas con sentido de negocio

**Nivel que evalúa:** Master

**Área:** Observabilidad

**Estado:** 🔲 Sin empezar

## Para qué te sirve este reto

Cierra el concepto de poder responder, con datos y sin adivinar, qué le pasa a un servicio en producción y a quién le afecta. En Bancolombia seguro ya miras logs y dashboards; este reto te pide clasificar cada señal por lo que realmente mide (infraestructura, aplicación, negocio, seguridad) y diseñar la alerta antes del incidente.

## Enunciado

Trabaja sobre la copia de `13-Resiliencia` que está en `app/` (la pasarela de pagos con CircuitBreaker). Instrumenta: (1) métricas con Actuator, Micrometer y Prometheus: las de la JVM, las del circuito de Resilience4j y una métrica de negocio propia (un contador `pagos_total` con la etiqueta `estado` y un temporizador de la latencia de la pasarela); (2) logs estructurados en JSON con un `correlationId` por solicitud (MDC); (3) una traza con un span en la llamada a la pasarela (OpenTelemetry / Micrometer Tracing); (4) una consulta o tablero que muestre la tasa de pagos `EN_PROCESO` y una regla de alerta (por ejemplo circuito abierto por más de un minuto, o más del 30% de pagos en proceso en cinco minutos) con su runbook corto; (5) una tabla que clasifique cada señal en una de las categorías: monitoreo de infraestructura, gestión de logs, APM, RUM, sintético, seguridad, auditoría transaccional o costos.

## Qué debes entregar

El código instrumentado en `app/`, `observabilidad/` funcionando con `docker compose up` (Prometheus y Grafana), capturas o consultas PromQL, `docs/clasificacion-de-senales.md` y `docs/runbook-alerta.md`.

## Cómo sabes que lo dominas

¿Cómo distinguirías con tus señales entre "la pasarela está lenta" y "mi servicio está lento"? ¿Cuál de tus métricas es de negocio y cuál de infraestructura, y por qué importa la diferencia al decidir a quién despertar de madrugada? ¿Por qué una alerta basada en un síntoma (pagos en proceso) suele ser mejor que una basada en una causa (CPU alta)?

## Qué hay en esta carpeta

Todo lo que dice `TODO` es tuyo. Lo demás es plomería para que arranques sin perder tiempo.

- `app/`: Copia de 13-Resiliencia (Gradle, con sus tests en verde). Aquí instrumentas.
- `observabilidad/docker-compose.yml`: Prometheus y Grafana locales.
- `observabilidad/prometheus.yml`: Scrape del endpoint de Actuator (ya configurado).
- `observabilidad/alertas.yml`: Reglas de alerta (TODO).
- `docs/clasificacion-de-senales.md`: Tabla de tipos de monitoreo (TODO).
- `docs/runbook-alerta.md`: Qué hacer cuando suena la alerta (TODO).

**Herramientas:** Spring Boot Actuator, Micrometer, Prometheus, Grafana y OpenTelemetry. Si en Bancolombia usan otra herramienta (Dynatrace, Datadog, ELK), el reto se defiende igual: cambia la herramienta, no el concepto.

## Repaso: explicación técnica del concepto

Observabilidad es la capacidad de entender el estado interno de un sistema a partir de lo que emite. Se apoya en tres señales: métricas (números agregados en el tiempo; baratas, buenas para alertar y ver tendencias), logs (eventos con contexto; caros, buenos para investigar un caso) y trazas (el recorrido de una solicitud a través de componentes, con el tiempo de cada tramo; buenas para ubicar dónde se pierde el tiempo). Se correlacionan mediante identificadores compartidos: un `correlationId` o el `traceId` que aparece en el log y en la traza.

Para decidir qué medir hay métodos probados: RED para servicios (Rate, Errors, Duration: solicitudes por segundo, errores, latencia), USE para recursos (Utilization, Saturation, Errors) y las cuatro señales doradas de Google (latencia, tráfico, errores, saturación). Una métrica de negocio (pagos aprobados, citas reservadas) responde "¿el negocio está funcionando?" aunque todos los servidores estén sanos, y suele ser la primera en cambiar cuando algo falla de verdad.

El assessment te pide distinguir tipos de monitoreo: de infraestructura (CPU, memoria, red), gestión de logs, APM (rendimiento de la aplicación y trazas), RUM (lo que vive el usuario real en su navegador o app), sintético (una sonda que ejecuta un flujo cada minuto desde fuera), de seguridad, auditoría transaccional (quién hizo qué, con valor probatorio) y de costos. No son lo mismo y se hacen con herramientas y retenciones distintas.

Cuándo NO: no registres datos personales ni secretos en logs; no etiquetes métricas con valores de alta cardinalidad (un id de usuario como etiqueta destruye Prometheus); y no crees alertas sin runbook, porque una alerta a la que nadie sabe responder es ruido.

## Paso a paso

1. Spec: lista tres preguntas que quieres poder contestar en un incidente (por ejemplo "¿la pasarela falla o es mi servicio?") y qué señal contesta cada una.
2. Agrega las dependencias de Actuator, Micrometer Prometheus y tracing. En Spring Boot 4 los starters cambiaron de nombre (como pasó con aop y kafka en los retos 13 y 16): revisa cuáles existen antes de asumir.
3. Expón `/actuator/prometheus` y verifica que ves las métricas del circuito. Agrega tu contador y temporizador de negocio en `PasarelaPagosAdapter` o en el caso de uso, no en el controlador.
4. Configura el log JSON con el `correlationId` en MDC. Verifica que un mismo id aparezca en todas las líneas de una solicitud.
5. Levanta `observabilidad/docker-compose.yml`, mira tus métricas en Prometheus y arma la consulta de la tasa de pagos en proceso. Escribe la regla en `alertas.yml` y provócala dejando la pasarela con 100% de fallos.
6. Llena la tabla de clasificación y el runbook. Cada señal va en una sola categoría principal y puedes justificarla.

## Autoevaluación

Antes de marcar el reto como ✅, responde en menos de 2 minutos, en voz alta o por escrito, las preguntas de repaso de este tema en `REPASO_MASTER.md` (están sin respuesta; las respuestas modelo están al final del archivo). Si te cuesta más que escribir el entregable, el hueco está en el concepto.

<!-- ENTITLEMENT:25:START -->
## Ejemplo fácil de explicar

Las tres señales: **métricas** (qué tan mal está: el termómetro), **logs** (qué pasó: el diario) y **trazas** (por dónde pasó la petición: el recorrido). Una métrica de negocio dice "cuántos flujos de aprobación se crearon"; una de infraestructura dice "CPU al 80%". Se alerta por **síntoma** (usuarios fallando), no por causa, y cada alerta lleva un runbook.

```
RED: Rate (peticiones/s), Errors (% fallos), Duration (latencia p95)
```

## Cómo lo trabajamos en Entitlement (micros)

Evidencia del código real de los micros (rutas relativas a `Bancolombia/Micros/`). Es lo que hace el equipo; cuenta qué parte hiciste tú y cuál es del equipo.

- **Logs estructurados ECS (Elastic Common Schema):** `Library_MR/ecs_logs` (`ecs-model`, `ecs-core`, `ecs-reactive`, `ecs-imperative`). `ReactiveLogsHandler implements WebFilter` (`@Order(Integer.MIN_VALUE)`) extrae/propaga `message-id` en el `Context` de Reactor (`MessageIdMngUseCase`). Elixir: `Library_MR/ecs_elixir_logs`.
- **Correlación:** `message-id` (UUID obligatorio), `X_REQUEST_ID` y `aidCreator` en `ContextData`. En `incidentes/` las consultas filtran por `trace_id`.
- **Métricas:** `spring-boot-starter-actuator` + `micrometer-registry-prometheus` (`ms_admin_authorization`, `client_parameters_ms`, `permitions_entitlement_ms`); `MicrometerMetricPublisher` publica métricas del SDK de AWS como `Timer`/`Counter`; el path de prometheus se permite en la `AuthorizationPolicy`.
- **Health:** `/actuator/health/readiness` y `/liveness` (+ `startupProbe`) en `app.yaml`; Elixir `get "/health"`.
- **Trazas:** OpenTelemetry en Elixir (`opentelemetry_plug`, `opentelemetry_ecto`, exporter OTLP, `Tracer.with_span "redis.get_permission"`, sidecar `opentelemetry.io/inject`). En Java no hay dependencias OTel en los `build.gradle` revisados: se usa `message-id`.
- **Logs sin PII:** `SensitiveHelper` y el sampling del configmap (`print-on-error`, `rules20XJson/rules40XJson`).

**No encontrado en los micros (no lo afirmes como experiencia del proyecto):**

- Alertas, dashboards y runbooks versionados: NO ENCONTRADO (solo un `grafana-event-decoder`). Trazas distribuidas en Java: NO ENCONTRADO.

**Cómo contarlo en la entrevista:** Tu historia: `message-id` propagado en el `Context` de Reactor para reconstruir un incidente. Reconoce que faltan alertas por síntoma y runbook (reto 25).
<!-- ENTITLEMENT:25:END -->

## Cómo cerré esta brecha (mi implementación)

*Completo esto yo mismo cuando termine el reto, no antes. Con lo que ya entregué, respondo aquí:*

- *¿Qué archivos y decisiones concretas produje y qué responsabilidad tiene cada uno?*
- *¿Cómo mi entrega, específicamente, resuelve el concepto de este reto? Cito mis propios archivos.*
- *¿Qué error o malentendido tuve en el camino y cómo lo corregí?*

## SDD — Spec-Driven Development

Antes de producir nada en este reto, escribe (3 a 5 líneas) la especificación de lo que vas a entregar: qué archivos, qué decisión toma cada uno, qué casos borde cubres y cómo vas a verificar que está bien. La decisión de diseño se toma antes de la primera línea, no se descubre mientras escribes. Puedes usar la IA para pulir la spec o para preguntarte lo que se te escapa, pero qué debe hacer cada pieza lo decides tú: es el feedback más repetido de tus evaluadores.

---
