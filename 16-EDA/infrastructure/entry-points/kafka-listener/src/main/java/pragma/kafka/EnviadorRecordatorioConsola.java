package pragma.kafka;

import org.springframework.stereotype.Component;
import pragma.model.CitaReservada;

@Component
public class EnviadorRecordatorioConsola implements EnviadorRecordatorio {
    @Override
    public void enviar(CitaReservada evento) {
        System.out.println("RECORDATORIO enviado para la cita " + evento.citaId()
                + " (evento " + evento.eventoId() + ")");
    }
}
