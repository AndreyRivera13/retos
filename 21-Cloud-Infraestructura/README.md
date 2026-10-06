# Reto 21 — Cloud — Infraestructura mínima de una solución

**Nivel que evalúa:** Master

**Área:** Cloud

**Estado:** 🔲 Sin empezar

## Para qué te sirve este reto

Cierra el concepto de traducir una decisión de arquitectura a componentes de infraestructura concretos: qué enruta, qué ejecuta y qué guarda. Ya usas EC2 y S3; el salto a Master es poder justificar por qué esos componentes y no otros, y qué atributo de calidad favorece cada uno.

## Enunciado

Declara como código la infraestructura mínima del servicio de citas en una cuenta AWS: un componente de enrutamiento (API Gateway o balanceador de carga), al menos una instancia de cómputo (Lambda, ECS/Fargate o EC2) y al menos un almacenamiento (DynamoDB, RDS/Aurora o S3). Además de los recursos, entrega la justificación de cada decisión: por qué ese cómputo y no los otros dos, por qué ese almacenamiento, y qué atributo de calidad (costo, escalabilidad, operación, latencia) favorece cada elección. Debe poder verificarse sin una cuenta de pago: `terraform validate` y `terraform plan` como mínimo, y de forma opcional un despliegue real en LocalStack o en una cuenta sandbox.

## Qué debes entregar

`iac/` con el Terraform (provider, recursos, variables y outputs), `docs/decisiones-infra.md` con la tabla decisión, componente y atributo, y el diagrama de contenedores del reto 20 actualizado con los nombres reales de los recursos.

## Cómo sabes que lo dominas

¿Por qué el cómputo va detrás del enrutamiento y no expuesto directo? ¿Qué cambia en tu diseño si el tráfico pasa de 10 a 10.000 solicitudes por segundo, y qué componente se vuelve el cuello de botella primero?

## Qué hay en esta carpeta

Todo lo que dice `TODO` es tuyo. Lo demás es plomería para que arranques sin perder tiempo.

- `iac/main.tf`: Provider (con interruptor para LocalStack) y recursos TODO.
- `iac/variables.tf`: Variables, incluida `usar_localstack`.
- `iac/outputs.tf`: URL del enrutamiento y nombres de recursos.
- `app/handler.py`: Función de ejemplo del cómputo (cámbiala si usas otro cómputo).
- `docker-compose.localstack.yml`: LocalStack para probar sin cuenta AWS (opcional).
- `docs/decisiones-infra.md`: Tabla de decisiones y atributos de calidad.

**Herramientas:** Terraform (ya lo tocaste en el reto 17). Este es el material base del reto 22, donde lo reescribes parcialmente con una segunda herramienta de IaC.

## Repaso: explicación técnica del concepto

Una solución en la nube casi siempre se descompone en tres preguntas: quién recibe el tráfico, quién ejecuta la lógica y dónde viven los datos. El enrutamiento (API Gateway, Application Load Balancer) es el punto de entrada: termina TLS, aplica autenticación y límites de tasa y reparte la carga, de modo que el cómputo no queda expuesto a internet. El cómputo se escoge por patrón de uso: Lambda cobra por ejecución y escala a cero (bueno para tráfico irregular o poco predecible, malo para procesos largos o con arranque en frío sensible); ECS/Fargate corre contenedores sin administrar servidores (bueno para servicios Spring Boot que ya tienes empaquetados y tráfico estable); EC2 da control total del sistema operativo a cambio de operar parches, escalado y capacidad. El almacenamiento también se decide por el acceso: DynamoDB para acceso por clave con latencia baja y escala casi ilimitada, RDS/Aurora cuando necesitas consultas relacionales y transacciones, S3 para objetos y archivos.

Cada elección es un trade-off con atributos de calidad: serverless favorece costo y escalabilidad elástica pero complica el control de latencia; contenedores favorecen portabilidad; una base relacional favorece consistencia y consultas ad hoc pero escala diferente. Lo que te evalúan no es que recuerdes los servicios sino que puedas decir qué sacrificaste.

Cuándo NO complicarlo: si el servicio recibe 50 solicitudes al día, Lambda más DynamoDB cuesta centavos y un clúster ECS sería sobreingeniería; si necesitas transacciones multi-tabla, DynamoDB probablemente no es la opción. Y todo esto se declara como código (reto 17 y 22), no se crea a mano en la consola.

## Paso a paso

1. Escribe la spec: lista el recurso de enrutamiento, el de cómputo y el de almacenamiento que vas a usar y la razón de cada uno en una línea, antes de abrir el editor.
2. Completa `main.tf` recurso por recurso. Cada permiso del cómputo hacia el almacenamiento va con la acción mínima (retoma lo que hiciste en el reto 17).
3. Corre `terraform init -backend=false`, `terraform fmt` y `terraform validate`. Luego `terraform plan` apuntando a LocalStack (`-var usar_localstack=true`) o con credenciales de sandbox.
4. Si usas LocalStack Community, verifica qué servicios cubre antes de elegir (los de pago, como algunos de API Gateway v2 o ALB, pueden no estar). Si algo no corre allí, queda en `validate` y `plan` y lo dices en la entrega.
5. Llena la tabla de decisiones con la alternativa que descartaste en cada fila.
6. Actualiza el diagrama de contenedores del reto 20 con los nombres reales.

## Autoevaluación

Antes de marcar el reto como ✅, responde en menos de 2 minutos, en voz alta o por escrito, las preguntas de repaso de este tema en `REPASO_MASTER.md` (están sin respuesta; las respuestas modelo están al final del archivo). Si te cuesta más que escribir el entregable, el hueco está en el concepto.

## Cómo cerré esta brecha (mi implementación)

*Completo esto yo mismo cuando termine el reto, no antes. Con lo que ya entregué, respondo aquí:*

- *¿Qué archivos y decisiones concretas produje y qué responsabilidad tiene cada uno?*
- *¿Cómo mi entrega, específicamente, resuelve el concepto de este reto? Cito mis propios archivos.*
- *¿Qué error o malentendido tuve en el camino y cómo lo corregí?*
- *¿Qué hice yo y qué hice con ayuda de IA?*

## SDD — Spec-Driven Development

Antes de producir nada en este reto, escribe (3 a 5 líneas) la especificación de lo que vas a entregar: qué archivos, qué decisión toma cada uno, qué casos borde cubres y cómo vas a verificar que está bien. La decisión de diseño se toma antes de la primera línea, no se descubre mientras escribes. Puedes usar la IA para pulir la spec o para preguntarte lo que se te escapa, pero qué debe hacer cada pieza lo decides tú: es el feedback más repetido de tus evaluadores.

---
