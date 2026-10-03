package pragma.usecase;

import org.junit.jupiter.api.Test;
import pragma.model.Cita;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GestionarCitasReactivoUseCaseTest {
    private static final LocalDate FECHA = LocalDate.of(2026, 10, 1);

    @Test
    void reservarConDatosValidosEmiteLaCita() {
        GestionarCitasReactivoUseCase useCase = new GestionarCitasReactivoUseCase();

        StepVerifier.create(useCase.reservar("dra-lopez", FECHA))
                .assertNext(cita -> {
                    assertEquals("dra-lopez", cita.getDoctorId());
                    assertEquals(FECHA, cita.getFecha());
                })
                .verifyComplete();
    }

    @Test
    void reservarSinDoctorEmiteErrorEnElFlujo() {
        GestionarCitasReactivoUseCase useCase = new GestionarCitasReactivoUseCase();

        StepVerifier.create(useCase.reservar(" ", FECHA))
                .expectError(IllegalArgumentException.class)
                .verify();
    }

    @Test
    void reservarSinFechaEmiteErrorEnElFlujo() {
        GestionarCitasReactivoUseCase useCase = new GestionarCitasReactivoUseCase();

        StepVerifier.create(useCase.reservar("dra-lopez", null))
                .expectError(IllegalArgumentException.class)
                .verify();
    }

    @Test
    void sinSuscripcionNoPasaNada() {
        GestionarCitasReactivoUseCase useCase = new GestionarCitasReactivoUseCase();

        assertDoesNotThrow(() -> useCase.reservar(null, FECHA));
    }

    @Test
    void citasDelDiaDentroDelTimeoutEmiteLasCitas() {
        GestionarCitasReactivoUseCase useCase =
                new GestionarCitasReactivoUseCase(Duration.ofMillis(50), Duration.ofSeconds(1));

        StepVerifier.create(useCase.citasDelDia(FECHA).map(Cita::getDoctorId))
                .expectNext("dra-lopez", "dr-perez")
                .verifyComplete();
    }

    @Test
    void citasDelDiaConOrigenMasLentoQueElTimeoutCompletaVacioEnVezDeFallar() {
        GestionarCitasReactivoUseCase useCase =
                new GestionarCitasReactivoUseCase(Duration.ofMillis(500), Duration.ofMillis(100));

        StepVerifier.create(useCase.citasDelDia(FECHA))
                .verifyComplete();
    }
}
