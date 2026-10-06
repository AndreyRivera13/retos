# Reto 1 — POO

**Nivel que evalúa:** Trainer

**Estado:** ✅ Cerrado — `Empleado`, `EmpleadoFijo`, `EmpleadoPorHoras` y `Nomina<T>` implementados (se corrigió un bug: `EmpleadoFijo.calcularSalario()` devolvía `salarioBase * 2` en vez de `salarioBase`). Verificado 2026-09-23.

## Para qué te sirve este reto

Cierra el concepto de polimorfismo y herencia: que cada subtipo resuelva su propio comportamiento sin que el código que los usa necesite preguntar "qué tipo sos" (`instanceof`). En tus servicios Spring de Entitlement es lo mismo que evitar un `if (tipo.equals(...))` cada vez que aparece un nuevo tipo de rol o solicitante.

## Enunciado

Modela un sistema de nómina con `Empleado` (clase abstracta) y dos tipos, `EmpleadoFijo` (salario fijo) y `EmpleadoPorHoras` (salario = horas × tarifa), cada uno con su propia lógica de `calcularSalario()`. Agrega una interfaz `Bonificable { double calcularBono(); }` que solo implementa `EmpleadoFijo`. Crea una clase genérica `Nomina<T extends Empleado>` que reciba una lista de empleados y calcule el total a pagar sin usar `instanceof` en ningún punto.

## Qué debés entregar

Código + un párrafo explicando qué pasaría si agregas un tercer tipo `EmpleadoComisionista` — qué archivos tocas y cuáles no.

## Cómo sabés que lo dominás

¿Podés explicar por qué `Nomina<T>` no necesita saber qué tipo concreto de empleado está sumando, y qué diferencia hay entre eso y usar `List<Object>` con casts?

## Explicación técnica del concepto

El polimorfismo permite que una clase abstracta (`Empleado`) delegue en cada subtipo (`EmpleadoFijo`, `EmpleadoPorHoras`) la implementación de un método común (`calcularSalario()`). El código que usa `Empleado` no necesita conocer el tipo concreto ni usar `instanceof`: cada objeto resuelve su propio comportamiento. Este es el mismo principio que sostiene Clean Architecture y varios patrones GoF, aplicado ahí a nivel de arquitectura en vez de a nivel de clase.

<!-- ENTITLEMENT:1:START -->
## Ejemplo fácil de explicar

Piensa en canales de notificación. La clase abstracta define *qué* se hace; cada subclase define *cómo*:

```java
abstract class Canal { abstract String enviar(String mensaje); }
class Correo extends Canal { String enviar(String m) { return "correo: " + m; } }
class Sms    extends Canal { String enviar(String m) { return "sms: " + m; } }

List<Canal> canales = List.of(new Correo(), new Sms());
canales.forEach(c -> c.enviar("tu flujo fue aprobado"));   // polimorfismo: quien llama no sabe cuál es cuál
```
Cuándo NO: si las "subclases" solo cambian un dato, no necesitas herencia; basta un campo o un enum.

## Cómo lo trabajamos en Entitlement (micros)

Evidencia del código real de los micros (rutas relativas a `Bancolombia/Micros/`). Es lo que hace el equipo; cuenta qué parte hiciste tú y cuál es del equipo.

- **Abstracción + herencia + validación:** `AbstractString` (clase abstracta con constructor protegido que valida) y `RoleCode extends AbstractString`, que solo acepta `TIT, TRE, REP, TAD, TAR`. Micro `Entitlement_Service_MR/ms_actors`, paquete `model/.../value`.
- **Genéricos:** `Command<P, C>(payload, context)` (record) y `DomainEvent<T>` en `ms_actors`; `CommandHandler<T extends CommandBusData>` y `CommandBus.dispatch(T command)` en `Monetary_Limits_MR/ms_limit_create`.
- **Anotaciones propias:** `@UseCase`, `@DomainService`, `@EventHandlerService` (masam_core_ms, ms_authorization_flows) y `@CommandHandlerService` (`ms_limit_create`) — marcadores `@Retention(RUNTIME)` que el registro descubre por reflexión.
- **Polimorfismo:** `ValidateSchemeChannelHandler implements SyncEventHandler<ValidateSchemeChannelInitiatedEvent>` en `Entitlement_MASAM_MR/masam_core_ms`: el bus resuelve el handler por tipo de evento.

**No encontrado en los micros (no lo afirmes como experiencia del proyecto):**

- No hay jerarquías profundas de herencia de negocio: el proyecto favorece composición + interfaces (gateways).

**Cómo contarlo en la entrevista:** Abre con `RoleCode`: "la validación vive en el tipo, no regada por los casos de uso". Luego `CommandHandler<T>` para genéricos y polimorfismo.
<!-- ENTITLEMENT:1:END -->

## Cómo cerré esta brecha (mi implementación)

Ya implementé el modelo completo. `Empleado` es abstracta y deja `calcularSalario()` sin implementar; `EmpleadoFijo` devuelve `salarioBase` directo —sin depender de horas trabajadas— y además implementa `Bonificable` con `calcularBono()` al 10% del salario base; `EmpleadoPorHoras` calcula `horasTrabajadas * tarifaPorHora` y a propósito no implementa `Bonificable`, porque ese tipo de empleado no tiene bono. `Nomina<T extends Empleado>` recibe cualquier subtipo de `Empleado` y solo llama a `calcularSalario()` dentro del loop de `totalAPagar()` — en ningún punto usa `instanceof` ni necesita saber si el empleado es fijo o por horas.

Eso es lo que cierra la brecha de POO: el polimorfismo vive en `calcularSalario()`, cada subtipo resuelve el suyo, y `Nomina<T>` queda genérica y cerrada a cambios. Si mañana agrego `EmpleadoComisionista`, solo creo esa clase —que extienda `Empleado` e implemente `calcularSalario()`— y ni `Nomina` ni el resto del código existente se tocan. Esa es la diferencia real con `List<Object>` y casts: con casts, el código que usa la lista necesita conocer el tipo concreto para poder operar sobre él; con polimorfismo, nunca lo necesita.

## SDD — Spec-Driven Development

Antes de tocar código en este reto, escribí (alcanza con 3-5 líneas, en un comentario o en un README aparte) la especificación de lo que vas a construir: qué clases/métodos necesitás, el contrato de cada uno (entradas, salidas, casos borde) y la regla de negocio que cubre — el "qué" antes del "cómo". Es la misma disciplina que separa TDD (diseñás guiado por tests que escribís vos) de SDD (diseñás guiado por una spec escrita, para vos mismo o para que una IA la ejecute): la decisión de diseño se toma **antes** de escribir la primera línea, no se descubre a medida que tecleás. Encaja directo con el feedback de tus evaluadores: podés usar la IA para redactar o pulir esa spec, pero la decisión de qué debe hacer cada pieza es tuya, no de la IA — spec en mano, después sí generás o escribís el código.

---

---

## Sobre este scaffold (Bancolombia Clean Architecture)

*Esto es la documentación original del scaffold, no del reto — te sirve de referencia para entender la estructura de carpetas que vas a ver en los 19 proyectos.*

### Antes de Iniciar

Empezaremos por explicar los diferentes componentes del proyectos y partiremos de los componentes externos, continuando con los componentes core de negocio (dominio) y por último el inicio y configuración de la aplicación.

Lee el artículo [Clean Architecture — Aislando los detalles](https://medium.com/bancolombia-tech/clean-architecture-aislando-los-detalles-4f9530f35d7a)

### Arquitectura

![Clean Architecture](https://miro.medium.com/max/1400/1*ZdlHz8B0-qu9Y-QO3AXR_w.png)

#### Domain

Es el módulo más interno de la arquitectura, pertenece a la capa del dominio y encapsula la lógica y reglas del negocio mediante modelos y entidades del dominio.

#### Usecases

Este módulo gradle perteneciente a la capa del dominio, implementa los casos de uso del sistema, define lógica de aplicación y reacciona a las invocaciones desde el módulo de entry points, orquestando los flujos hacia el módulo de entities.

#### Infrastructure

##### Helpers

En el apartado de helpers tendremos utilidades generales para los Driven Adapters y Entry Points.

Estas utilidades no están arraigadas a objetos concretos, se realiza el uso de generics para modelar comportamientos
genéricos de los diferentes objetos de persistencia que puedan existir, este tipo de implementaciones se realizan
basadas en el patrón de diseño [Unit of Work y Repository](https://medium.com/@krzychukosobudzki/repository-design-pattern-bc490b256006)

Estas clases no puede existir solas y debe heredarse su compartimiento en los **Driven Adapters**

##### Driven Adapters

Los driven adapter representan implementaciones externas a nuestro sistema, como lo son conexiones a servicios rest,
soap, bases de datos, lectura de archivos planos, y en concreto cualquier origen y fuente de datos con la que debamos
interactuar.

##### Entry Points

Los entry points representan los puntos de entrada de la aplicación o el inicio de los flujos de negocio.

#### Application

Este módulo es el más externo de la arquitectura, es el encargado de ensamblar los distintos módulos, resolver las dependencias y crear los beans de los casos de use (UseCases) de forma automática, inyectando en éstos instancias concretas de las dependencias declaradas. Además inicia la aplicación (es el único módulo del proyecto donde encontraremos la función “public static void main(String[] args)”.

**Los beans de los casos de uso se disponibilizan automaticamente gracias a un '@ComponentScan' ubicado en esta capa.**
