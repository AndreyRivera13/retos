# Reto 11 — Clean Architecture (Cebolla / Hexagonal)

**Nivel que evalúa:** Senior

**Estado:** 🔲 Sin empezar

## Para qué te sirve este reto

Cierra el concepto de independencia del dominio frente a la infraestructura: el dominio no sabe que existe Spring, JPA o una base de datos concreta. Es la arquitectura que ya usás en Entitlement — acá te toca explicar por qué domain no depende de infraestructura, no solo copiar la carpeta.

## Enunciado

Tomá el sistema de citas (reto 5) y convertilo a hexagonal: dominio puro, puerto de entrada (`GestionarCitasUseCase`), puerto de salida (`CitaRepositoryPort`), y dos adaptadores de salida intercambiables (memoria y un esqueleto JPA).

## Qué debés entregar

Código organizado en paquetes `dominio`/`aplicacion`/`infraestructura` + la prueba de que si borrás `infraestructura`, el resto sigue compilando.

## Cómo sabés que lo dominás

¿Podés explicar exactamente qué se rompería (y por qué) si pusieras una anotación `@Entity` directamente en la clase de dominio `Cita`?

## Explicación técnica del concepto

La regla de dependencia de Clean Architecture establece que las capas internas (dominio) no pueden depender de las externas (infraestructura) — la dependencia siempre apunta hacia adentro. Esto se logra con puertos (interfaces definidas por el dominio) y adaptadores (implementaciones concretas en infraestructura). La prueba práctica de que se cumple es que el módulo de dominio debe seguir compilando aunque se elimine el módulo de infraestructura.

## Cómo cerré esta brecha (mi implementación)

*Completo esto yo mismo cuando termine el reto — no antes. Con mi código real ya escrito, respondo acá (no sobre el enunciado, sobre mi implementación):*

- *¿Qué clases/métodos concretos escribí y qué responsabilidad tiene cada uno?*
- *¿Cómo mi código, específicamente, resuelve el concepto de este reto? Cito mis propias clases y métodos, no la teoría.*
- *¿Qué bug o mal entendido tuve en el camino, y cómo lo corregí? (revisar esto antes de la entrevista me sirve más que repasar la teoría de nuevo).*

## 🎯 Con tu evaluador

Lideró "reestructuración de arquitectura hacia microservicios" en dos empresas — este es territorio donde él ya se equivocó y aprendió, así que va a notar respuestas de manual. Prepárate para "¿qué pasó en tu experiencia real cuando una regla de negocio terminó acoplada a un detalle de infraestructura?" — si no tenés un ejemplo propio de Entitlement, este es el reto donde más te conviene tenerlo.

## SDD — Spec-Driven Development

Antes de tocar código en este reto, escribí (alcanza con 3-5 líneas, en un comentario o en un README aparte) la especificación de lo que vas a construir: qué clases/métodos necesitás, el contrato de cada uno (entradas, salidas, casos borde) y la regla de negocio que cubre — el "qué" antes del "cómo". Es la misma disciplina que separa TDD (diseñás guiado por tests que escribís vos) de SDD (diseñás guiado por una spec escrita, para vos mismo o para que una IA la ejecute): la decisión de diseño se toma **antes** de escribir la primera línea, no se descubre a medida que tecleás. Encaja directo con el feedback de tus evaluadores: podés usar la IA para redactar o pulir esa spec, pero la decisión de qué debe hacer cada pieza es tuya, no de la IA — spec en mano, después sí generás o escribís el código.

---
