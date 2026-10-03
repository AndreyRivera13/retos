package pragma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import pragma.model.CuentaBancaria;
import pragma.model.Dinero;

import java.math.BigDecimal;

@SpringBootApplication
@ConfigurationPropertiesScan
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
        pruebaManual();
    }

    private static Dinero cop(String monto) {
        return new Dinero(new BigDecimal(monto), "COP");
    }

    private static void pruebaManual() {
        System.out.println("=== PRUEBA MANUAL: CuentaBancaria (Aggregate Root) ===");
        CuentaBancaria cuenta = new CuentaBancaria("cta-1", cop("1000"));
        cuenta.depositar(cop("500"));
        System.out.println("1. Saldo tras depositar 500: " + cuenta.getSaldo() + " (esperado: 1500 COP)");
        cuenta.retirar(cop("300"));
        System.out.println("2. Saldo tras retirar 300: " + cuenta.getSaldo() + " (esperado: 1200 COP)");
        System.out.println("3. Eventos emitidos: " + cuenta.getEventos());
        intentar("4. Retirar 5000 (excede el saldo)", () -> cuenta.retirar(cop("5000")));
        intentar("5. Sumar COP + USD", () -> cop("1").sumar(new Dinero(BigDecimal.ONE, "USD")));
        cuenta.bloquear();
        intentar("6. Retirar con la cuenta BLOQUEADA", () -> cuenta.retirar(cop("10")));
        System.out.println("7. Dinero(100) == Dinero(100.00)? " + cop("100").equals(cop("100.00")) + " (esperado: true)");
    }

    private static void intentar(String caso, Runnable accion) {
        try {
            accion.run();
            System.out.println(caso + " -> NO falló (inesperado)");
        } catch (RuntimeException e) {
            System.out.println(caso + " -> " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }
}
