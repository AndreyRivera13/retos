package pragma.usecase;

import pragma.model.Cita;
import pragma.repository.CitaRepository;

/**
 * TODO: implementa reservar(doctorId, horario) que:
 *  - valide que NO exista ya una cita para el mismo doctor en el mismo horario
 *    (usa repository.buscarPorDoctor(doctorId) y compara horarios).
 *  - si hay choque, lanza IllegalStateException("Horario ocupado").
 *  - si no, crea la Cita y la guarda con repository.guardar(cita).
 *
 * Antes de programar: ¿por qué esta regla ("no dos citas en el mismo horario")
 * debe vivir AQUÍ y no en CitaController ni en la implementación del Repository?
 * (vas a tener que explicarlo sin ver el código).
 */
public class CitaService {
    private final CitaRepository repository;

    public CitaService(CitaRepository repository) {
        this.repository = repository;
    }

    public Cita reservar(String doctorId, String horario) {
        throw new UnsupportedOperationException("TODO: implementar CitaService.reservar");
    }
}
