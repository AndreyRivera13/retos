package pragma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;
import pragma.usecase.GestionarCitasUseCase;

@SpringBootApplication
@ConfigurationPropertiesScan
public class MainApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(MainApplication.class, args);
        pruebaManual(ctx.getBean(GestionarCitasUseCase.class));
    }

    private static void pruebaManual(GestionarCitasUseCase useCase) {
        System.out.println("=== PRUEBA MANUAL: los 3 escenarios del .feature ===");
        System.out.println("1. Reserva exitosa -> " + useCase.reservar("dra-lopez", "2026-10-05 09:00").getDoctorId());
        intentar(useCase, "2. Horario ocupado", "dra-lopez", "2026-10-05 09:00");
        intentar(useCase, "3. Doctor inexistente", "dr-fantasma", "2026-10-05 09:00");
        System.out.println("Citas registradas: " + useCase.getCitas().size() + " (esperado: 1)");
    }

    private static void intentar(GestionarCitasUseCase useCase, String caso, String doctor, String horario) {
        try {
            useCase.reservar(doctor, horario);
            System.out.println(caso + " -> NO falló (inesperado)");
        } catch (RuntimeException e) {
            System.out.println(caso + " -> " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }
}
