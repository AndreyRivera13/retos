package pragma.nomina;

import java.util.ArrayList;
import java.util.List;

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
