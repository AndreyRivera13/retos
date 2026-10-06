# Reto 14 — Pruebas BDD (Cucumber/Karate)

**Nivel que evalúa:** Senior

**Estado:** ✅ Cerrado — verificado el 2026-10-03 (3 escenarios Cucumber en verde + ArchitectureTest, prueba manual en MainApplication).

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

<!-- ENTITLEMENT:14:START -->
## Ejemplo fácil de explicar

BDD escribe el requisito como una historia que el negocio entiende y que además se ejecuta:

```gherkin
Escenario: Saldo insuficiente
  Dado una cuenta con saldo 100
  Cuando intento retirar 150
  Entonces el sistema rechaza el retiro
```
TDD prueba el *código* desde el desarrollador; BDD prueba el *comportamiento* en lenguaje de negocio. Cuándo NO: lógica interna trivial, ahí un test unitario basta.

## Cómo lo trabajamos en Entitlement (micros)

Evidencia del código real de los micros (rutas relativas a `Bancolombia/Micros/`). Es lo que hace el equipo; cuenta qué parte hiciste tú y cuál es del equipo.

- **BDD con Karate (sintaxis Gherkin), no Cucumber:** `.feature` con `Given/When/Then` y tags `@SmokeTest`, `@rutaCritica`, p. ej. `Monetary_Limits_MR/ms_limit_create/deployment/acceptance-test/.../CreateBaasLimit.feature`; runner `CreateProductGroupRunner` (`@Karate.Test`) en `ms_products`. Dependencias `karate-junit5` y `cucumber-reporting` (solo reportes).
- **Pruebas de aceptación** en `deployment/acceptance-test` de cada micro (Serenity, SoapUI en `ms_actors`) y de rendimiento con JMeter en `deployment/performance-test`.
- **Unitarias:** JUnit 5 + Mockito + `StepVerifier`, p. ej. `GetSchemeClientUseCaseTest` (`@ExtendWith(MockitoExtension.class)`, `@ParameterizedTest`, `expectErrorSatisfies(... BusinessException ...)`).
- **Calidad:** Jacoco (`jacocoMergedReport`), **mutation testing con Pitest** y Sonar con `sonar.coverage.jacoco.xmlReportPaths`; Sonar excluye `MainApplication` y `config/**`.

**No encontrado en los micros (no lo afirmes como experiencia del proyecto):**

- `io.cucumber` no aparece en ningún `.gradle`: lo tuyo en equipo es Karate; Cucumber lo practicas en el reto 14.

**Cómo contarlo en la entrevista:** "Usamos Karate, que habla Gherkin, para aceptación; y Pitest para saber si los tests realmente detectan fallos". Lo de Pitest suele impresionar.
<!-- ENTITLEMENT:14:END -->

## Cómo cerré esta brecha (mi implementación)

Escribí `reservar_cita.feature` en español (`# language: es`) con los tres escenarios del reto: reserva exitosa, horario ya ocupado y doctor inexistente. Usé Dado/Cuando/Entonces con frases de negocio ("el doctor dra-lopez ya tiene una cita a las..."), de modo que alguien no técnico pueda leerlo sin ver código. `ReservarCitaSteps` conecta cada frase con el `GestionarCitasUseCase` real y guarda la excepción capturada en un campo para verificarla en el `Entonces`, igual que el patrón del documento de retos.

El use case lo tuve que implementar yo: `reservar()` valida primero que el doctor exista (lanza `IllegalArgumentException("Doctor no existe")`), luego que el horario esté libre (`IllegalStateException("Horario ocupado")`) y solo entonces guarda la cita. Hice `getCitas()` devolver una copia inmutable para que el test no pueda alterar el estado interno. Cucumber crea una instancia nueva de los steps por escenario, así que cada escenario arranca con un use case vacío y no hay estado compartido entre ellos.

El único tropiezo fue de build: el `ArchitectureTest` que inyecta el plugin de Clean Architecture necesita `tools.jackson.core:jackson-databind` en el módulo de pruebas y sin eso `compileTestJava` fallaba. Verifiqué los 3 escenarios en verde y agregué una prueba manual en `MainApplication` con los mismos 3 casos.

## SDD — Spec-Driven Development

Antes de tocar código en este reto, escribí (alcanza con 3-5 líneas, en un comentario o en un README aparte) la especificación de lo que vas a construir: qué clases/métodos necesitás, el contrato de cada uno (entradas, salidas, casos borde) y la regla de negocio que cubre — el "qué" antes del "cómo". Es la misma disciplina que separa TDD (diseñás guiado por tests que escribís vos) de SDD (diseñás guiado por una spec escrita, para vos mismo o para que una IA la ejecute): la decisión de diseño se toma **antes** de escribir la primera línea, no se descubre a medida que tecleás. Encaja directo con el feedback de tus evaluadores: podés usar la IA para redactar o pulir esa spec, pero la decisión de qué debe hacer cada pieza es tuya, no de la IA — spec en mano, después sí generás o escribís el código.

---
