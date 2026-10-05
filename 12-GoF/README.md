# Reto 12 — Patrones GoF (6 de cada tipo)

**Nivel que evalúa:** Senior

**Estado:** ✅ Cerrado — verificado el 2026-10-03 (11 tests propios + ArchitectureTest en verde, prueba manual en MainApplication).

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

Apliqué tres patrones sobre un generador de reportes. Builder: `Reporte.Builder` arma el reporte con secciones opcionales (encabezado, tabla, gráfico, pie) encadenando métodos, sin un constructor de seis parámetros. Strategy: `ExportadorPDF` y `ExportadorCSV` implementan `ExportadorReporte`, y `GeneradorReportes.generar(reporte, exportador)` recibe la estrategia por parámetro, así el formato se elige en runtime sin ningún `if` por tipo. Observer: `GeneradorReportes` mantiene una lista de `ObservadorReporte` (`ContadorReportes` y `LogReportes`) y los notifica en cada generación; `Reporte` no conoce a ninguno.

Tuve un defecto real en el Builder que corregí: mi primera versión era mutable, porque `Reporte` tenía constructor público, `build()` devolvía la misma instancia que el Builder seguía guardando y `getSecciones()` exponía la lista interna. Si seguía usando el Builder después de `build()`, el reporte ya entregado cambiaba. Ahora el constructor es privado, el Builder guarda su propia lista y `build()` crea un `Reporte` nuevo con `List.copyOf`, de modo que cada reporte es independiente e inmutable. En el CSV escapo el contenido (siempre entre comillas, con las comillas internas duplicadas) para que comas o comillas dentro del texto no rompan el formato.

Las exportaciones, los observadores y los tests (11, incluidos los de inmutabilidad y de escape de CSV). Un tema abierto que conozco: en `generar()`, si un observador lanza una excepción, los observadores que venían después no se notifican; para producción aislaría cada notificación con su propio try/catch y decidiría qué hacer con el error en vez de tragarlo en silencio. Y sin Observer, con las notificaciones como llamadas directas dentro de `Reporte`, el reporte quedaría acoplado a cada consumidor y habría que modificarlo cada vez que apareciera uno nuevo.

## 🎯 Con tu evaluador

"Patrones de Diseño" está listado explícito en su stack en ambos trabajos. Es probable que te pida nombrar el patrón SIN que vos digas el nombre primero — es decir, te describe un problema y espera que identifiques cuál de los 3 (o cuál GoF en general) aplica, no que recites la definición.

## SDD — Spec-Driven Development

Antes de tocar código en este reto, escribí (alcanza con 3-5 líneas, en un comentario o en un README aparte) la especificación de lo que vas a construir: qué clases/métodos necesitás, el contrato de cada uno (entradas, salidas, casos borde) y la regla de negocio que cubre — el "qué" antes del "cómo". Es la misma disciplina que separa TDD (diseñás guiado por tests que escribís vos) de SDD (diseñás guiado por una spec escrita, para vos mismo o para que una IA la ejecute): la decisión de diseño se toma **antes** de escribir la primera línea, no se descubre a medida que tecleás. Encaja directo con el feedback de tus evaluadores: podés usar la IA para redactar o pulir esa spec, pero la decisión de qué debe hacer cada pieza es tuya, no de la IA — spec en mano, después sí generás o escribís el código.

---
