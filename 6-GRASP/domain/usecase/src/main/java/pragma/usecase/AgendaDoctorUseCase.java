package pragma.usecase;

import pragma.model.Cita;

import java.util.List;

/**
 * TODO (Creador - GRASP): decide quién debería crear las instancias de Cita.
 * ¿Tiene sentido que este UseCase arme "new Cita(...)" directamente, o debería
 * existir un método crear(...) más cerca de los datos (ej. en una clase Doctor
 * o Agenda que agrupe las citas)? Documenta tu decisión en un comentario aquí
 * y luego impleméntala.
 *
 * Método a implementar: dado un doctorId y su lista de citas del día,
 * suma la duración total usando Cita.duracionEnMinutos() (Experto en Información).
 */
public class AgendaDoctorUseCase {

    public long duracionTotalDelDia(String doctorId, List<Cita> citasDelDoctor) {
        throw new UnsupportedOperationException("TODO: implementar duracionTotalDelDia");
    }

    // TODO: agrega aquí (o donde decidas que corresponde) el método de creación
    // de citas, y deja un comentario explicando por qué lo pusiste ahí.
}
