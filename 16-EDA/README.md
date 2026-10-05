# Reto 16 — EDA — Arquitectura orientada a eventos (Kafka/RabbitMQ/etc)

**Nivel que evalúa:** Senior

**Estado:** ✅ Cerrado — verificado el 2026-10-03 (3 tests unitarios + 1 de extremo a extremo con Kafka embebido + ArchitectureTest en verde).

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

`CitaEventoProducer.publicar()` envía el evento `CitaReservada` al tópico `citas-reservadas` con `kafkaTemplate.send(TOPICO, evento.citaId(), evento)`: uso el id de la cita como clave para que todos los eventos de una misma cita caigan en la misma partición y conserven el orden. `RecordatorioConsumer.escuchar()` es idempotente: si `idsProcesados.add(evento.eventoId())` devuelve `false`, el evento ya se procesó, lo registra como duplicado y retorna; si devuelve `true`, llama a un `EnviadorRecordatorio`. Separé ese envío en una interfaz (`EnviadorRecordatorioConsola` es la implementación que imprime) para poder verificar en los tests cuántos recordatorios se enviaron de verdad.

La semántica que asumí es at-least-once: Kafka garantiza que el mensaje llega, pero ante un reintento del productor o un rebalanceo del consumidor puede llegar dos veces. Sin la verificación de idempotencia, el paciente recibiría el recordatorio duplicado, o, en un caso más grave (un cobro, un descuento de inventario), el efecto se aplicaría dos veces. La idempotencia usa `eventoId`, no `citaId`: dos eventos distintos sobre la misma cita sí deben procesarse, uno repetido no.

Lo verifiqué con 3 tests unitarios del consumidor y uno de extremo a extremo con un broker Kafka embebido: publica el mismo evento dos veces y otro distinto, y solo se envían 2 recordatorios. Hallazgos reales: con Spring Boot 4 hace falta `spring-boot-starter-kafka`, porque con solo `spring-kafka` no hay autoconfiguración y el `KafkaTemplate` ni siquiera existe como bean; y los (de)serializadores JSON ahora son `JacksonJsonSerializer`/`JacksonJsonDeserializer`. Dejé un `docker-compose.yml` y una prueba manual (`--demo.publicar=true`) para correrlo contra un Kafka real; la corrida contra Docker no la pude ejecutar en mi entorno, la verificación es con el broker embebido. Un tema abierto que conozco: el `Set` en memoria se pierde si el consumidor se reinicia y crece sin límite; en producción usaría una tabla con restricción de unicidad sobre `eventoId` (o Redis con TTL).

## SDD — Spec-Driven Development

Antes de tocar código en este reto, escribí (alcanza con 3-5 líneas, en un comentario o en un README aparte) la especificación de lo que vas a construir: qué clases/métodos necesitás, el contrato de cada uno (entradas, salidas, casos borde) y la regla de negocio que cubre — el "qué" antes del "cómo". Es la misma disciplina que separa TDD (diseñás guiado por tests que escribís vos) de SDD (diseñás guiado por una spec escrita, para vos mismo o para que una IA la ejecute): la decisión de diseño se toma **antes** de escribir la primera línea, no se descubre a medida que tecleás. Encaja directo con el feedback de tus evaluadores: podés usar la IA para redactar o pulir esa spec, pero la decisión de qué debe hacer cada pieza es tuya, no de la IA — spec en mano, después sí generás o escribís el código.

---
