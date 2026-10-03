package pragma.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import pragma.model.CitaReservada;

@Component
public class CitaEventoProducer {
    public static final String TOPICO = "citas-reservadas";

    private final KafkaTemplate<String, CitaReservada> kafkaTemplate;

    public CitaEventoProducer(KafkaTemplate<String, CitaReservada> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publicar(CitaReservada evento) {
        kafkaTemplate.send(TOPICO, evento.citaId(), evento);
    }
}
