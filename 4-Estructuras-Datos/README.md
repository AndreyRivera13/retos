# Reto 4 — Estructuras de datos

**Nivel que evalúa:** Trainer

**Estado:** 🟡 En revisión — `AnalizadorTransacciones` implementado y funciona, pero usa `Collectors.toMap(...)` para agrupar, y el enunciado pedía explícitamente hacerlo a mano (sin Collector), con `getOrDefault`/`merge`. Pendiente de tu respuesta antes de cerrar. Verificado 2026-09-29.

## Para qué te sirve este reto

Cierra el concepto de elegir la estructura de datos según lo que necesitás hacer con los datos, no por costumbre — `Map` para buscar por clave en O(1), `List` para recorrer en orden. Es la misma decisión que tomás cuando filtrás o buscás permisos por documento en Entitlement: `Map` indexado vs recorrer una `List` con `.filter()`.

## Enunciado

Dado un `List<String> transacciones` con formato `"cliente:monto"` (ej: `"ana:500"`, `"luis:300"`, `"ana:200"`), calculá el total por cliente en un `Map<String, Double>`, encontrá el cliente con mayor total, y devolvé la lista de clientes ordenada de mayor a menor total. Hacelo sin `Collectors.groupingBy` (a mano, con `getOrDefault`/`merge` y `Comparator`).

## Qué debés entregar

Código + tiempo estimado de tu solución en notación Big O para n transacciones.

## Cómo sabés que lo dominás

¿Podés explicar por qué usaste `Map` y no simplemente recorrer la lista dos veces con `List`, y qué pasaría con el rendimiento si fueran 10 millones de transacciones?

## Explicación técnica del concepto

La elección de estructura de datos depende de la operación dominante: `Map` da acceso por clave en O(1) amortizado, mientras que buscar en una `List` recorriéndola es O(n). Agrupar por cliente con `merge()`/`getOrDefault()` sobre un `Map` evita procesar la lista más de una vez por cada búsqueda, evitando la complejidad cuadrática que aparecería si se buscara el cliente recorriendo la lista en cada iteración.

<!-- ENTITLEMENT:4:START -->
## Ejemplo fácil de explicar

Buscar un permiso en una lista de 10.000 es recorrerla; en un `Set` es un salto directo:

```java
List<String> permisos = ...;      permisos.contains("PAGAR");   // O(n)
Set<String>  permisos = new HashSet<>(...); permisos.contains("PAGAR"); // O(1) promedio
Map<String, List<Permiso>> porRol = permisos.stream().collect(groupingBy(Permiso::rol)); // agrupar sin doble for
```
Cuándo NO: con 5 elementos la diferencia es invisible; legibilidad primero.

## Cómo lo trabajamos en Entitlement (micros)

Evidencia del código real de los micros (rutas relativas a `Bancolombia/Micros/`). Es lo que hace el equipo; cuenta qué parte hiciste tú y cuál es del equipo.

- **`Map` como catálogo cargado al arranque:** `StateConfig` y `PrivilegeConfig` (`ms_admin_authorization`, `r2dbc-postgresql/.../config`) hacen `collect(Collectors.toMap(StateData::getNameState, d -> d))` una sola vez. Ojo: usan `.block()`, aceptable solo en el arranque.
- **`Set` para pertenencia O(1):** `ValidateActorRoleCapabilitiesUseCase` (`Set` de claves `id|tipo`), `RoleCode` (`Set.of("TIT","TRE","REP","TAD","TAR")`) y `PermissionsUseCase` (`Set.of(...)` de privilegios válidos).
- **`toMap` con merge function** para deduplicar: `ValidateProductGroupUseCase` (`(first, dup) -> first`), reemplazando un `findFirst` lineal.
- **`groupingBy` en lógica real:** `OwnerFlowUseCase` (`Monetary_Limits_MR/ms_limit_orchestration_services`) agrupa `ChannelLimits` por `getClientLimitsId` y hace el "join en memoria" con `containsKey/get`; también `ThirdCustomerFlowUseCase`, `UpdateRoleByFeatureLogUseCase`.
- **Concurrencia:** `ConcurrentHashMap` en `CurrenciesAdapter` (`ms_limit_create`) y un caché de `Mono` en `RetrieveObjectRoleUseCase` (`ms_limit_clone_processor`). `TreeMap` en `ReverseReason`/`CurrencyValues`, `LinkedHashMap` en `FunctionalLogPayload`.

**No encontrado en los micros (no lo afirmes como experiencia del proyecto):**

- `PriorityQueue`, `Deque` y `EnumMap` no aparecen en los micros.

**Cómo contarlo en la entrevista:** La historia más fuerte: el refactor de `ValidateActorRoleCapabilitiesUseCase` de lista a `Set` y por qué (Big O).
<!-- ENTITLEMENT:4:END -->

## Cómo cerré esta brecha (mi implementación)

*Completo esto yo mismo cuando termine el reto — no antes. Con mi código real ya escrito, respondo acá (no sobre el enunciado, sobre mi implementación):*

- *¿Qué clases/métodos concretos escribí y qué responsabilidad tiene cada uno?*
- *¿Cómo mi código, específicamente, resuelve el concepto de este reto? Cito mis propias clases y métodos, no la teoría.*
- *¿Qué bug o mal entendido tuve en el camino, y cómo lo corregí? (revisar esto antes de la entrevista me sirve más que repasar la teoría de nuevo).*

## SDD — Spec-Driven Development

Antes de tocar código en este reto, escribí (alcanza con 3-5 líneas, en un comentario o en un README aparte) la especificación de lo que vas a construir: qué clases/métodos necesitás, el contrato de cada uno (entradas, salidas, casos borde) y la regla de negocio que cubre — el "qué" antes del "cómo". Es la misma disciplina que separa TDD (diseñás guiado por tests que escribís vos) de SDD (diseñás guiado por una spec escrita, para vos mismo o para que una IA la ejecute): la decisión de diseño se toma **antes** de escribir la primera línea, no se descubre a medida que tecleás. Encaja directo con el feedback de tus evaluadores: podés usar la IA para redactar o pulir esa spec, pero la decisión de qué debe hacer cada pieza es tuya, no de la IA — spec en mano, después sí generás o escribís el código.

---
