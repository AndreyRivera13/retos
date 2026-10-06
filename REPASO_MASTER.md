# Repaso — Nivel Master

Guía de estudio de los 7 temas del nivel Master, con su reto asociado. Cada tema trae: qué pide el assessment, qué tienes que poder explicar, cómo se conecta con tu trabajo en Entitlement/Bancolombia y preguntas de repaso. **Las respuestas modelo están al final**: respóndelas primero por tu cuenta, igual que lo harías frente al evaluador.

> Nota: no tengo evidencia de quién será tu evaluador de Master ni de su experiencia, así que las marcas de prioridad son por confirmar. Calibra preguntándole a tu líder o a quien te evalúe.

## Mapa: qué pide el assessment y dónde se practica

| # | Tema | Qué pide el assessment (resumen) | Reto |
|---|---|---|---|
| 20 | Documentación | Documenta decisiones de arquitectura (contexto, stakeholders, componentes, contenedores, relaciones) para distintos públicos con vistas (C4 / 4+1); diagramas informales; guía al equipo con self-documenting code; hace diagramas de secuencia, clases y entidad-relación. | `20-Documentacion` |
| 21 | Cloud | Decisiones de arquitectura reflejadas en infraestructura de una solución en una cuenta/proyecto: mínimo un enrutamiento, un cómputo y un almacenamiento. | `21-Cloud-Infraestructura` |
| 22 | DevOps / IaC | Aplica ML/DL/IA generativa en DevSecOps; dirige IaC que automatiza zero trust y pruebas de rendimiento con buenas prácticas; ha trabajado con al menos dos tecnologías de IaC distintas. | `22-DevOps-IaC` |
| 23 | Seguridad | Aplica al menos una táctica de seguridad que favorezca la protección contra ataques en la arquitectura de una cuenta/proyecto. | `23-Seguridad-Arquitectura` |
| 24 | Diseño de arquitectura | Identifica los atributos de calidad más importantes de los requerimientos y diseña arquitecturas que los favorezcan. | `24-Diseno-Arquitectura` |
| 25 | Observabilidad | Implementa en al menos un proyecto una herramienta de análisis de métricas, registros y trazas relevantes para negocio, identificando el tipo de monitoreo (infraestructura, logs, APM, RUM, sintético, seguridad, auditoría transaccional, costos). | `25-Observabilidad` |
| 26 | Arquitectura de Datos | Da lineamientos de todo el ciclo de vida de la información: captura, almacenamiento, gestión, publicación y disposición. | `26-Arquitectura-Datos` |

Nota sobre el tema 22: en la hoja que me diste, el texto de DevOps (IA en DevSecOps, IaC con zero trust y pruebas de rendimiento, dos tecnologías de IaC) quedó pegado a la línea "Infraestructura como código (IaC)", así que lo traté como un solo tema. Si en tu matriz real son dos filas separadas, avísame y lo separo.

## 20. Documentación — Documentación de arquitectura (C4 / 4+1 / ADR)

**Reto:** `20-Documentacion` · **Prioridad:** 🟡 Media (por confirmar con tu evaluador de Master)

### Lo que tienes que poder explicar

Una vista de arquitectura es un recorte del sistema pensado para un público y una pregunta concreta. C4 (Simon Brown) lo ordena en cuatro niveles de zoom: Contexto (el sistema como una caja, sus usuarios y los sistemas vecinos; lo entiende cualquiera), Contenedores (las unidades desplegables o que guardan datos: aplicación, base de datos, broker; para quien construye y opera), Componentes (las piezas internas de un contenedor: controladores, casos de uso, adaptadores; para desarrolladores) y Código (clases, casi siempre opcional porque el IDE lo genera). El modelo 4+1 de Kruchten es la alternativa: vistas lógica, de procesos, de desarrollo y física (despliegue), unidas por una vista de escenarios (casos de uso) que las valida. No hay que usar las dos; hay que escoger una y ser consistente.

Un ADR (Architecture Decision Record, Nygard) registra una decisión, no una descripción: qué contexto había, qué se decidió, qué alternativas se descartaron y qué consecuencias (buenas y malas) se aceptaron. Su valor es que dentro de un año alguien entienda por qué se hizo así sin tener que preguntarle a quien ya no está. Los diagramas de secuencia muestran el orden de las interacciones, los de clases la estructura estática del dominio y el entidad-relación los datos persistidos; cada uno responde una pregunta distinta, no son intercambiables.

Self-documenting code es la práctica complementaria: antes de escribir un comentario o un diagrama para explicar una clase, intentas que los nombres y la estructura lo expliquen solos. Un comentario que dice "suma el IVA" sobre `total = x * 1.19` se reemplaza por `precioConIva(precio)`. No elimina la documentación de arquitectura (el código no cuenta por qué se eligió Kafka), pero sí la de bajo nivel.

Cuándo NO: un diagrama de cada clase "por si acaso" (se desactualiza el mismo día), un ADR por cada decisión trivial (diluye los importantes) o un documento de 40 páginas que nadie abre. La regla práctica: documenta lo que es caro de redescubrir.

### Preguntas de repaso

1. ¿Qué muestra cada nivel de C4 y a qué público va?
2. ¿Qué es el modelo 4+1 y en qué se diferencia de C4?
3. ¿Qué debe tener un ADR y por qué incluye consecuencias negativas?
4. ¿Cuándo escribes un ADR y cuándo no?
5. ¿Qué diagrama usas para el orden de las llamadas, cuál para la estructura del dominio y cuál para los datos?
6. ¿Qué es self-documenting code y qué NO reemplaza?

## 21. Cloud — Infraestructura mínima de una solución

**Reto:** `21-Cloud-Infraestructura` · **Prioridad:** 🟡 Media (por confirmar con tu evaluador de Master)

### Lo que tienes que poder explicar

Una solución en la nube casi siempre se descompone en tres preguntas: quién recibe el tráfico, quién ejecuta la lógica y dónde viven los datos. El enrutamiento (API Gateway, Application Load Balancer) es el punto de entrada: termina TLS, aplica autenticación y límites de tasa y reparte la carga, de modo que el cómputo no queda expuesto a internet. El cómputo se escoge por patrón de uso: Lambda cobra por ejecución y escala a cero (bueno para tráfico irregular o poco predecible, malo para procesos largos o con arranque en frío sensible); ECS/Fargate corre contenedores sin administrar servidores (bueno para servicios Spring Boot que ya tienes empaquetados y tráfico estable); EC2 da control total del sistema operativo a cambio de operar parches, escalado y capacidad. El almacenamiento también se decide por el acceso: DynamoDB para acceso por clave con latencia baja y escala casi ilimitada, RDS/Aurora cuando necesitas consultas relacionales y transacciones, S3 para objetos y archivos.

Cada elección es un trade-off con atributos de calidad: serverless favorece costo y escalabilidad elástica pero complica el control de latencia; contenedores favorecen portabilidad; una base relacional favorece consistencia y consultas ad hoc pero escala diferente. Lo que te evalúan no es que recuerdes los servicios sino que puedas decir qué sacrificaste.

Cuándo NO complicarlo: si el servicio recibe 50 solicitudes al día, Lambda más DynamoDB cuesta centavos y un clúster ECS sería sobreingeniería; si necesitas transacciones multi-tabla, DynamoDB probablemente no es la opción. Y todo esto se declara como código (reto 17 y 22), no se crea a mano en la consola.

### Preguntas de repaso

1. ¿Qué mínimo de infraestructura necesita una solución completa?
2. ¿Cuándo eliges Lambda, cuándo ECS y cuándo EC2?
3. ¿Cuándo DynamoDB y cuándo RDS?
4. ¿Por qué el cómputo no se expone directo a internet?
5. ¿Qué se rompe primero al pasar de 10 a 10.000 rps?
6. ¿Por qué se declara como código y no en la consola?

## 22. DevOps / IaC — IaC con dos herramientas, zero trust, rendimiento e IA en DevSecOps

**Reto:** `22-DevOps-IaC` · **Prioridad:** 🟢 Baja en el CV de Rudyard, pero es Master: probablemente te pidan evidencia propia

### Lo que tienes que poder explicar

Zero trust (NIST SP 800-207) parte de dos ideas: nunca confiar por estar "dentro de la red" y verificar explícitamente cada acceso con el mínimo privilegio, asumiendo que alguna credencial o componente ya está comprometido. Llevado a infraestructura como código significa que esas reglas son archivos revisables y comprobables: nada es público por defecto, cada función tiene su propio rol, todo se cifra, cada llamada se autentica y autoriza, y el pipeline rechaza un cambio que las viole. Eso se llama policy as code: tfsec, checkov, cfn-lint o cfn_nag convierten una norma en una prueba automática.

Tener dos herramientas de IaC demuestra que entiendes el concepto y no solo la sintaxis. Terraform es multinube, declarativo (HCL) y guarda estado propio; CloudFormation es nativo de AWS, sin estado que administrar y con integración inmediata a nuevos servicios; CDK escribe lo mismo en un lenguaje de programación y por debajo genera CloudFormation; SAM es una extensión de CloudFormation para serverless. La decisión depende del equipo, de si hay más de una nube y de dónde vive el estado.

Una prueba de rendimiento (k6, Gatling, JMeter) mide latencia y errores bajo carga. Metida al pipeline con umbrales, convierte un requisito no funcional ("p95 menor a 500 ms") en una condición de aceptación que se verifica en cada cambio, en vez de descubrirse en producción.

La IA en DevSecOps sirve para lo que el humano hace mal por volumen: leer cientos de hallazgos, agruparlos, explicarlos y proponer prioridad. No debería: ver secretos o datos reales (se redactan antes), aprobar un despliegue, ni cerrar un hallazgo por sí sola. El criterio es el mismo de tu reto 8: la IA propone, tú decides y puedes explicar por qué.

Cuándo NO: no tiene sentido tener dos herramientas de IaC en un proyecto pequeño solo por tenerlas (aquí lo haces para demostrar el criterio, en un proyecto real escogerías una); y una prueba de carga contra un entorno compartido sin avisar puede tumbarlo.

### Preguntas de repaso

1. ¿Qué significa zero trust llevado a IaC?
2. ¿Terraform o CloudFormation?
3. ¿Qué es policy as code?
4. ¿Por qué un umbral de rendimiento en el pipeline?
5. ¿Qué le das y qué NO le das a la IA en DevSecOps?
6. ¿Por qué dos herramientas de IaC?

## 23. Seguridad — tácticas de arquitectura contra ataques

**Reto:** `23-Seguridad-Arquitectura` · **Prioridad:** 🔴 Alta si tu evaluador es Rudyard (JWT en el CV)

### Lo que tienes que poder explicar

Una táctica de seguridad es una decisión de diseño que influye en la capacidad del sistema de enfrentar un ataque. El catálogo clásico (Bass, Clements y Kazman) las agrupa en cuatro categorías según el momento: resistir ataques (autenticar, autorizar, limitar acceso y exposición, cifrar, validar entrada, separar entidades), detectar ataques (detectar intrusión o denegación de servicio, verificar integridad de mensajes), reaccionar a ataques (revocar acceso, bloquear, notificar) y recuperarse (restaurar y mantener pistas de auditoría que den no repudio). Una arquitectura robusta combina categorías: resistir reduce la superficie, detectar te avisa cuando resistir no bastó, reaccionar contiene el daño.

STRIDE es la forma de decidir qué táctica necesitas: Spoofing (suplantación), Tampering (manipulación), Repudiation (repudio), Information disclosure (divulgación), Denial of service y Elevation of privilege. El IDOR del reto 18 es divulgación de información por elevación de privilegio horizontal; la autorización por dueño lo resuelve en la raíz, pero la enumeración masiva (probar los ids 1 a 1000) sigue siendo una señal de ataque que quieres limitar y ver. Por eso el rate limiting no corrige el IDOR: reduce lo rápido que se puede explotar y te da tiempo, no arregla la falla.

Cuándo NO: limitar la tasa de forma muy agresiva rompe a usuarios legítimos (una aplicación móvil que reintenta); registrar en el log el contenido de los documentos para auditar crea un problema nuevo de privacidad (se audita quién y qué recurso, no el contenido); y una alerta que se dispara diez veces al día se ignora.

### Preguntas de repaso

1. ¿Cuáles son las cuatro categorías de tácticas de seguridad?
2. Da un ejemplo de táctica de cada categoría.
3. ¿Por qué el rate limiting no corrige un IDOR?
4. ¿Qué es STRIDE y para qué lo usas?
5. ¿Qué NO deberías registrar en un log de auditoría?
6. ¿Qué limitación tiene un limitador de tasa en memoria?

## 24. Diseño de arquitectura — atributos de calidad y trade-offs

**Reto:** `24-Diseno-Arquitectura` · **Prioridad:** 🔴 Alta si tu evaluador es Rudyard (reestructuración hacia microservicios en dos empresas)

### Lo que tienes que poder explicar

Los atributos de calidad (rendimiento, disponibilidad, seguridad, modificabilidad, escalabilidad, costo, operabilidad...) son los requisitos no funcionales que realmente moldean una arquitectura: la funcionalidad casi cualquier arquitectura la puede entregar, la calidad no. El primer paso es priorizarlos con el negocio, porque no se puede maximizar todo a la vez; un árbol de utilidad ordena cada atributo en escenarios concretos con importancia para el negocio y dificultad técnica.

Un escenario de calidad hace el requisito medible. En lugar de "debe ser rápido" se escribe: fuente (un paciente), estímulo (500 reservas por segundo un lunes a las 7 a. m.), artefacto (la API de citas), entorno (operación normal con pico), respuesta (cada reserva se confirma) y medida (p95 menor a 800 ms y menos de 0,5% de errores). Lo medible permite probar y discutir.

ATAM (Architecture Tradeoff Analysis Method) evalúa una arquitectura contra esos escenarios y busca tres cosas: puntos de sensibilidad (una decisión de la que depende mucho un atributo), trade-offs (una decisión que mejora un atributo y empeora otro) y riesgos. Por ejemplo, microservicios mejoran la modificabilidad independiente y la escalabilidad por componente, pero empeoran la complejidad operativa y la consistencia; un monolito modular hexagonal mantiene la consistencia simple y puede extraer un módulo después si un dato lo justifica.

Una fitness function (arquitectura evolutiva) es una prueba automática que protege un atributo a lo largo del tiempo: un test ArchUnit que impide que el dominio dependa de Spring protege la modificabilidad; una prueba de carga con umbral protege el rendimiento. Sin ellas, la arquitectura se degrada sin que nadie lo note.

Cuándo NO: no escojas microservicios porque "es lo moderno" con un equipo de seis personas y sin necesidad real de escalar partes por separado; el costo operativo se come el beneficio. Y no escribas veinte escenarios: con tres bien medidos se decide más que con veinte vagos.

### Preguntas de repaso

1. ¿Qué es un atributo de calidad y por qué moldea la arquitectura?
2. ¿Qué partes tiene un escenario de calidad?
3. ¿Qué es un punto de sensibilidad, un trade-off y un riesgo en ATAM?
4. ¿Monolito modular o microservicios para un equipo de seis?
5. ¿Qué es una fitness function?
6. ¿Cómo sabes que tu decisión funcionó?

## 25. Observabilidad — métricas, logs y trazas con sentido de negocio

**Reto:** `25-Observabilidad` · **Prioridad:** 🟡 Media (por confirmar con tu evaluador de Master)

### Lo que tienes que poder explicar

Observabilidad es la capacidad de entender el estado interno de un sistema a partir de lo que emite. Se apoya en tres señales: métricas (números agregados en el tiempo; baratas, buenas para alertar y ver tendencias), logs (eventos con contexto; caros, buenos para investigar un caso) y trazas (el recorrido de una solicitud a través de componentes, con el tiempo de cada tramo; buenas para ubicar dónde se pierde el tiempo). Se correlacionan mediante identificadores compartidos: un `correlationId` o el `traceId` que aparece en el log y en la traza.

Para decidir qué medir hay métodos probados: RED para servicios (Rate, Errors, Duration: solicitudes por segundo, errores, latencia), USE para recursos (Utilization, Saturation, Errors) y las cuatro señales doradas de Google (latencia, tráfico, errores, saturación). Una métrica de negocio (pagos aprobados, citas reservadas) responde "¿el negocio está funcionando?" aunque todos los servidores estén sanos, y suele ser la primera en cambiar cuando algo falla de verdad.

El assessment te pide distinguir tipos de monitoreo: de infraestructura (CPU, memoria, red), gestión de logs, APM (rendimiento de la aplicación y trazas), RUM (lo que vive el usuario real en su navegador o app), sintético (una sonda que ejecuta un flujo cada minuto desde fuera), de seguridad, auditoría transaccional (quién hizo qué, con valor probatorio) y de costos. No son lo mismo y se hacen con herramientas y retenciones distintas.

Cuándo NO: no registres datos personales ni secretos en logs; no etiquetes métricas con valores de alta cardinalidad (un id de usuario como etiqueta destruye Prometheus); y no crees alertas sin runbook, porque una alerta a la que nadie sabe responder es ruido.

### Preguntas de repaso

1. ¿Cuáles son las tres señales y para qué sirve cada una?
2. ¿Qué son RED y USE?
3. ¿Qué diferencia hay entre una métrica de negocio y una de infraestructura?
4. Nombra los tipos de monitoreo del assessment.
5. ¿Por qué alertar por síntomas y no por causas?
6. ¿Qué es la alta cardinalidad y por qué daña las métricas?

## 26. Arquitectura de Datos — ciclo de vida de la información

**Reto:** `26-Arquitectura-Datos` · **Prioridad:** 🟡 Media si tu evaluador es Rudyard (SQL Server/MySQL/Postgres en el CV)

### Lo que tienes que poder explicar

El ciclo de vida de los datos ordena las decisiones en el tiempo. Captura: se recolecta solo lo necesario para la finalidad declarada (minimización), con base legal o consentimiento y validando calidad en la entrada, porque un dato malo capturado aquí contamina todo lo que viene después. Almacenamiento: se decide dónde y por cuánto; es habitual separar el almacenamiento transaccional (rápido, caro, datos vivos) del histórico o de archivo (barato, de solo lectura), con plazos de retención justificados por negocio y por norma. Gestión: define el acceso (roles, mínimo privilegio, desde qué recursos), la protección (cifrado, enmascaramiento, seudonimización), el catálogo (qué dato existe, quién es su dueño, cómo se clasifica) y la calidad. Publicación: es cómo se entrega información a negocio y analítica; lo sano es publicar vistas, agregados o copias anonimizadas, no dar acceso a las tablas operativas. Disposición: el procedimiento explícito para eliminar o anonimizar lo que ya cumplió su plazo, dejando evidencia (qué se depuró, cuándo, bajo qué regla).

En Colombia el marco general de protección de datos personales es la Ley 1581 de 2012 (habeas data), que da al titular derechos de acceso, rectificación y supresión. Los datos de salud son sensibles y la historia clínica tiene reglas propias de conservación en la normativa del sector salud; verifica con tu equipo legal los plazos vigentes antes de afirmarlos, porque este reto te pide justificar los tuyos, no recitar una norma de memoria. Un punto fino y muy preguntado: el derecho de supresión puede chocar con una obligación legal de conservar; la respuesta usual es anonimizar o restringir lo que no se está obligado a guardar y bloquear el acceso a lo que sí.

Cuándo NO: no borres físicamente sin pensar en dependencias (una cita cancelada puede estar referenciada por un cobro); no guardes "todo por si acaso" (cada dato retenido es un riesgo y un costo); y no confundas anonimizar (irreversible, ya no es dato personal) con seudonimizar (reversible con una llave, sigue siendo dato personal).

### Preguntas de repaso

1. ¿Cuáles son las cinco fases del ciclo de vida de los datos?
2. ¿Qué es la minimización de datos?
3. ¿Diferencia entre anonimizar y seudonimizar?
4. ¿Por qué negocio lee una vista y no la tabla operativa?
5. Un paciente pide borrar sus datos pero hay obligación legal de conservar parte. ¿Qué haces?
6. ¿Cómo demuestras que un dato se depuró?

---

# Respuestas modelo

Son respuestas cortas para contrastar, no para memorizar: con las tuyas, usa ejemplos propios de Entitlement o del sistema de citas.

## 20. Documentación

1. **¿Qué muestra cada nivel de C4 y a qué público va?** Contexto: sistema, personas y sistemas vecinos, para cualquiera. Contenedores: unidades desplegables y almacenes de datos, para líder técnico e infraestructura. Componentes: piezas internas de un contenedor, para desarrolladores. Código: clases, opcional.
2. **¿Qué es el modelo 4+1 y en qué se diferencia de C4?** Vistas lógica, de procesos, de desarrollo y física, validadas por una de escenarios. C4 organiza por nivel de zoom; 4+1 por punto de vista. Se escoge una y se usa de forma consistente.
3. **¿Qué debe tener un ADR y por qué incluye consecuencias negativas?** Contexto, decisión, alternativas y consecuencias. Las negativas muestran que se pesó el trade-off y le dicen al futuro lector qué riesgo se aceptó a conciencia.
4. **¿Cuándo escribes un ADR y cuándo no?** Cuando la decisión es cara de revertir o de redescubrir (estilo de integración, base de datos, modelo de seguridad). No para decisiones triviales o reversibles en una tarde.
5. **¿Qué diagrama usas para el orden de las llamadas, cuál para la estructura del dominio y cuál para los datos?** Secuencia, clases y entidad-relación respectivamente: cada uno responde una pregunta distinta.
6. **¿Qué es self-documenting code y qué NO reemplaza?** Nombres y estructura que explican qué hace el código. No reemplaza el porqué de una decisión de arquitectura, que sigue necesitando un ADR.

## 21. Cloud

1. **¿Qué mínimo de infraestructura necesita una solución completa?** Un enrutamiento (API Gateway o balanceador), un cómputo (Lambda, ECS/Fargate o EC2) y un almacenamiento (S3, DynamoDB, RDS/Aurora, EFS).
2. **¿Cuándo eliges Lambda, cuándo ECS y cuándo EC2?** Lambda para tráfico irregular y procesos cortos; ECS/Fargate para servicios contenedorizados con tráfico estable; EC2 cuando necesitas control del sistema operativo o capacidad dedicada, asumiendo su operación.
3. **¿Cuándo DynamoDB y cuándo RDS?** DynamoDB para acceso por clave y escala alta con latencia baja; RDS/Aurora para consultas relacionales y transacciones multi-tabla.
4. **¿Por qué el cómputo no se expone directo a internet?** El enrutamiento termina TLS, autentica, limita tasa y reparte carga, y reduce la superficie de ataque.
5. **¿Qué se rompe primero al pasar de 10 a 10.000 rps?** Depende de tu diseño; lo habitual es el almacenamiento (capacidad provisionada o conexiones) o los límites de concurrencia del cómputo. Lo importante es nombrarlo y decir cómo lo medirías.
6. **¿Por qué se declara como código y no en la consola?** Es reproducible, revisable, versionado y escaneable antes de aplicarse.

## 22. DevOps / IaC

1. **¿Qué significa zero trust llevado a IaC?** Nunca confiar por estar en la red, verificar cada acceso y dar mínimo privilegio, expresado como archivos que un pipeline puede comprobar y rechazar.
2. **¿Terraform o CloudFormation?** Terraform: multinube, HCL, estado propio. CloudFormation: nativo de AWS, sin estado que administrar, cobertura inmediata de servicios nuevos. CDK genera CloudFormation desde código; SAM lo extiende para serverless.
3. **¿Qué es policy as code?** Convertir una norma de seguridad en una prueba automática (tfsec, checkov, cfn-lint, cfn_nag) que se ejecuta en cada cambio.
4. **¿Por qué un umbral de rendimiento en el pipeline?** Convierte un requisito no funcional en una condición de aceptación verificada en cada cambio en lugar de descubrirla en producción.
5. **¿Qué le das y qué NO le das a la IA en DevSecOps?** Le das volumen (leer, agrupar y explicar hallazgos). No le das secretos ni datos reales, ni la decisión de aprobar un despliegue o cerrar un hallazgo.
6. **¿Por qué dos herramientas de IaC?** Para el assessment, demuestra el concepto más allá de la sintaxis. En un proyecto real normalmente escoges una según equipo, nubes y manejo de estado.

## 23. Seguridad

1. **¿Cuáles son las cuatro categorías de tácticas de seguridad?** Resistir, detectar, reaccionar y recuperarse.
2. **Da un ejemplo de táctica de cada categoría.** Resistir: autorizar por dueño, limitar tasa. Detectar: registro de auditoría y alertas por denegaciones. Reaccionar: bloqueo temporal o revocar acceso. Recuperarse: restaurar con pista de auditoría.
3. **¿Por qué el rate limiting no corrige un IDOR?** El IDOR se corrige verificando autorización por dueño. El límite de tasa solo hace más lenta la explotación y te da tiempo y señal.
4. **¿Qué es STRIDE y para qué lo usas?** Un catálogo de amenazas (suplantación, manipulación, repudio, divulgación, denegación de servicio, elevación de privilegios) para decidir qué táctica necesitas.
5. **¿Qué NO deberías registrar en un log de auditoría?** El contenido sensible (documentos, contraseñas, tokens). Se registra quién, qué recurso y cuándo.
6. **¿Qué limitación tiene un limitador de tasa en memoria?** No es compartido entre instancias ni sobrevive a reinicios; en producción va en Redis o en el API Gateway.

## 24. Diseño de arquitectura

1. **¿Qué es un atributo de calidad y por qué moldea la arquitectura?** Un requisito no funcional (rendimiento, disponibilidad, seguridad, modificabilidad...). La funcionalidad casi cualquier arquitectura la entrega; la calidad es lo que las distingue.
2. **¿Qué partes tiene un escenario de calidad?** Fuente, estímulo, artefacto, entorno, respuesta y medida de respuesta.
3. **¿Qué es un punto de sensibilidad, un trade-off y un riesgo en ATAM?** Sensibilidad: una decisión de la que depende mucho un atributo. Trade-off: una decisión que mejora un atributo y empeora otro. Riesgo: una decisión con consecuencia indeseable probable.
4. **¿Monolito modular o microservicios para un equipo de seis?** Normalmente monolito modular hexagonal, y extraer un módulo cuando un dato (escala distinta, frecuencia de despliegue, equipo propio) lo justifique. Lo defiendes con escenarios, no con moda.
5. **¿Qué es una fitness function?** Una prueba automática que protege un atributo en el tiempo (un test ArchUnit de dependencias o una prueba de carga con umbral).
6. **¿Cómo sabes que tu decisión funcionó?** Con la medida del escenario: un número que puedes observar en pruebas o producción.

## 25. Observabilidad

1. **¿Cuáles son las tres señales y para qué sirve cada una?** Métricas: tendencias y alertas. Logs: investigar un caso con contexto. Trazas: ubicar dónde se pierde el tiempo en el recorrido de una solicitud.
2. **¿Qué son RED y USE?** RED para servicios: Rate, Errors, Duration. USE para recursos: Utilization, Saturation, Errors.
3. **¿Qué diferencia hay entre una métrica de negocio y una de infraestructura?** La de negocio mide si el negocio funciona (pagos aprobados); la de infraestructura mide recursos (CPU). La de negocio suele cambiar primero cuando algo falla de verdad.
4. **Nombra los tipos de monitoreo del assessment.** Infraestructura, gestión de logs, APM, RUM, sintético, seguridad, auditoría transaccional y costos.
5. **¿Por qué alertar por síntomas y no por causas?** El síntoma es lo que siente el negocio y evita falsas alarmas por causas que no afectan al usuario.
6. **¿Qué es la alta cardinalidad y por qué daña las métricas?** Etiquetas con muchos valores distintos (un id de usuario) multiplican las series y degradan la base de métricas.

## 26. Arquitectura de Datos

1. **¿Cuáles son las cinco fases del ciclo de vida de los datos?** Captura, almacenamiento, gestión, publicación y disposición.
2. **¿Qué es la minimización de datos?** Capturar solo lo necesario para la finalidad declarada.
3. **¿Diferencia entre anonimizar y seudonimizar?** Anonimizar es irreversible y el dato deja de ser personal; seudonimizar es reversible con una llave y sigue siendo dato personal.
4. **¿Por qué negocio lee una vista y no la tabla operativa?** Para no exponer datos personales, no afectar el rendimiento transaccional y poder cambiar la tabla sin romper reportes.
5. **Un paciente pide borrar sus datos pero hay obligación legal de conservar parte. ¿Qué haces?** Anonimizas o eliminas lo que no estás obligado a conservar, restringes el acceso a lo que sí, y dejas evidencia de lo que hiciste y de la base legal para lo retenido.
6. **¿Cómo demuestras que un dato se depuró?** Con una tabla de auditoría de depuración (regla, cantidad, fecha) y un procedimiento repetible y probado.
