package pragma;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.test.context.EmbeddedKafka;
import pragma.kafka.CitaEventoProducer;
import pragma.kafka.EnviadorRecordatorio;
import pragma.model.CitaReservada;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(properties = "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}")
@EmbeddedKafka(partitions = 1, topics = "citas-reservadas")
class KafkaExtremoAExtremoTest {

    static final List<String> ENVIADOS = new CopyOnWriteArrayList<>();

    @TestConfiguration
    static class EnviadorRegistrador {
        @Bean
        @Primary
        EnviadorRecordatorio enviadorRegistrador() {
            return evento -> ENVIADOS.add(evento.eventoId());
        }
    }

    @Autowired
    CitaEventoProducer producer;

    @Test
    void publicarElMismoEventoDosVecesEnviaUnSoloRecordatorio() throws InterruptedException {
        CitaReservada evento = new CitaReservada("evt-e2e", "cita-9", Instant.now());
        CitaReservada otro = new CitaReservada("evt-e2e-otro", "cita-10", Instant.now());

        producer.publicar(evento);
        producer.publicar(evento);
        producer.publicar(otro);

        long limite = System.nanoTime() + Duration.ofSeconds(20).toNanos();
        while (ENVIADOS.size() < 2 && System.nanoTime() < limite) {
            Thread.sleep(100);
        }
        Thread.sleep(1000);

        assertEquals(List.of("evt-e2e", "evt-e2e-otro"), ENVIADOS);
    }
}
