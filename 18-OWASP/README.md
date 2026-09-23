# Reto 18 — Seguridad — OWASP Top 10 / CWE

**Nivel que evalúa:** Senior

**Estado:** 🔲 Sin empezar

## Para qué te sirve este reto

Cierra el concepto de autenticación vs autorización: que un JWT sea válido no significa que quien lo tiene pueda acceder a ese recurso puntual. Es el corazón de Entitlement — acá te toca distinguir esas dos cosas con precisión, que es donde la mayoría se confunde.

## Enunciado

Te dan un endpoint `GET /usuarios/{id}/documentos` sin ninguna verificación de que el usuario autenticado sea el dueño del `id` solicitado. Identificá la vulnerabilidad (categoría OWASP + CWE), explicá el vector de ataque, y corregila.

## Qué debés entregar

Código corregido + identificación de categoría/CWE.

## Cómo sabés que lo dominás

¿Podés explicar la diferencia entre este fallo (control de acceso roto, CWE-284/A01) y una autenticación rota? — muchos los confunden.

## Explicación técnica del concepto

Autenticación verifica identidad (quién es el usuario); autorización verifica permiso sobre un recurso específico (qué puede hacer ese usuario). Un JWT válido resuelve autenticación pero no garantiza autorización — un endpoint que no verifica la relación entre el usuario autenticado y el recurso solicitado tiene control de acceso roto (CWE-284, OWASP A01), independientemente de que el token presentado sea válido.

## Cómo cerré esta brecha (mi implementación)

*Completo esto yo mismo cuando termine el reto — no antes. Con mi código real ya escrito, respondo acá (no sobre el enunciado, sobre mi implementación):*

- *¿Qué clases/métodos concretos escribí y qué responsabilidad tiene cada uno?*
- *¿Cómo mi código, específicamente, resuelve el concepto de este reto? Cito mis propias clases y métodos, no la teoría.*
- *¿Qué bug o mal entendido tuve en el camino, y cómo lo corregí? (revisar esto antes de la entrevista me sirve más que repasar la teoría de nuevo).*

## 🎯 Con tu evaluador

JWT aparece explícito en el stack de sus dos trabajos como Technical Leader, así que conoce bien el terreno de autenticación — justo por eso es más probable que te tienda la trampa de confundir autenticación (JWT válido) con autorización (dueño del recurso), que es exactamente la distinción que pide este reto. No te quedes en "faltaba validar el JWT": el JWT SÍ es válido, el problema es otro.

## SDD — Spec-Driven Development

Antes de tocar código en este reto, escribí (alcanza con 3-5 líneas, en un comentario o en un README aparte) la especificación de lo que vas a construir: qué clases/métodos necesitás, el contrato de cada uno (entradas, salidas, casos borde) y la regla de negocio que cubre — el "qué" antes del "cómo". Es la misma disciplina que separa TDD (diseñás guiado por tests que escribís vos) de SDD (diseñás guiado por una spec escrita, para vos mismo o para que una IA la ejecute): la decisión de diseño se toma **antes** de escribir la primera línea, no se descubre a medida que tecleás. Encaja directo con el feedback de tus evaluadores: podés usar la IA para redactar o pulir esa spec, pero la decisión de qué debe hacer cada pieza es tuya, no de la IA — spec en mano, después sí generás o escribís el código.

---
