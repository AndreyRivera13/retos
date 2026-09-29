# Reto 7 — Normalización BD / modelo E-R

**Nivel que evalúa:** Trainer

**Estado:** ✅ Cerrado — `reto.sql` con los 3 pasos (1FN/2FN/3FN) y justificación de cada dependencia resuelta, sin errores de modelado. Verificado 2026-09-29.

## Para qué te sirve este reto

Cierra el concepto de dependencias funcionales y formas normales: qué dato depende de qué clave, y qué inconsistencia aparece si no lo separás. El modelo de roles/permisos/usuarios detrás de Entitlement vive de este mismo análisis, aunque ya no lo hagas conscientemente.

## Enunciado

Normalizá hasta 3FN esta tabla: `Matriculas(id_matricula, estudiante_nombre, estudiante_documento, curso1_nombre, curso1_profesor, curso2_nombre, curso2_profesor)`.

## Qué debés entregar

Tablas finales con PK/FK marcadas + una frase por cada paso (1FN, 2FN, 3FN) diciendo qué dependencia resolviste.

## Cómo sabés que lo dominás

¿Podés explicar qué problema de inconsistencia real evitás al separar `curso_profesor` en su propia tabla? (pista: ¿qué pasa si el mismo profesor aparece con dos nombres distintos en filas distintas antes de normalizar?)

## Explicación técnica del concepto

Una dependencia funcional parcial —un atributo que depende solo de parte de una clave primaria compuesta, no de la clave completa— rompe 2FN. Si `curso1_profesor` depende únicamente de `curso1_nombre` y no de `id_matricula`, separarla en su propia tabla elimina la redundancia y el riesgo de que el mismo dato quede inconsistente en filas distintas (el mismo profesor escrito de dos formas distintas, por ejemplo).

## Cómo cerré esta brecha (mi implementación)

Normalicé `Matriculas` en tres pasos. En 1FN eliminé los grupos repetidos `curso1_*`/`curso2_*`, moviendo cada curso matriculado a su propia fila en `MatriculaCursos`. En 2FN separé `Cursos` porque `profesor_nombre` dependía solo de `curso_nombre` —parte de la clave compuesta— y no de la matrícula completa. En 3FN separé `Profesores` de `Cursos` porque los datos del profesor dependían del profesor mismo, no directamente del curso.

El resultado evita que el mismo profesor quede escrito de forma inconsistente en filas distintas —si antes de normalizar el mismo profesor aparecía con dos nombres distintos en dos filas, la base ya no tiene una sola fuente de verdad sobre quién es ese profesor.

## SDD — Spec-Driven Development

Antes de tocar código en este reto, escribí (alcanza con 3-5 líneas, en un comentario o en un README aparte) la especificación de lo que vas a construir: qué clases/métodos necesitás, el contrato de cada uno (entradas, salidas, casos borde) y la regla de negocio que cubre — el "qué" antes del "cómo". Es la misma disciplina que separa TDD (diseñás guiado por tests que escribís vos) de SDD (diseñás guiado por una spec escrita, para vos mismo o para que una IA la ejecute): la decisión de diseño se toma **antes** de escribir la primera línea, no se descubre a medida que tecleás. Encaja directo con el feedback de tus evaluadores: podés usar la IA para redactar o pulir esa spec, pero la decisión de qué debe hacer cada pieza es tuya, no de la IA — spec en mano, después sí generás o escribís el código.

---
