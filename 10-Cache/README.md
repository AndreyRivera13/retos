# Reto 10 — Caché — Redis/Memcached y estrategias

**Nivel que evalúa:** Advanced

**Estado:** 🔲 Sin empezar

## Para qué te sirve este reto

Cierra el concepto de estrategias de caché e invalidación: cuándo el cache se llena en la lectura (Cache-Aside) y cuándo se llena en la escritura (Write-Through), y qué pasa si el dato de origen cambia sin pasar por tu método. Ya lo tenés cerca en Entitlement con la invalidación de permisos — acá lo formalizás con las 4 estrategias, no solo la que ya usás.

## Enunciado

Implementá Cache-Aside completo (lectura con miss/hit, invalidación en escritura) para un catálogo de precios, y agregale TTL: cada entrada en cache expira sola a los 30 segundos aunque nadie la invalide. Luego, sin código, diseñá por escrito cómo se vería el mismo catálogo con Write-Through (qué cambia en el método de escritura) y en qué escenario de tu proyecto real usarías cada estrategia.

## Qué debés entregar

Código + diseño escrito de Write-Through.

## Cómo sabés que lo dominás

¿Podés explicar qué problema resuelve el TTL que la invalidación manual no resuelve? (pista: cuando el dato cambia en el origen sin pasar por tu método `actualizar()`, ej. otro servicio escribe directo a la BD).

## Explicación técnica del concepto

Cache-Aside deja que la aplicación gestione el caché: lee primero de ahí, y ante un miss consulta la fuente y guarda el resultado. Write-Through mueve esa responsabilidad al momento de escribir: cada escritura actualiza caché y fuente en el mismo paso. El TTL resuelve un problema que la invalidación manual no cubre: cuando el dato cambia en el origen sin pasar por el método de escritura de la aplicación (otro servicio escribe directo a la base, por ejemplo), la entrada en caché queda desactualizada indefinidamente si no expira sola.

<!-- ENTITLEMENT:10:START -->
## Ejemplo fácil de explicar

La libreta de teléfonos pegada en la nevera: antes de buscar en internet (la BD) miras la libreta (el caché). Si no está, buscas y lo anotas con fecha de vencimiento (TTL).

```java
return cache.get(id)                                   // cache-aside
    .switchIfEmpty(Mono.defer(() -> fuente.consultar(id)
        .flatMap(v -> cache.guardar(id, v, Duration.ofMinutes(10)).thenReturn(v))));
```
Cuándo NO: datos que cambian en cada petición o que exigen consistencia fuerte al instante.

## Cómo lo trabajamos en Entitlement (micros)

Evidencia del código real de los micros (rutas relativas a `Bancolombia/Micros/`). Es lo que hace el equipo; cuenta qué parte hiciste tú y cuál es del equipo.

- **Redis reactivo con TTL:** `ClientInformationCacheAdapter` (`Entitlement_Service_MR/client_parameters_ms`, módulo `redis`): `ReactiveRedisTemplate.opsForValue().set(id, valor, Duration.ofSeconds(expirationTime))`, TTL por `adapter.redis.expirationTime`. Misma estructura en `products_information_ms` (`PermissionCacheAdapter`), `ms_actors` (`BasicInformationCacheAdapter`) y `operational_transaction_management_ms` (`SaveConsumerCacheAdapter`/`GetConsumerCacheAdapter`).
- **Cache-Aside manual:** `RetrieveClientInformationAdapter.getOwnerBasicInformationFromCache`: lee Redis, `switchIfEmpty` llama al servicio MDM y puebla el caché en segundo plano.
- **Caché local L1 con Caffeine:** `CacheCaffeineConfig` (`expireAfterWrite`, `maximumSize`, `recordStats`), usado por `QueryRoleBySchemeGatewayAdapter` / `SaveTemporalRoleBySchemeGatewayAdapter`. Resultado: caché de dos niveles (Caffeine → Redis → fuente).
- **Redis con réplica de lectura:** `RedisConfig` usa `ReadFrom.REPLICA_PREFERRED`; credenciales desde Secrets Manager.
- **Elixir:** `ms_retrieve_role/.../permission_cache_read.ex` cachea permisos con prefijo `PER`.

**No encontrado en los micros (no lo afirmes como experiencia del proyecto):**

- Read-Through, Write-Through y Write-Behind no aparecen (no hay `@Cacheable` tampoco): el proyecto usa Cache-Aside. Las otras tres son teoría + reto 10.

**Cómo contarlo en la entrevista:** Cache-Aside en dos niveles, con TTL por configuración. Prepárate para "¿qué pasa si el rol cambia mientras está en caché?" (invalidación/TTL).
<!-- ENTITLEMENT:10:END -->

## Cómo cerré esta brecha (mi implementación)

*Completo esto yo mismo cuando termine el reto — no antes. Con mi código real ya escrito, respondo acá (no sobre el enunciado, sobre mi implementación):*

- *¿Qué clases/métodos concretos escribí y qué responsabilidad tiene cada uno?*
- *¿Cómo mi código, específicamente, resuelve el concepto de este reto? Cito mis propias clases y métodos, no la teoría.*
- *¿Qué bug o mal entendido tuve en el camino, y cómo lo corregí? (revisar esto antes de la entrevista me sirve más que repasar la teoría de nuevo).*

## 🎯 Con tu evaluador

Redis está listado explícito en su stack (Mercania). Este es de los temas donde más "contame un caso real" te va a hacer — tené lista una respuesta de cuándo VOS (en Entitlement) usarías Cache-Aside vs Write-Through, no solo la definición de libro.

## SDD — Spec-Driven Development

Antes de tocar código en este reto, escribí (alcanza con 3-5 líneas, en un comentario o en un README aparte) la especificación de lo que vas a construir: qué clases/métodos necesitás, el contrato de cada uno (entradas, salidas, casos borde) y la regla de negocio que cubre — el "qué" antes del "cómo". Es la misma disciplina que separa TDD (diseñás guiado por tests que escribís vos) de SDD (diseñás guiado por una spec escrita, para vos mismo o para que una IA la ejecute): la decisión de diseño se toma **antes** de escribir la primera línea, no se descubre a medida que tecleás. Encaja directo con el feedback de tus evaluadores: podés usar la IA para redactar o pulir esa spec, pero la decisión de qué debe hacer cada pieza es tuya, no de la IA — spec en mano, después sí generás o escribís el código.

---
