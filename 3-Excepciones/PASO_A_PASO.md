# Reto 3 — Excepciones y depuración

**Prioridad con tu evaluador (Rudyard):** 🟢 Baja

## Cómo trabajar este reto (paso a paso)

1. **Abrí este proyecto en tu IDE.** Es un Gradle independiente, con su propio `gradlew` — se compila y corre desde esta misma carpeta (`./gradlew build`).
2. **Buscá los `// TODO`** en las clases de este módulo. Ahí está la pista, nunca la solución. Todo lo que NO tiene `// TODO` (DTOs simples, `MainApplication`, config) ya está completo a propósito — es plomería de framework, no lo que te están evaluando.
3. **No mires el "Ejemplo (dominio distinto)"** de este reto en `Retos_Assessment_Andrey.md` todavía. Intentá primero tu propia solución desde cero, como si fuera la prueba real.
4. **Implementá.** Corré `./gradlew test` (o `./gradlew build`) para validar que compila y que tus pruebas (si el reto pide alguna) pasan.
5. **Autoevaluate antes de dar el reto por cerrado:** respondé en menos de 2 minutos, en voz alta o por escrito, la pregunta de "¿Cómo sabés que lo dominás?" de abajo. Si te cuesta responderla más que escribir el código, el hueco está en el concepto, no en la implementación — volvé a la guía de estudio antes de seguir.
6. **Marcá este reto como ✅ en `README_RETOS.md`** (en la raíz de `retos/`) y pasá al siguiente según el orden sugerido ahí.

---

## Enunciado

Implementá `ProcesadorPagos.procesar(Pago pago)` que lance `SaldoInsuficienteException` (checked) si el monto excede el saldo disponible, y `PagoInvalidoException` (checked) si el monto es ≤ 0 — ambas con constructor que acepte causa encadenada. El método debe usar `try-with-resources` para "cerrar" un recurso simulado (`RegistroTransaccion implements AutoCloseable`). Escribí 3 pruebas: pago válido, saldo insuficiente, monto inválido.

## Qué debés entregar

Código + pruebas JUnit pasando.

## Cómo sabés que lo dominás

¿Podés explicar, sin ver el código, en qué orden se ejecutan las cosas si `procesar()` lanza una excepción dentro del bloque `try-with-resources` — se cierra igual el recurso?


---

*Enunciado completo, entrega esperada y ejemplo de la técnica en un dominio distinto: `Retos_Assessment_Andrey.md` en la raíz de `retos/`. No copies el ejemplo — el dominio es distinto a propósito, para que entiendas la técnica y no el código.*
