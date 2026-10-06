# Reto 15 — Paradigmas reactivo/funcional

**Nivel que evalúa:** Senior

**Estado:** ✅ Cerrado — verificado el 2026-10-03 (6 tests con StepVerifier + ArchitectureTest en verde, prueba manual en MainApplication).

## Para qué te sirve este reto

Cierra el concepto de programación reactiva frente a imperativa: no bloquear un hilo esperando una respuesta, sino declarar qué pasa cuando el dato llegue (`Mono`/`Flux`). Si tus APIs hoy son bloqueantes con Spring MVC, el reto te exige justificar cuándo migrar a WebFlux vale la pena y cuándo no — no es "reactivo porque sí".

## Enunciado

Convertí `GestionarCitasUseCase` a reactivo: `Mono<Cita> reservar(...)`, `Flux<Cita> citasDelDia(LocalDate fecha)`. Agregá un operador que, si `citasDelDia` no emite nada en 2 segundos (simulando latencia), devuelva un valor por defecto (`Mono.empty()` transformado con `.switchIfEmpty` o `.timeout` + fallback).

## Qué debés entregar

Código + explicación de qué pasa con la suscripción si nadie llama `.subscribe()`.

## Cómo sabés que lo dominás

¿Podés explicar la diferencia entre que tu método retorne `Mono<Cita>` vacío por diseño vs que lance una excepción, y cuándo usarías cada uno?

## Explicación técnica del concepto

La programación reactiva declara qué hacer cuando un dato esté disponible (`Mono`, `Flux`) en vez de bloquear un hilo esperando ese dato, lo que permite manejar más operaciones de I/O concurrentes con menos hilos. No es una mejora universal: para lógica sin I/O significativo, el modelo imperativo es más simple de leer y depurar. La decisión de migrar depende de si el cuello de botella real es la espera por I/O, no de preferencia estilística.

<!-- ENTITLEMENT:15:START -->
## Ejemplo fácil de explicar

En una pizzería con un solo mesero, el imperativo es esperar parado en el mostrador hasta que salga la pizza. Reactivo es dejar el pedido y atender a otro cliente; cuando la pizza esté, te avisan.

```java
Mono<Cliente> c = repo.buscar(id)                 // nada se ejecuta todavía (lazy)
    .switchIfEmpty(Mono.error(new NoExiste()))
    .timeout(Duration.ofSeconds(2));
c.subscribe(...);                                  // aquí arranca
```
Cuándo NO: CRUD simple con poca concurrencia y librerías bloqueantes (JDBC): reactivo + `.block()` es peor que MVC.

## Cómo lo trabajamos en Entitlement (micros)

Evidencia del código real de los micros (rutas relativas a `Bancolombia/Micros/`). Es lo que hace el equipo; cuenta qué parte hiciste tú y cuál es del equipo.

- **Todo el stack nuevo es WebFlux** (Spring Boot 4.1.x en `client_parameters_ms`, `ms_actors`, `ms_admin_authorization`, `ms_products`, los nueve `ms_limit_*`, `ms_authorization_flows`…; Boot 3.5.x en `masam_*`, `products_information_ms`, `permitions_entitlement_ms`). Solo `ms_masam` es MVC.
- **WebFlux funcional:** `AvailableLimitRouter` (`route(POST(...), handler::availableLimit)`) y `ValidateClientLimitHandlerRequest` con `Mono.zip(modelMono, contextMono).map(t -> new Command<>(t.getT1(), t.getT2()))`.
- **Operadores reales:** `switchIfEmpty(Mono.defer(() -> Mono.error(...)))` en `GetSchemeClientUseCase`; `flatMap` anidados, `onErrorMap`, `onErrorResume`; `Flux.fromIterable(...).flatMap(...).collectList()` en `OwnerFlowUseCase`.
- **Persistencia reactiva:** R2DBC (`ReactiveCrudRepository` + `@Query`), `TransactionalOperator` (`SaveInitTransactionAdapter`), DynamoDB/S3 asíncronos.
- **Lotes:** `.buffer(BUFFER_SIZE).flatMap(...)` en `SaveEventPublicationInfoAdapter`; `subscribeOn(Schedulers.boundedElastic())` al envolver llamadas bloqueantes.
- **Cuidados:** `.subscribe()` fire-and-forget para logs funcionales (en `GetSchemeClientUseCase`) y `.block()` al arranque en `StateConfig`/`PrivilegeConfig`.

**No encontrado en los micros (no lo afirmes como experiencia del proyecto):**

- `limitRate` y `onBackpressure*` explícitos: NO ENCONTRADO (el backpressure es el implícito de Reactor/R2DBC/Netty).

**Cómo contarlo en la entrevista:** Es tu tema fuerte: cuenta el flujo `Mono.zip` → caso de uso → R2DBC con `TransactionalOperator`, y explica cuándo `subscribeOn(boundedElastic)` y por qué `.block()` en el arranque no es lo mismo que en una petición.
<!-- ENTITLEMENT:15:END -->

## Cómo cerré esta brecha (mi implementación)

Dejé el caso de uso de citas en reactivo: `reservar` devuelve `Mono<Cita>` y `citasDelDia` devuelve `Flux<Cita>`. La validación de entrada (doctor o fecha nulos) la envuelvo en `Mono.defer` y respondo con `Mono.error(IllegalArgumentException)`: un dato inválido es un error, no un resultado vacío. Vacío lo reservo para "consulté y no hay nada", que es una respuesta válida del negocio. Como `Mono`/`Flux` son perezosos, nada se ejecuta hasta que alguien se suscribe: si nadie llama `.subscribe()` o `.block()`, el origen ni siquiera se consulta y el error de validación ni siquiera se emite.

Para el requisito de los 2 segundos usé `.timeout(Duration)` seguido de `.onErrorResume(TimeoutException.class, error -> Flux.empty())`: si el origen no emite a tiempo, el flujo completa vacío en vez de propagar el error, y cualquier otro error sigue propagándose porque solo capturo `TimeoutException`. La latencia del origen y el timeout son inyectables por constructor, así los tests usan 50 ms / 500 ms en vez de esperar 2 segundos reales. Los 6 tests usan `StepVerifier` y no hay `Thread.sleep` dentro de la cadena reactiva (BlockHound está activo).

Mi versión inicial tenía tres defectos reales. Uno: no compilaba, porque el `.onErrorResume(..., Flux.empty())` encadenado infería `Flux<Object>` y no se podía convertir a `Flux<Cita>`. Dos: el origen de prueba era un `Flux.empty()`, que completa de inmediato, así que `timeout` nunca se disparaba (medí 141 ms, contra ~2005 ms con un origen que nunca emite); un timeout solo actúa si el origen no emite ni completa a tiempo. Tres: el `MainApplication` nunca se suscribía, así que la "prueba manual" no probaba nada. El esqueleto de tests, la prueba manual.

Un tema abierto que conozco: con este fallback, "no hay citas" y "el origen estuvo lento" se ven igual para quien consume el `Flux` (ambos completan vacío). En producción distinguiría el caso con un valor por defecto explícito o con una métrica/log del timeout, para no esconder una degradación como si fuera ausencia de datos.

## 🎯 Con tu evaluador

Este es EL tema donde tiene más autoridad de todo tu assessment — lideró personalmente la migración de POO a programación funcional con Spring WebFlux. No te va a preguntar "qué es un Mono" — te va a preguntar por los dolores reales de esa migración: qué se vuelve difícil de leer/debuggear en reactivo, y cómo decidiste (o decidirías) qué sí migrar a reactivo y qué no. Tené una opinión propia, no solo la teoría.

## SDD — Spec-Driven Development

Antes de tocar código en este reto, escribí (alcanza con 3-5 líneas, en un comentario o en un README aparte) la especificación de lo que vas a construir: qué clases/métodos necesitás, el contrato de cada uno (entradas, salidas, casos borde) y la regla de negocio que cubre — el "qué" antes del "cómo". Es la misma disciplina que separa TDD (diseñás guiado por tests que escribís vos) de SDD (diseñás guiado por una spec escrita, para vos mismo o para que una IA la ejecute): la decisión de diseño se toma **antes** de escribir la primera línea, no se descubre a medida que tecleás. Encaja directo con el feedback de tus evaluadores: podés usar la IA para redactar o pulir esa spec, pero la decisión de qué debe hacer cada pieza es tuya, no de la IA — spec en mano, después sí generás o escribís el código.

---
