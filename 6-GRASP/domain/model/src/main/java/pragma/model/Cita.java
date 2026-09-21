package pragma.model;

import java.time.LocalDateTime;

/**
 * TODO (Experto en Información - GRASP): Cita tiene los datos (horaInicio,
 * horaFin) para poder calcular su propia duración. No lo calcules afuera
 * (en un Service) si Cita ya tiene todo lo necesario para hacerlo.
 */
public class Cita {
    private final String doctorId;
    private final LocalDateTime horaInicio;
    private final LocalDateTime horaFin;

    public Cita(String doctorId, LocalDateTime horaInicio, LocalDateTime horaFin) {
        this.doctorId = doctorId;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    /** TODO: duración en minutos entre horaInicio y horaFin. */
    public long duracionEnMinutos() {
        throw new UnsupportedOperationException("TODO: implementar Cita.duracionEnMinutos");
    }

    /** TODO: ¿esta cita se solapa en el tiempo con otra? */
    public boolean seSolapaCon(Cita otra) {
        throw new UnsupportedOperationException("TODO: implementar Cita.seSolapaCon");
    }

    public String getDoctorId() { return doctorId; }
    public LocalDateTime getHoraInicio() { return horaInicio; }
    public LocalDateTime getHoraFin() { return horaFin; }
}
