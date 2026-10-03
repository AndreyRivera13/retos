package pragma.usecase;

import pragma.model.Cita;
import pragma.repository.CitaRepositoryPort;

public class GestionarCitasUseCase {
    private final CitaRepositoryPort repository;

    public GestionarCitasUseCase(CitaRepositoryPort repository) {
        this.repository = repository;
    }

    public Cita reservar(String id, String doctorId, String horario) {
        if (repository.existePorDoctorYHorario(doctorId, horario)) {
            throw new IllegalStateException(
                    "El doctor " + doctorId + " ya tiene una cita en el horario " + horario);
        }
        return repository.guardar(new Cita(id, doctorId, horario));
    }
}
