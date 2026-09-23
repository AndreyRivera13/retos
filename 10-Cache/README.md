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
