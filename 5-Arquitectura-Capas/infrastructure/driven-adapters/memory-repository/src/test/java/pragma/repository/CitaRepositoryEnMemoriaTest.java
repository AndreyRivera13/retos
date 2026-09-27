package pragma.repository;

import org.junit.jupiter.api.Test;
import pragma.model.Cita;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CitaRepositoryEnMemoriaTest {

    @Test
    void guardarYBuscarPorDoctor() {
        CitaRepositoryEnMemoria repository = new CitaRepositoryEnMemoria();

        assertTrue(repository.buscarPorDoctor("DOC-1").isEmpty());

        Cita cita1 = new Cita("DOC-1", "2026-09-27T10:00");
        Cita cita2 = new Cita("DOC-1", "2026-09-27T11:00");
        Cita cita3 = new Cita("DOC-2", "2026-09-27T10:00");

        repository.guardar(cita1);
        repository.guardar(cita2);
        repository.guardar(cita3);

        List<Cita> doc1Citas = repository.buscarPorDoctor("DOC-1");
        assertEquals(2, doc1Citas.size());
        assertTrue(doc1Citas.contains(cita1));
        assertTrue(doc1Citas.contains(cita2));

        List<Cita> doc2Citas = repository.buscarPorDoctor("DOC-2");
        assertEquals(1, doc2Citas.size());
        assertTrue(doc2Citas.contains(cita3));
    }
}
