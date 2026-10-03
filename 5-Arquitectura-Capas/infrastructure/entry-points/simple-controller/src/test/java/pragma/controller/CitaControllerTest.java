package pragma.controller;

import org.junit.jupiter.api.Test;
import pragma.model.Cita;
import pragma.repository.CitaRepository;
import pragma.usecase.CitaService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class CitaControllerTest {
    @Test
    void solicitarCitaDelegaEnCitaService() {
        CitaRepository repository = new CitaRepository() {
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
        CitaService service = new CitaService(repository);
        CitaController controller = new CitaController(service);

        Cita cita = controller.solicitarCita("DOC-1", "2026-09-27T10:00");
        assertNotNull(cita);
        assertEquals("DOC-1", cita.getDoctorId());
        assertEquals("2026-09-27T10:00", cita.getHorario());
        assertEquals(1, repository.buscarPorDoctor("DOC-1").size());
    }
}
