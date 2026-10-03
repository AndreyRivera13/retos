package pragma;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;
import pragma.model.Pago;
import pragma.model.RespuestaPago;
import pragma.usecase.RealizarPagoUseCase;

@SpringBootApplication
@ConfigurationPropertiesScan
public class MainApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(MainApplication.class, args);
        pruebaManual(ctx);
    }

    private static void pruebaManual(ConfigurableApplicationContext ctx) {
        RealizarPagoUseCase useCase = ctx.getBean(RealizarPagoUseCase.class);
        CircuitBreaker cb = ctx.getBean(CircuitBreakerRegistry.class).circuitBreaker("pasarelaPagos");
        cb.getEventPublisher().onStateTransition(e ->
                System.out.println("   >> CIRCUITO: " + e.getStateTransition()));
        System.out.println("=== PRUEBA MANUAL: 60 pagos contra una pasarela que falla ~40% ===");
        int aprobados = 0;
        int enProceso = 0;
        for (int i = 1; i <= 60; i++) {
            if (cb.getState() == CircuitBreaker.State.OPEN) {
                pausa(1200);
            }
            RespuestaPago r = useCase.ejecutar(new Pago("p-" + i, 100.0 * i));
            if ("APROBADO".equals(r.estado())) {
                aprobados++;
            } else {
                enProceso++;
            }
            System.out.printf("%2d. %-10s circuito=%s%n", i, r.estado(), cb.getState());
        }
        System.out.println("Resumen: aprobados=" + aprobados + " enProceso=" + enProceso
                + " (ningún pago terminó en error crudo)");
    }

    private static void pausa(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
