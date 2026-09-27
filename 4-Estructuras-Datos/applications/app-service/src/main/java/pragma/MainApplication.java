package pragma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import pragma.model.AnalizadorTransacciones;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


@SpringBootApplication
@ConfigurationPropertiesScan
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);

        AnalizadorTransacciones analizador = new AnalizadorTransacciones();

        List<String> transacciones = Arrays.asList(
                "ana:500",
                "luis:300",
                "ana:200",
                "carlos:800",
                "luis:150"
        );

        System.out.println("=== 1. Total por cliente ===");
        Map<String, Double> totales = analizador.totalPorCliente(transacciones);
        totales.forEach((cliente, total) -> System.out.println(cliente + " -> $" + total));

        System.out.println("\n=== 2. Cliente con mayor total ===");
        String topCliente = analizador.clienteConMayorTotal(transacciones);
        System.out.println("Cliente: " + topCliente);

        System.out.println("\n=== 3. Clientes ordenados de mayor a menor total ===");
        List<String> ordenados = analizador.ordenarPorTotalDescendente(transacciones);
        System.out.println(ordenados);
    }
}