# Reto 17 — IaC (Terraform/CloudFormation/CDK/SAM) + escaneo de seguridad

**Nivel que evalúa:** Senior

**Estado:** 🔲 Sin empezar

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

## Cómo cerré esta brecha (mi implementación)

*Completo esto yo mismo cuando termine el reto — no antes. Con mi código real ya escrito, respondo acá (no sobre el enunciado, sobre mi implementación):*

- *¿Qué clases/métodos concretos escribí y qué responsabilidad tiene cada uno?*
- *¿Cómo mi código, específicamente, resuelve el concepto de este reto? Cito mis propias clases y métodos, no la teoría.*
- *¿Qué bug o mal entendido tuve en el camino, y cómo lo corregí? (revisar esto antes de la entrevista me sirve más que repasar la teoría de nuevo).*

## SDD — Spec-Driven Development

Antes de tocar código en este reto, escribí (alcanza con 3-5 líneas, en un comentario o en un README aparte) la especificación de lo que vas a construir: qué clases/métodos necesitás, el contrato de cada uno (entradas, salidas, casos borde) y la regla de negocio que cubre — el "qué" antes del "cómo". Es la misma disciplina que separa TDD (diseñás guiado por tests que escribís vos) de SDD (diseñás guiado por una spec escrita, para vos mismo o para que una IA la ejecute): la decisión de diseño se toma **antes** de escribir la primera línea, no se descubre a medida que tecleás. Encaja directo con el feedback de tus evaluadores: podés usar la IA para redactar o pulir esa spec, pero la decisión de qué debe hacer cada pieza es tuya, no de la IA — spec en mano, después sí generás o escribís el código.

---
