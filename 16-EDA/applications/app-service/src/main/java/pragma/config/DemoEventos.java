package pragma.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import pragma.kafka.CitaEventoProducer;
import pragma.model.CitaReservada;

import java.time.Instant;

@Component
@ConditionalOnProperty(name = "demo.publicar", havingValue = "true")
public class DemoEventos implements ApplicationRunner {
    private final CitaEventoProducer producer;

    public DemoEventos(CitaEventoProducer producer) {
        this.producer = producer;
    }

    @Override
    public void run(ApplicationArguments args) {
        CitaReservada evento = new CitaReservada("evt-001", "cita-42", Instant.now());
        System.out.println("=== PRUEBA MANUAL: publico el MISMO evento dos veces ===");
        producer.publicar(evento);
        producer.publicar(evento);
    }
}
