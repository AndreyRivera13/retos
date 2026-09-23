# Reto 19 — DDD — Diseño guiado por el dominio

**Nivel que evalúa:** Senior

**Estado:** 🔲 Sin empezar

## Para qué te sirve este reto

Cierra el concepto de modelar el dominio con sus propias reglas (agregados, value objects, bounded context), no solo con tablas y getters/setters. Le pone vocabulario formal a la arquitectura que ya trabajaste en Clean Architecture y a los modelos de permisos/roles que ya manejás en Entitlement.

## Enunciado

Modelá `CuentaBancaria` como Aggregate Root con `depositar()` y `retirar()` (retirar lanza excepción si excede el saldo o si la cuenta está `BLOQUEADA`), un Value Object `Dinero` (monto + moneda, inmutable, no permite operar dos `Dinero` de monedas distintas), y un evento de dominio `RetiroRealizado` emitido tras un retiro exitoso.

## Qué debés entregar

Código + explicación de por qué `Dinero` es Value Object y no Entidad.

## Cómo sabés que lo dominás

¿Podés explicar qué pasaría si `Dinero` tuviera un `setMonto()` público — qué garantía del dominio se rompería?

## Explicación técnica del concepto

El Aggregate Root es el único punto de entrada para modificar el estado de un agregado, lo que garantiza que las invariantes del dominio (no retirar más saldo del disponible, por ejemplo) se cumplan siempre. Un Value Object se identifica por sus atributos, no por una identidad propia — dos instancias con los mismos valores son intercambiables — y por eso se modela inmutable: un setter público rompería esa garantía de igualdad por valor.

## Cómo cerré esta brecha (mi implementación)

*Completo esto yo mismo cuando termine el reto — no antes. Con mi código real ya escrito, respondo acá (no sobre el enunciado, sobre mi implementación):*

- *¿Qué clases/métodos concretos escribí y qué responsabilidad tiene cada uno?*
- *¿Cómo mi código, específicamente, resuelve el concepto de este reto? Cito mis propias clases y métodos, no la teoría.*
- *¿Qué bug o mal entendido tuve en el camino, y cómo lo corregí? (revisar esto antes de la entrevista me sirve más que repasar la teoría de nuevo).*

## SDD — Spec-Driven Development

Antes de tocar código en este reto, escribí (alcanza con 3-5 líneas, en un comentario o en un README aparte) la especificación de lo que vas a construir: qué clases/métodos necesitás, el contrato de cada uno (entradas, salidas, casos borde) y la regla de negocio que cubre — el "qué" antes del "cómo". Es la misma disciplina que separa TDD (diseñás guiado por tests que escribís vos) de SDD (diseñás guiado por una spec escrita, para vos mismo o para que una IA la ejecute): la decisión de diseño se toma **antes** de escribir la primera línea, no se descubre a medida que tecleás. Encaja directo con el feedback de tus evaluadores: podés usar la IA para redactar o pulir esa spec, pero la decisión de qué debe hacer cada pieza es tuya, no de la IA — spec en mano, después sí generás o escribís el código.

---
