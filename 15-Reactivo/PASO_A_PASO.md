# Reto 15 — Paradigmas reactivo/funcional

**Prioridad con tu evaluador (Rudyard):** 🔴🔴 La más alta de todas

## Cómo trabajar este reto (paso a paso)

1. **Abrí este proyecto en tu IDE.** Es un Gradle independiente, con su propio `gradlew` — se compila y corre desde esta misma carpeta (`./gradlew build`).
2. **Buscá los `// TODO`** en las clases de este módulo. Ahí está la pista, nunca la solución. Todo lo que NO tiene `// TODO` (DTOs simples, `MainApplication`, config) ya está completo a propósito — es plomería de framework, no lo que te están evaluando.
3. **No mires el "Ejemplo (dominio distinto)"** de este reto en `Retos_Assessment_Andrey.md` todavía. Intentá primero tu propia solución desde cero, como si fuera la prueba real.
4. **Implementá.** Corré `./gradlew test` (o `./gradlew build`) para validar que compila y que tus pruebas (si el reto pide alguna) pasan.
5. **Autoevaluate antes de dar el reto por cerrado:** respondé en menos de 2 minutos, en voz alta o por escrito, la pregunta de "¿Cómo sabés que lo dominás?" de abajo. Si te cuesta responderla más que escribir el código, el hueco está en el concepto, no en la implementación — volvé a la guía de estudio antes de seguir.
6. **Marcá este reto como ✅ en `README_RETOS.md`** (en la raíz de `retos/`) y pasá al siguiente según el orden sugerido ahí.

---

## Enunciado

Convertí `GestionarCitasUseCase` a reactivo: `Mono<Cita> reservar(...)`, `Flux<Cita> citasDelDia(LocalDate fecha)`. Agregá un operador que, si `citasDelDia` no emite nada en 2 segundos (simulando latencia), devuelva un valor por defecto (`Mono.empty()` transformado con `.switchIfEmpty` o `.timeout` + fallback).

## Qué debés entregar

Código + explicación de qué pasa con la suscripción si nadie llama `.subscribe()`.

## Cómo sabés que lo dominás

¿Podés explicar la diferencia entre que tu método retorne `Mono<Cita>` vacío por diseño vs que lance una excepción, y cuándo usarías cada uno?

## 🎯 Con tu evaluador (Rudyard)

Este es EL tema donde tiene más autoridad de todo tu assessment — lideró personalmente la migración de POO a programación funcional con Spring WebFlux. No te va a preguntar "qué es un Mono" — te va a preguntar por los dolores reales de esa migración: qué se vuelve difícil de leer/debuggear en reactivo, y cómo decidiste (o decidirías) qué sí migrar a reactivo y qué no. Tené una opinión propia, no solo la teoría.


---

*Enunciado completo, entrega esperada y ejemplo de la técnica en un dominio distinto: `Retos_Assessment_Andrey.md` en la raíz de `retos/`. No copies el ejemplo — el dominio es distinto a propósito, para que entiendas la técnica y no el código.*
