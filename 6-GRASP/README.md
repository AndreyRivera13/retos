# Reto 6 — Patrones GRASP

**Nivel que evalúa:** Trainer

**Estado:** 🔲 Sin empezar

## Para qué te sirve este reto

Cierra el concepto de asignar responsabilidades con un criterio (quién tiene la información para decidir, quién debería crear qué), no por dónde "queda cómodo" el código. Cuando decidís en qué clase va una regla dentro de un servicio Spring, ya estás aplicando o rompiendo esto sin nombrarlo — el reto te da el vocabulario para defenderlo.

## Enunciado

Sobre el sistema de citas del reto 5, identificá y ajustá si hace falta: ¿quién calcula la duración total de las citas del día de un doctor (Experto en Información)? ¿quién crea las instancias de `Cita` — el Service directamente con `new`, o debería haber un método `crear()` en el propio agregado/clase relacionada (Creador)?

## Qué debés entregar

El código ajustado + comentarios señalando exactamente la línea donde aplicaste cada patrón.

## Cómo sabés que lo dominás

¿Podés nombrar una clase candidata alternativa para cada responsabilidad y explicar por qué la descartaste?

## Explicación técnica del concepto

Experto en Información asigna una responsabilidad a la clase que tiene los datos necesarios para cumplirla: si el doctor conoce sus propias citas, es el doctor quien calcula su duración total, no un Service externo que le pide los datos uno por uno. Creador determina quién debería instanciar un objeto — normalmente la clase que lo agrega, contiene o usa de cerca. Ambos patrones evitan clases que solo mueven datos sin tener responsabilidad real sobre ellos.

## Cómo cerré esta brecha (mi implementación)

*Completo esto yo mismo cuando termine el reto — no antes. Con mi código real ya escrito, respondo acá (no sobre el enunciado, sobre mi implementación):*

- *¿Qué clases/métodos concretos escribí y qué responsabilidad tiene cada uno?*
- *¿Cómo mi código, específicamente, resuelve el concepto de este reto? Cito mis propias clases y métodos, no la teoría.*
- *¿Qué bug o mal entendido tuve en el camino, y cómo lo corregí? (revisar esto antes de la entrevista me sirve más que repasar la teoría de nuevo).*

## SDD — Spec-Driven Development

Antes de tocar código en este reto, escribí (alcanza con 3-5 líneas, en un comentario o en un README aparte) la especificación de lo que vas a construir: qué clases/métodos necesitás, el contrato de cada uno (entradas, salidas, casos borde) y la regla de negocio que cubre — el "qué" antes del "cómo". Es la misma disciplina que separa TDD (diseñás guiado por tests que escribís vos) de SDD (diseñás guiado por una spec escrita, para vos mismo o para que una IA la ejecute): la decisión de diseño se toma **antes** de escribir la primera línea, no se descubre a medida que tecleás. Encaja directo con el feedback de tus evaluadores: podés usar la IA para redactar o pulir esa spec, pero la decisión de qué debe hacer cada pieza es tuya, no de la IA — spec en mano, después sí generás o escribís el código.

---
