# Reto 10 — Caché — Redis/Memcached y estrategias

**Nivel que evalúa:** Advanced

**Prioridad con tu evaluador (Rudyard):** 🔴 Alta

**Estado:** 🔲 Sin empezar

## Para qué te sirve este reto

Cierra el concepto de estrategias de caché e invalidación: cuándo el cache se llena en la lectura (Cache-Aside) y cuándo se llena en la escritura (Write-Through), y qué pasa si el dato de origen cambia sin pasar por tu método. Ya lo tenés cerca en Entitlement con la invalidación de permisos — acá lo formalizás con las 4 estrategias, no solo la que ya usás.

## Cómo trabajar este reto (paso a paso)

1. **Abrí este proyecto en tu IDE.** Es un Gradle independiente, con su propio `gradlew` — se compila y corre desde esta misma carpeta (`./gradlew build`).
2. **Buscá los `// TODO`** en las clases de este módulo. Ahí está la pista, nunca la solución. Todo lo que NO tiene `// TODO` (DTOs simples, `MainApplication`, config) ya está completo a propósito — es plomería de framework, no lo que te están evaluando.
3. **No mires el "Ejemplo (dominio distinto)"** de este reto en `Retos_Assessment_Andrey.md` todavía. Intentá primero tu propia solución desde cero, como si fuera la prueba real.
4. **Implementá.** Corré `./gradlew test` (o `./gradlew build`) para validar que compila y que tus pruebas (si el reto pide alguna) pasan.
5. **Autoevaluate antes de dar el reto por cerrado:** respondé en menos de 2 minutos, en voz alta o por escrito, la pregunta de "¿Cómo sabés que lo dominás?" de abajo. Si te cuesta responderla más que escribir el código, el hueco está en el concepto, no en la implementación — volvé a la guía de estudio antes de seguir.
6. **Marcá este reto como ✅ en `README_RETOS.md`** (en la raíz de `retos/`) y pasá al siguiente según el orden sugerido ahí.

---

## Enunciado

Implementá Cache-Aside completo (lectura con miss/hit, invalidación en escritura) para un catálogo de precios, y agregale TTL: cada entrada en cache expira sola a los 30 segundos aunque nadie la invalide. Luego, sin código, diseñá por escrito cómo se vería el mismo catálogo con Write-Through (qué cambia en el método de escritura) y en qué escenario de tu proyecto real usarías cada estrategia.

## Qué debés entregar

Código + diseño escrito de Write-Through.

## Cómo sabés que lo dominás

¿Podés explicar qué problema resuelve el TTL que la invalidación manual no resuelve? (pista: cuando el dato cambia en el origen sin pasar por tu método `actualizar()`, ej. otro servicio escribe directo a la BD).

## 🎯 Con tu evaluador (Rudyard)

Redis está listado explícito en su stack (Mercania). Este es de los temas donde más "contame un caso real" te va a hacer — tené lista una respuesta de cuándo VOS (en Entitlement) usarías Cache-Aside vs Write-Through, no solo la definición de libro.

## SDD — Spec-Driven Development

Antes de tocar código en este reto, escribí (alcanza con 3-5 líneas, en un comentario o en un README aparte) la especificación de lo que vas a construir: qué clases/métodos necesitás, el contrato de cada uno (entradas, salidas, casos borde) y la regla de negocio que cubre — el "qué" antes del "cómo". Es la misma disciplina que separa TDD (diseñás guiado por tests que escribís vos) de SDD (diseñás guiado por una spec escrita, para vos mismo o para que una IA la ejecute): la decisión de diseño se toma **antes** de escribir la primera línea, no se descubre a medida que tecleás. Encaja directo con el feedback de tus evaluadores: podés usar la IA para redactar o pulir esa spec, pero la decisión de qué debe hacer cada pieza es tuya, no de la IA — spec en mano, después sí generás o escribís el código.

---


*Enunciado completo, entrega esperada y ejemplo de la técnica en un dominio distinto: `Retos_Assessment_Andrey.md` en la raíz de `retos/`. No copies el ejemplo — el dominio es distinto a propósito, para que entiendas la técnica y no el código.*
