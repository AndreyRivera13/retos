package pragma.controller;

import pragma.model.Cita;
import pragma.usecase.CitaService;

public class CitaController {
    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    public Cita solicitarCita(String doctorId, String horario) {
        return citaService.reservar(doctorId, horario);
    }
}
