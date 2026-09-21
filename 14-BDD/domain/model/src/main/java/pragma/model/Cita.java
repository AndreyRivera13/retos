package pragma.model;

/** Ya está completo — reutiliza el mismo modelo simple de los retos 5/11. */
public class Cita {
    private final String doctorId;
    private final String horario;

    public Cita(String doctorId, String horario) {
        this.doctorId = doctorId;
        this.horario = horario;
    }

    public String getDoctorId() { return doctorId; }
    public String getHorario() { return horario; }
}
