package pragma.repository;

import org.springframework.stereotype.Repository;
import pragma.model.Cita;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO: implementación simple en memoria (una List<Cita> interna) de
 * CitaRepository. No necesita ser thread-safe para este ejercicio.
 */
@Repository
public class CitaRepositoryEnMemoria implements CitaRepository {
    private final List<Cita> citas = new ArrayList<>();

    @Override
    public void guardar(Cita cita) {
        throw new UnsupportedOperationException("TODO: implementar guardar");
    }

    @Override
    public List<Cita> buscarPorDoctor(String doctorId) {
        throw new UnsupportedOperationException("TODO: implementar buscarPorDoctor");
    }
}
