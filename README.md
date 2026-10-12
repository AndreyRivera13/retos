# Retos del assessment Pragma — Brayam Oliveros

26 retos, uno por tema del assessment (de Trainer a Master), cada uno en su propia carpeta numerada. Los retos 1 al 19 son proyectos Gradle (scaffold Bancolombia, Clean Architecture) que se abren y se corren por separado; algunos del 17 en adelante son solo documentos, IaC o SQL.

## Por dónde empezar

**Todo el contenido de estudio está en un solo archivo: [`GUIA_ASSESSMENT.md`](GUIA_ASSESSMENT.md).** Por cada reto trae la frase para abrirlo en la entrevista, el concepto que debes poder explicar, el enunciado y la entrega, cómo lo resolviste, la evidencia en Entitlement y preguntas para practicar (con respuestas modelo en Master).

| Quiero... | Voy a... |
|---|---|
| Explicar un reto en la entrevista | `GUIA_ASSESSMENT.md`, "Mapa rápido", y la parte 1 del reto |
| Estudiar un concepto | `GUIA_ASSESSMENT.md`, parte 2 del reto |
| Resolver o repasar un reto | La carpeta numerada y su `README.md` |
| Saber qué temas pregunta más mi evaluador | `GUIA_ASSESSMENT.md`, "Tu evaluador real" |

## Estructura de la carpeta

```
retos/
├── README.md                 <- este archivo
├── GUIA_ASSESSMENT.md        <- guía única: conceptos, retos, entrevista y repaso Master
├── _anteriores/              <- los archivos que se unificaron en la guía (por si necesitas consultarlos)
├── 1-POO/ ... 19-DDD/        <- proyectos Gradle por reto (Trainer a Senior)
└── 20-Documentacion/ ... 26-Arquitectura-Datos/   <- retos Master (docs, IaC, SQL)
```

Cada carpeta de reto trae su propio `README.md` con el enunciado, qué entregar, cómo saber que lo dominas y tu explicación de cómo lo cerraste.

## Estado de los retos

**Regla del juego:** todo lo que dice `// TODO` es tuyo. Las clases sin TODO (DTOs simples, `MainApplication`, `UseCasesConfig`, configuración de WebSocket) están completas a propósito: son plomería de framework, no el concepto que se evalúa. No mires los "Ejemplo en otro dominio" de la guía hasta haber intentado tu propia solución. Cuando cierres un reto, marca el estado en el `README.md` de su carpeta y en esta tabla.

| # | Carpeta | Módulos | Estado |
|---|---|---|---|
| 1 | `1-POO` | model + app-service | ✅ Cerrado — verificado el 2026-09-23 |
| 2 | `2-SOLID` | model + app-service | ✅ Cerrado — verificado el 2026-09-23 |
| 3 | `3-Excepciones` | model + app-service | ✅ Cerrado — verificado el 2026-09-29 |
| 4 | `4-Estructuras-Datos` | model + app-service | ✅ Cerrado — verificado el 2026-09-29 |
| 5 | `5-Arquitectura-Capas` | model + usecase + memory-repository + simple-controller | ✅ Cerrado — verificado el 2026-09-29 |
| 6 | `6-GRASP` | model + usecase | ✅ Cerrado — verificado el 2026-09-29 |
| 7 | `7-Normalizacion-BD` | solo SQL/README (sin Gradle) | ✅ Cerrado — verificado el 2026-09-29 |
| 8 | `8-Uso-IA` | solo README (sin código) | ✅ Cerrado — verificado el 2026-09-23 |
| 9 | `9-WebSocket` | model + websocket-endpoint | ✅ Cerrado — verificado el 2026-09-29 |
| 10 | `10-Cache` | model + usecase | ✅ Cerrado — verificado el 2026-10-02 |
| 11 | `11-Clean-Architecture` | model + usecase + memory-adapter + jpa-adapter | ✅ Cerrado — verificado el 2026-10-02 |
| 12 | `12-GoF` | model + app-service | ✅ Cerrado — verificado el 2026-10-03 |
| 13 | `13-Resiliencia` | model + usecase + pasarela-pagos | ✅ Cerrado — verificado el 2026-10-03 |
| 14 | `14-BDD` | model + usecase + features/steps Cucumber | ✅ Cerrado — verificado el 2026-10-03 |
| 15 | `15-Reactivo` | model + usecase | ✅ Cerrado — verificado el 2026-10-03 |
| 16 | `16-EDA` | model + kafka-producer + kafka-listener | ✅ Cerrado — verificado el 2026-10-03 |
| 17 | `17-IaC` | solo .tf/.yml (sin Gradle) | ✅ Cerrado — verificado el 2026-10-03 |
| 18 | `18-OWASP` | model + rest-documentos + memory-documentos | ✅ Cerrado — verificado el 2026-10-03 |
| 19 | `19-DDD` | model + app-service | ✅ Cerrado — verificado el 2026-10-03 |
| 20 | `20-Documentacion` | solo docs (Mermaid, ADR) | 🔲 TODO |
| 21 | `21-Cloud-Infraestructura` | solo .tf (sin Gradle) | 🔲 TODO |
| 22 | `22-DevOps-IaC` | CloudFormation + k6 + pipeline + script de IA | 🔲 TODO |
| 23 | `23-Seguridad-Arquitectura` | copia de 18-OWASP (Gradle) + docs | 🔲 TODO |
| 24 | `24-Diseno-Arquitectura` | solo docs + fitness function | 🔲 TODO |
| 25 | `25-Observabilidad` | copia de 13-Resiliencia (Gradle) + Prometheus/Grafana | 🔲 TODO |
| 26 | `26-Arquitectura-Datos` | solo SQL + docs (Postgres) | 🔲 TODO |

Los retos 20 al 26 son de nivel Master: más de diseño y evidencia que de código. Los retos 23 y 25 traen una copia de `18-OWASP` y `13-Resiliencia` en su subcarpeta `app/` para trabajar sin tocar los originales.

## Cómo trabajar cada reto

1. Abre la carpeta del reto en tu IDE (cada una es un proyecto Gradle independiente, con su propio `gradlew`).
2. Escribe antes la spec (SDD): 3 a 5 líneas con las clases o archivos que necesitas, el contrato de cada uno y la regla de negocio que cubre.
3. Lee el Javadoc del `// TODO` en cada clase: ahí está la pista, no la solución.
4. Implementa y corre `./gradlew test` (o `./gradlew build`) para validar que compila y pasa.
5. Antes de cerrarlo, responde en voz alta, en menos de 2 minutos, el "Demuestra dominio si" de ese reto en la guía. Si no puedes, el hueco está en el concepto, no en el código.

## Notas

- Los `ArchitectureTest.java` y `Utils.java` de `1-POO` (generados por el scaffold de Bancolombia) no se replicaron en los demás retos. Si quieres esa validación de arquitectura, corre el scaffold oficial dentro de la carpeta del reto.
- `13-Resiliencia`, `16-EDA` y `9-WebSocket` traen las dependencias de Resilience4j, Kafka y WebSocket declaradas en su `build.gradle`; solo implementas la lógica.
- `16-EDA` necesita un Kafka corriendo (local o Docker) solo para correrlo de extremo a extremo; para compilar e implementar la lógica no hace falta.
