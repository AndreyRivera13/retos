package pragma.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Recibe transacciones en formato "cliente:monto" (ej: "ana:500").
 */
public class AnalizadorTransacciones {

    public Map<String, Double> totalPorCliente(List<String> transacciones) {
        Map<String, Double> resultado = new HashMap<>();
        for (String transaccion : transacciones) {
            String cliente = transaccion.split(":")[0];
            double monto = Double.parseDouble(transaccion.split(":")[1]);
            resultado.merge(cliente, monto, Double::sum);
        }
        return resultado;
    }

    public String clienteConMayorTotal(List<String> transacciones) {
        Map<String, Double> totalPorCliente = totalPorCliente(transacciones);
        return totalPorCliente.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public List<String> ordenarPorTotalDescendente(List<String> transacciones) {
        Map<String, Double> totalPorCliente = totalPorCliente(transacciones);
        return new ArrayList<>(totalPorCliente.keySet()).stream()
                .sorted((cliente1, cliente2) -> totalPorCliente.get(cliente2).compareTo(totalPorCliente.get(cliente1)))
                .collect(Collectors.toList());
    }

    // la complejidad Big O de la solución para n transacciones
    //  O(n log n) debido a la operación de ordenamiento.

}
