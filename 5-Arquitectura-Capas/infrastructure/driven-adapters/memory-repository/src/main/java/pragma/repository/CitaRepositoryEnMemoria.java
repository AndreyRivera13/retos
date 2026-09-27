package pragma.repository;

import pragma.model.Cita;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CitaRepositoryEnMemoria implements CitaRepository {
    private final List<Cita> citas = new ArrayList<>();

    @Override
    public void guardar(Cita cita) {
        citas.add(cita);
    }

    @Override
    public List<Cita> buscarPorDoctor(String doctorId) {
        return citas.stream()
                .filter(cita -> cita.getDoctorId().equals(doctorId))
                .collect(Collectors.toList());
    }
}
