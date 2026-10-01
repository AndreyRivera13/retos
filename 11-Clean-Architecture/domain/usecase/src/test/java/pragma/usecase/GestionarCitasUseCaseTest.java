package pragma.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pragma.model.Cita;
import pragma.repository.CitaRepositoryPort;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Prueba el use case con un puerto falso propio, sin Spring ni infraestructura:
 * demuestra que el dominio no sabe qué hay detrás del puerto.
 */
class GestionarCitasUseCaseTest {

    private static class RepositorioFalso implements CitaRepositoryPort {
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

    private RepositorioFalso repositorio;
    private GestionarCitasUseCase useCase;

    @BeforeEach
    void setUp() {
        repositorio = new RepositorioFalso();
        useCase = new GestionarCitasUseCase(repositorio);
    }

    @Test
    void reservaCitaCuandoElHorarioEstaLibre() {
        Cita cita = useCase.reservar("1", "dra-lopez", "2026-10-01 10:00");

        assertEquals("1", cita.getId());
        assertTrue(repositorio.buscarPorId("1").isPresent());
    }

    @Test
    void lanzaIllegalStateExceptionSiElDoctorYaTieneCitaEnEseHorario() {
        useCase.reservar("1", "dra-lopez", "2026-10-01 10:00");

        assertThrows(IllegalStateException.class,
                () -> useCase.reservar("2", "dra-lopez", "2026-10-01 10:00"));
        assertTrue(repositorio.buscarPorId("2").isEmpty());
    }

    @Test
    void permiteElMismoHorarioConOtroDoctor() {
        useCase.reservar("1", "dra-lopez", "2026-10-01 10:00");

        Cita cita = useCase.reservar("2", "dr-perez", "2026-10-01 10:00");

        assertEquals("2", cita.getId());
    }
}
