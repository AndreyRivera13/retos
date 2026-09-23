# Reto 7 — Normalización BD / modelo E-R

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

Normalizá hasta 3FN esta tabla: `Matriculas(id_matricula, estudiante_nombre, estudiante_documento, curso1_nombre, curso1_profesor, curso2_nombre, curso2_profesor)`.

## Qué debés entregar

Tablas finales con PK/FK marcadas + una frase por cada paso (1FN, 2FN, 3FN) diciendo qué dependencia resolviste.

## Cómo sabés que lo dominás

¿Podés explicar qué problema de inconsistencia real evitás al separar `curso_profesor` en su propia tabla? (pista: ¿qué pasa si el mismo profesor aparece con dos nombres distintos en filas distintas antes de normalizar?)


---

*Enunciado completo, entrega esperada y ejemplo de la técnica en un dominio distinto: `Retos_Assessment_Andrey.md` en la raíz de `retos/`. No copies el ejemplo — el dominio es distinto a propósito, para que entiendas la técnica y no el código.*
