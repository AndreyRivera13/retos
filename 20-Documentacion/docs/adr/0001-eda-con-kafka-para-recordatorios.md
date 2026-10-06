# ADR 0001: ¿Por qué eventos con Kafka para los recordatorios?

- **Estado:** TODO (propuesto / aceptado)
- **Fecha:** TODO

## Contexto
TODO: qué problema había, qué fuerzas empujaban (acoplamiento, picos, equipos distintos...).

## Decisión
TODO: una frase en voz activa. Ej.: "Publicaremos el evento CitaReservada y el recordatorio será un consumidor separado."

## Alternativas consideradas
1. TODO: llamada síncrona directa al servicio de recordatorios. Por qué no.
2. TODO: otra alternativa real (cola más simple, tarea programada...). Por qué no.

## Consecuencias
- Positivas: TODO
- Negativas que aceptamos: TODO (consistencia eventual, idempotencia, operar el broker...)
- Cuándo revisaríamos esta decisión: TODO (qué dato nos haría cambiar)
