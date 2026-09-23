# Reto 16 — EDA — Arquitectura orientada a eventos (Kafka/RabbitMQ/etc)

**Nivel que evalúa:** Senior

**Estado:** 🔲 Sin empezar

## Para qué te sirve este reto

Cierra el concepto de desacoplar servicios con eventos en vez de llamadas directas, y la idempotencia que eso obliga (un mensaje puede llegar duplicado). Si tus integraciones hoy son llamada síncrona directa, este reto te mueve al extremo opuesto y te hace resolver el problema que aparece ahí: qué pasa si el mismo evento llega dos veces.

## Enunciado

Cuando se reserva una cita (reto 5/11), publicá un evento `CitaReservada`. Creá un consumidor separado que, al recibirlo, simule el envío de un recordatorio — y hacelo idempotente: si el mismo evento (mismo id) llega dos veces, el recordatorio no se duplica (usá un `Set` de ids ya procesados, aunque sea en memoria para el ejercicio).

## Qué debés entregar

Productor + consumidor corriendo por separado + prueba de que mandar el mismo evento dos veces no duplica el efecto.

## Cómo sabés que lo dominás

¿Podés explicar qué semántica de entrega asumiste (at-least-once) y por qué sin la verificación de idempotencia tu sistema tendría un bug real en producción?

## Explicación técnica del concepto

Una arquitectura orientada a eventos desacopla productor y consumidor: el productor publica un hecho ocurrido (`CitaReservada`) sin saber quién lo consume ni cómo. La semántica at-least-once, la más común en sistemas de mensajería, garantiza que el evento llegue al menos una vez pero no exactamente una — por eso el consumidor debe ser idempotente (verificar si el id del evento ya fue procesado), de forma que un reintento normal del broker no duplique el efecto.

## Cómo cerré esta brecha (mi implementación)

*Completo esto yo mismo cuando termine el reto — no antes. Con mi código real ya escrito, respondo acá (no sobre el enunciado, sobre mi implementación):*

- *¿Qué clases/métodos concretos escribí y qué responsabilidad tiene cada uno?*
- *¿Cómo mi código, específicamente, resuelve el concepto de este reto? Cito mis propias clases y métodos, no la teoría.*
- *¿Qué bug o mal entendido tuve en el camino, y cómo lo corregí? (revisar esto antes de la entrevista me sirve más que repasar la teoría de nuevo).*

## SDD — Spec-Driven Development

Antes de tocar código en este reto, escribí (alcanza con 3-5 líneas, en un comentario o en un README aparte) la especificación de lo que vas a construir: qué clases/métodos necesitás, el contrato de cada uno (entradas, salidas, casos borde) y la regla de negocio que cubre — el "qué" antes del "cómo". Es la misma disciplina que separa TDD (diseñás guiado por tests que escribís vos) de SDD (diseñás guiado por una spec escrita, para vos mismo o para que una IA la ejecute): la decisión de diseño se toma **antes** de escribir la primera línea, no se descubre a medida que tecleás. Encaja directo con el feedback de tus evaluadores: podés usar la IA para redactar o pulir esa spec, pero la decisión de qué debe hacer cada pieza es tuya, no de la IA — spec en mano, después sí generás o escribís el código.

---
