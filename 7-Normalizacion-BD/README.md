# Reto 7 — Normalización BD / modelo E-R

**Nivel que evalúa:** Trainer

**Estado:** 🔲 Sin empezar

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

*Completo esto yo mismo cuando termine el reto — no antes. Con mi código real ya escrito, respondo acá (no sobre el enunciado, sobre mi implementación):*

- *¿Qué clases/métodos concretos escribí y qué responsabilidad tiene cada uno?*
- *¿Cómo mi código, específicamente, resuelve el concepto de este reto? Cito mis propias clases y métodos, no la teoría.*
- *¿Qué bug o mal entendido tuve en el camino, y cómo lo corregí? (revisar esto antes de la entrevista me sirve más que repasar la teoría de nuevo).*

## SDD — Spec-Driven Development

Antes de tocar código en este reto, escribí (alcanza con 3-5 líneas, en un comentario o en un README aparte) la especificación de lo que vas a construir: qué clases/métodos necesitás, el contrato de cada uno (entradas, salidas, casos borde) y la regla de negocio que cubre — el "qué" antes del "cómo". Es la misma disciplina que separa TDD (diseñás guiado por tests que escribís vos) de SDD (diseñás guiado por una spec escrita, para vos mismo o para que una IA la ejecute): la decisión de diseño se toma **antes** de escribir la primera línea, no se descubre a medida que tecleás. Encaja directo con el feedback de tus evaluadores: podés usar la IA para redactar o pulir esa spec, pero la decisión de qué debe hacer cada pieza es tuya, no de la IA — spec en mano, después sí generás o escribís el código.

---
