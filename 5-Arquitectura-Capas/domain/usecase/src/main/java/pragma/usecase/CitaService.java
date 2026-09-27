package pragma.usecase;

import pragma.model.Cita;
import pragma.repository.CitaRepository;

import java.util.List;

public class CitaService {
    private final CitaRepository repository;

    public CitaService(CitaRepository repository) {
        this.repository = repository;
    }

    public Cita reservar(String doctorId, String horario) {
        List<Cita> citas = repository.buscarPorDoctor(doctorId);
        for (Cita cita : citas) {
            if (cita.getHorario().equals(horario)) {
                throw new IllegalStateException("Horario ocupado");
            }
        }
        Cita cita = new Cita(doctorId, horario);
        repository.guardar(cita);
        return cita;
    }
}
