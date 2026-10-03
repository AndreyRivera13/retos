package pragma.model;

public class Cita {
    private final String id;
    private final String doctorId;
    private final String horario;

    public Cita(String id, String doctorId, String horario) {
        this.id = id;
        this.doctorId = doctorId;
        this.horario = horario;
    }

    public String getId() { return id; }
    public String getDoctorId() { return doctorId; }
    public String getHorario() { return horario; }
}
