package pragma.repository;

import pragma.model.Cita;

/**
 * Mapper para convertir entre el modelo de dominio {@link Cita}
 * y la entidad JPA {@link CitaEntity}.
 */
public final class CitaMapper {

    private CitaMapper() {
    }

    public static CitaEntity toEntity(Cita cita) {
        if (cita == null) {
            return null;
        }
        CitaEntity entity = new CitaEntity();
        entity.setId(cita.getId());
        entity.setDoctorId(cita.getDoctorId());
        entity.setHorario(cita.getHorario());
        return entity;
    }

    public static Cita toDomain(CitaEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Cita(
                entity.getId(),
                entity.getDoctorId(),
                entity.getHorario()
        );
    }

    public static Cita toModel(CitaEntity entity) {
        return toDomain(entity);
    }
}
