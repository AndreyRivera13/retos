# Reto 1 — POO

**Prioridad con tu evaluador (Rudyard):** ✅ Ya lo hiciste

## Cómo trabajar este reto (paso a paso)

1. **Abrí este proyecto en tu IDE.** Es un Gradle independiente, con su propio `gradlew` — se compila y corre desde esta misma carpeta (`./gradlew build`).
2. **Buscá los `// TODO`** en las clases de este módulo. Ahí está la pista, nunca la solución. Todo lo que NO tiene `// TODO` (DTOs simples, `MainApplication`, config) ya está completo a propósito — es plomería de framework, no lo que te están evaluando.
3. **No mires el "Ejemplo (dominio distinto)"** de este reto en `Retos_Assessment_Andrey.md` todavía. Intentá primero tu propia solución desde cero, como si fuera la prueba real.
4. **Implementá.** Corré `./gradlew test` (o `./gradlew build`) para validar que compila y que tus pruebas (si el reto pide alguna) pasan.
5. **Autoevaluate antes de dar el reto por cerrado:** respondé en menos de 2 minutos, en voz alta o por escrito, la pregunta de "¿Cómo sabés que lo dominás?" de abajo. Si te cuesta responderla más que escribir el código, el hueco está en el concepto, no en la implementación — volvé a la guía de estudio antes de seguir.
6. **Marcá este reto como ✅ en `README_RETOS.md`** (en la raíz de `retos/`) y pasá al siguiente según el orden sugerido ahí.

---

## Enunciado

Modela un sistema de nómina con `Empleado` (clase abstracta) y dos tipos, `EmpleadoFijo` (salario fijo) y `EmpleadoPorHoras` (salario = horas × tarifa), cada uno con su propia lógica de `calcularSalario()`. Agrega una interfaz `Bonificable { double calcularBono(); }` que solo implementa `EmpleadoFijo`. Crea una clase genérica `Nomina<T extends Empleado>` que reciba una lista de empleados y calcule el total a pagar sin usar `instanceof` en ningún punto.

## Qué debés entregar

Código + un párrafo explicando qué pasaría si agregas un tercer tipo `EmpleadoComisionista` — qué archivos tocas y cuáles no.

## Cómo sabés que lo dominás

¿Podés explicar por qué `Nomina<T>` no necesita saber qué tipo concreto de empleado está sumando, y qué diferencia hay entre eso y usar `List<Object>` con casts?


---

*Enunciado completo, entrega esperada y ejemplo de la técnica en un dominio distinto: `Retos_Assessment_Andrey.md` en la raíz de `retos/`. No copies el ejemplo — el dominio es distinto a propósito, para que entiendas la técnica y no el código.*
