package pragma.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import pragma.model.Cita;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Adaptador de salida #1: memoria. @Primary para que sea el que se use por
 * defecto (puedes quitarlo cuando quieras probar el de JPA).
 * TODO: implementa guardar/buscarPorId con un Map interno.
 */
@Repository
@Primary
public class CitaRepositoryMemoriaAdapter implements CitaRepositoryPort {
    private final Map<String, Cita> citas = new HashMap<>();

    @Override
    public Cita guardar(Cita cita) {
        throw new UnsupportedOperationException("TODO: implementar guardar (memoria)");
    }

    @Override
    public Optional<Cita> buscarPorId(String id) {
        throw new UnsupportedOperationException("TODO: implementar buscarPorId (memoria)");
    }
}
