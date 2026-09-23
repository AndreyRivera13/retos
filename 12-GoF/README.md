# Reto 12 — Patrones GoF (6 de cada tipo)

**Nivel que evalúa:** Senior

**Estado:** 🔲 Sin empezar

## Para qué te sirve este reto

Cierra el concepto de reconocer un patrón de diseño frente a un problema concreto, no de memorizar su definición. En tus integraciones entre servicios seguro ya aplicaste algo como Strategy (distintos tipos de validación según el caso) sin ponerle el nombre — acá te toca identificarlo en código real.

## Enunciado

Sistema de generación de reportes con: Builder para construir un `Reporte` con secciones opcionales (encabezado, tabla, gráfico, pie de página) sin un constructor de 6 parámetros; Strategy para exportar el mismo reporte a distintos formatos (`ExportadorPDF`, `ExportadorCSV`) elegible en runtime; Observer para notificar a "suscriptores" (ej: un log, un contador de reportes generados) cada vez que se genera un reporte, sin que `Reporte` conozca a sus observadores concretos.

## Qué debés entregar

Código con los 3 patrones funcionando juntos en un `main`.

## Cómo sabés que lo dominás

¿Podés explicar qué problema tendrías si en vez de Observer hubieras puesto las notificaciones como llamadas directas dentro de la clase `Reporte`?

## Explicación técnica del concepto

Builder resuelve la construcción de objetos con múltiples atributos opcionales sin recurrir a un constructor con parámetros excesivos. Strategy permite intercambiar un algoritmo —en este caso, el formato de exportación— en tiempo de ejecución sin condicionales. Observer desacopla al emisor de un evento (`Reporte`) de sus receptores (suscriptores), que se registran sin que el emisor conozca su implementación concreta. Cada patrón resuelve un problema de diseño específico; no son intercambiables entre sí.

## Cómo cerré esta brecha (mi implementación)

*Completo esto yo mismo cuando termine el reto — no antes. Con mi código real ya escrito, respondo acá (no sobre el enunciado, sobre mi implementación):*

- *¿Qué clases/métodos concretos escribí y qué responsabilidad tiene cada uno?*
- *¿Cómo mi código, específicamente, resuelve el concepto de este reto? Cito mis propias clases y métodos, no la teoría.*
- *¿Qué bug o mal entendido tuve en el camino, y cómo lo corregí? (revisar esto antes de la entrevista me sirve más que repasar la teoría de nuevo).*

## 🎯 Con tu evaluador

"Patrones de Diseño" está listado explícito en su stack en ambos trabajos. Es probable que te pida nombrar el patrón SIN que vos digas el nombre primero — es decir, te describe un problema y espera que identifiques cuál de los 3 (o cuál GoF en general) aplica, no que recites la definición.

## SDD — Spec-Driven Development

Antes de tocar código en este reto, escribí (alcanza con 3-5 líneas, en un comentario o en un README aparte) la especificación de lo que vas a construir: qué clases/métodos necesitás, el contrato de cada uno (entradas, salidas, casos borde) y la regla de negocio que cubre — el "qué" antes del "cómo". Es la misma disciplina que separa TDD (diseñás guiado por tests que escribís vos) de SDD (diseñás guiado por una spec escrita, para vos mismo o para que una IA la ejecute): la decisión de diseño se toma **antes** de escribir la primera línea, no se descubre a medida que tecleás. Encaja directo con el feedback de tus evaluadores: podés usar la IA para redactar o pulir esa spec, pero la decisión de qué debe hacer cada pieza es tuya, no de la IA — spec en mano, después sí generás o escribís el código.

---
