# Reto 20 — Documentación de arquitectura (C4 / 4+1 / ADR)

**Nivel que evalúa:** Master

**Área:** Documentación

**Estado:** 🔲 Sin empezar

## Para qué te sirve este reto

Cierra el concepto de comunicar una arquitectura a públicos distintos sin cambiar el sistema: el mismo sistema se cuenta distinto al gerente de negocio, al líder de infraestructura y al desarrollador nuevo. En Entitlement seguro ya tienes decisiones tomadas (por qué tal servicio autoriza de tal forma, por qué tal cache) que hoy viven en la cabeza de alguien — este reto es sacarlas a un ADR y a un diagrama.

## Enunciado

Documenta el sistema de citas médicas que ya construiste (retos 5, 11, 15 y 16: API de reservas, evento `CitaReservada` en Kafka, consumidor de recordatorios, base de datos). Entrega: (1) tres vistas C4 — Contexto (nivel 1, para negocio: personas y sistemas, sin tecnología), Contenedores (nivel 2, para líder técnico y equipo de infraestructura) y Componentes del contenedor "API de citas" (nivel 3, para desarrolladores); (2) un ADR en formato MADR (contexto, decisión, alternativas consideradas, consecuencias) para "¿por qué EDA con Kafka para los recordatorios?"; (3) un diagrama de secuencia de "reservar cita" con el flujo feliz y el de horario ocupado, un diagrama de clases del dominio y un diagrama entidad-relación; (4) una clase tuya refactorizada para que se explique sola (nombres, métodos pequeños, sin comentarios) con el antes y el después; (5) una guía de una página para el equipo: cuándo se escribe un ADR y qué vista se usa para qué público.

## Qué debes entregar

Carpeta `docs/` con los archivos de la estructura, todo como código (Mermaid o PlantUML) versionado en el repo, más `GUIA-DEL-EQUIPO.md`.

## Cómo sabes que lo dominas

Si le muestras el diagrama de contenedores al gerente de negocio, ¿qué sobra? Y si le muestras el de contexto al equipo de infraestructura, ¿qué le falta? Además: ¿por qué un diagrama que "nadie actualiza" es peor que no tener diagrama, y qué haces para que se mantenga vivo?

## Qué hay en esta carpeta

Todo lo que dice `TODO` es tuyo. Lo demás es plomería para que arranques sin perder tiempo.

- `docs/01-contexto-C4-nivel1.md`: Vista de Contexto. Público: negocio y quien no es técnico.
- `docs/02-contenedores-C4-nivel2.md`: Vista de Contenedores. Público: líder técnico e infraestructura.
- `docs/03-componentes-C4-nivel3.md`: Componentes de la API de citas. Público: desarrolladores.
- `docs/04-secuencia-reservar-cita.md`: Secuencia: flujo feliz y horario ocupado.
- `docs/05-clases-dominio.md`: Diagrama de clases del dominio de citas.
- `docs/06-entidad-relacion.md`: ER de la base de datos.
- `docs/adr/0001-eda-con-kafka-para-recordatorios.md`: ADR en formato MADR.
- `docs/07-self-documenting-code-antes-despues.md`: Una clase tuya antes y después.
- `docs/GUIA-DEL-EQUIPO.md`: Una página: cuándo ADR, qué vista para quién.

**Herramientas:** Mermaid (se renderiza en GitHub e IDEs), PlantUML o Structurizr DSL. El assessment acepta "boxes and lines": lo importante es el contenido y el público, no la herramienta.

## Repaso: explicación técnica del concepto

Una vista de arquitectura es un recorte del sistema pensado para un público y una pregunta concreta. C4 (Simon Brown) lo ordena en cuatro niveles de zoom: Contexto (el sistema como una caja, sus usuarios y los sistemas vecinos; lo entiende cualquiera), Contenedores (las unidades desplegables o que guardan datos: aplicación, base de datos, broker; para quien construye y opera), Componentes (las piezas internas de un contenedor: controladores, casos de uso, adaptadores; para desarrolladores) y Código (clases, casi siempre opcional porque el IDE lo genera). El modelo 4+1 de Kruchten es la alternativa: vistas lógica, de procesos, de desarrollo y física (despliegue), unidas por una vista de escenarios (casos de uso) que las valida. No hay que usar las dos; hay que escoger una y ser consistente.

Un ADR (Architecture Decision Record, Nygard) registra una decisión, no una descripción: qué contexto había, qué se decidió, qué alternativas se descartaron y qué consecuencias (buenas y malas) se aceptaron. Su valor es que dentro de un año alguien entienda por qué se hizo así sin tener que preguntarle a quien ya no está. Los diagramas de secuencia muestran el orden de las interacciones, los de clases la estructura estática del dominio y el entidad-relación los datos persistidos; cada uno responde una pregunta distinta, no son intercambiables.

Self-documenting code es la práctica complementaria: antes de escribir un comentario o un diagrama para explicar una clase, intentas que los nombres y la estructura lo expliquen solos. Un comentario que dice "suma el IVA" sobre `total = x * 1.19` se reemplaza por `precioConIva(precio)`. No elimina la documentación de arquitectura (el código no cuenta por qué se eligió Kafka), pero sí la de bajo nivel.

Cuándo NO: un diagrama de cada clase "por si acaso" (se desactualiza el mismo día), un ADR por cada decisión trivial (diluye los importantes) o un documento de 40 páginas que nadie abre. La regla práctica: documenta lo que es caro de redescubrir.

## Paso a paso

1. Escribe primero la especificación: lista las 3 preguntas que cada público te haría (¿qué hace?, ¿dónde corre?, ¿cómo está armado por dentro?) y asigna cada una a un nivel de C4.
2. Empieza por el Contexto. Si lo entiende alguien de negocio sin que le expliques, vas bien; si aparece la palabra Kafka o Spring, sobra.
3. Haz Contenedores y Componentes. Cada flecha lleva una etiqueta con el protocolo o lo que viaja (HTTPS/JSON, evento CitaReservada).
4. Redacta el ADR con al menos dos alternativas reales (por ejemplo llamada síncrona directa y una cola más simple) y una consecuencia negativa que aceptaste.
5. Haz secuencia, clases y ER a partir del código real de los retos 11, 16 y 7, no de memoria.
6. Elige una clase tuya con comentarios o nombres pobres, refactorízala y pega el antes y el después.
7. Escribe la guía del equipo. Grábate explicando los tres niveles C4 a tres públicos distintos en menos de 6 minutos.

## Autoevaluación

Antes de marcar el reto como ✅, responde en menos de 2 minutos, en voz alta o por escrito, las preguntas de repaso de este tema en `REPASO_MASTER.md` (están sin respuesta; las respuestas modelo están al final del archivo). Si te cuesta más que escribir el entregable, el hueco está en el concepto.

<!-- ENTITLEMENT:20:START -->
## Ejemplo fácil de explicar

Un mapa sirve a distintas personas con distinto zoom: el país (C4 nivel 1, contexto), la ciudad (nivel 2, contenedores), la calle (nivel 3, componentes) y la casa (nivel 4, código). Y un ADR es el acta de **por qué** se eligió ese camino:

```
# ADR-0001: Usar RabbitMQ para notificar recordatorios
Contexto → Decisión → Alternativas (Kafka, SQS) → Consecuencias (+desacople, -consistencia eventual)
```
Cuándo NO: no documentes lo que el código ya dice solo; documenta decisiones y fronteras.

## Cómo lo trabajamos en Entitlement (micros)

Evidencia del código real de los micros (rutas relativas a `Bancolombia/Micros/`). Es lo que hace el equipo; cuenta qué parte hiciste tú y cuál es del equipo.

- **Contratos de API:** OpenAPI 3.1 en `ms_actors/deployment/ApiDoc/entitlement_service_actors-documentacion.yml`, `ms_entitlement/resources/api-doc/…documentation.yaml`, `permitions_entitlement_ms/API-definition.yaml`, `ms_limit_clone_*/deployment/ApiDoc/specification/*.yaml`.
- **Contratos de eventos:** AsyncAPI en `ms_actors/deployment/ApiDoc/{External_Events,Internal_Events}`.
- **Catálogo:** `catalog-info.yaml` (Backstage) en cada micro con `sonarqube.org/project-key`; `mkdocs.yml` (techdocs) existe pero con contenido de plantilla sin editar.
- **Diagramas:** `Library_MR/ecs_logs/docs/` (componentes + 2 de secuencia), capturas ER en `bd/*.png`. (El pipeline cita un `Arquitectura.png` en `sonar.exclusions`, pero ese archivo no está en el árbol revisado: confírmalo antes de mencionarlo.)
- **Documentación de reglas del equipo:** la skill `java-code-review` (`Library_MR/cursor-sources`) describe estructura y principios.
- **README:** la mayoría son plantilla del scaffold (checklist de HPA, pools, tolerancia a fallos); `Library_MR/ecs_logs/README.md` es la excepción con módulos y diagramas.
- **Incidentes:** `incidentes/` son volcados de logs/consultas, sin estructura de postmortem.

**No encontrado en los micros (no lo afirmes como experiencia del proyecto):**

- ADR, C4 y diagramas de código (PlantUML/Mermaid): NO ENCONTRADO. Es tu brecha más clara: el reto 20 la cierra.

**Cómo contarlo en la entrevista:** "Documentamos contratos (OpenAPI/AsyncAPI) y catálogo (Backstage); lo que faltaba eran ADR y C4, que son lo que trabajo en el reto 20".
<!-- ENTITLEMENT:20:END -->

## Cómo cerré esta brecha (mi implementación)

*Completo esto yo mismo cuando termine el reto, no antes. Con lo que ya entregué, respondo aquí:*

- *¿Qué archivos y decisiones concretas produje y qué responsabilidad tiene cada uno?*
- *¿Cómo mi entrega, específicamente, resuelve el concepto de este reto? Cito mis propios archivos.*
- *¿Qué error o malentendido tuve en el camino y cómo lo corregí?*

## SDD — Spec-Driven Development

Antes de producir nada en este reto, escribe (3 a 5 líneas) la especificación de lo que vas a entregar: qué archivos, qué decisión toma cada uno, qué casos borde cubres y cómo vas a verificar que está bien. La decisión de diseño se toma antes de la primera línea, no se descubre mientras escribes. Puedes usar la IA para pulir la spec o para preguntarte lo que se te escapa, pero qué debe hacer cada pieza lo decides tú: es el feedback más repetido de tus evaluadores.

---
