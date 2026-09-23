# Reto 2 — Código limpio (SOLID/KISS/YAGNI/DRY)

**Nivel que evalúa:** Trainer

**Estado:** ✅ Cerrado — `ReglaEdad`, `ReglaIngresos`, `ReglaHistorial` y `ValidadorSolicitud` implementados y revisados (se corrigieron dos bugs de la primera versión: `ReglaHistorial` no leía `isHistorialCrediticioOk()`, y `ReglaEdad` usaba `>` en vez de `>=`). Verificado 2026-09-23.

## Para qué te sirve este reto

Cierra el concepto de SRP y OCP: una clase con una sola razón para cambiar, y abierta a extensión sin modificar lo que ya funciona. Es la misma pregunta que te hacés cuando un método de un servicio Spring Boot termina validando, llamando al repositorio y armando la respuesta HTTP todo junto — eso es una violación de SRP en tu propio código, no solo en el reto.

## Enunciado

Tenés (o escribís) una clase `ValidadorSolicitud` con un método de 40 líneas que valida un formulario de crédito: revisa edad, ingresos, historial, y según el resultado arma un mensaje, lo loguea, y lo envía por email — todo ahí mismo, con `if/else` anidados. Refactorizala en al menos 3 clases nuevas aplicando SRP y OCP (usá una lista de `ReglaValidacion` como Strategy en vez de `if/else`).

## Qué debés entregar

Código antes/después (aunque sea el "antes" resumido) + tabla de 3 columnas: principio aplicado, qué cambiaste, qué se rompería si no lo hubieras hecho.

## Cómo sabés que lo dominás

¿Podés agregar una regla de validación nueva creando solo una clase, sin tocar ninguna de las existentes?

## Explicación técnica del concepto

SRP establece que una clase debe tener una sola razón para cambiar. Un método que valida, loguea y notifica en un solo bloque tiene tres razones para cambiar, no una — por eso se separa en una regla por responsabilidad (`ReglaEdad`, `ReglaIngresos`, `ReglaHistorial`). OCP se cumple porque el punto de extensión es la lista de reglas: agregar una regla nueva es agregar una clase, no modificar `ValidadorSolicitud`. El riesgo típico en la entrevista es quedarse en la definición del principio sin poder señalar, en código real, cuál se rompió y por qué el rediseño lo corrige.

## Cómo cerré esta brecha (mi implementación)

Ya implementé el patrón completo. Mi `ValidadorSolicitud.validar()` recorre la lista de `ReglaValidacion` que recibe por constructor, corta apenas encuentra una regla que no aprueba (`if (!resultado.isAprobada()) return resultado;`) y solo si todas pasan devuelve el resultado aprobado. La regla de negocio nunca vive en el orquestador — cada `ReglaXxx` valida un solo dato de `Solicitud` (edad, ingresos o historial) y no sabe nada de las otras dos.

Con esto cerré los dos principios que pedía el reto, en mi propio código:

- **SRP**: `ReglaEdad`, `ReglaIngresos` y `ReglaHistorial` tienen cada una una sola razón para cambiar. Si mañana el umbral de ingresos sube a 2.000.000, toco solo `ReglaIngresos` — nada más se ve afectado. `ValidadorSolicitud` tiene su propia única razón para cambiar: cómo se orquesta el recorrido (por ejemplo, si algún día necesito acumular todos los errores en vez de cortar en el primero), no qué reglas existen.
- **OCP**: para agregar una regla nueva —por ejemplo `ReglaMontoMaximo`— creo una clase que implemente `ReglaValidacion` y la agrego a la lista que le paso al constructor. No toco `ValidadorSolicitud` ni ninguna regla existente. El punto de extensión es exactamente ese `List<ReglaValidacion> reglas` recibido por constructor.

Vale la pena que recuerde los dos bugs que tuve en la primera versión, porque son el mismo tipo de error y probablemente me los vuelva a encontrar: `ReglaHistorial` devolvía `true` sin leer `isHistorialCrediticioOk()` — una regla que "aprobaba" sin validar nada — y `ReglaEdad` usaba `>` en vez de `>=`, rechazando a alguien de exactamente 18 años que el enunciado sí aprueba. La interfaz `ReglaValidacion` me obliga a implementar los métodos, pero no garantiza que la lógica de adentro sea correcta — eso solo lo valida una prueba con el caso borde real, no que el código compile.

## 🎯 Con tu evaluador

SOLID + Patrones de Diseño está en el stack técnico de sus dos roles como Technical Leader — no lo va a dejar en "sabe qué significa la S de SRP". Prepárate para que te pida nombrar, EN TU CÓDIGO, cuál principio rompiste primero (antes de refactorizar) y por qué el Strategy es mejor que solo dividir el método en 3 sin cambiar el diseño.

## SDD — Spec-Driven Development

Antes de tocar código en este reto, escribí (alcanza con 3-5 líneas, en un comentario o en un README aparte) la especificación de lo que vas a construir: qué clases/métodos necesitás, el contrato de cada uno (entradas, salidas, casos borde) y la regla de negocio que cubre — el "qué" antes del "cómo". Es la misma disciplina que separa TDD (diseñás guiado por tests que escribís vos) de SDD (diseñás guiado por una spec escrita, para vos mismo o para que una IA la ejecute): la decisión de diseño se toma **antes** de escribir la primera línea, no se descubre a medida que tecleás. Encaja directo con el feedback de tus evaluadores: podés usar la IA para redactar o pulir esa spec, pero la decisión de qué debe hacer cada pieza es tuya, no de la IA — spec en mano, después sí generás o escribís el código.

---
