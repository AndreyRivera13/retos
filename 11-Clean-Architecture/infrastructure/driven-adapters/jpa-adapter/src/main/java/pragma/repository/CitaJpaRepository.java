package pragma.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CitaJpaRepository extends JpaRepository<CitaEntity, String> {
    boolean existsByDoctorIdAndHorario(String doctorId, String horario);
}
