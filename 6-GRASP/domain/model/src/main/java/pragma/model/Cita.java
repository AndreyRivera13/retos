package pragma.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Cita {
    private final String doctorId;
    private final LocalDateTime horaInicio;
    private final LocalDateTime horaFin;

    public Cita(String doctorId, LocalDateTime horaInicio, LocalDateTime horaFin) {
        this.doctorId = doctorId;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public long duracionEnMinutos() {
        return Duration.between(horaInicio, horaFin).toMinutes();
    }

    public boolean seSolapaCon(Cita otra) {
        return horaInicio.isBefore(otra.horaFin)
                && otra.horaInicio.isBefore(horaFin);
    }

    public String getDoctorId() {
        return doctorId;
    }

    public LocalDateTime getHoraInicio() {
        return horaInicio;
    }

    public LocalDateTime getHoraFin() {
        return horaFin;
    }
}
