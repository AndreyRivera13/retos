package pragma.kafka;

import pragma.model.CitaReservada;

public interface EnviadorRecordatorio {
    void enviar(CitaReservada evento);
}
