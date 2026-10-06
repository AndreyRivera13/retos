# Reto 6 — Patrones GRASP

**Nivel que evalúa:** Trainer

**Estado:** ✅ Cerrado — Experto en Información (`Cita.duracionEnMinutos()`) y Creador (`AgendaDoctor.crearCita()`) aplicados correctamente, con comentarios propios señalando cada patrón. Verificado 2026-09-29.

## Para qué te sirve este reto

Cierra el concepto de asignar responsabilidades con un criterio (quién tiene la información para decidir, quién debería crear qué), no por dónde "queda cómodo" el código. Cuando decidís en qué clase va una regla dentro de un servicio Spring, ya estás aplicando o rompiendo esto sin nombrarlo — el reto te da el vocabulario para defenderlo.

## Enunciado

Sobre el sistema de citas del reto 5, identificá y ajustá si hace falta: ¿quién calcula la duración total de las citas del día de un doctor (Experto en Información)? ¿quién crea las instancias de `Cita` — el Service directamente con `new`, o debería haber un método `crear()` en el propio agregado/clase relacionada (Creador)?

## Qué debés entregar

El código ajustado + comentarios señalando exactamente la línea donde aplicaste cada patrón.

## Cómo sabés que lo dominás

¿Podés nombrar una clase candidata alternativa para cada responsabilidad y explicar por qué la descartaste?

## Explicación técnica del concepto

Experto en Información asigna una responsabilidad a la clase que tiene los datos necesarios para cumplirla: si el doctor conoce sus propias citas, es el doctor quien calcula su duración total, no un Service externo que le pide los datos uno por uno. Creador determina quién debería instanciar un objeto — normalmente la clase que lo agrega, contiene o usa de cerca. Ambos patrones evitan clases que solo mueven datos sin tener responsabilidad real sobre ellos.

<!-- ENTITLEMENT:6:START -->
## Ejemplo fácil de explicar

¿Quién calcula el total de una factura? Quien tiene los datos: la propia `Factura` (**Experto**). ¿Quién construye los `Item`? Quien los contiene o los usa de cerca: `Factura.agregar(...)` (**Creador**). Un `FacturaController` solo recibe la petición y delega (**Controlador**).

```java
class Factura { List<Item> items; double total() { return items.stream().mapToDouble(Item::subtotal).sum(); } }
```

## Cómo lo trabajamos en Entitlement (micros)

Evidencia del código real de los micros (rutas relativas a `Bancolombia/Micros/`). Es lo que hace el equipo; cuenta qué parte hiciste tú y cuál es del equipo.

- **Experto:** `CreateNaturalPerson` (`ms_actors`) tiene el `aid` y valida con `isValidAid()` / `isValidType(...)`; `RelationshipCreator.isPrivDirect()`.
- **Creador:** `@Builder(toBuilder = true)` en los modelos y `BuildCreateExceptionRule.buildEventData(...)` (`@UtilityClass`) que arma el evento a partir del comando.
- **Controlador:** los `*Handler` de `reactive-web` (ej. `RetrieveUserHandler`) validan headers y delegan a `UseCase`; un caso de uso orquestador como `MonetaryLimitCreator`.
- **Bajo acoplamiento / Polimorfismo:** casos de uso que dependen solo de `*Gateway`; `SyncEventHandler<T>` y `CommandHandler<T>` resueltos por tipo.
- **Fabricación pura:** `ValueValidator`, `ExceptionResponse`, `MapperFactory`, `CommandHandlersRegistry` (clases sin concepto de negocio, creadas por cohesión).
- **Indirección:** `ReactiveEventsGateway` entre los casos de uso y el `DomainEventBus`.

**No encontrado en los micros (no lo afirmes como experiencia del proyecto):**

- Protected Variations como patrón nombrado no aparece, aunque los gateways cumplen ese rol.

**Cómo contarlo en la entrevista:** Elige 3: Experto (`CreateNaturalPerson`), Fabricación pura (`ValueValidator`) e Indirección (`ReactiveEventsGateway`). Con ejemplo propio cada una.
<!-- ENTITLEMENT:6:END -->

## Cómo cerré esta brecha (mi implementación)

Apliqué Experto en Información dejando que `Cita` calcule su propia `duracionEnMinutos()` con los datos que ya tiene (`horaInicio`, `horaFin`), en vez de sacarle esos datos a un Service externo para que él haga la cuenta. Apliqué Creador dejando que `AgendaDoctor` sea quien construye las instancias de `Cita` con su método `crearCita()`, porque es quien agrupa y mantiene las citas de un doctor —tiene más contexto que nadie más para crearlas.

`AgendaDoctorUseCase` quedó solo coordinando: suma las duraciones sin necesitar saber cómo se calcula una duración ni construir citas directamente. Dejé el razonamiento de cada patrón comentado en el propio código, en la línea donde se aplica.

## SDD — Spec-Driven Development

Antes de tocar código en este reto, escribí (alcanza con 3-5 líneas, en un comentario o en un README aparte) la especificación de lo que vas a construir: qué clases/métodos necesitás, el contrato de cada uno (entradas, salidas, casos borde) y la regla de negocio que cubre — el "qué" antes del "cómo". Es la misma disciplina que separa TDD (diseñás guiado por tests que escribís vos) de SDD (diseñás guiado por una spec escrita, para vos mismo o para que una IA la ejecute): la decisión de diseño se toma **antes** de escribir la primera línea, no se descubre a medida que tecleás. Encaja directo con el feedback de tus evaluadores: podés usar la IA para redactar o pulir esa spec, pero la decisión de qué debe hacer cada pieza es tuya, no de la IA — spec en mano, después sí generás o escribís el código.

---
