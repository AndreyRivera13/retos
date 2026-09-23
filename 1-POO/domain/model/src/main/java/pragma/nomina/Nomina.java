package pragma.nomina;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: clase genérica que recibe una lista de Empleado (de cualquier
 * subtipo) y calcula el total a pagar SIN usar instanceof en ningún punto
 * (ese es justo el punto del reto: Nomina<T> no debe saber qué tipo
 * concreto de empleado está sumando).
 *
 * Entrega además un párrafo (en un README aparte si quieres) explicando:
 * si agregaras un tercer tipo EmpleadoComisionista, ¿qué archivos tocas y
 * cuáles no? Y la pregunta del evaluador: ¿qué diferencia hay entre esto y
 * usar List<Object> con casts?
 */
public class Nomina<T extends Empleado> {
    private final List<T> empleados = new ArrayList<>();

    public void agregar(T empleado) {
        empleados.add(empleado);
    }

    public double totalAPagar() {
        double total = 0;
        for (T empleado : empleados) {
            total += empleado.calcularSalario();
        }
        return total;
    }
}
