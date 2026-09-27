package pragma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;
import pragma.model.AgendaDoctor;
import pragma.model.Cita;
import pragma.usecase.AgendaDoctorUseCase;

import java.time.LocalDateTime;

import static java.lang.System.out;

/**
 * Punto de arranque del reto "6-GRASP".
 * No necesitas tocar esta clase.
 */
@SpringBootApplication
@ConfigurationPropertiesScan
public class MainApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MainApplication.class, args);

        // Prueba manual de GRASP Creator
        AgendaDoctor agenda = new AgendaDoctor("DOC-001");
        Cita cita1 = agenda.crearCita(
                LocalDateTime.of(2026, 9, 27, 8, 0),
                LocalDateTime.of(2026, 9, 27, 8, 30));
        Cita cita2 = agenda.crearCita(
                LocalDateTime.of(2026, 9, 27, 9, 0),
                LocalDateTime.of(2026, 9, 27, 10, 0));

        // Prueba manual de Information Expert
        System.out.println("Duración cita 1: "
                + cita1.duracionEnMinutos() + " minutos");
        System.out.println("Duración cita 2: "
                + cita2.duracionEnMinutos() + " minutos");

        // Prueba del UseCase
        AgendaDoctorUseCase useCase = new AgendaDoctorUseCase();
        long total = useCase.duracionTotalDelDia(agenda.getDoctorId(), agenda.getCitas());
        
        System.out.println("Doctor: " + agenda.getDoctorId());
        System.out.println("Total del día: " + total + " minutos");
        context.close();
    }
}
