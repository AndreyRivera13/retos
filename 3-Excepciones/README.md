# Reto 3 — Excepciones y depuración

**Nivel que evalúa:** Trainer

**Estado:** ✅ Cerrado — `ProcesadorPagos`, las dos excepciones checked y `RegistroTransaccion` implementados, 3 pruebas JUnit pasando (revisado en código, sin bugs). Verificado 2026-09-29.

## Para qué te sirve este reto

Cierra el concepto de manejo de excepciones: distinguir cuándo una falla es esperable (checked) de cuándo es un error de programación (unchecked), y no perder el recurso ni la causa original al propagarla. Es el mismo concepto detrás del `@ControllerAdvice`/`@ExceptionHandler` que mapea excepciones de dominio a códigos HTTP en tus APIs.

## Enunciado

Implementá `ProcesadorPagos.procesar(Pago pago)` que lance `SaldoInsuficienteException` (checked) si el monto excede el saldo disponible, y `PagoInvalidoException` (checked) si el monto es ≤ 0 — ambas con constructor que acepte causa encadenada. El método debe usar `try-with-resources` para "cerrar" un recurso simulado (`RegistroTransaccion implements AutoCloseable`). Escribí 3 pruebas: pago válido, saldo insuficiente, monto inválido.

## Qué debés entregar

Código + pruebas JUnit pasando.

## Cómo sabés que lo dominás

¿Podés explicar, sin ver el código, en qué orden se ejecutan las cosas si `procesar()` lanza una excepción dentro del bloque `try-with-resources` — se cierra igual el recurso?

## Explicación técnica del concepto

Una excepción checked obliga a quien llama a manejar explícitamente una falla esperable del negocio (saldo insuficiente, monto inválido); una unchecked representa un error de programación que no tiene sentido forzar a capturar en cada punto de llamada. Encadenar la causa original en el constructor conserva el stacktrace real para depuración. `try-with-resources` garantiza el cierre del recurso incluso si el bloque lanza una excepción, porque el cierre ocurre en un `finally` implícito generado por el compilador.

<!-- ENTITLEMENT:3:START -->
## Ejemplo fácil de explicar

```java
// unchecked: error de negocio, el llamador no puede "arreglarlo" en el momento
if (saldo < monto) throw new SaldoInsuficienteException(saldo, monto);

// checked: I/O que SÍ puede fallar y el llamador debe decidir
try (var in = Files.newInputStream(ruta)) { ... }          // try-with-resources cierra solo
catch (IOException e) { throw new ReporteException("no pude leer " + ruta, e); }   // chaining: conserva la causa (e)
```
Regla: una excepción por **qué falló en el negocio**, no por quién la lanzó; y siempre pasa la causa original.

## Cómo lo trabajamos en Entitlement (micros)

Evidencia del código real de los micros (rutas relativas a `Bancolombia/Micros/`). Es lo que hace el equipo; cuenta qué parte hiciste tú y cuál es del equipo.

- **Jerarquía propia:** `BusinessException` y `AppException`, ambas `extends BusinessExceptionECS` (librería ECS), y el enum `ConstantBusinessException` que trae HTTP status, mensaje, código de negocio, mensaje interno y código de log por cada error (`ms_actors/domain/model/.../exception`). Solo en `ms_actors` hay ~137 `new BusinessException(...)`.
- **Manejo global WebFlux:** `ExceptionResponse` (`@Order(-2)`, `extends AbstractErrorWebExceptionHandler`) en `ms_actors`: traduce `BusinessException` → `AppException` → error desconocido 500, y manda log a SQS. Equivalente: `GlobalWebExceptionHandler implements WebExceptionHandler` en `ms_limit_clone_processor`.
- **Manejo global MVC:** `CustomExceptionHandler` con `@ControllerAdvice` en `ms_masam` (el único micro MVC).
- **Traducción/wrapping:** en `ExecTrxCreateDelegateUseCase`, `.onErrorMap(t -> t instanceof AppException || t instanceof BusinessException ? t : new AppException(UNKNOWN_FINISH_CREATE_DELEGATE, messageId))`.
- **Compensación:** `onErrorResume(error -> rollback(...).then(Mono.error(error)))` en `CreateRuleExceptionUseCase`.
- **try-with-resources:** solo 2 usos reales, en `GetConsumerServiceAdapter` y `GenerateAndSendOtpAdapter` (`ms_masam`), sobre la respuesta HTTP.

**No encontrado en los micros (no lo afirmes como experiencia del proyecto):**

- Debilidad real: `BusinessException(ConstantBusinessException, Throwable)` solo propaga `getMessage()`, no el `cause`. El exception chaining queda débil — mencionarlo y decir cómo lo arreglarías es un buen punto.

**Cómo contarlo en la entrevista:** Enum de errores centralizado + handler global que convierte excepción → HTTP. Cierra con la mejora del `cause`.
<!-- ENTITLEMENT:3:END -->

## Cómo cerré esta brecha (mi implementación)

Implementé `ProcesadorPagos.procesar()` con `try-with-resources` sobre `RegistroTransaccion` (que implementa `AutoCloseable`), y las dos excepciones checked —`PagoInvalidoException` y `SaldoInsuficienteException`— con constructor que acepta causa encadenada. Cubrí los tres casos con JUnit: pago válido, saldo insuficiente y monto inválido, los tres pasan.

El `try-with-resources` garantiza que `RegistroTransaccion.close()` se ejecuta aunque `procesar()` lance una excepción dentro del bloque: el cierre del recurso ocurre antes de que la excepción se propague hacia quien llamó, no después. Eso es justo lo que evita que un recurso quede abierto cuando algo falla a mitad de camino.

## SDD — Spec-Driven Development

Antes de tocar código en este reto, escribí (alcanza con 3-5 líneas, en un comentario o en un README aparte) la especificación de lo que vas a construir: qué clases/métodos necesitás, el contrato de cada uno (entradas, salidas, casos borde) y la regla de negocio que cubre — el "qué" antes del "cómo". Es la misma disciplina que separa TDD (diseñás guiado por tests que escribís vos) de SDD (diseñás guiado por una spec escrita, para vos mismo o para que una IA la ejecute): la decisión de diseño se toma **antes** de escribir la primera línea, no se descubre a medida que tecleás. Encaja directo con el feedback de tus evaluadores: podés usar la IA para redactar o pulir esa spec, pero la decisión de qué debe hacer cada pieza es tuya, no de la IA — spec en mano, después sí generás o escribís el código.

---
