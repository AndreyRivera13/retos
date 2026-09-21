package pragma.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import pragma.model.CitaReservada;

/**
 * TODO: publica el evento en el tópico "citas-reservadas" usando kafkaTemplate.send(...).
 * (ver ejemplo OrdenService del documento de retos, tema 16).
 */
@Component
public class CitaEventoProducer {
    private final KafkaTemplate<String, CitaReservada> kafkaTemplate;

    public CitaEventoProducer(KafkaTemplate<String, CitaReservada> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publicar(CitaReservada evento) {
        throw new UnsupportedOperationException("TODO: implementar CitaEventoProducer.publicar");
    }
}
