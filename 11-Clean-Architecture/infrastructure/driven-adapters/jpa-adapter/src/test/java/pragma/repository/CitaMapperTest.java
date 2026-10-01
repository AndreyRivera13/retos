package pragma.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pragma.model.Cita;

import static org.junit.jupiter.api.Assertions.*;

class CitaMapperTest {

    @Test
    @DisplayName("Debe mapear de Cita (dominio) a CitaEntity correctamente")
    void testToEntity() {
        Cita cita = new Cita("1", "doc-123", "2026-10-01 10:00");

        CitaEntity entity = CitaMapper.toEntity(cita);

        assertNotNull(entity);
        assertEquals("1", entity.getId());
        assertEquals("doc-123", entity.getDoctorId());
        assertEquals("2026-10-01 10:00", entity.getHorario());
    }

    @Test
    @DisplayName("Debe retornar null al mapear Cita null a Entity")
    void testToEntityNull() {
        assertNull(CitaMapper.toEntity(null));
    }

    @Test
    @DisplayName("Debe mapear de CitaEntity a Cita (dominio) correctamente")
    void testToDomain() {
        CitaEntity entity = new CitaEntity();
        entity.setId("2");
        entity.setDoctorId("doc-456");
        entity.setHorario("2026-10-01 11:00");

        Cita cita = CitaMapper.toDomain(entity);

        assertNotNull(cita);
        assertEquals("2", cita.getId());
        assertEquals("doc-456", cita.getDoctorId());
        assertEquals("2026-10-01 11:00", cita.getHorario());
    }

    @Test
    @DisplayName("Debe retornar null al mapear CitaEntity null a Dominio")
    void testToDomainNull() {
        assertNull(CitaMapper.toDomain(null));
        assertNull(CitaMapper.toModel(null));
    }

    @Test
    @DisplayName("toModel debe comportarse igual que toDomain")
    void testToModel() {
        CitaEntity entity = new CitaEntity();
        entity.setId("3");
        entity.setDoctorId("doc-789");
        entity.setHorario("2026-10-01 12:00");

        Cita cita = CitaMapper.toModel(entity);

        assertNotNull(cita);
        assertEquals("3", cita.getId());
        assertEquals("doc-789", cita.getDoctorId());
        assertEquals("2026-10-01 12:00", cita.getHorario());
    }
}
