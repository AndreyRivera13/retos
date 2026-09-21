package pragma.model;

/**
 * Dominio puro — NO debe tener imports de Spring ni de JPA (@Entity, etc).
 * Si en algún momento sientes que "necesitas" una anotación de framework
 * aquí, es una señal de que estás rompiendo la regla de dependencias del
 * hexágono (el dominio no debe conocer la infraestructura).
 * Ya está completo.
 */
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
