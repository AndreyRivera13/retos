# Reto 2 — Código limpio (SOLID/KISS/YAGNI/DRY)

**Prioridad con tu evaluador (Rudyard):** 🔴 Alta

## Cómo trabajar este reto (paso a paso)

1. **Abrí este proyecto en tu IDE.** Es un Gradle independiente, con su propio `gradlew` — se compila y corre desde esta misma carpeta (`./gradlew build`).
2. **Buscá los `// TODO`** en las clases de este módulo. Ahí está la pista, nunca la solución. Todo lo que NO tiene `// TODO` (DTOs simples, `MainApplication`, config) ya está completo a propósito — es plomería de framework, no lo que te están evaluando.
3. **No mires el "Ejemplo (dominio distinto)"** de este reto en `Retos_Assessment_Andrey.md` todavía. Intentá primero tu propia solución desde cero, como si fuera la prueba real.
4. **Implementá.** Corré `./gradlew test` (o `./gradlew build`) para validar que compila y que tus pruebas (si el reto pide alguna) pasan.
5. **Autoevaluate antes de dar el reto por cerrado:** respondé en menos de 2 minutos, en voz alta o por escrito, la pregunta de "¿Cómo sabés que lo dominás?" de abajo. Si te cuesta responderla más que escribir el código, el hueco está en el concepto, no en la implementación — volvé a la guía de estudio antes de seguir.
6. **Marcá este reto como ✅ en `README_RETOS.md`** (en la raíz de `retos/`) y pasá al siguiente según el orden sugerido ahí.

---

## Enunciado

Tenés (o escribís) una clase `ValidadorSolicitud` con un método de 40 líneas que valida un formulario de crédito: revisa edad, ingresos, historial, y según el resultado arma un mensaje, lo loguea, y lo envía por email — todo ahí mismo, con `if/else` anidados. Refactorizala en al menos 3 clases nuevas aplicando SRP y OCP (usá una lista de `ReglaValidacion` como Strategy en vez de `if/else`).

## Qué debés entregar

Código antes/después (aunque sea el "antes" resumido) + tabla de 3 columnas: principio aplicado, qué cambiaste, qué se rompería si no lo hubieras hecho.

## Cómo sabés que lo dominás

¿Podés agregar una regla de validación nueva creando solo una clase, sin tocar ninguna de las existentes?

## 🎯 Con tu evaluador (Rudyard)

SOLID + Patrones de Diseño está en el stack técnico de sus dos roles como Technical Leader — no lo va a dejar en "sabe qué significa la S de SRP". Prepárate para que te pida nombrar, EN TU CÓDIGO, cuál principio rompiste primero (antes de refactorizar) y por qué el Strategy es mejor que solo dividir el método en 3 sin cambiar el diseño.


---

*Enunciado completo, entrega esperada y ejemplo de la técnica en un dominio distinto: `Retos_Assessment_Andrey.md` en la raíz de `retos/`. No copies el ejemplo — el dominio es distinto a propósito, para que entiendas la técnica y no el código.*
