package pragma;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import pragma.model.Cita;
import pragma.usecase.GestionarCitasUseCase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ReservarCitaSteps {
    private final GestionarCitasUseCase useCase = new GestionarCitasUseCase();
    private Cita citaReservada;
    private Exception errorCapturado;

    @Dado("que el doctor {string} no tiene citas")
    public void elDoctorNoTieneCitas(String doctorId) {
        assertEquals(0, citasDe(doctorId));
    }

    @Dado("que el doctor {string} ya tiene una cita a las {string}")
    public void elDoctorYaTieneUnaCita(String doctorId, String horario) {
        useCase.reservar(doctorId, horario);
    }

    @Dado("que no existe un doctor llamado {string}")
    public void noExisteElDoctor(String doctorId) {
        assertEquals(0, citasDe(doctorId));
    }

    @Cuando("reservo una cita con {string} para las {string}")
    public void reservoUnaCita(String doctorId, String horario) {
        try {
            citaReservada = useCase.reservar(doctorId, horario);
        } catch (Exception e) {
            errorCapturado = e;
        }
    }

    @Entonces("la cita queda registrada con {string} a las {string}")
    public void laCitaQuedaRegistrada(String doctorId, String horario) {
        assertNull(errorCapturado);
        assertNotNull(citaReservada);
        assertEquals(doctorId, citaReservada.getDoctorId());
        assertEquals(horario, citaReservada.getHorario());
        assertEquals(1, useCase.getCitas().size());
    }

    @Entonces("el sistema me avisa {string}")
    public void elSistemaMeAvisa(String mensaje) {
        assertNotNull(errorCapturado);
        assertEquals(mensaje, errorCapturado.getMessage());
    }

    @Y("el doctor {string} sigue teniendo una sola cita")
    public void sigueTeniendoUnaSolaCita(String doctorId) {
        assertEquals(1, citasDe(doctorId));
    }

    @Y("no se registra ninguna cita")
    public void noSeRegistraNingunaCita() {
        assertEquals(0, useCase.getCitas().size());
    }

    private long citasDe(String doctorId) {
        return useCase.getCitas().stream().filter(c -> c.getDoctorId().equals(doctorId)).count();
    }
}
