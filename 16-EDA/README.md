# Reto 16 — EDA — Arquitectura orientada a eventos (Kafka/RabbitMQ/etc)

**Nivel que evalúa:** Senior

**Prioridad con tu evaluador (Rudyard):** 🟡 Media

**Estado:** 🔲 Sin empezar

## Para qué te sirve este reto

Cierra el concepto de desacoplar servicios con eventos en vez de llamadas directas, y la idempotencia que eso obliga (un mensaje puede llegar duplicado). Si tus integraciones hoy son llamada síncrona directa, este reto te mueve al extremo opuesto y te hace resolver el problema que aparece ahí: qué pasa si el mismo evento llega dos veces.

## Cómo trabajar este reto (paso a paso)

1. **Abrí este proyecto en tu IDE.** Es un Gradle independiente, con su propio `gradlew` — se compila y corre desde esta misma carpeta (`./gradlew build`).
2. **Buscá los `// TODO`** en las clases de este módulo. Ahí está la pista, nunca la solución. Todo lo que NO tiene `// TODO` (DTOs simples, `MainApplication`, config) ya está completo a propósito — es plomería de framework, no lo que te están evaluando.
3. **No mires el "Ejemplo (dominio distinto)"** de este reto en `Retos_Assessment_Andrey.md` todavía. Intentá primero tu propia solución desde cero, como si fuera la prueba real.
4. **Implementá.** Corré `./gradlew test` (o `./gradlew build`) para validar que compila y que tus pruebas (si el reto pide alguna) pasan.
5. **Autoevaluate antes de dar el reto por cerrado:** respondé en menos de 2 minutos, en voz alta o por escrito, la pregunta de "¿Cómo sabés que lo dominás?" de abajo. Si te cuesta responderla más que escribir el código, el hueco está en el concepto, no en la implementación — volvé a la guía de estudio antes de seguir.
6. **Marcá este reto como ✅ en `README_RETOS.md`** (en la raíz de `retos/`) y pasá al siguiente según el orden sugerido ahí.

---

## Enunciado

Cuando se reserva una cita (reto 5/11), publicá un evento `CitaReservada`. Creá un consumidor separado que, al recibirlo, simule el envío de un recordatorio — y hacelo idempotente: si el mismo evento (mismo id) llega dos veces, el recordatorio no se duplica (usá un `Set` de ids ya procesados, aunque sea en memoria para el ejercicio).

## Qué debés entregar

Productor + consumidor corriendo por separado + prueba de que mandar el mismo evento dos veces no duplica el efecto.

## Cómo sabés que lo dominás

¿Podés explicar qué semántica de entrega asumiste (at-least-once) y por qué sin la verificación de idempotencia tu sistema tendría un bug real en producción?

## SDD — Spec-Driven Development

Antes de tocar código en este reto, escribí (alcanza con 3-5 líneas, en un comentario o en un README aparte) la especificación de lo que vas a construir: qué clases/métodos necesitás, el contrato de cada uno (entradas, salidas, casos borde) y la regla de negocio que cubre — el "qué" antes del "cómo". Es la misma disciplina que separa TDD (diseñás guiado por tests que escribís vos) de SDD (diseñás guiado por una spec escrita, para vos mismo o para que una IA la ejecute): la decisión de diseño se toma **antes** de escribir la primera línea, no se descubre a medida que tecleás. Encaja directo con el feedback de tus evaluadores: podés usar la IA para redactar o pulir esa spec, pero la decisión de qué debe hacer cada pieza es tuya, no de la IA — spec en mano, después sí generás o escribís el código.

---


*Enunciado completo, entrega esperada y ejemplo de la técnica en un dominio distinto: `Retos_Assessment_Andrey.md` en la raíz de `retos/`. No copies el ejemplo — el dominio es distinto a propósito, para que entiendas la técnica y no el código.*
