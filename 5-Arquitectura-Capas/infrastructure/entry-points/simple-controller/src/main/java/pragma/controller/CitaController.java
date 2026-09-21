package pragma.controller;

import org.springframework.stereotype.Component;
import pragma.model.Cita;
import pragma.usecase.CitaService;

/**
 * "Controller" simulado (el reto pide una clase simple, no un @RestController
 * real). Debe SOLO llamar al Service — si aquí terminas escribiendo un if
 * de negocio, esa lógica está en la capa equivocada.
 *
 * TODO: implementa solicitarCita(...) delegando en CitaService.
 */
@Component
public class CitaController {
    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    public Cita solicitarCita(String doctorId, String horario) {
        throw new UnsupportedOperationException("TODO: implementar CitaController.solicitarCita");
    }
}
