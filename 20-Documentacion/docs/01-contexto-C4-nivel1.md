# Vista de Contexto (C4 nivel 1)

**Público:** negocio y cualquier persona no técnica.
**Pregunta que responde:** ¿quién usa el sistema y con qué otros sistemas se habla?
**Regla:** no aparece ninguna tecnología (ni Kafka, ni Spring, ni Postgres).

```mermaid
flowchart LR
    paciente([Paciente]) -->|reserva y consulta citas| sistema[Sistema de citas]
    %% TODO: agrega al doctor/personal de la clínica, el sistema de notificaciones y cualquier otro sistema externo
    %% TODO: cada flecha lleva una etiqueta en lenguaje de negocio
```

TODO: dos líneas que expliquen en palabras de negocio qué hace el sistema.
