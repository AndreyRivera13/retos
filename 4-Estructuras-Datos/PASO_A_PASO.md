# Reto 4 — Estructuras de datos

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

Dado un `List<String> transacciones` con formato `"cliente:monto"` (ej: `"ana:500"`, `"luis:300"`, `"ana:200"`), calculá el total por cliente en un `Map<String, Double>`, encontrá el cliente con mayor total, y devolvé la lista de clientes ordenada de mayor a menor total. Hacelo sin `Collectors.groupingBy` (a mano, con `getOrDefault`/`merge` y `Comparator`).

## Qué debés entregar

Código + tiempo estimado de tu solución en notación Big O para n transacciones.

## Cómo sabés que lo dominás

¿Podés explicar por qué usaste `Map` y no simplemente recorrer la lista dos veces con `List`, y qué pasaría con el rendimiento si fueran 10 millones de transacciones?


---

*Enunciado completo, entrega esperada y ejemplo de la técnica en un dominio distinto: `Retos_Assessment_Andrey.md` en la raíz de `retos/`. No copies el ejemplo — el dominio es distinto a propósito, para que entiendas la técnica y no el código.*
