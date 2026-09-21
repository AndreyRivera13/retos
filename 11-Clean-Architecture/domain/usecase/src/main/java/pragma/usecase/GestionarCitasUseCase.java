package pragma.usecase;

import pragma.model.Cita;
import pragma.repository.CitaRepositoryPort;

/**
 * Puerto de entrada + su propia implementación (convención del scaffold:
 * el nombre debe terminar en "UseCase" para que UseCasesConfig lo registre
 * como bean automáticamente).
 *
 * TODO: implementa reservar(...). La regla de negocio (ej. no duplicar
 * citas) va aquí, nunca en los adaptadores de infraestructura.
 *
 * Prueba mental antes de programar: si le pusieras @Entity directamente a
 * la clase Cita (dominio), ¿qué se rompería exactamente y por qué?
 */
public class GestionarCitasUseCase {
    private final CitaRepositoryPort repository;

    public GestionarCitasUseCase(CitaRepositoryPort repository) {
        this.repository = repository;
    }

    public Cita reservar(String id, String doctorId, String horario) {
        throw new UnsupportedOperationException("TODO: implementar GestionarCitasUseCase.reservar");
    }
}
