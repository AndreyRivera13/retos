package pragma.repository;

import pragma.model.Cita;

import java.util.Optional;

public interface CitaRepositoryPort {
    Cita guardar(Cita cita);
    Optional<Cita> buscarPorId(String id);

    /** Necesario para la regla de negocio "un doctor no puede tener dos citas en el mismo horario". */
    boolean existePorDoctorYHorario(String doctorId, String horario);
}
