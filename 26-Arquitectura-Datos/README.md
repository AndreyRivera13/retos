# Reto 26 — Arquitectura de datos — ciclo de vida de la información

**Nivel que evalúa:** Master

**Área:** Arquitectura de Datos

**Estado:** 🔲 Sin empezar

## Para qué te sirve este reto

Cierra el concepto de decidir qué pasa con un dato desde que entra hasta que se elimina, no solo cómo se guarda. Tus datos en Entitlement (usuarios, permisos, accesos) son sensibles: quién los captura, cuánto viven, quién los ve y cómo se borran son decisiones de arquitectura, no detalles de implementación.

## Enunciado

Define el ciclo de vida de los datos del sistema de citas médicas siguiendo las cinco fases: (1) Captura: qué campos se capturan, por qué (minimización), con qué consentimiento y qué validación en el borde; (2) Almacenamiento: dónde viven los datos activos y los históricos, y por cuánto tiempo (justifica los plazos); (3) Gestión: quién accede, con qué rol y desde qué recursos, qué datos se enmascaran o seudonimizan y cómo se garantiza calidad; (4) Publicación: cómo se presenta información a negocio sin exponer datos personales (una vista de citas agregadas por especialidad y mes); (5) Disposición: el procedimiento para depurar o anonimizar datos vencidos, con evidencia de lo eliminado. Implementa lo implementable en Postgres: el esquema, los roles con GRANT, la vista de publicación y el script de depuración, y pruébalos con datos de ejemplo.

## Qué debes entregar

`docs/ciclo-de-vida.md` con la tabla de cinco fases, `docs/clasificacion-datos.md`, y los scripts `sql/01_schema.sql`, `sql/02_roles_y_vistas.sql` y `sql/03_depuracion.sql` ejecutados contra el Postgres del `docker-compose.yml`, con la salida que demuestra el resultado.

## Cómo sabes que lo dominas

Si un paciente pide que borren sus datos pero existe una obligación legal de conservar parte de ellos, ¿qué haces en cada fase del ciclo? ¿Por qué la vista para negocio no debe leer la tabla transaccional directamente? ¿Cómo demuestras ante un auditor que un dato se depuró?

## Qué hay en esta carpeta

Todo lo que dice `TODO` es tuyo. Lo demás es plomería para que arranques sin perder tiempo.

- `docs/ciclo-de-vida.md`: Tabla de las cinco fases (TODO).
- `docs/clasificacion-datos.md`: Clasificación de cada campo y su tratamiento (TODO).
- `sql/01_schema.sql`: Tablas activas, histórico y auditoría de depuración (TODO).
- `sql/02_roles_y_vistas.sql`: Roles, GRANT y vista de publicación (TODO).
- `sql/03_depuracion.sql`: Procedimiento de depuración con evidencia (TODO).
- `docker-compose.yml`: Postgres local para probar los scripts.

**Herramientas:** Postgres (puede ser el motor que uses en Bancolombia o no; el concepto es el mismo en SQL Server o MySQL).

## Repaso: explicación técnica del concepto

El ciclo de vida de los datos ordena las decisiones en el tiempo. Captura: se recolecta solo lo necesario para la finalidad declarada (minimización), con base legal o consentimiento y validando calidad en la entrada, porque un dato malo capturado aquí contamina todo lo que viene después. Almacenamiento: se decide dónde y por cuánto; es habitual separar el almacenamiento transaccional (rápido, caro, datos vivos) del histórico o de archivo (barato, de solo lectura), con plazos de retención justificados por negocio y por norma. Gestión: define el acceso (roles, mínimo privilegio, desde qué recursos), la protección (cifrado, enmascaramiento, seudonimización), el catálogo (qué dato existe, quién es su dueño, cómo se clasifica) y la calidad. Publicación: es cómo se entrega información a negocio y analítica; lo sano es publicar vistas, agregados o copias anonimizadas, no dar acceso a las tablas operativas. Disposición: el procedimiento explícito para eliminar o anonimizar lo que ya cumplió su plazo, dejando evidencia (qué se depuró, cuándo, bajo qué regla).

En Colombia el marco general de protección de datos personales es la Ley 1581 de 2012 (habeas data), que da al titular derechos de acceso, rectificación y supresión. Los datos de salud son sensibles y la historia clínica tiene reglas propias de conservación en la normativa del sector salud; verifica con tu equipo legal los plazos vigentes antes de afirmarlos, porque este reto te pide justificar los tuyos, no recitar una norma de memoria. Un punto fino y muy preguntado: el derecho de supresión puede chocar con una obligación legal de conservar; la respuesta usual es anonimizar o restringir lo que no se está obligado a guardar y bloquear el acceso a lo que sí.

Cuándo NO: no borres físicamente sin pensar en dependencias (una cita cancelada puede estar referenciada por un cobro); no guardes "todo por si acaso" (cada dato retenido es un riesgo y un costo); y no confundas anonimizar (irreversible, ya no es dato personal) con seudonimizar (reversible con una llave, sigue siendo dato personal).

## Paso a paso

1. Spec: haz la lista de campos de una cita (paciente, documento, doctor, horario, motivo, notas) y clasifícalos (público, interno, personal, sensible). Eso gobierna todo lo demás.
2. Llena la tabla del ciclo de vida: una fila por fase, con decisión, justificación y responsable.
3. Escribe el esquema con la separación entre datos activos e histórico y una tabla de auditoría de depuración.
4. Crea los roles (por ejemplo `app_citas`, `analista_negocio`) con GRANT mínimo y la vista agregada sin datos personales. Comprueba con `SET ROLE` que el analista no puede leer la tabla base.
5. Escribe el script de depuración: mueve o anonimiza los registros vencidos, registra en la auditoría cuántos y cuándo, y es repetible sin duplicar.
6. Ejecuta todo contra el Postgres del compose con datos de ejemplo y guarda la salida como evidencia.

## Autoevaluación

Antes de marcar el reto como ✅, responde en menos de 2 minutos, en voz alta o por escrito, las preguntas de repaso de este tema en `REPASO_MASTER.md` (están sin respuesta; las respuestas modelo están al final del archivo). Si te cuesta más que escribir el entregable, el hueco está en el concepto.

## Cómo cerré esta brecha (mi implementación)

*Completo esto yo mismo cuando termine el reto, no antes. Con lo que ya entregué, respondo aquí:*

- *¿Qué archivos y decisiones concretas produje y qué responsabilidad tiene cada uno?*
- *¿Cómo mi entrega, específicamente, resuelve el concepto de este reto? Cito mis propios archivos.*
- *¿Qué error o malentendido tuve en el camino y cómo lo corregí?*
- *¿Qué hice yo y qué hice con ayuda de IA?*

## 🎯 Con tu evaluador

Trabajó con SQL Server, MySQL y Postgres y modelado de datos, así que puede ir a lo concreto (retención, índices, particionado). Si es tu evaluador, prepara un ejemplo propio de Entitlement en vez de uno de salud.

## SDD — Spec-Driven Development

Antes de producir nada en este reto, escribe (3 a 5 líneas) la especificación de lo que vas a entregar: qué archivos, qué decisión toma cada uno, qué casos borde cubres y cómo vas a verificar que está bien. La decisión de diseño se toma antes de la primera línea, no se descubre mientras escribes. Puedes usar la IA para pulir la spec o para preguntarte lo que se te escapa, pero qué debe hacer cada pieza lo decides tú: es el feedback más repetido de tus evaluadores.

---
