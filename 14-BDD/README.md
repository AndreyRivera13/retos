# Reto 14 — Pruebas BDD (Cucumber/Karate)

**Nivel que evalúa:** Senior

**Estado:** 🔲 Sin empezar

## Para qué te sirve este reto

Cierra el concepto de especificar comportamiento en lenguaje de negocio antes de codificar (Given/When/Then), no describir el código después. Se conecta con la documentación técnica que ya generás — la diferencia es escribirla antes, y que la entienda alguien de negocio sin traducirla.

## Enunciado

Escribí un `.feature` con 3 escenarios para "Reservar una cita" (reto 5/11): reserva exitosa, horario ya ocupado, doctor inexistente. Implementá los step definitions conectados al `GestionarCitasUseCase` real.

## Qué debés entregar

`.feature` + step definitions + los 3 escenarios pasando.

## Cómo sabés que lo dominás

¿Un compañero no técnico puede leer tu `.feature` y decirte qué se está probando sin que le expliques nada del código?

## Explicación técnica del concepto

BDD especifica el comportamiento esperado en lenguaje natural estructurado (Given/When/Then) antes de escribir código, de forma que la especificación sea legible por alguien sin conocimiento técnico. Los step definitions traducen ese lenguaje a llamadas reales sobre el caso de uso, por lo que el `.feature` no es solo documentación sino una prueba ejecutable de que el comportamiento descrito se cumple.

## Cómo cerré esta brecha (mi implementación)

*Completo esto yo mismo cuando termine el reto — no antes. Con mi código real ya escrito, respondo acá (no sobre el enunciado, sobre mi implementación):*

- *¿Qué clases/métodos concretos escribí y qué responsabilidad tiene cada uno?*
- *¿Cómo mi código, específicamente, resuelve el concepto de este reto? Cito mis propias clases y métodos, no la teoría.*
- *¿Qué bug o mal entendido tuve en el camino, y cómo lo corregí? (revisar esto antes de la entrevista me sirve más que repasar la teoría de nuevo).*

## SDD — Spec-Driven Development

Antes de tocar código en este reto, escribí (alcanza con 3-5 líneas, en un comentario o en un README aparte) la especificación de lo que vas a construir: qué clases/métodos necesitás, el contrato de cada uno (entradas, salidas, casos borde) y la regla de negocio que cubre — el "qué" antes del "cómo". Es la misma disciplina que separa TDD (diseñás guiado por tests que escribís vos) de SDD (diseñás guiado por una spec escrita, para vos mismo o para que una IA la ejecute): la decisión de diseño se toma **antes** de escribir la primera línea, no se descubre a medida que tecleás. Encaja directo con el feedback de tus evaluadores: podés usar la IA para redactar o pulir esa spec, pero la decisión de qué debe hacer cada pieza es tuya, no de la IA — spec en mano, después sí generás o escribís el código.

---
