package pragma.repository;

import org.springframework.stereotype.Repository;
import pragma.model.Cita;

import java.util.Optional;

/**
 * Adaptador de salida #2 (esqueleto): JPA. No es @Primary — solo existe
 * para que veas que puedes tener dos implementaciones intercambiables del
 * mismo puerto. TODO (opcional, si quieres ir más allá): conecta esto a un
 * JpaRepository<CitaEntity, String> real con H2.
 */
@Repository
public class CitaRepositoryJpaAdapter implements CitaRepositoryPort {

    @Override
    public Cita guardar(Cita cita) {
        throw new UnsupportedOperationException("TODO (opcional): implementar guardar (JPA)");
    }

    @Override
    public Optional<Cita> buscarPorId(String id) {
        throw new UnsupportedOperationException("TODO (opcional): implementar buscarPorId (JPA)");
    }
}
