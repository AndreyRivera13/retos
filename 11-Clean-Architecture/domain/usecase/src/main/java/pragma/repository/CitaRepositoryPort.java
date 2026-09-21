package pragma.repository;

import pragma.model.Cita;

import java.util.Optional;

/** Puerto de salida. Ya está completo — dos adaptadores lo van a implementar. */
public interface CitaRepositoryPort {
    Cita guardar(Cita cita);
    Optional<Cita> buscarPorId(String id);
}
