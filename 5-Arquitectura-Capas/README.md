# Reto 5 — Arquitectura de capas

**Nivel que evalúa:** Trainer

**Estado:** 🔲 Sin empezar

## Para qué te sirve este reto

Cierra el concepto de separación de responsabilidades por capa: Controller no valida reglas de negocio, Repository no decide nada, Service es el único lugar donde vive la regla. Es literalmente la estructura de cualquier microservicio Spring Boot que ya armás en Bancolombia — acá te toca explicar por qué existe esa separación, no solo replicarla.

## Enunciado

Diseñá (sin frameworks) un mini sistema de reservas de citas: `CitaRepository` (interfaz) + implementación en memoria, `CitaService` que valida que no haya dos citas en el mismo horario para el mismo doctor, y un "Controller" (clase simple con métodos que simula endpoints) que solo llama al Service.

## Qué debés entregar

Código + diagrama de texto (ASCII) mostrando qué capa llama a cuál.

## Cómo sabés que lo dominás

¿Podés señalar exactamente en qué capa vive la regla "no dos citas en el mismo horario" y explicar por qué no debería estar en el Controller ni en el Repository?

## Explicación técnica del concepto

La separación en capas asigna una responsabilidad exclusiva a cada una: el Controller adapta entrada/salida, el Service contiene la regla de negocio, el Repository solo persiste. La regla "no dos citas en el mismo horario para el mismo doctor" vive en el Service porque es lógica de negocio, no un detalle de transporte ni de almacenamiento — ubicarla en cualquiera de esos dos la acopla a un detalle técnico y la vuelve difícil de testear sin levantar esa capa.

## Cómo cerré esta brecha (mi implementación)

*Completo esto yo mismo cuando termine el reto — no antes. Con mi código real ya escrito, respondo acá (no sobre el enunciado, sobre mi implementación):*

- *¿Qué clases/métodos concretos escribí y qué responsabilidad tiene cada uno?*
- *¿Cómo mi código, específicamente, resuelve el concepto de este reto? Cito mis propias clases y métodos, no la teoría.*
- *¿Qué bug o mal entendido tuve en el camino, y cómo lo corregí? (revisar esto antes de la entrevista me sirve más que repasar la teoría de nuevo).*

## SDD — Spec-Driven Development

Antes de tocar código en este reto, escribí (alcanza con 3-5 líneas, en un comentario o en un README aparte) la especificación de lo que vas a construir: qué clases/métodos necesitás, el contrato de cada uno (entradas, salidas, casos borde) y la regla de negocio que cubre — el "qué" antes del "cómo". Es la misma disciplina que separa TDD (diseñás guiado por tests que escribís vos) de SDD (diseñás guiado por una spec escrita, para vos mismo o para que una IA la ejecute): la decisión de diseño se toma **antes** de escribir la primera línea, no se descubre a medida que tecleás. Encaja directo con el feedback de tus evaluadores: podés usar la IA para redactar o pulir esa spec, pero la decisión de qué debe hacer cada pieza es tuya, no de la IA — spec en mano, después sí generás o escribís el código.

---
