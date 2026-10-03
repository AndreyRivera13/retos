package pragma.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AgendaDoctor {
    private final String doctorId;
    private final List<Cita> citas;

    public AgendaDoctor(String doctorId) {
        this.doctorId = doctorId;
        this.citas = new ArrayList<>();
    }

    public Cita crearCita(
            LocalDateTime horaInicio,
            LocalDateTime horaFin) {
        Cita cita = new Cita(doctorId, horaInicio, horaFin);

        citas.add(cita);

        return cita;
    }

    public List<Cita> getCitas() {
        return citas;
    }

    public String getDoctorId() {
        return doctorId;
    }
}
