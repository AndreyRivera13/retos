# Reto 18 — Seguridad — OWASP Top 10 / CWE

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

Te dan un endpoint `GET /usuarios/{id}/documentos` sin ninguna verificación de que el usuario autenticado sea el dueño del `id` solicitado. Identificá la vulnerabilidad (categoría OWASP + CWE), explicá el vector de ataque, y corregila.

## Qué debés entregar

Código corregido + identificación de categoría/CWE.

## Cómo sabés que lo dominás

¿Podés explicar la diferencia entre este fallo (control de acceso roto, CWE-284/A01) y una autenticación rota? — muchos los confunden.

## 🎯 Con tu evaluador (Rudyard)

JWT aparece explícito en el stack de sus dos trabajos como Technical Leader, así que conoce bien el terreno de autenticación — justo por eso es más probable que te tienda la trampa de confundir autenticación (JWT válido) con autorización (dueño del recurso), que es exactamente la distinción que pide este reto. No te quedes en "faltaba validar el JWT": el JWT SÍ es válido, el problema es otro.


---

*Enunciado completo, entrega esperada y ejemplo de la técnica en un dominio distinto: `Retos_Assessment_Andrey.md` en la raíz de `retos/`. No copies el ejemplo — el dominio es distinto a propósito, para que entiendas la técnica y no el código.*
