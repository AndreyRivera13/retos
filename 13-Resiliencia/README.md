# Reto 13 — Resiliencia (mínimo 2: CircuitBreaker, Retry, Fallback, RateLimit, Bulkhead)

**Nivel que evalúa:** Senior

**Estado:** 🔲 Sin empezar

## Para qué te sirve este reto

Cierra el concepto de qué hacer cuando una dependencia externa falla: reintentar, cortar el circuito, degradar la respuesta, limitar la concurrencia. Cualquier llamada externa de un microservicio tuyo en Bancolombia puede fallar así — este reto es la respuesta técnica formal a algo que probablemente ya resolviste de forma manual.

## Enunciado

Simulá una pasarela de pagos externa que falla 40% de las veces. Envolvela con Resilience4j: `@Retry` (3 intentos, backoff exponencial), `@CircuitBreaker` (se abre con >50% de fallos en ventana de 10), `@Bulkhead` (máximo 5 llamadas concurrentes), y `@Fallback` que responda "pago en proceso, se confirmará luego" en vez de error crudo.

## Qué debés entregar

Código + log de una corrida donde se vea el circuito pasando por los 3 estados.

## Cómo sabés que lo dominás

¿Podés explicar por qué combinaste Bulkhead con CircuitBreaker en vez de solo uno de los dos — qué problema distinto resuelve cada uno en este caso?

## Explicación técnica del concepto

Retry reintenta una operación que falla por una causa transitoria. CircuitBreaker detiene los intentos cuando la tasa de fallos supera un umbral, evitando insistir sobre una dependencia ya caída. Bulkhead limita la concurrencia hacia una dependencia para que su lentitud no consuma todos los recursos del sistema. Fallback define una respuesta alternativa cuando lo anterior no evita la falla. Son complementarios porque cada uno actúa en un momento distinto: antes, durante y después de que la falla ocurre.

## Cómo cerré esta brecha (mi implementación)

*Completo esto yo mismo cuando termine el reto — no antes. Con mi código real ya escrito, respondo acá (no sobre el enunciado, sobre mi implementación):*

- *¿Qué clases/métodos concretos escribí y qué responsabilidad tiene cada uno?*
- *¿Cómo mi código, específicamente, resuelve el concepto de este reto? Cito mis propias clases y métodos, no la teoría.*
- *¿Qué bug o mal entendido tuve en el camino, y cómo lo corregí? (revisar esto antes de la entrevista me sirve más que repasar la teoría de nuevo).*

## SDD — Spec-Driven Development

Antes de tocar código en este reto, escribí (alcanza con 3-5 líneas, en un comentario o en un README aparte) la especificación de lo que vas a construir: qué clases/métodos necesitás, el contrato de cada uno (entradas, salidas, casos borde) y la regla de negocio que cubre — el "qué" antes del "cómo". Es la misma disciplina que separa TDD (diseñás guiado por tests que escribís vos) de SDD (diseñás guiado por una spec escrita, para vos mismo o para que una IA la ejecute): la decisión de diseño se toma **antes** de escribir la primera línea, no se descubre a medida que tecleás. Encaja directo con el feedback de tus evaluadores: podés usar la IA para redactar o pulir esa spec, pero la decisión de qué debe hacer cada pieza es tuya, no de la IA — spec en mano, después sí generás o escribís el código.

---
