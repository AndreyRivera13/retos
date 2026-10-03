package pragma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import pragma.model.Pago;
import pragma.model.PagoInvalidoException;
import pragma.model.ProcesadorPagos;
import pragma.model.SaldoInsuficienteException;

@SpringBootApplication
@ConfigurationPropertiesScan
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);

        ProcesadorPagos procesador = new ProcesadorPagos();

        try {
            System.out.println("--- Prueba 1: Pago válido ---");
            procesador.procesar(new Pago(50, 100));
            System.out.println("Pago procesado exitosamente.");
        } catch (SaldoInsuficienteException | PagoInvalidoException e) {
            System.err.println("Error procesando pago: " + e.getMessage());
        }

        try {
            System.out.println("\n--- Prueba 2: Saldo insuficiente ---");
            procesador.procesar(new Pago(150, 100));
        } catch (SaldoInsuficienteException e) {
            System.out.println("Excepción esperada capturada: " + e.getMessage());
        } catch (PagoInvalidoException e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }

        try {
            System.out.println("\n--- Prueba 3: Monto inválido ---");
            procesador.procesar(new Pago(-10, 100));
        } catch (PagoInvalidoException e) {
            System.out.println("Excepción esperada capturada: " + e.getMessage());
        } catch (SaldoInsuficienteException e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }
    }
}
