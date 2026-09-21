package pragma.repository;

// import jakarta.persistence.Entity;
// import jakarta.persistence.Id;

/**
 * TODO: esqueleto de la entidad JPA (independiente del Cita de dominio, a
 * propósito — así queda claro que "infraestructura" puede cambiar sin tocar
 * el dominio). Descomenta las anotaciones @Entity/@Id cuando la completes.
 */
// @Entity
public class CitaEntity {
    // @Id
    private String id;
    private String doctorId;
    private String horario;

    // TODO: getters/setters, y un mapeo Cita <-> CitaEntity (puede ir aquí
    // mismo o en un mapper aparte, tú decides).
}
