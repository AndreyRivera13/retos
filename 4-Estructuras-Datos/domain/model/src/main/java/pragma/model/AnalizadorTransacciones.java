package pragma.model;

import java.util.List;
import java.util.Map;

/**
 * Recibe transacciones en formato "cliente:monto" (ej: "ana:500").
 * TODO: implementa los 3 métodos SIN usar Collectors.groupingBy
 * (a mano, con Map + getOrDefault/merge + Comparator, como en el ejemplo
 * de votos del documento de retos, tema 4).
 */
public class AnalizadorTransacciones {

    /** TODO: suma el monto total por cliente. */
    public Map<String, Double> totalPorCliente(List<String> transacciones) {
        throw new UnsupportedOperationException("TODO: implementar totalPorCliente");
    }

    /** TODO: usando totalPorCliente(...), devuelve el cliente con mayor total. */
    public String clienteConMayorTotal(List<String> transacciones) {
        throw new UnsupportedOperationException("TODO: implementar clienteConMayorTotal");
    }

    /** TODO: lista de clientes ordenada de mayor a menor total (sin Collectors). */
    public List<String> ordenarPorTotalDescendente(List<String> transacciones) {
        throw new UnsupportedOperationException("TODO: implementar ordenarPorTotalDescendente");
    }

    // Recuerda: en la entrega debes anotar la complejidad Big O de tu solución
    // para n transacciones (coméntala aquí arriba de cada método cuando termines).
}
