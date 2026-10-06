# Reto 17 — IaC (Terraform/CloudFormation/CDK/SAM) + escaneo de seguridad

**Nivel que evalúa:** Senior

**Estado:** ✅ Cerrado — verificado el 2026-10-03 (terraform validate, tfsec y checkov en cero hallazgos; actionlint sin errores).

## Para qué te sirve este reto

Cierra el concepto de infraestructura como código con seguridad incorporada al pipeline (menor privilegio, escaneo antes de desplegar), no infraestructura creada a mano. Conecta directo con lo que ya hacés en AWS (EC2, S3, despliegues) — acá lo formalizás en código versionado.

## Enunciado

Escribí un Terraform mínimo que declare una tabla DynamoDB (o RDS) y un rol IAM con permisos mínimos necesarios (no `*`). Agregá un pipeline YAML de ejemplo con: paso de escaneo de secretos, paso de escaneo de dependencias, y un paso `tfsec`/`checkov` sobre el propio código Terraform, en el orden correcto con comentarios explicando por qué van en ese orden.

## Qué debés entregar

`.tf` + `.yml` comentado.

## Cómo sabés que lo dominás

¿Podés explicar qué es el principio de menor privilegio aplicado al rol IAM que escribiste, con un ejemplo concreto de qué permiso NO le diste y por qué?

## Explicación técnica del concepto

Infraestructura como código versiona la infraestructura igual que el código de aplicación, lo que permite revisarla, auditarla y escanearla antes de aplicarla. El principio de menor privilegio en IAM implica otorgar solo los permisos estrictamente necesarios, nunca un comodín. Un pipeline con escaneo de secretos, de dependencias y de la propia infraestructura (`tfsec`/`checkov`) detecta estos problemas antes del despliegue, no después.

<!-- ENTITLEMENT:17:START -->
## Ejemplo fácil de explicar

IaC es escribir la receta de la infraestructura en un archivo y versionarla, en vez de hacer clic en una consola:

```hcl
resource "aws_s3_bucket" "reportes" { bucket = "reportes-${var.env}" }
```
`terraform plan` te muestra qué va a cambiar *antes* de aplicarlo; así la infra se revisa en un PR como cualquier código. Cuándo NO: un experimento de 10 minutos que vas a borrar.

## Cómo lo trabajamos en Entitlement (micros)

Evidencia del código real de los micros (rutas relativas a `Bancolombia/Micros/`). Es lo que hace el equipo; cuenta qué parte hiciste tú y cuál es del equipo.

- **Terraform, CloudFormation y Helm: NO existen en los micros.** La infraestructura declarativa del proyecto es otra:
  - **Manifiestos Kubernetes/Istio parametrizados** con tokens `#{variable}#` que se reemplazan en el release: `deployment/k8s/` de `ms_actors` (`app.yaml`, `configmap.yaml`, `hpa.yaml`, `gateway.yaml`, `destinationrule.yaml`, `authorization_policy.yaml`) y `pdb.yaml` en `ms_retrieve_role`.
  - **Base de datos como código con Liquibase Pro:** `azure-pipelines.yml` con `liquibase-pro-task@1` en los repos `ENTITLEMENT_ENTMG_SQL` y `ENTITLEMENT_ENTNT_SQL`; changesets con `context:REF,QA,PDN`.
  - **Dockerfile** versionado (`ms_actors/deployment/Dockerfile`, usuario no root).
- **Escaneo en pipeline:** job `Task_DevSecops` (`devsecops-engine@1`, pool `DevSecOps-Engine`) con configuración remota; qué escáneres corren adentro no se ve en el repo. Fortify/Trivy/Gitleaks/Dependency-Check explícitos: NO ENCONTRADO.

**No encontrado en los micros (no lo afirmes como experiencia del proyecto):**

- Terraform/CloudFormation: NO ENCONTRADO. Lo tuyo con Terraform son los retos 17 y 21.

**Cómo contarlo en la entrevista:** "En Entitlement la infra es declarativa con K8s/Istio y Liquibase; Terraform lo practiqué con `tfsec`/`checkov` en el reto 17". Es honesto y sigue siendo IaC.
<!-- ENTITLEMENT:17:END -->

## Cómo cerré esta brecha (mi implementación)

`main.tf` declara una tabla DynamoDB `citas` (pago por uso, clave `citaId`, recuperación a un punto en el tiempo y cifrado con una llave KMS propia con rotación) y un rol IAM `servicio-citas` para Lambda con una política en línea. El principio de menor privilegio está aplicado así: el rol solo tiene `dynamodb:GetItem`, `PutItem` y `Query`, y únicamente sobre el ARN de esa tabla (no sobre `*`). El permiso que NO le di es `dynamodb:DeleteItem` (y tampoco `Scan` ni `UpdateItem`): este servicio reserva y consulta citas, y si una credencial se filtra, quien la use no puede borrar ni recorrer toda la tabla. También agregué solo `kms:Decrypt` y `kms:GenerateDataKey` sobre esa llave, condicionados a `kms:ViaService = dynamodb.<región>.amazonaws.com`, porque cifrar con llave propia exige ese permiso.

`pipeline.yml` (GitHub Actions) tiene cuatro jobs y el orden está explicado en sus comentarios: primero `seguridad-secretos` (gitleaks sobre todo el historial: es lo más barato y un secreto expuesto invalida todo lo demás), luego en paralelo `validar-iac` (`terraform fmt`/`validate`, tfsec y checkov sobre mi propio Terraform) y `escaneo-dependencias` (OWASP Dependency-Check), y al final `deploy`, que solo corre en `main`, necesita los tres anteriores y usa OIDC en lugar de llaves guardadas como secretos.

Lo verifiqué ejecutando las herramientas de verdad: `terraform fmt -check` y `terraform validate` (con el proveedor AWS real), tfsec (11 chequeos, 0 hallazgos), checkov (22 pasan, 0 fallan) y actionlint sobre el pipeline. Mi primera versión falló un chequeo de checkov (CKV2_AWS_64): la llave KMS no tenía policy explícita; la agregué. Aclaración: los comentarios del `.yml` los dejé a propósito porque el enunciado los pide como entregable. Un tema abierto: el estado de Terraform (`tfstate`) debería ir en un backend remoto con bloqueo (S3 + DynamoDB), que no configuré porque el reto es mínimo.

## SDD — Spec-Driven Development

Antes de tocar código en este reto, escribí (alcanza con 3-5 líneas, en un comentario o en un README aparte) la especificación de lo que vas a construir: qué clases/métodos necesitás, el contrato de cada uno (entradas, salidas, casos borde) y la regla de negocio que cubre — el "qué" antes del "cómo". Es la misma disciplina que separa TDD (diseñás guiado por tests que escribís vos) de SDD (diseñás guiado por una spec escrita, para vos mismo o para que una IA la ejecute): la decisión de diseño se toma **antes** de escribir la primera línea, no se descubre a medida que tecleás. Encaja directo con el feedback de tus evaluadores: podés usar la IA para redactar o pulir esa spec, pero la decisión de qué debe hacer cada pieza es tuya, no de la IA — spec en mano, después sí generás o escribís el código.

---
