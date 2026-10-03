package pragma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import pragma.model.Cita;
import pragma.usecase.GestionarCitasReactivoUseCase;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
@ConfigurationPropertiesScan
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);

        pruebaManual();
    }

    private static void pruebaManual() {
        GestionarCitasReactivoUseCase useCase = new GestionarCitasReactivoUseCase();
        LocalDate hoy = LocalDate.now();

        useCase.reservar(null, hoy);
        System.out.println("1. Sin suscribirse -> no pasó nada, el error ni siquiera se emitió (esperado: sin excepción)");

        Cita cita = useCase.reservar("dra-lopez", hoy).block();
        System.out.println("2. Reservar válido -> " + cita.getDoctorId() + " (esperado: dra-lopez)");

        try {
            useCase.reservar(" ", hoy).block();
        } catch (IllegalArgumentException e) {
            System.out.println("3. Reservar sin doctor -> " + e.getMessage() + " (esperado: El doctor es obligatorio)");
        }

        List<Cita> normales = useCase.citasDelDia(hoy).collectList().block();
        System.out.println("4. Citas del día (origen rápido) -> " + normales.size() + " citas (esperado: 2)");

        GestionarCitasReactivoUseCase origenLento =
                new GestionarCitasReactivoUseCase(Duration.ofSeconds(3), Duration.ofSeconds(1));
        long inicio = System.currentTimeMillis();
        List<Cita> conTimeout = origenLento.citasDelDia(hoy).collectList().block();
        long ms = System.currentTimeMillis() - inicio;
        System.out.println("5. Citas del día (origen lento) -> " + conTimeout.size() + " citas en ~" + ms
                + " ms, sin error (esperado: 0 citas en ~1000 ms)");
    }
}
