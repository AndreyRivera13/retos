# Reto 9 — Protocolo adicional a REST

**Nivel que evalúa:** Advanced

**Estado:** 🔲 Sin empezar

## Para qué te sirve este reto

Cierra el concepto de comunicación con estado y bidireccional, la otra cara de REST: acá el servidor le habla al cliente sin que este pregunte primero, y hay que manejar conexión/desconexión. Si tus integraciones hoy son solo REST síncrono, este reto te obliga a pensar en el caso que más se aleja de tu día a día.

## Enunciado

Implementá un servidor WebSocket de notificaciones: cuando un cliente se conecta, recibe un mensaje de bienvenida; cuando cualquier cliente manda un mensaje tipo `{"tipo":"alerta","texto":"..."}`, todos los demás clientes conectados lo reciben, pero si el tipo es `{"tipo":"privado","destino":"id","texto":"..."}` solo le llega al cliente con ese id de sesión.

## Qué debés entregar

Código del servidor + captura o log mostrando el broadcast y el mensaje privado funcionando con al menos 3 clientes.

## Cómo sabés que lo dominás

¿Podés explicar cómo garantizás que la lista de sesiones conectadas es segura ante acceso concurrente, y qué pasa si un cliente se desconecta abruptamente sin `onClose`?

## Explicación técnica del concepto

WebSocket mantiene una conexión persistente y con estado, a diferencia de REST, donde cada petición es independiente y sin estado. Esto habilita que el servidor inicie el envío de datos (broadcast, mensajes privados) sin que el cliente pregunte primero, pero introduce dos problemas que REST no tiene: acceso concurrente a la lista de sesiones conectadas, y desconexiones abruptas que no disparan `onClose` y pueden dejar sesiones huérfanas si no se manejan explícitamente.

## Cómo cerré esta brecha (mi implementación)

*Completo esto yo mismo cuando termine el reto — no antes. Con mi código real ya escrito, respondo acá (no sobre el enunciado, sobre mi implementación):*

- *¿Qué clases/métodos concretos escribí y qué responsabilidad tiene cada uno?*
- *¿Cómo mi código, específicamente, resuelve el concepto de este reto? Cito mis propias clases y métodos, no la teoría.*
- *¿Qué bug o mal entendido tuve en el camino, y cómo lo corregí? (revisar esto antes de la entrevista me sirve más que repasar la teoría de nuevo).*

## 🎯 Con tu evaluador

Su experiencia fuerte en protocolos es SOAP (core bancario legado) + REST/APIs para integraciones B2B, no WebSocket puntualmente. Probablemente no se quede en el WebSocket en sí — es más probable que te compare: "¿por qué aquí SÍ necesitás algo con estado/bidireccional y en tu integración B2B no?" (esa es la pregunta que él mismo se hizo migrando SOAP legado hacia APIs modernas).

## SDD — Spec-Driven Development

Antes de tocar código en este reto, escribí (alcanza con 3-5 líneas, en un comentario o en un README aparte) la especificación de lo que vas a construir: qué clases/métodos necesitás, el contrato de cada uno (entradas, salidas, casos borde) y la regla de negocio que cubre — el "qué" antes del "cómo". Es la misma disciplina que separa TDD (diseñás guiado por tests que escribís vos) de SDD (diseñás guiado por una spec escrita, para vos mismo o para que una IA la ejecute): la decisión de diseño se toma **antes** de escribir la primera línea, no se descubre a medida que tecleás. Encaja directo con el feedback de tus evaluadores: podés usar la IA para redactar o pulir esa spec, pero la decisión de qué debe hacer cada pieza es tuya, no de la IA — spec en mano, después sí generás o escribís el código.

---
