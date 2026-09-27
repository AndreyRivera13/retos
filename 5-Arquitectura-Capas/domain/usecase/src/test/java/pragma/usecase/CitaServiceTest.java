package pragma.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pragma.model.Cita;
import pragma.repository.CitaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class CitaServiceTest {

    private CitaRepository repository;
    private CitaService service;

    @BeforeEach
    void setUp() {
        repository = new CitaRepository() {
            private final List<Cita> list = new ArrayList<>();

            @Override
            public void guardar(Cita cita) {
                list.add(cita);
            }

            @Override
            public List<Cita> buscarPorDoctor(String doctorId) {
                return list.stream()
                        .filter(c -> c.getDoctorId().equals(doctorId))
                        .collect(Collectors.toList());
            }
        };
        service = new CitaService(repository);
    }

    @Test
    void reservarCitaExitosa() {
        Cita cita = service.reservar("DOC-1", "2026-09-27T10:00");
        assertNotNull(cita);
        assertEquals("DOC-1", cita.getDoctorId());
        assertEquals("2026-09-27T10:00", cita.getHorario());
        assertEquals(1, repository.buscarPorDoctor("DOC-1").size());
    }

    @Test
    void reservarCitaMismoHorarioMismoDoctorLanzaExcepcion() {
        service.reservar("DOC-1", "2026-09-27T10:00");

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> service.reservar("DOC-1", "2026-09-27T10:00")
        );
        assertEquals("Horario ocupado", exception.getMessage());
    }

    @Test
    void reservarCitaMismoHorarioDiferenteDoctorPermitido() {
        Cita cita1 = service.reservar("DOC-1", "2026-09-27T10:00");
        Cita cita2 = service.reservar("DOC-2", "2026-09-27T10:00");

        assertNotNull(cita1);
        assertNotNull(cita2);
        assertEquals(1, repository.buscarPorDoctor("DOC-1").size());
        assertEquals(1, repository.buscarPorDoctor("DOC-2").size());
    }
}
