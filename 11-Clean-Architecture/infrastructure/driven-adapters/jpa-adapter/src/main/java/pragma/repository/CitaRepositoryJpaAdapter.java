package pragma.repository;

import org.springframework.stereotype.Repository;
import pragma.model.Cita;

import java.util.Optional;

@Repository
public class CitaRepositoryJpaAdapter implements CitaRepositoryPort {

    private final CitaJpaRepository jpaRepository;

    public CitaRepositoryJpaAdapter(CitaJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Cita guardar(Cita cita) {
        return CitaMapper.toDomain(jpaRepository.save(CitaMapper.toEntity(cita)));
    }

    @Override
    public Optional<Cita> buscarPorId(String id) {
        return jpaRepository.findById(id).map(CitaMapper::toDomain);
    }

    @Override
    public boolean existePorDoctorYHorario(String doctorId, String horario) {
        return jpaRepository.existsByDoctorIdAndHorario(doctorId, horario);
    }
}
