# Reto 8 — Uso consciente de IA en el desarrollo

**Nivel que evalúa:** Junior

**Estado:** ✅ Cerrado — tabla armada con tareas reales de esta misma sesión (generación de READMEs, script de automatización, y la lógica/bugs de los retos 1 y 2). Verificado 2026-09-23.

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

Usé como evidencia real las tareas de esta misma sesión trabajando con Claude en mis retos del assessment, en vez de una tarea inventada:

| Qué hice (tarea concreta) | ¿Usé IA? | Si sí: ¿qué le delegué? / Si no: ¿qué decisión tomé yo? |
|---|---|---|
| Generar los 19 `README.md` de los retos (estructura, redacción de las secciones explicativas) | Sí | Le delegué la redacción y el formato repetitivo de 19 archivos. Las decisiones editoriales —qué secciones iban, sacar las menciones al evaluador, pasar la sección de cierre a primera persona, quitar el paso a paso— las tomé yo, iterando hasta que quedó como quería. |
| Escribir el script `gen_pasoapaso.py` que genera los README a partir de datos estructurados | Sí | Le delegué la implementación del script en sí (loops, f-strings, manejo de archivos). Decidí yo la estructura de datos (un dict por reto) y qué campos necesitaba. |
| Entender conceptos técnicos para el assessment (SOLID, polimorfismo, GRASP, etc.) antes de codificar | Sí | Le delegué la explicación/síntesis de la teoría. No le delegué la aplicación: cada concepto lo apliqué yo en mi propio código. |
| Implementar la lógica de negocio de `ReglaEdad`, `ReglaIngresos` y `ReglaHistorial` en el reto 2 (SOLID) | No | La IA me dio la estructura del patrón Strategy (ya venía en el scaffold) y preguntas guía, pero la lógica de cada `if` la escribí yo. De hecho cometí dos bugs yo solo —`ReglaHistorial` no leía el campo real, `ReglaEdad` usaba `>` en vez de `>=`— y los corregí yo, no la IA. |
| Corregir el bug de `EmpleadoFijo.calcularSalario()` en el reto 1 (POO) que devolvía `salarioBase * 2` | No | La IA me hizo la pregunta de por qué estaba ese `*2`, pero la decisión de que el salario fijo debía ser directo (sin duplicar) y el cambio del código los hice yo. |
| Decidir qué iba en cada sección de los README (quitar el "paso a paso", sacar el nombre del evaluador, primera persona sí/no) | No | Cada uno de esos cambios lo pedí yo explícitamente después de ver el resultado — la IA no propuso ninguno por su cuenta. |

El patrón que se ve: a la IA le delego generación de contenido repetitivo y explicación de teoría, pero la lógica de negocio, los bugs y las decisiones de formato/edición las tomo yo. Puedo responder "¿por qué decidiste eso?" en cualquier fila sin necesitar abrir el chat.

## SDD — Spec-Driven Development

Antes de tocar código en este reto, escribí (alcanza con 3-5 líneas, en un comentario o en un README aparte) la especificación de lo que vas a construir: qué clases/métodos necesitás, el contrato de cada uno (entradas, salidas, casos borde) y la regla de negocio que cubre — el "qué" antes del "cómo". Es la misma disciplina que separa TDD (diseñás guiado por tests que escribís vos) de SDD (diseñás guiado por una spec escrita, para vos mismo o para que una IA la ejecute): la decisión de diseño se toma **antes** de escribir la primera línea, no se descubre a medida que tecleás. Encaja directo con el feedback de tus evaluadores: podés usar la IA para redactar o pulir esa spec, pero la decisión de qué debe hacer cada pieza es tuya, no de la IA — spec en mano, después sí generás o escribís el código.

---
