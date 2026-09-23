# Reto 9 — Protocolo adicional a REST

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

Implementá un servidor WebSocket de notificaciones: cuando un cliente se conecta, recibe un mensaje de bienvenida; cuando cualquier cliente manda un mensaje tipo `{"tipo":"alerta","texto":"..."}`, todos los demás clientes conectados lo reciben, pero si el tipo es `{"tipo":"privado","destino":"id","texto":"..."}` solo le llega al cliente con ese id de sesión.

## Qué debés entregar

Código del servidor + captura o log mostrando el broadcast y el mensaje privado funcionando con al menos 3 clientes.

## Cómo sabés que lo dominás

¿Podés explicar cómo garantizás que la lista de sesiones conectadas es segura ante acceso concurrente, y qué pasa si un cliente se desconecta abruptamente sin `onClose`?

## 🎯 Con tu evaluador (Rudyard)

Su experiencia fuerte en protocolos es SOAP (core bancario legado) + REST/APIs para integraciones B2B, no WebSocket puntualmente. Probablemente no se quede en el WebSocket en sí — es más probable que te compare: "¿por qué aquí SÍ necesitás algo con estado/bidireccional y en tu integración B2B no?" (esa es la pregunta que él mismo se hizo migrando SOAP legado hacia APIs modernas).


---

*Enunciado completo, entrega esperada y ejemplo de la técnica en un dominio distinto: `Retos_Assessment_Andrey.md` en la raíz de `retos/`. No copies el ejemplo — el dominio es distinto a propósito, para que entiendas la técnica y no el código.*
