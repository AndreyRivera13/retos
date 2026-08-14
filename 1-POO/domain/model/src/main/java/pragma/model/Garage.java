package pragma.model;

import java.util.ArrayList;
import java.util.List;

public class Garage <T extends Vehiculo> {
    private List<T> vehiculo;

    public Garage() {
        this.vehiculo = new ArrayList<>();
    }

    public void agregarVehiculo(T vehiculo) {
        this.vehiculo.add(vehiculo);
    }

    public double total() {
        double total = 0;
        for (T v : vehiculo) {
            total += v.calcularImpuesto();
        }
        return total;
    }
}
