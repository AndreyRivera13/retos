package pragma.model;

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
