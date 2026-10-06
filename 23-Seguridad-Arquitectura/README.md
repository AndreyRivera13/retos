# Reto 23 — Seguridad — tácticas de arquitectura contra ataques

**Nivel que evalúa:** Master

**Área:** Seguridad

**Estado:** 🔲 Sin empezar

## Para qué te sirve este reto

Cierra la diferencia entre corregir una vulnerabilidad (reto 18) y diseñar la arquitectura para que ese tipo de ataque falle, se detecte y se pueda contener. Es exactamente lo que Entitlement necesita: autorización correcta, pero también límites, trazabilidad y respuesta cuando alguien insiste.

## Enunciado

Trabaja sobre la copia de `18-OWASP` que está en `app/`. Aplica al menos dos tácticas de seguridad de categorías distintas —una de resistir ataques y una de detectar ataques, idealmente una tercera de reaccionar— para proteger el endpoint `/usuarios/{id}/documentos` del ataque de enumeración de identificadores. Ejemplos: resistir (limitar tasa de solicitudes por usuario, cabeceras de seguridad, validación de entrada), detectar (registro de auditoría de cada acceso denegado con quién, qué recurso y cuándo, y una alerta tras N denegaciones en una ventana de tiempo), reaccionar (bloqueo temporal del usuario). Demuestra el ataque antes y bloqueado después con una prueba automática que enumera identificadores del 1 al 1000.

## Qué debes entregar

Código en `app/`, la prueba de ataque (copiada desde `pruebas/` a `src/test`) en verde, y `docs/tacticas.md` con la tabla amenaza STRIDE, táctica, categoría, dónde está en el código y cómo se prueba.

## Cómo sabes que lo dominas

¿Qué táctica es de detectar y cuál de resistir, y por qué una no reemplaza a la otra? ¿Qué amenaza STRIDE cubre cada una? ¿Por qué el rate limiting no corrige el IDOR y qué corrige entonces?

## Qué hay en esta carpeta

Todo lo que dice `TODO` es tuyo. Lo demás es plomería para que arranques sin perder tiempo.

- `app/`: Copia de 18-OWASP (Gradle, con sus tests en verde). Aquí aplicas las tácticas.
- `pruebas/AtaqueEnumeracionTest.java.txt`: Esqueleto de la prueba de ataque. Cópialo a `app/applications/app-service/src/test/java/pragma/` como `.java`.
- `docs/tacticas.md`: Tabla STRIDE, táctica, categoría y evidencia.

## Repaso: explicación técnica del concepto

Una táctica de seguridad es una decisión de diseño que influye en la capacidad del sistema de enfrentar un ataque. El catálogo clásico (Bass, Clements y Kazman) las agrupa en cuatro categorías según el momento: resistir ataques (autenticar, autorizar, limitar acceso y exposición, cifrar, validar entrada, separar entidades), detectar ataques (detectar intrusión o denegación de servicio, verificar integridad de mensajes), reaccionar a ataques (revocar acceso, bloquear, notificar) y recuperarse (restaurar y mantener pistas de auditoría que den no repudio). Una arquitectura robusta combina categorías: resistir reduce la superficie, detectar te avisa cuando resistir no bastó, reaccionar contiene el daño.

STRIDE es la forma de decidir qué táctica necesitas: Spoofing (suplantación), Tampering (manipulación), Repudiation (repudio), Information disclosure (divulgación), Denial of service y Elevation of privilege. El IDOR del reto 18 es divulgación de información por elevación de privilegio horizontal; la autorización por dueño lo resuelve en la raíz, pero la enumeración masiva (probar los ids 1 a 1000) sigue siendo una señal de ataque que quieres limitar y ver. Por eso el rate limiting no corrige el IDOR: reduce lo rápido que se puede explotar y te da tiempo, no arregla la falla.

Cuándo NO: limitar la tasa de forma muy agresiva rompe a usuarios legítimos (una aplicación móvil que reintenta); registrar en el log el contenido de los documentos para auditar crea un problema nuevo de privacidad (se audita quién y qué recurso, no el contenido); y una alerta que se dispara diez veces al día se ignora.

## Paso a paso

1. Spec: elige las tácticas, decide el umbral (por ejemplo 20 solicitudes por minuto por usuario, y cuántas denegaciones disparan la alerta) y anótalas en `docs/tacticas.md` antes de programar.
2. Escribe primero la prueba de ataque y verifica que HOY pasa (enumera sin problema): esa es tu evidencia del "antes".
3. Implementa la táctica de resistir (un filtro de Spring o una librería como Bucket4j) y la de detectar (un registro de auditoría estructurado con un contador de denegaciones por usuario).
4. Vuelve a correr la prueba: ahora el atacante debe recibir 429 pasado el umbral y el evento de denegación debe quedar registrado. Si aplicas la tercera, el usuario queda bloqueado un tiempo.
5. Verifica que el usuario legítimo sigue funcionando normal (una prueba que NO debe dar 429).
6. Completa la tabla STRIDE y prepárate para explicar por qué cada táctica está en esa categoría.

## Autoevaluación

Antes de marcar el reto como ✅, responde en menos de 2 minutos, en voz alta o por escrito, las preguntas de repaso de este tema en `REPASO_MASTER.md` (están sin respuesta; las respuestas modelo están al final del archivo). Si te cuesta más que escribir el entregable, el hueco está en el concepto.

## Cómo cerré esta brecha (mi implementación)

*Completo esto yo mismo cuando termine el reto, no antes. Con lo que ya entregué, respondo aquí:*

- *¿Qué archivos y decisiones concretas produje y qué responsabilidad tiene cada uno?*
- *¿Cómo mi entrega, específicamente, resuelve el concepto de este reto? Cito mis propios archivos.*
- *¿Qué error o malentendido tuve en el camino y cómo lo corregí?*
- *¿Qué hice yo y qué hice con ayuda de IA?*

## 🎯 Con tu evaluador

Su experiencia con JWT es real, así que puede preguntarte por la trampa de siempre: que un token válido (autenticación) no resuelve el acceso al recurso (autorización). Si el evaluador de Master es otra persona, esta parte se mantiene igual, pero ya no tengo evidencia de por dónde te va a preguntar.

## SDD — Spec-Driven Development

Antes de producir nada en este reto, escribe (3 a 5 líneas) la especificación de lo que vas a entregar: qué archivos, qué decisión toma cada uno, qué casos borde cubres y cómo vas a verificar que está bien. La decisión de diseño se toma antes de la primera línea, no se descubre mientras escribes. Puedes usar la IA para pulir la spec o para preguntarte lo que se te escapa, pero qué debe hacer cada pieza lo decides tú: es el feedback más repetido de tus evaluadores.

---
