# Reto 22 — DevOps — IaC con dos herramientas, zero trust, rendimiento e IA en DevSecOps

**Nivel que evalúa:** Master

**Área:** DevOps / IaC

**Estado:** 🔲 Sin empezar

## Para qué te sirve este reto

Cierra el salto de "escribí un Terraform" a "dirigí la automatización completa de un esquema seguro": dos herramientas de IaC distintas, controles zero trust verificados por máquina (no por revisión manual), pruebas de rendimiento que rompen el build y un uso criterioso de IA dentro del pipeline. Conecta con tu feedback más recurrente: usar la IA para potenciar tu aprendizaje, no para que decida por ti.

## Enunciado

Toma la infraestructura del reto 21 y entrega: (1) una segunda tecnología de IaC distinta de Terraform (CloudFormation, CDK o SAM) que declare el rol IAM y el almacenamiento con el mismo resultado, y una nota de cuándo escogerías cada herramienta; (2) controles zero trust automatizados como código y validados en el pipeline: sin acceso público (bloqueo de acceso público en S3, sin `0.0.0.0/0`), API con autorizador, un rol por función con permisos mínimos, cifrado en tránsito y en reposo, validados con tfsec/checkov (Terraform) y cfn-lint/cfn_nag o checkov (CloudFormation); (3) una prueba de rendimiento con k6 (o Gatling) con umbrales —por ejemplo p95 menor a 500 ms y tasa de error menor a 1%— que haga fallar el pipeline si no se cumplen; (4) un paso de IA en DevSecOps: un script que toma el JSON de hallazgos de checkov/tfsec, quita cualquier dato sensible, le pide a un modelo que priorice y explique cada hallazgo y deja el resultado como borrador para revisión humana; documenta qué decidió la IA y qué decidió una persona.

## Qué debes entregar

`terraform/` (o el del reto 21), `cloudformation/`, `pipeline.yml`, `k6/citas.js`, `triage-ia/` y `docs/zero-trust.md` (tabla: principio zero trust, control, archivo donde está, cómo se verifica).

## Cómo sabes que lo dominas

¿Qué parte de zero trust automatizaste que una revisión manual se saltaría? ¿Qué NO le delegarías a la IA en el triage de hallazgos y por qué? ¿Por qué un umbral de rendimiento en el pipeline protege más que una prueba de carga que corres a mano antes de salir?

## Qué hay en esta carpeta

Todo lo que dice `TODO` es tuyo. Lo demás es plomería para que arranques sin perder tiempo.

- `cloudformation/almacenamiento-y-rol.yaml`: Rol IAM y almacenamiento en CloudFormation (TODO).
- `k6/citas.js`: Prueba de rendimiento con umbrales (TODO).
- `pipeline.yml`: Pipeline con secretos, IaC, rendimiento y triage (TODO).
- `triage-ia/triage.py`: Script de priorización asistida por IA, con redacción de datos sensibles (TODO).
- `docs/zero-trust.md`: Tabla de controles zero trust.

**Herramientas:** Terraform + CloudFormation (o CDK/SAM), checkov/tfsec/cfn-lint, k6, y un modelo de lenguaje a tu elección para el triage.

## Repaso: explicación técnica del concepto

Zero trust (NIST SP 800-207) parte de dos ideas: nunca confiar por estar "dentro de la red" y verificar explícitamente cada acceso con el mínimo privilegio, asumiendo que alguna credencial o componente ya está comprometido. Llevado a infraestructura como código significa que esas reglas son archivos revisables y comprobables: nada es público por defecto, cada función tiene su propio rol, todo se cifra, cada llamada se autentica y autoriza, y el pipeline rechaza un cambio que las viole. Eso se llama policy as code: tfsec, checkov, cfn-lint o cfn_nag convierten una norma en una prueba automática.

Tener dos herramientas de IaC demuestra que entiendes el concepto y no solo la sintaxis. Terraform es multinube, declarativo (HCL) y guarda estado propio; CloudFormation es nativo de AWS, sin estado que administrar y con integración inmediata a nuevos servicios; CDK escribe lo mismo en un lenguaje de programación y por debajo genera CloudFormation; SAM es una extensión de CloudFormation para serverless. La decisión depende del equipo, de si hay más de una nube y de dónde vive el estado.

Una prueba de rendimiento (k6, Gatling, JMeter) mide latencia y errores bajo carga. Metida al pipeline con umbrales, convierte un requisito no funcional ("p95 menor a 500 ms") en una condición de aceptación que se verifica en cada cambio, en vez de descubrirse en producción.

La IA en DevSecOps sirve para lo que el humano hace mal por volumen: leer cientos de hallazgos, agruparlos, explicarlos y proponer prioridad. No debería: ver secretos o datos reales (se redactan antes), aprobar un despliegue, ni cerrar un hallazgo por sí sola. El criterio es el mismo de tu reto 8: la IA propone, tú decides y puedes explicar por qué.

Cuándo NO: no tiene sentido tener dos herramientas de IaC en un proyecto pequeño solo por tenerlas (aquí lo haces para demostrar el criterio, en un proyecto real escogerías una); y una prueba de carga contra un entorno compartido sin avisar puede tumbarlo.

## Paso a paso

1. Spec primero: qué recursos pasan a CloudFormation (rol y almacenamiento), qué umbrales de rendimiento defiendes y qué datos NO puede ver la IA.
2. Escribe el template de CloudFormation y valídalo con `cfn-lint`. Compara con tu Terraform: ¿hacen exactamente lo mismo?
3. Corre checkov sobre ambos. Arregla lo que reporte y anota en `docs/zero-trust.md` qué control corresponde a qué principio.
4. Completa `k6/citas.js` con un escenario de reserva y los umbrales. Corre contra tu entorno local o LocalStack y verifica que el comando devuelve código distinto de cero cuando un umbral falla (rómpelo a propósito para comprobarlo).
5. Completa `triage.py`: lee el JSON de hallazgos, redacta lo sensible, arma el prompt, llama al modelo (o simula la respuesta si no tienes API) y escribe un borrador en Markdown. Nada se cierra solo.
6. Conecta todo en `pipeline.yml` con el orden razonado y `needs` correctos.

## Autoevaluación

Antes de marcar el reto como ✅, responde en menos de 2 minutos, en voz alta o por escrito, las preguntas de repaso de este tema en `REPASO_MASTER.md` (están sin respuesta; las respuestas modelo están al final del archivo). Si te cuesta más que escribir el entregable, el hueco está en el concepto.

<!-- ENTITLEMENT:22:START -->
## Ejemplo fácil de explicar

Pipeline = una banda transportadora: cada commit pasa por compilar → pruebas → análisis de calidad → seguridad → artefacto, y si una estación falla, la banda se detiene. Las pruebas de rendimiento definen **umbrales** (por ejemplo p95 < 300 ms) y fallan si no se cumplen.

```js
export const options = { thresholds: { http_req_duration: ['p(95)<300'] } };  // k6
```
Cuándo NO: no metas una prueba de carga de 20 minutos en cada commit; córrela en una etapa o nocturna.

## Cómo lo trabajamos en Entitlement (micros)

Evidencia del código real de los micros (rutas relativas a `Bancolombia/Micros/`). Es lo que hace el equipo; cuenta qué parte hiciste tú y cuál es del equipo.

- **Pipeline por micro** (monorepo con triggers por `paths`): `deployment/azure_build.yaml` (Java), `ms_retrieve_role/resources/pipeline/azure-pipelines.yaml` (Elixir), `ms_masam_front/azure-pipeline.yaml` (Angular). Etapa `CI`, job `build`, JDK 21: `SonarQubePrepare` → `Gradle clean build jacocoMergedReport` → reporte Pitest → `PublishCodeCoverageResults`; en `trunk` empaqueta jar, Dockerfile, k8s, `AcceptanceTest` y `PerformanceTest`.
- **Calidad:** Sonar con exclusiones, Jacoco, Pitest (mutation testing); `Sonar-buildbreaker` (rompe el build si falla el quality gate) en `ms_retrieve_role` y `ms_masam_front`; Elixir con `credo` + `sobelow` (SAST) enviados a Sonar.
- **Seguridad:** job `Task_DevSecops` (`devsecops-engine@1`, `useVulnerabilityManagement: true`, config remota).
- **Rendimiento:** JMeter por micro (`deployment/performance-test/Jmeter`, `SC_EntitlementMsRetrieveRole.jmx`, `EntitlementRoles.jmx`); varios son plantilla del scaffold.
- **Base de datos:** pipeline Liquibase Pro por contextos de ambiente.
- **Despliegue (CD):** no está en estos pipelines; publican artefactos y un release externo despliega.
- **Zero trust (lo más cercano):** `AuthorizationPolicy` de Istio con identidad JWT por scope/método/path y `ALLOW` explícito: "nunca confiar, siempre verificar", aunque no está escrito como *policy as code* con OPA/Conftest.

**No encontrado en los micros (no lo afirmes como experiencia del proyecto):**

- k6, Gatling, CloudFormation/CDK y policy-as-code con OPA: NO ENCONTRADO. IA en el pipeline: solo la skill de revisión (tema 8).

**Cómo contarlo en la entrevista:** Pipeline de calidad con Sonar + Pitest + DevSecOps como lo real; k6, CloudFormation y el triage con IA son el reto 22.
<!-- ENTITLEMENT:22:END -->

## Cómo cerré esta brecha (mi implementación)

*Completo esto yo mismo cuando termine el reto, no antes. Con lo que ya entregué, respondo aquí:*

- *¿Qué archivos y decisiones concretas produje y qué responsabilidad tiene cada uno?*
- *¿Cómo mi entrega, específicamente, resuelve el concepto de este reto? Cito mis propios archivos.*
- *¿Qué error o malentendido tuve en el camino y cómo lo corregí?*

## SDD — Spec-Driven Development

Antes de producir nada en este reto, escribe (3 a 5 líneas) la especificación de lo que vas a entregar: qué archivos, qué decisión toma cada uno, qué casos borde cubres y cómo vas a verificar que está bien. La decisión de diseño se toma antes de la primera línea, no se descubre mientras escribes. Puedes usar la IA para pulir la spec o para preguntarte lo que se te escapa, pero qué debe hacer cada pieza lo decides tú: es el feedback más repetido de tus evaluadores.

---
