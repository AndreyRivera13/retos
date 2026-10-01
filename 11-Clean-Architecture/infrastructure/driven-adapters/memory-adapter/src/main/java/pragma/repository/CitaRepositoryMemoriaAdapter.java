package pragma.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import pragma.model.Cita;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
@Primary
public class CitaRepositoryMemoriaAdapter implements CitaRepositoryPort {
    private final Map<String, Cita> citas = new HashMap<>();

    @Override
    public Cita guardar(Cita cita) {
        citas.put(cita.getId(), cita);
        return cita;
    }

    @Override
    public Optional<Cita> buscarPorId(String id) {
        return Optional.ofNullable(citas.get(id));
    }

    @Override
    public boolean existePorDoctorYHorario(String doctorId, String horario) {
        return citas.values().stream()
                .anyMatch(c -> c.getDoctorId().equals(doctorId) && c.getHorario().equals(horario));
    }
}
