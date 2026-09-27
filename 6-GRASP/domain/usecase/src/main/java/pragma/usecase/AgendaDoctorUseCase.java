package pragma.usecase;

import pragma.model.Cita;

import java.util.List;

/**
 * Creator → AgendaDoctor: crea Cita porque mantiene/agrega las citas.
 * Information Expert → Cita: calcula su propia duración porque tiene horaInicio y horaFin.
 * UseCase → AgendaDoctorUseCase: coordina y suma las duraciones; no necesita conocer cómo se calcula una duración ni construir directamente las citas.
 */
public class AgendaDoctorUseCase {

    public long duracionTotalDelDia(
            String doctorId,
            List<Cita> citasDelDoctor) {

        return citasDelDoctor.stream()
                .mapToLong(Cita::duracionEnMinutos)
                .sum();
    }
}