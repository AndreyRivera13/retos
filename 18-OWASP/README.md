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

<!-- ENTITLEMENT:18:START -->
## Ejemplo fácil de explicar

IDOR (CWE-639): el usuario 7 pide `GET /documentos/8` y el sistema se lo entrega porque solo verificó que **está logueado** (autenticación), no que **el documento es suyo** (autorización).

```java
Documento d = repo.buscar(id);
if (!d.duenoId().equals(usuarioAutenticado.id())) throw new AccesoDenegado();   // el chequeo que faltaba
```
Cuándo NO sirve como arreglo: poner rate limiting. Limita la velocidad del ataque, no cierra el hueco.

## Cómo lo trabajamos en Entitlement (micros)

Evidencia del código real de los micros (rutas relativas a `Bancolombia/Micros/`). Es lo que hace el equipo; cuenta qué parte hiciste tú y cuál es del equipo.

- **Autenticación en el borde (Istio), no en Spring Security:** `ms_actors/deployment/k8s/authorization_policy.yaml` define `RequestAuthentication` con `jwtRules` (issuer + jwksUri) y `AuthorizationPolicy action: ALLOW` con `requestPrincipals`, claim `scope`, métodos y paths. Mismo patrón en `ms_admin_authorization`, `Monetary_Limits_MR/*` y `ms_retrieve_role`. No hay `spring-boot-starter-security`, `@PreAuthorize` ni `SecurityWebFilterChain`.
- **Autorización de negocio en código (el corazón de Entitlement):** `PermissionsUseCase.getPermission` (`ms_admin_authorization`): `findRelation` (exige la funcionalidad `MANAGEMENT_ENTITLEMENT`) → `validateRole` (rechaza `CON` y `ADA`) → `validatePrivilege` (solo admite 3 privilegios en un `Set.of`). Errores como `BusinessException(PERMISSIONS_NOT_PRIVILEGES_02_ERROR)`. Esta es **exactamente la diferencia autenticación vs autorización** que tu evaluador suele probar.
- **Validación de entrada:** value objects con invariantes (`Email` con regex y `maxLength 250`, `MessageId` UUID), `jakarta.validation` (`@NotNull`) en `InitTransactionHttpRequest`, validadores manuales (`CreateRoleValidation`); en Elixir `request_validation.ex` (largo y patrón de headers).
- **Cabeceras y CORS:** `SecurityHeadersConfig implements WebFilter` (`Strict-Transport-Security`, `X-Content-Type-Options: nosniff`, `Cache-Control: no-store`, quita `Server`); `CorsConfig` con orígenes configurables.
- **Secretos:** no van en configmaps, solo nombres (`aws.secret.rds`, `aws.redis.secretName`…); se leen de AWS Secrets Manager / Parameter Store.
- **Hallazgos para mencionar con criterio:** `ms_retrieve_role` (Elixir) conecta a Postgres con `ssl: true` pero `verify: :verify_none` (cifra pero no valida el certificado); `CorsConfig` usa `allowCredentials=true` con `allowedHeaders ALL`, vigilar que los orígenes sean explícitos.

**No encontrado en los micros (no lo afirmes como experiencia del proyecto):**

- Spring Security / `@PreAuthorize`: NO ENCONTRADO. SAST/DAST explícitos: solo Sonar y el DevSecOps Engine (no se ve su configuración).

**Cómo contarlo en la entrevista:** Autenticación la resuelve Istio (JWT); autorización la resuelve `PermissionsUseCase`. Es una respuesta sólida y específica.
<!-- ENTITLEMENT:18:END -->

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
