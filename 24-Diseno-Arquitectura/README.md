# Reto 24 — Diseño de arquitectura — atributos de calidad y trade-offs

**Nivel que evalúa:** Master

**Área:** Diseño de arquitectura

**Estado:** 🔲 Sin empezar

## Para qué te sirve este reto

Cierra el paso de "sé aplicar patrones" a "decido qué arquitectura conviene y por qué", partiendo de los requisitos de calidad y no de la moda. Es el tema donde un Master se diferencia de un Senior: la pregunta ya no es cómo se implementa algo sino qué sacrificas al escogerlo.

## Enunciado

Con los requisitos de `docs/requerimientos.md` (sistema de citas con picos fuertes, disponibilidad del 99,9%, datos de salud sensibles, reglas de agenda que cambian cada semana, equipo de 6 personas y presupuesto limitado): (1) identifica los 3 a 5 atributos de calidad más importantes y priorízalos en un árbol de utilidad; (2) escribe tres escenarios de calidad en el formato de seis partes (fuente, estímulo, artefacto, entorno, respuesta, medida de respuesta); (3) propone dos arquitecturas candidatas —por ejemplo un monolito modular hexagonal y microservicios con EDA— y compáralas con un análisis tipo ATAM ligero (puntos de sensibilidad, trade-offs, riesgos); (4) decide y registra la decisión en un ADR; (5) escribe una fitness function ejecutable (una prueba ArchUnit o de carga) que proteja uno de los atributos escogidos.

## Qué debes entregar

Los archivos de `docs/` completos y la fitness function funcionando en un proyecto Gradle tuyo (por ejemplo copiada al reto 11).

## Cómo sabes que lo dominas

¿Qué atributo sacrificaste para favorecer cuál? ¿Con qué número o prueba sabes que la decisión funcionó? ¿Cuándo cambiarías de opinión (qué dato te haría migrar a la otra arquitectura)?

## Qué hay en esta carpeta

Todo lo que dice `TODO` es tuyo. Lo demás es plomería para que arranques sin perder tiempo.

- `docs/requerimientos.md`: El enunciado de requisitos y restricciones del sistema.
- `docs/01-atributos-y-utility-tree.md`: Atributos priorizados y árbol de utilidad.
- `docs/02-escenarios-de-calidad.md`: Tres escenarios de seis partes.
- `docs/03-comparacion-arquitecturas.md`: Comparación tipo ATAM ligero de dos candidatas.
- `docs/adr/0001-arquitectura-elegida.md`: ADR con la decisión.
- `fitness/FitnessFunctionTest.java.txt`: Esqueleto de la fitness function (ArchUnit).

## Repaso: explicación técnica del concepto

Los atributos de calidad (rendimiento, disponibilidad, seguridad, modificabilidad, escalabilidad, costo, operabilidad...) son los requisitos no funcionales que realmente moldean una arquitectura: la funcionalidad casi cualquier arquitectura la puede entregar, la calidad no. El primer paso es priorizarlos con el negocio, porque no se puede maximizar todo a la vez; un árbol de utilidad ordena cada atributo en escenarios concretos con importancia para el negocio y dificultad técnica.

Un escenario de calidad hace el requisito medible. En lugar de "debe ser rápido" se escribe: fuente (un paciente), estímulo (500 reservas por segundo un lunes a las 7 a. m.), artefacto (la API de citas), entorno (operación normal con pico), respuesta (cada reserva se confirma) y medida (p95 menor a 800 ms y menos de 0,5% de errores). Lo medible permite probar y discutir.

ATAM (Architecture Tradeoff Analysis Method) evalúa una arquitectura contra esos escenarios y busca tres cosas: puntos de sensibilidad (una decisión de la que depende mucho un atributo), trade-offs (una decisión que mejora un atributo y empeora otro) y riesgos. Por ejemplo, microservicios mejoran la modificabilidad independiente y la escalabilidad por componente, pero empeoran la complejidad operativa y la consistencia; un monolito modular hexagonal mantiene la consistencia simple y puede extraer un módulo después si un dato lo justifica.

Una fitness function (arquitectura evolutiva) es una prueba automática que protege un atributo a lo largo del tiempo: un test ArchUnit que impide que el dominio dependa de Spring protege la modificabilidad; una prueba de carga con umbral protege el rendimiento. Sin ellas, la arquitectura se degrada sin que nadie lo note.

Cuándo NO: no escojas microservicios porque "es lo moderno" con un equipo de seis personas y sin necesidad real de escalar partes por separado; el costo operativo se come el beneficio. Y no escribas veinte escenarios: con tres bien medidos se decide más que con veinte vagos.

## Paso a paso

1. Lee los requisitos y subraya cada frase que sea un atributo de calidad disfrazado ("picos", "sensibles", "cambian cada semana").
2. Prioriza con el árbol de utilidad: cada hoja lleva importancia (alta, media, baja) y dificultad (alta, media, baja). Las hojas altas-altas son tus decisiones críticas.
3. Escribe los tres escenarios de seis partes con medida numérica. Si no tiene número, no es un escenario.
4. Describe las dos arquitecturas candidatas y llena la tabla de comparación: para cada escenario, cómo responde cada una, y marca sensibilidad, trade-off o riesgo.
5. Decide y escribe el ADR. Incluye una consecuencia negativa que aceptas y el dato que te haría revisar la decisión.
6. Implementa la fitness function y comprueba que falla cuando rompes la regla a propósito.

## Autoevaluación

Antes de marcar el reto como ✅, responde en menos de 2 minutos, en voz alta o por escrito, las preguntas de repaso de este tema en `REPASO_MASTER.md` (están sin respuesta; las respuestas modelo están al final del archivo). Si te cuesta más que escribir el entregable, el hueco está en el concepto.

## Cómo cerré esta brecha (mi implementación)

*Completo esto yo mismo cuando termine el reto, no antes. Con lo que ya entregué, respondo aquí:*

- *¿Qué archivos y decisiones concretas produje y qué responsabilidad tiene cada uno?*
- *¿Cómo mi entrega, específicamente, resuelve el concepto de este reto? Cito mis propios archivos.*
- *¿Qué error o malentendido tuve en el camino y cómo lo corregí?*
- *¿Qué hice yo y qué hice con ayuda de IA?*

## 🎯 Con tu evaluador

Lideró la reestructuración de arquitectura hacia microservicios en dos empresas, así que es terreno donde ya se equivocó y aprendió. Si es tu evaluador, espera que te pregunte por qué microservicios (o por qué no) sin aceptar "escalabilidad" como respuesta: pídete a ti mismo un dato concreto (tráfico, tamaño del equipo, frecuencia de despliegue) que justifique la decisión.

## SDD — Spec-Driven Development

Antes de producir nada en este reto, escribe (3 a 5 líneas) la especificación de lo que vas a entregar: qué archivos, qué decisión toma cada uno, qué casos borde cubres y cómo vas a verificar que está bien. La decisión de diseño se toma antes de la primera línea, no se descubre mientras escribes. Puedes usar la IA para pulir la spec o para preguntarte lo que se te escapa, pero qué debe hacer cada pieza lo decides tú: es el feedback más repetido de tus evaluadores.

---
