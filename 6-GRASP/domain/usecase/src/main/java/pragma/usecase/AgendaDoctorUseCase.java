package pragma.usecase;

import pragma.model.Cita;

import java.util.List;

public class AgendaDoctorUseCase {
    public long duracionTotalDelDia(
            String doctorId,
            List<Cita> citasDelDoctor) {
        return citasDelDoctor.stream()
                .mapToLong(Cita::duracionEnMinutos)
                .sum();
    }
}
