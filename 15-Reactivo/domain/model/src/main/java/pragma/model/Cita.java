package pragma.model;

import java.time.LocalDate;

/** Ya está completo. */
public class Cita {
    private final String doctorId;
    private final LocalDate fecha;

    public Cita(String doctorId, LocalDate fecha) {
        this.doctorId = doctorId;
        this.fecha = fecha;
    }

    public String getDoctorId() { return doctorId; }
    public LocalDate getFecha() { return fecha; }
}
