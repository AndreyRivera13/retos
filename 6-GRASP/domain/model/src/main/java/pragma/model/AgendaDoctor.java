package pragma.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa la agenda de un doctor.
 */
public class AgendaDoctor {

    private final String doctorId;
    private final List<Cita> citas;

    public AgendaDoctor(String doctorId) {
        this.doctorId = doctorId;
        this.citas = new ArrayList<>();
    }

    /**
     * GRASP Creator:
     * AgendaDoctor crea las Cita porque es quien mantiene y agrupa
     * las citas correspondientes a un doctor.
     */
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