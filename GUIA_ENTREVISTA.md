# Guía de entrevista — los 26 retos de un vistazo

Para usar ANTES de abrir el código: busca el número del reto, di la frase de apertura ("de qué trata") y después muestra los archivos que indica. Cada reto tiene su carpeta con el mismo número (`1-POO`, `2-SOLID`, ..., `26-Arquitectura-Datos`) dentro de `retos/`.

## Mapa rápido

| # | Carpeta | De qué trata (en una línea) | Estado |
|---|---|---|---|
| 1 | `1-POO` | Nómina con herencia, polimorfismo y genéricos | ✅ Cerrado |
| 2 | `2-SOLID` | Refactor de un validador de créditos (SRP, OCP, Strategy) | ✅ Cerrado |
| 3 | `3-Excepciones` | Procesador de pagos con excepciones checked y try-with-resources | ✅ Cerrado |
| 4 | `4-Estructuras-Datos` | Totales por cliente con `Map`, a mano, sin Collectors | ✅ Cerrado |
| 5 | `5-Arquitectura-Capas` | Citas en 3 capas: Controller / Service / Repository | ✅ Cerrado |
| 6 | `6-GRASP` | Quién tiene cada responsabilidad: Experto y Creador | ✅ Cerrado |
| 7 | `7-Normalizacion-BD` | Normalizar una tabla de matrículas hasta 3FN | ✅ Cerrado |
| 8 | `8-Uso-IA` | Dónde sí y dónde no usé IA (sin código) | ✅ Cerrado |
| 9 | `9-WebSocket` | Notificaciones en tiempo real: broadcast y mensaje privado | ✅ Cerrado |
| 10 | `10-Cache` | Cache-Aside con TTL (+ comparación con Write-Through) | ✅ Cerrado |
| 11 | `11-Clean-Architecture` | Citas en hexagonal: puertos y adaptadores intercambiables | ✅ Cerrado |
| 12 | `12-GoF` | Builder + Strategy + Observer en un generador de reportes | ✅ Cerrado |
| 13 | `13-Resiliencia` | Pasarela de pagos que falla: Retry, CircuitBreaker, Bulkhead, Fallback | ✅ Cerrado |
| 14 | `14-BDD` | Pruebas Cucumber para reservar una cita | ✅ Cerrado |
| 15 | `15-Reactivo` | El use case de citas en Mono/Flux con timeout y fallback | ✅ Cerrado |
| 16 | `16-EDA` | Evento `CitaReservada` en Kafka con consumidor idempotente | ✅ Cerrado |
| 17 | `17-IaC` | Terraform con IAM de mínimo privilegio + pipeline de escaneo | ✅ Cerrado |
| 18 | `18-OWASP` | Encontrar y corregir un control de acceso roto | ✅ Cerrado |
| 19 | `19-DDD` | Cuenta bancaria: Aggregate Root, Value Object y evento de dominio | ✅ Cerrado |
| 20 | `20-Documentacion` | Documentación de arquitectura: C4, ADR y diagramas de secuencia, clases y ER | 🔲 Pendiente |
| 21 | `21-Cloud-Infraestructura` | Infraestructura mínima en AWS como código: enrutamiento, cómputo y almacenamiento | 🔲 Pendiente |
| 22 | `22-DevOps-IaC` | IaC con dos herramientas, zero trust, pruebas de rendimiento e IA en DevSecOps | 🔲 Pendiente |
| 23 | `23-Seguridad-Arquitectura` | Tácticas de seguridad (resistir y detectar) contra la enumeración de ids | 🔲 Pendiente |
| 24 | `24-Diseno-Arquitectura` | Atributos de calidad, escenarios, comparación de arquitecturas y ADR | 🔲 Pendiente |
| 25 | `25-Observabilidad` | Métricas, logs y trazas de la pasarela, con alerta y runbook | 🔲 Pendiente |
| 26 | `26-Arquitectura-Datos` | Ciclo de vida de los datos de citas: captura a disposición | 🔲 Pendiente |

**Hilo conductor para contarlo:** los retos 5, 6, 11, 14, 15 y 16 son el MISMO sistema de citas médicas visto desde ángulos distintos: primero en capas (5), luego asignando responsabilidades (6), luego en hexagonal (11), después probado con BDD (14), reactivo (15) y publicando eventos (16). Si el entrevistador pregunta "¿cómo se conectan?", ahí está la respuesta.

**Agrupados por tema:** fundamentos (1, 2, 3, 4) · arquitectura y diseño (5, 6, 11, 19, 12) · datos y caché (7, 10) · comunicación (9, 16) · resiliencia y calidad (13, 14) · paradigma reactivo (15) · seguridad e infraestructura (17, 18) · IA (8) · nivel Master (20 al 26): documentación (20), cloud e IaC (21, 22), seguridad (23), diseño de arquitectura (24), observabilidad (25), datos (26).

> Los retos 1 al 19 están cerrados. Los retos 20 al 26 (nivel Master) están pendientes: sus frases de apertura salen del enunciado y los "archivos a mostrar" son los de la carpeta de arranque. Cuando los cierres, actualiza el estado y agrega aquí lo que realmente decidiste.

---

## Reto 1 — POO (`1-POO`)

**Antes de abrir el código, di:** "Este proyecto modela una nómina con herencia y polimorfismo: un `Empleado` abstracto con dos tipos, fijo y por horas, y cada uno calcula su salario a su manera. Hay una clase genérica `Nomina` que suma el total sin saber de qué tipo es cada empleado. La idea es mostrar polimorfismo y genéricos en vez de `instanceof` o casts."

**Archivos a mostrar:** `Empleado` (abstracta), `EmpleadoFijo`, `EmpleadoPorHoras`, `Bonificable` (solo la implementa el fijo), `Nomina<T extends Empleado>`.

**Para contar:** tuve un bug real, `EmpleadoFijo.calcularSalario()` devolvía `salarioBase * 2`; era un error de lógica, no de diseño.

**Pregunta probable:** ¿por qué `Nomina<T>` no necesita saber el tipo concreto, y qué diferencia hay con `List<Object>` y casts?

## Reto 2 — SOLID (`2-SOLID`)

**Antes de abrir el código, di:** "Parte de un validador de solicitudes de crédito que lo hacía todo en un método con `if` anidados. Lo refactoricé con SRP y OCP: cada regla (edad, ingresos, historial) es su propia clase detrás de una interfaz, y el validador solo recorre la lista. Agregar una regla nueva es crear una clase, sin tocar las demás."

**Archivos a mostrar:** `ValidadorSolicitud`, `ReglaValidacion`, `ReglaEdad`, `ReglaIngresos`, `ReglaHistorial`.

**Para contar:** cometí dos bugs yo solo (`ReglaHistorial` no leía el campo real y `ReglaEdad` usaba `>` en vez de `>=`) y los corregí.

**Pregunta probable:** ¿puedes agregar una regla nueva creando solo una clase, sin tocar ninguna existente?

## Reto 3 — Excepciones (`3-Excepciones`)

**Antes de abrir el código, di:** "Un procesador de pagos con dos excepciones checked propias, saldo insuficiente y pago inválido, que encadenan la causa. Usa un recurso simulado que se cierra con try-with-resources aunque el pago falle. Lo probé con tres tests."

**Archivos a mostrar:** `ProcesadorPagos`, `Pago`, `RegistroTransaccion` (`AutoCloseable`), `SaldoInsuficienteException`, `PagoInvalidoException`, `ProcesadorPagosTest`.

**Pregunta probable:** si `procesar()` lanza una excepción dentro del `try-with-resources`, ¿en qué orden pasan las cosas y se cierra el recurso?

## Reto 4 — Estructuras de datos (`4-Estructuras-Datos`)

**Antes de abrir el código, di:** "Recibe transacciones con formato `cliente:monto` y calcula el total por cliente, el cliente con mayor total y la lista de clientes ordenada de mayor a menor. Lo hice a mano con un `Map`, sin `Collectors`. La complejidad es O(n log n), dominada por el ordenamiento."

**Archivos a mostrar:** `AnalizadorTransacciones` (`totalPorCliente`, `clienteConMayorTotal`, `ordenarPorTotalDescendente`).

**Para contar:** al pasar de `Collectors.toMap` a iteración manual dejé dos líneas que acumulaban el mismo monto y duplicaban los totales; lo detecté y dejé solo `merge`.

**Pregunta probable:** ¿por qué un `Map` y no recorrer la lista dos veces, y qué pasaría con 10 millones de transacciones?

## Reto 5 — Arquitectura de capas (`5-Arquitectura-Capas`)

**Antes de abrir el código, di:** "Un mini sistema de reservas de citas en tres capas, sin frameworks. El Controller solo delega, el Service tiene la regla de negocio (un doctor no puede tener dos citas en el mismo horario) y el Repository en memoria vive detrás de una interfaz."

**Archivos a mostrar:** `CitaController`, `CitaService` (aquí está la regla), `CitaRepository` y `CitaRepositoryEnMemoria`, `Cita`.

**Pregunta probable:** ¿en qué capa vive la regla y por qué no en el Controller ni en el Repository?

## Reto 6 — GRASP (`6-GRASP`)

**Antes de abrir el código, di:** "Sobre el mismo sistema de citas decidí quién tiene cada responsabilidad. Experto en Información: la duración la calcula `Cita`, que tiene los datos. Creador: quien instancia las citas es `AgendaDoctor`, que las agrupa."

**Archivos a mostrar:** `Cita.duracionEnMinutos()`, `AgendaDoctor.crearCita()`, `AgendaDoctorUseCase.duracionTotalDelDia()`.

**Pregunta probable:** nombra una clase candidata alternativa para cada responsabilidad y por qué la descartaste.

## Reto 7 — Normalización de BD (`7-Normalizacion-BD`)

**Antes de abrir el código, di:** "Normalización de una tabla de matrículas hasta 3FN: eliminé los grupos repetidos de cursos (1FN), separé los cursos por dependencia parcial (2FN) y los profesores por dependencia transitiva (3FN). Termina con el diagrama E-R."

**Archivos a mostrar:** `reto.sql` (no tiene Gradle: solo SQL).

**Pregunta probable:** ¿qué inconsistencia real evitas al separar el profesor en su propia tabla?

## Reto 8 — Uso de IA (`8-Uso-IA`)

**Antes de abrir el código, di:** "Este no es código: es una tabla que documenta en qué tareas usé IA y en cuáles no, con evidencia real de mi trabajo en estos mismos retos, y qué decisiones de diseño tomé yo en vez de delegarlas."

**Archivo a mostrar:** el `README.md` de la carpeta (la tabla "sí usé IA / no usé IA").

**Pregunta probable:** sobre cualquier fila, ¿por qué decidiste eso? Hay que poder responder sin abrir el chat.

## Reto 9 — WebSocket (`9-WebSocket`)

**Antes de abrir el código, di:** "Un servidor WebSocket de notificaciones. Al conectarse recibes una bienvenida; un mensaje de tipo `alerta` le llega a todos los demás clientes y uno `privado` solo al destino por id de sesión. El registro de sesiones es seguro ante concurrencia y maneja las sesiones caídas."

**Archivos a mostrar:** `NotificacionesEndpoint` (`onOpen`, `onMessage`, `onClose`), `SesionesRegistro` (`CopyOnWriteArraySet`), `MensajeEntrante`.

**Para contar:** el broadcast al principio incluía al remitente; lo excluí. Y envolví cada `sendText` en su propio try/catch, para que una sesión muerta (sin `onClose`) no corte el envío al resto y se saque del registro.

**Pregunta probable:** ¿cómo garantizas que la lista de sesiones sea segura ante concurrencia, y qué pasa si un cliente se desconecta sin `onClose`?

## Reto 10 — Caché (`10-Cache`)

**Antes de abrir el código, di:** "Un catálogo de precios con Cache-Aside y TTL de 30 segundos: si hay hit vigente responde del caché; si no, va al origen y cachea. Al actualizar, escribe en el origen y después invalida el caché. Además documenté en `WRITE-THROUGH.md` cómo cambiaría con Write-Through."

**Archivos a mostrar:** `CatalogoPreciosUseCase`, `EntradaCache`, `CatalogoPreciosUseCaseTest` (miss, hit, actualizar, expirado), `WRITE-THROUGH.md`, y la prueba manual en `MainApplication`.

**Para contar:** el TTL entra por constructor para poder probar la expiración sin esperar 30 segundos. Tema abierto que conozco: el `HashMap` no es seguro bajo concurrencia; en producción usaría `ConcurrentHashMap`.

**Pregunta probable:** ¿qué problema resuelve el TTL que la invalidación manual no resuelve? (cuando otro servicio escribe directo en la BD).

## Reto 11 — Clean Architecture (`11-Clean-Architecture`)

**Antes de abrir el código, di:** "El sistema de citas en arquitectura hexagonal: dominio puro, un use case que depende solo de un puerto, y dos adaptadores intercambiables, memoria y JPA con H2. Demuestro que el dominio compila sin la carpeta `infrastructure`."

**Archivos a mostrar:** `Cita` (sin anotaciones), `CitaRepositoryPort`, `GestionarCitasUseCase` (con la regla), `CitaRepositoryMemoriaAdapter`, `CitaRepositoryJpaAdapter` + `CitaEntity` + `CitaMapper`, `PRUEBA-SIN-INFRAESTRUCTURA.txt` y la prueba manual en `MainApplication` (mismos pasos con ambos adaptadores).

**Para contar:** para aplicar la regla tuve que ampliar el puerto con `existePorDoctorYHorario`, lo que obligó a implementarlo en los dos adaptadores: el puerto es el contrato.

**Pregunta probable:** ¿qué se rompería si pusieras `@Entity` directamente en `Cita`?

## Reto 12 — GoF (`12-GoF`)

**Antes de abrir el código, di:** "Un generador de reportes con tres patrones: Builder para armar el reporte con secciones opcionales, sin un constructor de seis parámetros; Strategy para exportar el mismo reporte a PDF o CSV, elegido en runtime; y Observer para notificar a suscriptores sin que el generador los conozca."

**Archivos a mostrar:** `Reporte` y su `Builder`, `ExportadorReporte` con `ExportadorPDF` y `ExportadorCSV`, `GeneradorReportes` (recibe la estrategia en `generar(reporte, exportador)`), `ObservadorReporte` con `ContadorReportes` y `LogReportes`, y la prueba manual en `MainApplication`.

**Para contar:** mi primer Builder era mutable (constructor público, `build()` devolvía la misma instancia y `getSecciones()` exponía la lista interna); lo corregí con constructor privado y `List.copyOf`. En el CSV escapo comas y comillas. Tema abierto que conozco: si un observador lanza una excepción, los que vienen después no se notifican.

**Pregunta probable:** ¿qué problema tendrías si las notificaciones fueran llamadas directas dentro de `Reporte` en vez de Observer?

## Reto 13 — Resiliencia (`13-Resiliencia`) — ✅

**Antes de abrir el código, di:** "Una pasarela de pagos simulada que falla cerca del 40% de las veces, protegida con Resilience4j: Retry con backoff exponencial, CircuitBreaker, Bulkhead y un fallback que responde 'pago en proceso' en vez de un error crudo."

**Archivos a mostrar:** `PasarelaPagosAdapter` (las tres anotaciones y el fallback), `application.yaml` (valores de resiliencia), `RealizarPagoUseCase` (solo delega), `PasarelaResilienciaTest` y `LOG-CIRCUITO.txt`.

**Para contar:** el fallback va en `@Retry` (la capa más externa); si va en `@CircuitBreaker` devuelve una respuesta "exitosa" y el Retry nunca reintenta. `minimum-number-of-calls` vale 100 por defecto: con ventana de 10 el circuito nunca abriría. El Retry ignora `CallNotPermittedException` y `BulkheadFullException`. En Spring Boot 4 hay que usar `resilience4j-spring-boot4`.

**Pregunta probable:** ¿por qué combinar Bulkhead con CircuitBreaker? El CircuitBreaker protege del fallo; el Bulkhead de la lentitud (una pasarela lenta no falla, pero agotaría los hilos).

## Reto 14 — BDD (`14-BDD`) — ✅

**Antes de abrir el código, di:** "Pruebas BDD con Cucumber para reservar una cita: un archivo `.feature` en Gherkin, en español, con tres escenarios (reserva exitosa, horario ocupado y doctor inexistente) conectado al use case real."

**Archivos a mostrar:** `reservar_cita.feature` (primero, sin código), `ReservarCitaSteps`, `GestionarCitasUseCase`.

**Para contar:** el `.feature` es una prueba ejecutable, no solo documentación: si el use case cambia, el escenario falla. Cada escenario arranca con instancias nuevas, sin estado compartido. El use case valida primero el doctor y luego el horario.

**Pregunta probable:** ¿un compañero no técnico puede leer el `.feature` y decir qué se prueba sin que se lo expliques?

## Reto 15 — Reactivo (`15-Reactivo`) — ✅

**Antes de abrir el código, di:** "El use case de citas llevado a programación reactiva: `Mono` para reservar y `Flux` para las citas del día, con un timeout de 2 segundos que, si el origen no responde, completa vacío en vez de propagar el error."

**Archivos a mostrar:** `GestionarCitasReactivoUseCase` (`reservar` con `Mono.defer` + `Mono.error`; `citasDelDia` con `timeout` + `onErrorResume(TimeoutException.class, ...)`), `GestionarCitasReactivoUseCaseTest` (StepVerifier) y la prueba manual en `MainApplication`.

**Para contar:** `Mono`/`Flux` son perezosos: sin `subscribe()` o `block()` no se ejecuta nada, ni siquiera la validación. Dato inválido = `Mono.error`; vacío lo reservo para "consulté y no hay nada". Mi versión inicial no compilaba (`Flux<Object>`), y el timeout nunca se disparaba porque el origen de prueba era `Flux.empty()`, que completa de inmediato (141 ms contra ~2005 ms con un origen que nunca emite). Latencia y timeout inyectables para testear sin esperar 2 s reales. Tema abierto que conozco: "sin citas" y "origen lento" se ven igual para el consumidor.

**Pregunta probable:** ¿qué diferencia hay entre devolver un `Mono` vacío por diseño y lanzar una excepción, y cuándo usas cada uno? Y, con tu evaluador (lideró la migración a WebFlux): ¿qué se vuelve difícil de depurar en reactivo y qué no migrarías?

## Reto 16 — EDA (`16-EDA`) — ✅

**Antes de abrir el código, di:** "Arquitectura orientada a eventos con Kafka: al reservar una cita se publica un evento `CitaReservada` y un consumidor separado simula el recordatorio de forma idempotente, ignorando el evento si ya lo procesó."

**Archivos a mostrar:** `CitaReservada`, `CitaEventoProducer`, `RecordatorioConsumer`, `KafkaExtremoAExtremoTest`.

**Para contar:** uso `citaId` como clave del mensaje (orden por partición) e `eventoId` para la idempotencia. Verificado con un Kafka embebido: el mismo evento dos veces envía un solo recordatorio. En Spring Boot 4 hace falta `spring-boot-starter-kafka`. Tema abierto: el `Set` en memoria se pierde al reiniciar; en producción, tabla con restricción de unicidad o Redis con TTL. No lo corrí contra Docker real, solo embebido.

**Pregunta probable:** ¿qué semántica de entrega asumiste (at-least-once) y qué bug tendrías en producción sin la verificación de idempotencia?

## Reto 17 — IaC (`17-IaC`) — ✅

**Antes de abrir el código, di:** "Infraestructura como código: un Terraform con una tabla DynamoDB y un rol IAM de mínimo privilegio, más un pipeline con escaneo de secretos, de dependencias y de `tfsec`/`checkov`, en el orden correcto y con comentarios de por qué."

**Archivos a mostrar:** `main.tf`, `pipeline.yml` (sin Gradle: solo `.tf` y `.yml`), `RESULTADOS-ESCANEO.txt`.

**Para contar:** el rol solo tiene `GetItem`, `PutItem` y `Query` sobre el ARN de la tabla; NO tiene `DeleteItem` ni `Scan`. Orden del pipeline: secretos, luego IaC y dependencias en paralelo, deploy al final solo en `main` y con OIDC. Verificado con `terraform validate`, tfsec y checkov; checkov me falló una vez (KMS sin policy) y lo corregí.

**Pregunta probable:** ¿qué es el principio de menor privilegio en el rol que escribiste, y qué permiso NO le diste y por qué?

## Reto 18 — OWASP (`18-OWASP`) — ✅

**Antes de abrir el código, di:** "Análisis de una vulnerabilidad: un endpoint que devuelve los documentos de cualquier usuario según el `id` de la URL. Identifico la categoría OWASP y el CWE, explico el vector de ataque y lo corrijo verificando que el usuario autenticado sea el dueño."

**Archivos a mostrar:** `DocumentoController` (el chequeo de dueño), `SecurityConfig`, `ControlDeAccesoTest`.

**Para contar:** A01:2021 Broken Access Control; CWE-639 (IDOR), dentro de CWE-284/CWE-862. Vector: cambiar el 1 por el 2 en la URL. Corregido con `autenticado.getName()` contra `id` y `AccessDeniedException` (403). Probado por HTTP real: dueño 200, otro usuario 403, sin credenciales 401. Alternativa declarativa: `@PreAuthorize`. Tema abierto: 403 revela que el recurso existe; a veces se prefiere 404.

**Pregunta probable:** ¿cuál es la diferencia entre control de acceso roto y autenticación rota? Acá el token es válido; lo que falta es autorización sobre ese recurso.

## Reto 19 — DDD (`19-DDD`) — ✅

**Antes de abrir el código, di:** "El modelado de una cuenta bancaria con DDD: `CuentaBancaria` como Aggregate Root, `Dinero` como Value Object inmutable que no mezcla monedas, y un evento de dominio `RetiroRealizado` tras un retiro exitoso."

**Archivos a mostrar:** `CuentaBancaria`, `Dinero`, `RetiroRealizado`, `DineroTest`, `CuentaBancariaTest`.

**Para contar:** `Dinero` es VO porque no tiene identidad (100 COP son intercambiables con otros 100 COP); `CuentaBancaria` es Entidad porque sigue siendo la misma aunque cambie su saldo. Si un retiro falla no cambia el saldo ni se emite el evento. `equals()` usa `compareTo` porque `BigDecimal.equals` distingue 100 de 100.00, y `hashCode()` con `stripTrailingZeros`. Agregué `bloquear()` porque el esqueleto no tenía cómo llegar a `BLOQUEADA`.

**Pregunta probable:** ¿qué garantía del dominio se rompería si `Dinero` tuviera un `setMonto()` público?

## Reto 20 — Documentación (`20-Documentacion`) — pendiente

**Antes de abrir el código, di:** "La documentación de arquitectura del sistema de citas: tres vistas C4 pensadas para públicos distintos, un ADR sobre por qué usé eventos con Kafka, y los diagramas de secuencia, clases y entidad-relación, todo versionado como código."

**Archivos a mostrar:** `docs/01…03` (C4), `docs/adr/0001…`, `docs/GUIA-DEL-EQUIPO.md`.

**Pregunta probable:** ¿qué sobra en el diagrama de contenedores para el gerente de negocio?

## Reto 21 — Cloud (`21-Cloud-Infraestructura`) — pendiente

**Antes de abrir el código, di:** "La infraestructura mínima del servicio de citas en AWS como código: enrutamiento, cómputo y almacenamiento, con la justificación de cada elección y qué atributo de calidad favorece."

**Archivos a mostrar:** `iac/main.tf`, `docs/decisiones-infra.md`.

**Pregunta probable:** ¿por qué ese cómputo y no los otros dos, y qué se rompe primero a 10.000 solicitudes por segundo?

## Reto 22 — DevOps / IaC (`22-DevOps-IaC`) — pendiente

**Antes de abrir el código, di:** "La misma infraestructura en una segunda herramienta de IaC, con controles zero trust verificados en el pipeline, una prueba de rendimiento con umbrales y un paso de IA para priorizar hallazgos con revisión humana."

**Archivos a mostrar:** `cloudformation/`, `pipeline.yml`, `k6/citas.js`, `triage-ia/`, `docs/zero-trust.md`.

**Pregunta probable:** ¿qué NO le delegarías a la IA en el triage de hallazgos?

## Reto 23 — Seguridad (`23-Seguridad-Arquitectura`) — pendiente

**Antes de abrir el código, di:** "Tácticas de seguridad aplicadas a la API de documentos: limitar la tasa y auditar denegaciones, demostradas con una prueba de enumeración de identificadores antes y después."

**Archivos a mostrar:** `app/` (filtro de límite y auditoría), `AtaqueEnumeracionTest`, `docs/tacticas.md`.

**Pregunta probable:** ¿por qué el rate limiting no corrige el IDOR y qué corrige entonces?

## Reto 24 — Diseño de arquitectura (`24-Diseno-Arquitectura`) — pendiente

**Antes de abrir el código, di:** "El diseño de la arquitectura del sistema de citas a partir de atributos de calidad: árbol de utilidad, tres escenarios medibles, comparación de dos arquitecturas y un ADR con la decisión, protegida por una fitness function."

**Archivos a mostrar:** `docs/01…03`, `docs/adr/0001…`, `fitness/`.

**Pregunta probable:** ¿qué atributo sacrificaste para favorecer cuál y con qué dato sabes que funcionó?

## Reto 25 — Observabilidad (`25-Observabilidad`) — pendiente

**Antes de abrir el código, di:** "Observabilidad de la pasarela de pagos: métricas técnicas y de negocio, logs JSON con correlationId, una traza, una alerta por síntoma con su runbook y la clasificación de cada señal."

**Archivos a mostrar:** `app/` (métricas y MDC), `observabilidad/`, `docs/clasificacion-de-senales.md`, `docs/runbook-alerta.md`.

**Pregunta probable:** ¿cómo distingues con tus señales que la pasarela está lenta de que tu servicio está lento?

## Reto 26 — Arquitectura de Datos (`26-Arquitectura-Datos`) — pendiente

**Antes de abrir el código, di:** "El ciclo de vida de los datos de citas médicas: captura, almacenamiento, gestión, publicación y disposición, con roles, una vista sin datos personales y una depuración con evidencia, probados en Postgres."

**Archivos a mostrar:** `docs/ciclo-de-vida.md`, `sql/01…03`.

**Pregunta probable:** Si un paciente pide borrar sus datos pero hay obligación legal de conservar parte, ¿qué haces en cada fase?
