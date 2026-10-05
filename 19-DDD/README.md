# Reto 19 — DDD — Diseño guiado por el dominio

**Nivel que evalúa:** Senior

**Estado:** ✅ Cerrado — verificado el 2026-10-03 (15 tests propios + ArchitectureTest en verde, prueba manual en MainApplication).

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

`CuentaBancaria` es el Aggregate Root: es la única puerta para cambiar el saldo y protege sus propias reglas. `depositar()` y `retirar()` rechazan operar con la cuenta `BLOQUEADA` (`IllegalStateException`), con monto nulo o cero (`IllegalArgumentException`) o con otra moneda (lo detecta `Dinero`). `retirar()` además lanza `IllegalStateException` si el monto excede el saldo, y solo después de validar todo resta el saldo y agrega un `RetiroRealizado` a la lista de eventos; si el retiro falla, ni cambia el saldo ni emite el evento. Permitir retirar exactamente el saldo (`esMayorQue` es estricto) fue una decisión deliberada y está probada. Agregué `bloquear()`/`desbloquear()` porque el esqueleto no tenía forma de llegar al estado `BLOQUEADA`, y una interfaz marcadora `EventoDominio` para tipar los eventos en vez de `List<Object>`; `extraerEventos()` entrega los pendientes y los limpia, y `getEventos()` devuelve una vista no modificable para que desde afuera nadie pueda saltarse el agregado.

`Dinero` es un Value Object y no una Entidad porque no tiene identidad: lo que lo define es su valor. 100 COP son intercambiables con cualquier otros 100 COP, no hay un "ese billete en particular"; una Entidad, como `CuentaBancaria`, tiene un id y sigue siendo la misma aunque cambie su saldo. Por eso `Dinero` es `final`, sus atributos son `final`, no tiene setters, `sumar()`/`restar()` devuelven un `Dinero` nuevo y `equals()`/`hashCode()` se basan en monto y moneda. Si tuviera un `setMonto()` público se rompería la inmutabilidad: el mismo objeto `Dinero` compartido (por ejemplo el que está en el evento `RetiroRealizado`, o el saldo de otra cuenta) podría cambiar a espaldas de quien lo guardó, el historial del evento dejaría de ser un hecho, y la validación del constructor (sin negativos, sin nulls) se podría saltar después de creado.

Tuve un detalle sutil: `equals()` con `BigDecimal.equals` considera distintos 100 y 100.00 porque difieren en escala, así que usé `compareTo` y, para que `hashCode()` sea consistente con `equals()`, `stripTrailingZeros()` (hay un test para eso). Lo verifiqué con 7 tests de `Dinero` y 8 de `CuentaBancaria`, más una prueba manual en `MainApplication`.

## SDD — Spec-Driven Development

Antes de tocar código en este reto, escribí (alcanza con 3-5 líneas, en un comentario o en un README aparte) la especificación de lo que vas a construir: qué clases/métodos necesitás, el contrato de cada uno (entradas, salidas, casos borde) y la regla de negocio que cubre — el "qué" antes del "cómo". Es la misma disciplina que separa TDD (diseñás guiado por tests que escribís vos) de SDD (diseñás guiado por una spec escrita, para vos mismo o para que una IA la ejecute): la decisión de diseño se toma **antes** de escribir la primera línea, no se descubre a medida que tecleás. Encaja directo con el feedback de tus evaluadores: podés usar la IA para redactar o pulir esa spec, pero la decisión de qué debe hacer cada pieza es tuya, no de la IA — spec en mano, después sí generás o escribís el código.

---
