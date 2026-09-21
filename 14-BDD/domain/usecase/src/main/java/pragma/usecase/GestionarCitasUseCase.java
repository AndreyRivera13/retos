package pragma.usecase;

import pragma.model.Cita;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: implementa reservar(...) para que los 3 escenarios del .feature pasen:
 *  1. reserva exitosa
 *  2. horario ya ocupado -> lanza IllegalStateException("Horario ocupado")
 *  3. doctor inexistente -> lanza IllegalArgumentException("Doctor no existe")
 *
 * doctoresValidos ya viene cargado para el ejercicio (no hace falta persistencia real).
 */
public class GestionarCitasUseCase {
    private final List<Cita> citas = new ArrayList<>();
    private final List<String> doctoresValidos = List.of("dra-lopez", "dr-perez");

    public Cita reservar(String doctorId, String horario) {
        throw new UnsupportedOperationException("TODO: implementar GestionarCitasUseCase.reservar");
    }

    public List<Cita> getCitas() { return citas; }
}
