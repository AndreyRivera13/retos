# Reto 3 — Excepciones y depuración

**Nivel que evalúa:** Trainer

**Estado:** ✅ Completado

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

## Cómo cerré esta brecha (mi implementación)

- **¿Qué clases/métodos concretos escribí y qué responsabilidad tiene cada uno?**
  - `Pago`: Modelo de dominio inmutable que encapsula el monto a pagar y el saldo disponible.
  - `PagoInvalidoException`: Excepción checked (`extends Exception`) lanzada cuando el monto a procesar es menor o igual a cero. Soporta mensajes y causa encadenada (`Throwable causa`).
  - `SaldoInsuficienteException`: Excepción checked (`extends Exception`) lanzada cuando el monto solicitado supera el saldo disponible. Soporta mensajes y causa encadenada.
  - `RegistroTransaccion`: Recurso transaccional que implementa `AutoCloseable` para simular la apertura y cierre controlado de recursos.
  - `ProcesadorPagos.procesar(Pago pago)`: Orquesta la validación de negocio utilizando `try-with-resources` para garantizar el cierre seguro de `RegistroTransaccion` ante ejecuciones exitosas o excepciones.
  - `ProcesadorPagosTest`: Suite de pruebas unitarias con JUnit 5 que valida los tres escenarios clave: pago exitoso, saldo insuficiente y monto inválido.
  - `MainApplication`: Punto de entrada con demostración manual interactiva de captura y procesamiento de los tres escenarios.

- **¿Cómo mi código, específicamente, resuelve el concepto de este reto?**
  - `ProcesadorPagos.procesar(Pago pago)` declara explícitamente `throws SaldoInsuficienteException, PagoInvalidoException` asegurando que los llamadores manejen las fallas de negocio esperadas.
  - El uso de la sentencia `try (RegistroTransaccion registro = new RegistroTransaccion())` garantiza que `registro.close()` sea invocado de forma determinista y prioritaria antes de que cualquier excepción escape del método.
  - Los constructores sobrecargados de `PagoInvalidoException` y `SaldoInsuficienteException` permiten encadenar excepciones previas preservando la causa raíz y el stacktrace.

- **¿Qué bug o mal entendido tuve en el camino, y cómo lo corregí?**
  - Es fundamental recordar que `try-with-resources` cierra el recurso *antes* de que la excepción sea capturada por un bloque `catch` externo o propagada hacia el llamador, y que si `close()` falla mientras existe una excepción activa en el bloque `try`, la excepción de `close()` pasa a ser una excepción suprimida (`suppressed exception`) para no opacar la causa principal.

## SDD — Spec-Driven Development

Antes de tocar código en este reto, escribí (alcanza con 3-5 líneas, en un comentario o en un README aparte) la especificación de lo que vas a construir: qué clases/métodos necesitás, el contrato de cada uno (entradas, salidas, casos borde) y la regla de negocio que cubre — el "qué" antes del "cómo". Es la misma disciplina que separa TDD (diseñás guiado por tests que escribís vos) de SDD (diseñás guiado por una spec escrita, para vos mismo o para que una IA la ejecute): la decisión de diseño se toma **antes** de escribir la primera línea, no se descubre a medida que tecleás. Encaja directo con el feedback de tus evaluadores: podés usar la IA para redactar o pulir esa spec, pero la decisión de qué debe hacer cada pieza es tuya, no de la IA — spec en mano, después sí generás o escribís el código.

---
