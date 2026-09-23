# Reto 17 — IaC (Terraform/CloudFormation/CDK/SAM) + escaneo de seguridad

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

Escribí un Terraform mínimo que declare una tabla DynamoDB (o RDS) y un rol IAM con permisos mínimos necesarios (no `*`). Agregá un pipeline YAML de ejemplo con: paso de escaneo de secretos, paso de escaneo de dependencias, y un paso `tfsec`/`checkov` sobre el propio código Terraform, en el orden correcto con comentarios explicando por qué van en ese orden.

## Qué debés entregar

`.tf` + `.yml` comentado.

## Cómo sabés que lo dominás

¿Podés explicar qué es el principio de menor privilegio aplicado al rol IAM que escribiste, con un ejemplo concreto de qué permiso NO le diste y por qué?


---

*Enunciado completo, entrega esperada y ejemplo de la técnica en un dominio distinto: `Retos_Assessment_Andrey.md` en la raíz de `retos/`. No copies el ejemplo — el dominio es distinto a propósito, para que entiendas la técnica y no el código.*
