# Reto 9 — Protocolo adicional a REST

**Nivel que evalúa:** Advanced

**Estado:** ✅ Cerrado — verificado el 2026-09-29.

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

Implementé `SesionesRegistro` con un `CopyOnWriteArraySet<Session>` para que el acceso concurrente a la lista de sesiones no necesite sincronización manual — está pensado para muchas lecturas (broadcast) y pocas escrituras (conectar/desconectar), que es justo el patrón de este reto. En `NotificacionesEndpoint`, `onOpen` registra la sesión y manda el mensaje de bienvenida; `onMessage` distingue "alerta" (broadcast a todos menos el remitente) de "privado" (busca la sesión por `session.getId()` y le manda solo a esa).

Tuve dos huecos reales que corregí: el broadcast de "alerta" al principio incluía al remitente —lo excluí filtrando `!s.equals(session)` antes del `forEach`. Y el envío en el broadcast no tenía manejo de sesión caída: si un cliente se desconecta abruptamente sin disparar `onClose`, `sendText` a esa sesión lanza excepción, y sin un try/catch por sesión esa excepción cortaba el resto del broadcast a mitad de camino. Lo resolví envolviendo el `sendText` de cada sesión en su propio try/catch, sacando del registro (`sesiones.quitar(s)`) la que falla, en vez de dejar que una sesión muerta tumbe el envío a las demás.

## 🎯 Con tu evaluador

Su experiencia fuerte en protocolos es SOAP (core bancario legado) + REST/APIs para integraciones B2B, no WebSocket puntualmente. Probablemente no se quede en el WebSocket en sí — es más probable que te compare: "¿por qué aquí SÍ necesitás algo con estado/bidireccional y en tu integración B2B no?" (esa es la pregunta que él mismo se hizo migrando SOAP legado hacia APIs modernas).

## SDD — Spec-Driven Development

Antes de tocar código en este reto, escribí (alcanza con 3-5 líneas, en un comentario o en un README aparte) la especificación de lo que vas a construir: qué clases/métodos necesitás, el contrato de cada uno (entradas, salidas, casos borde) y la regla de negocio que cubre — el "qué" antes del "cómo". Es la misma disciplina que separa TDD (diseñás guiado por tests que escribís vos) de SDD (diseñás guiado por una spec escrita, para vos mismo o para que una IA la ejecute): la decisión de diseño se toma **antes** de escribir la primera línea, no se descubre a medida que tecleás. Encaja directo con el feedback de tus evaluadores: podés usar la IA para redactar o pulir esa spec, pero la decisión de qué debe hacer cada pieza es tuya, no de la IA — spec en mano, después sí generás o escribís el código.

---
