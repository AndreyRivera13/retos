# Prueba manual del reto 16

Contra un Kafka real (docker):

    docker compose up -d
    ./gradlew bootRun --args='--demo.publicar=true'

`DemoEventos` publica el MISMO `CitaReservada` (`evt-001`) dos veces. En la consola debe verse:

    RECORDATORIO enviado para la cita cita-42 (evento evt-001)
    EVENTO DUPLICADO ignorado: evt-001

Sin docker, la misma prueba corre contra un broker embebido:

    ./gradlew :app-service:test --tests '*KafkaExtremoAExtremoTest*'

Resultado verificado el 2026-10-03: se publican `evt-e2e` (x2) y `evt-e2e-otro` (x1) y solo se envían 2 recordatorios, uno por evento distinto.
