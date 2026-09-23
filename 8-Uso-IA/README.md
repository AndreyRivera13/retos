# Reto 8 — Uso consciente de IA en el desarrollo

**Nivel que evalúa:** Junior

**Estado:** 🔲 Sin empezar

## Para qué te sirve este reto

Cierra el concepto de qué decisión es tuya y cuál delegás a la IA: la IA ejecuta, vos decidís el diseño. Es directamente tu forma de trabajar hoy con Python y automatización — el reto te pide dejarlo explícito con una tarea real, no en abstracto.

## Enunciado

No es código. Tomá una tarea real de tu sprint actual en Entitlement. Documentá, paso a paso, qué partes resolviste con ayuda de IA y cuáles no, y para las que no, explicá qué decisión de diseño tomaste que la IA no podía tomar por vos.

## Qué debés entregar

Tabla con al menos 3 filas "sí usé IA" y 3 "no usé IA", con tareas reales tuyas de Entitlement, no genéricas.

## Cómo sabés que lo dominás

¿Tu evaluador podría preguntarte "¿por qué decidiste eso?" sobre cualquier parte de la tarea y responder sin necesitar abrir el chat de la IA?

## Explicación técnica del concepto

El criterio de uso de IA en desarrollo no es técnico sino de trazabilidad de decisiones: qué se delega (código repetitivo, boilerplate, sintaxis) y qué se mantiene bajo control humano (diseño, reglas de negocio, trade-offs). Documentar una tarea real separando ambas columnas deja evidencia de que las decisiones de diseño no se delegaron a la IA, que es justamente lo que suele fallar cuando se usa IA sin ese criterio.

## Cómo cerré esta brecha (mi implementación)

*Completo esto yo mismo cuando termine el reto — no antes. Con mi código real ya escrito, respondo acá (no sobre el enunciado, sobre mi implementación):*

- *¿Qué clases/métodos concretos escribí y qué responsabilidad tiene cada uno?*
- *¿Cómo mi código, específicamente, resuelve el concepto de este reto? Cito mis propias clases y métodos, no la teoría.*
- *¿Qué bug o mal entendido tuve en el camino, y cómo lo corregí? (revisar esto antes de la entrevista me sirve más que repasar la teoría de nuevo).*

## SDD — Spec-Driven Development

Antes de tocar código en este reto, escribí (alcanza con 3-5 líneas, en un comentario o en un README aparte) la especificación de lo que vas a construir: qué clases/métodos necesitás, el contrato de cada uno (entradas, salidas, casos borde) y la regla de negocio que cubre — el "qué" antes del "cómo". Es la misma disciplina que separa TDD (diseñás guiado por tests que escribís vos) de SDD (diseñás guiado por una spec escrita, para vos mismo o para que una IA la ejecute): la decisión de diseño se toma **antes** de escribir la primera línea, no se descubre a medida que tecleás. Encaja directo con el feedback de tus evaluadores: podés usar la IA para redactar o pulir esa spec, pero la decisión de qué debe hacer cada pieza es tuya, no de la IA — spec en mano, después sí generás o escribís el código.

---
