package pragma.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import pragma.model.CitaReservada;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TODO: implementa escuchar(...) de forma IDEMPOTENTE:
 *  - si idsProcesados.add(evento.eventoId()) devuelve false, el evento ya se
 *    procesó -> loguéalo y no hagas nada más (return).
 *  - si devuelve true, simula el envío del recordatorio (System.out.println).
 *
 * Debes poder explicar qué semántica de entrega asumiste (at-least-once) y
 * por qué sin esta verificación tendrías un bug real en producción.
 */
@Component
public class RecordatorioConsumer {
    private final Set<String> idsProcesados = ConcurrentHashMap.newKeySet();

    @KafkaListener(topics = "citas-reservadas")
    public void escuchar(CitaReservada evento) {
        throw new UnsupportedOperationException("TODO: implementar RecordatorioConsumer.escuchar");
    }
}
