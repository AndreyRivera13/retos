package pragma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;
import pragma.repository.CitaRepositoryJpaAdapter;
import pragma.usecase.GestionarCitasUseCase;

@SpringBootApplication
@ConfigurationPropertiesScan
public class MainApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MainApplication.class, args);

        System.out.println("=== Prueba manual con el adaptador de MEMORIA (el que inyecta Spring) ===");
        pruebaManual(context.getBean(GestionarCitasUseCase.class));

        System.out.println("=== Prueba manual con el adaptador JPA (mismo use case, otro adaptador) ===");
        pruebaManual(new GestionarCitasUseCase(context.getBean(CitaRepositoryJpaAdapter.class)));
    }

    private static void pruebaManual(GestionarCitasUseCase useCase) {
        System.out.println("1. Reserva horario libre  -> " + useCase.reservar("1", "dra-lopez", "2026-10-01 10:00").getId()
                + " (esperado: 1)");

        try {
            useCase.reservar("2", "dra-lopez", "2026-10-01 10:00");
            System.out.println("2. Mismo doctor y horario -> NO lanzó excepción (ERROR: la regla no se aplicó)");
        } catch (IllegalStateException e) {
            System.out.println("2. Mismo doctor y horario -> " + e.getMessage() + " (esperado: excepción)");
        }

        System.out.println("3. Mismo horario, otro doctor -> " + useCase.reservar("3", "dr-perez", "2026-10-01 10:00").getId()
                + " (esperado: 3)");

        System.out.println("4. Mismo doctor, otro horario -> " + useCase.reservar("4", "dra-lopez", "2026-10-01 11:00").getId()
                + " (esperado: 4)");
    }
}
