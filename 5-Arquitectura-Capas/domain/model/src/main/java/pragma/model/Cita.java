package pragma.model;

/** Ya está completo. */
public class Cita {
    private final String doctorId;
    private final String horario; // ej: "2026-09-21T10:00"

    public Cita(String doctorId, String horario) {
        this.doctorId = doctorId;
        this.horario = horario;
    }

    public String getDoctorId() { return doctorId; }
    public String getHorario() { return horario; }
}
