package pragma;

import org.junit.jupiter.api.Test;
import pragma.kafka.EnviadorRecordatorio;
import pragma.kafka.RecordatorioConsumer;
import pragma.model.CitaReservada;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RecordatorioIdempotenteTest {

    private final List<String> enviados = new ArrayList<>();
    private final EnviadorRecordatorio enviadorFalso = evento -> enviados.add(evento.eventoId());
    private final RecordatorioConsumer consumer = new RecordatorioConsumer(enviadorFalso);

    @Test
    void elMismoEventoDosVecesEnviaUnSoloRecordatorio() {
        CitaReservada evento = new CitaReservada("evt-1", "cita-1", Instant.now());

        consumer.escuchar(evento);
        consumer.escuchar(evento);

        assertEquals(List.of("evt-1"), enviados);
    }

    @Test
    void eventosDistintosEnvianUnRecordatorioCadaUno() {
        consumer.escuchar(new CitaReservada("evt-1", "cita-1", Instant.now()));
        consumer.escuchar(new CitaReservada("evt-2", "cita-1", Instant.now()));

        assertEquals(List.of("evt-1", "evt-2"), enviados);
    }

    @Test
    void laIdempotenciaSeBasaEnElIdDelEventoNoEnLaCita() {
        consumer.escuchar(new CitaReservada("evt-1", "cita-1", Instant.now()));
        consumer.escuchar(new CitaReservada("evt-1", "cita-OTRA", Instant.now()));

        assertEquals(1, enviados.size());
    }
}
