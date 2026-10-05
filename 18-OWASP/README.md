# Reto 18 — Seguridad — OWASP Top 10 / CWE

**Nivel que evalúa:** Senior

**Estado:** ✅ Cerrado — verificado el 2026-10-03 (4 tests de control de acceso por HTTP + ArchitectureTest en verde, prueba manual en MainApplication).

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

La vulnerabilidad del endpoint `GET /usuarios/{id}/documentos` es control de acceso roto: OWASP Top 10 2021 A01 (Broken Access Control). El CWE más preciso es CWE-639 (autorización eludida mediante una clave controlada por el usuario, lo que comúnmente se llama IDOR), dentro de la familia CWE-284/CWE-862 (autorización ausente). El vector de ataque: un usuario legítimo se autentica, ve que su URL es `/usuarios/1/documentos`, cambia el 1 por 2, 3, 4... y el servidor le devuelve los documentos de otras personas, porque solo verifica que el token sea válido, no que el recurso le pertenezca.

La corrección está en `DocumentoController.ver()`: ahora recibe el `Authentication` y compara `autenticado.getName()` con el `id` pedido; si no es el dueño lanza `AccessDeniedException`, que Spring Security traduce a 403. El nombre del principal es el id del usuario (en un JWT real sería el claim `sub`). La alternativa declarativa sería `@PreAuthorize("#id.toString() == authentication.name")` con `@EnableMethodSecurity`; dejé el chequeo explícito porque es más fácil de leer y de probar. Para que el proyecto corra agregué un `SecurityConfig` (todo requiere autenticación, HTTP Basic y dos usuarios de demo con contraseña cifrada con BCrypt) y un adaptador en memoria `DocumentoRepositoryMemoria`, en su propio módulo.

Lo probé con 4 tests por HTTP real: el dueño recibe 200 y solo ve sus documentos, un usuario autenticado que pide los de otro recibe 403, sin credenciales 401 y con clave incorrecta 401. Hallazgo real: el controlador falló con 500 ("Name for argument of type [Long] not specified") porque ese módulo se compila sin el flag `-parameters`; lo resolví con `@PathVariable("id")` explícito.

La diferencia que me van a preguntar: autenticación rota es no poder verificar quién eres (contraseñas débiles, tokens mal validados, sin límite de intentos); acá la autenticación funciona perfecto, el token es válido, y el fallo es de autorización, que no se verifica que ese usuario autenticado tenga permiso sobre ese recurso concreto. Un JWT válido resuelve la primera pregunta, no la segunda. Un tema abierto: un 403 revela que el recurso existe; para algunos casos se prefiere responder 404.

## 🎯 Con tu evaluador

JWT aparece explícito en el stack de sus dos trabajos como Technical Leader, así que conoce bien el terreno de autenticación — justo por eso es más probable que te tienda la trampa de confundir autenticación (JWT válido) con autorización (dueño del recurso), que es exactamente la distinción que pide este reto. No te quedes en "faltaba validar el JWT": el JWT SÍ es válido, el problema es otro.

## SDD — Spec-Driven Development

Antes de tocar código en este reto, escribí (alcanza con 3-5 líneas, en un comentario o en un README aparte) la especificación de lo que vas a construir: qué clases/métodos necesitás, el contrato de cada uno (entradas, salidas, casos borde) y la regla de negocio que cubre — el "qué" antes del "cómo". Es la misma disciplina que separa TDD (diseñás guiado por tests que escribís vos) de SDD (diseñás guiado por una spec escrita, para vos mismo o para que una IA la ejecute): la decisión de diseño se toma **antes** de escribir la primera línea, no se descubre a medida que tecleás. Encaja directo con el feedback de tus evaluadores: podés usar la IA para redactar o pulir esa spec, pero la decisión de qué debe hacer cada pieza es tuya, no de la IA — spec en mano, después sí generás o escribís el código.

---
