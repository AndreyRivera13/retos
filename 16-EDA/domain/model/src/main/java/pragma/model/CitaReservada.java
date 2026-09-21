package pragma.model;

import java.time.Instant;

/** Evento de dominio. Ya está completo. */
public record CitaReservada(String eventoId, String citaId, Instant ocurridoEn) {
}
