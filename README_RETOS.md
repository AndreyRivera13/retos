# Estructura de retos — Assessment Pragma

Un proyecto Gradle (scaffold Bancolombia, Clean Architecture) por reto,
igual al patrón que ya usaste en `1-POO`. Cada uno se abre y se corre
independiente (`./gradlew build` desde su propia carpeta).

**Regla del juego:** todo lo que dice `// TODO` es tuyo. Las clases sin TODO
(DTOs simples, MainApplication, UseCasesConfig, config de WebSocket) ya
están completas a propósito — son plomería de framework, no el concepto que
te están evaluando. El enunciado completo de cada reto está en
`Retos_Assessment_Andrey.md`; no mires los "Ejemplo (dominio distinto)" de
ahí hasta haber intentado tu propia solución.

| # | Carpeta | Módulos | Estado |
|---|---|---|---|
| 1 | `1-POO` | model + app-service | ✅ Ya lo hice|
| 2 | `2-SOLID` | model + app-service | 🔲 TODO |
| 3 | `3-Excepciones` | model + app-service | 🔲 TODO |
| 4 | `4-Estructuras-Datos` | model + app-service | 🔲 TODO |
| 5 | `5-Arquitectura-Capas` | model + usecase + memory-repository + simple-controller | 🔲 TODO |
| 6 | `6-GRASP` | model + usecase | 🔲 TODO |
| 7 | `7-Normalizacion-BD` | solo SQL/README (sin Gradle) | 🔲 TODO |
| 8 | `8-Uso-IA` | solo README (sin código) | 🔲 TODO |
| 9 | `9-WebSocket` | model + websocket-endpoint | 🔲 TODO |
| 10 | `10-Cache` | model + usecase | 🔲 TODO |
| 11 | `11-Clean-Architecture` | model + usecase + memory-adapter + jpa-adapter | 🔲 TODO |
| 12 | `12-GoF` | model + app-service | 🔲 TODO |
| 13 | `13-Resiliencia` | model + usecase + pasarela-pagos | 🔲 TODO |
| 14 | `14-BDD` | model + usecase + features/steps Cucumber | 🔲 TODO |
| 15 | `15-Reactivo` | model + usecase | 🔲 TODO |
| 16 | `16-EDA` | model + kafka-producer + kafka-listener | 🔲 TODO |
| 17 | `17-IaC` | solo .tf/.yml (sin Gradle) | 🔲 TODO |
| 18 | `18-OWASP` | model + rest-documentos | 🔲 TODO |
| 19 | `19-DDD` | model + app-service | 🔲 TODO |

## Cómo trabajar cada reto

1. Abre la carpeta del reto en tu IDE (cada una es un proyecto Gradle
   independiente, con su propio `gradlew`).
2. Lee el Javadoc del `// TODO` en cada clase — ahí está la pista, no la
   solución.
3. Implementa. Corre `./gradlew test` (o `./gradlew build`) para validar
   que compila.
4. Antes de dar el reto por cerrado, responde en voz alta o por escrito la
   pregunta de "Demuestra dominio si..." de `Retos_Assessment_Andrey.md`
   para ese tema — si no puedes responderla en <2 min, el hueco está en el
   concepto, no en el código.

## Notas

- Los `ArchitectureTest.java`/`Utils.java` que ves en `1-POO` (generados por
  el scaffold real de Bancolombia) no se replicaron en los demás retos para
  no arrastrar un archivo grande sin adaptar — si quieres esa validación de
  arquitectura, corre el scaffold oficial dentro de la carpeta del reto.
- `13-Resiliencia`, `16-EDA` y `9-WebSocket` traen las dependencias de
  Resilience4j / Kafka / WebSocket ya declaradas en el `build.gradle`
  correspondiente — no necesitas agregarlas, solo implementar la lógica.
- `16-EDA` necesita un Kafka corriendo (local o Docker) solo si quieres
  correrlo de extremo a extremo; para compilar e implementar la lógica no
  hace falta.
