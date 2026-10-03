package pragma.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import pragma.model.CitaReservada;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RecordatorioConsumer {
    private final Set<String> idsProcesados = ConcurrentHashMap.newKeySet();
    private final EnviadorRecordatorio enviador;

    public RecordatorioConsumer(EnviadorRecordatorio enviador) {
        this.enviador = enviador;
    }

    @KafkaListener(topics = "citas-reservadas")
    public void escuchar(CitaReservada evento) {
        if (!idsProcesados.add(evento.eventoId())) {
            System.out.println("EVENTO DUPLICADO ignorado: " + evento.eventoId());
            return;
        }
        enviador.enviar(evento);
    }
}
