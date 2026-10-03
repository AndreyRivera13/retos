package pragma.usecase;

import pragma.model.Cita;

import java.util.ArrayList;
import java.util.List;

public class GestionarCitasUseCase {
    private final List<Cita> citas = new ArrayList<>();
    private final List<String> doctoresValidos = List.of("dra-lopez", "dr-perez");

    public Cita reservar(String doctorId, String horario) {
        if (!doctoresValidos.contains(doctorId)) {
            throw new IllegalArgumentException("Doctor no existe");
        }
        boolean ocupado = citas.stream()
                .anyMatch(c -> c.getDoctorId().equals(doctorId) && c.getHorario().equals(horario));
        if (ocupado) {
            throw new IllegalStateException("Horario ocupado");
        }
        Cita cita = new Cita(doctorId, horario);
        citas.add(cita);
        return cita;
    }

    public List<Cita> getCitas() {
        return List.copyOf(citas);
    }
}
