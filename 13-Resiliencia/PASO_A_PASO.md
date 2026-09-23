# Reto 13 — Resiliencia (mínimo 2: CircuitBreaker, Retry, Fallback, RateLimit, Bulkhead)

**Prioridad con tu evaluador (Rudyard):** 🟡 Media

## Cómo trabajar este reto (paso a paso)

1. **Abrí este proyecto en tu IDE.** Es un Gradle independiente, con su propio `gradlew` — se compila y corre desde esta misma carpeta (`./gradlew build`).
2. **Buscá los `// TODO`** en las clases de este módulo. Ahí está la pista, nunca la solución. Todo lo que NO tiene `// TODO` (DTOs simples, `MainApplication`, config) ya está completo a propósito — es plomería de framework, no lo que te están evaluando.
3. **No mires el "Ejemplo (dominio distinto)"** de este reto en `Retos_Assessment_Andrey.md` todavía. Intentá primero tu propia solución desde cero, como si fuera la prueba real.
4. **Implementá.** Corré `./gradlew test` (o `./gradlew build`) para validar que compila y que tus pruebas (si el reto pide alguna) pasan.
5. **Autoevaluate antes de dar el reto por cerrado:** respondé en menos de 2 minutos, en voz alta o por escrito, la pregunta de "¿Cómo sabés que lo dominás?" de abajo. Si te cuesta responderla más que escribir el código, el hueco está en el concepto, no en la implementación — volvé a la guía de estudio antes de seguir.
6. **Marcá este reto como ✅ en `README_RETOS.md`** (en la raíz de `retos/`) y pasá al siguiente según el orden sugerido ahí.

---

## Enunciado

Simulá una pasarela de pagos externa que falla 40% de las veces. Envolvela con Resilience4j: `@Retry` (3 intentos, backoff exponencial), `@CircuitBreaker` (se abre con >50% de fallos en ventana de 10), `@Bulkhead` (máximo 5 llamadas concurrentes), y `@Fallback` que responda "pago en proceso, se confirmará luego" en vez de error crudo.

## Qué debés entregar

Código + log de una corrida donde se vea el circuito pasando por los 3 estados.

## Cómo sabés que lo dominás

¿Podés explicar por qué combinaste Bulkhead con CircuitBreaker en vez de solo uno de los dos — qué problema distinto resuelve cada uno en este caso?


---

*Enunciado completo, entrega esperada y ejemplo de la técnica en un dominio distinto: `Retos_Assessment_Andrey.md` en la raíz de `retos/`. No copies el ejemplo — el dominio es distinto a propósito, para que entiendas la técnica y no el código.*
