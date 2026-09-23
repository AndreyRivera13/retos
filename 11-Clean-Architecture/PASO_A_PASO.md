# Reto 11 — Clean Architecture (Cebolla / Hexagonal)

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

Tomá el sistema de citas (reto 5) y convertilo a hexagonal: dominio puro, puerto de entrada (`GestionarCitasUseCase`), puerto de salida (`CitaRepositoryPort`), y dos adaptadores de salida intercambiables (memoria y un esqueleto JPA).

## Qué debés entregar

Código organizado en paquetes `dominio`/`aplicacion`/`infraestructura` + la prueba de que si borrás `infraestructura`, el resto sigue compilando.

## Cómo sabés que lo dominás

¿Podés explicar exactamente qué se rompería (y por qué) si pusieras una anotación `@Entity` directamente en la clase de dominio `Cita`?

## 🎯 Con tu evaluador (Rudyard)

Lideró "reestructuración de arquitectura hacia microservicios" en dos empresas — este es territorio donde él ya se equivocó y aprendió, así que va a notar respuestas de manual. Prepárate para "¿qué pasó en tu experiencia real cuando una regla de negocio terminó acoplada a un detalle de infraestructura?" — si no tenés un ejemplo propio de Entitlement, este es el reto donde más te conviene tenerlo.


---

*Enunciado completo, entrega esperada y ejemplo de la técnica en un dominio distinto: `Retos_Assessment_Andrey.md` en la raíz de `retos/`. No copies el ejemplo — el dominio es distinto a propósito, para que entiendas la técnica y no el código.*
