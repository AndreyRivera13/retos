package pragma.repository;

import pragma.model.Cita;

import java.util.List;

public interface CitaRepository {
    void guardar(Cita cita);
    List<Cita> buscarPorDoctor(String doctorId);
}
