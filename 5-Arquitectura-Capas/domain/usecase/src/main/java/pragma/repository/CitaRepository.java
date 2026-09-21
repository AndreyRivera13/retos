package pragma.repository;

import pragma.model.Cita;

import java.util.List;

/**
 * Puerto (interfaz). Vive en el módulo usecase para que CitaService dependa
 * de esta abstracción y NO de la implementación en memoria concreta.
 * Ya está completa, no la modifiques.
 */
public interface CitaRepository {
    void guardar(Cita cita);
    List<Cita> buscarPorDoctor(String doctorId);
}
