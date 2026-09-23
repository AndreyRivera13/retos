# Reto 15 — Paradigmas reactivo/funcional

**Nivel que evalúa:** Senior

**Estado:** 🔲 Sin empezar

## Para qué te sirve este reto

Cierra el concepto de programación reactiva frente a imperativa: no bloquear un hilo esperando una respuesta, sino declarar qué pasa cuando el dato llegue (`Mono`/`Flux`). Si tus APIs hoy son bloqueantes con Spring MVC, el reto te exige justificar cuándo migrar a WebFlux vale la pena y cuándo no — no es "reactivo porque sí".

## Enunciado

Convertí `GestionarCitasUseCase` a reactivo: `Mono<Cita> reservar(...)`, `Flux<Cita> citasDelDia(LocalDate fecha)`. Agregá un operador que, si `citasDelDia` no emite nada en 2 segundos (simulando latencia), devuelva un valor por defecto (`Mono.empty()` transformado con `.switchIfEmpty` o `.timeout` + fallback).

## Qué debés entregar

Código + explicación de qué pasa con la suscripción si nadie llama `.subscribe()`.

## Cómo sabés que lo dominás

¿Podés explicar la diferencia entre que tu método retorne `Mono<Cita>` vacío por diseño vs que lance una excepción, y cuándo usarías cada uno?

## Explicación técnica del concepto

La programación reactiva declara qué hacer cuando un dato esté disponible (`Mono`, `Flux`) en vez de bloquear un hilo esperando ese dato, lo que permite manejar más operaciones de I/O concurrentes con menos hilos. No es una mejora universal: para lógica sin I/O significativo, el modelo imperativo es más simple de leer y depurar. La decisión de migrar depende de si el cuello de botella real es la espera por I/O, no de preferencia estilística.

## Cómo cerré esta brecha (mi implementación)

*Completo esto yo mismo cuando termine el reto — no antes. Con mi código real ya escrito, respondo acá (no sobre el enunciado, sobre mi implementación):*

- *¿Qué clases/métodos concretos escribí y qué responsabilidad tiene cada uno?*
- *¿Cómo mi código, específicamente, resuelve el concepto de este reto? Cito mis propias clases y métodos, no la teoría.*
- *¿Qué bug o mal entendido tuve en el camino, y cómo lo corregí? (revisar esto antes de la entrevista me sirve más que repasar la teoría de nuevo).*

## 🎯 Con tu evaluador

Este es EL tema donde tiene más autoridad de todo tu assessment — lideró personalmente la migración de POO a programación funcional con Spring WebFlux. No te va a preguntar "qué es un Mono" — te va a preguntar por los dolores reales de esa migración: qué se vuelve difícil de leer/debuggear en reactivo, y cómo decidiste (o decidirías) qué sí migrar a reactivo y qué no. Tené una opinión propia, no solo la teoría.

## SDD — Spec-Driven Development

Antes de tocar código en este reto, escribí (alcanza con 3-5 líneas, en un comentario o en un README aparte) la especificación de lo que vas a construir: qué clases/métodos necesitás, el contrato de cada uno (entradas, salidas, casos borde) y la regla de negocio que cubre — el "qué" antes del "cómo". Es la misma disciplina que separa TDD (diseñás guiado por tests que escribís vos) de SDD (diseñás guiado por una spec escrita, para vos mismo o para que una IA la ejecute): la decisión de diseño se toma **antes** de escribir la primera línea, no se descubre a medida que tecleás. Encaja directo con el feedback de tus evaluadores: podés usar la IA para redactar o pulir esa spec, pero la decisión de qué debe hacer cada pieza es tuya, no de la IA — spec en mano, después sí generás o escribís el código.

---
