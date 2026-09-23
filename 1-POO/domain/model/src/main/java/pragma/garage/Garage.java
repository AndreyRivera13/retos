package pragma.garage;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase genérica que representa un garage.
 *
 * La clase usa generics para aceptar cualquier tipo de vehículo que herede de Vehiculo.
 * Esto es una buena muestra de reutilización y flexibilidad en POO.
 */
public class Garage <T extends Vehiculo> {
    // Lista que almacena los vehículos del garage.
    private List<T> vehiculo;

    /**
     * Constructor que inicializa la colección.
     */
    public Garage() {
        this.vehiculo = new ArrayList<>();
    }

    /**
     * Agrega un vehículo al garage.
     * Como T está limitado a Vehiculo, se puede recibir cualquier subclase (Carro, Moto).
     */
    public void agregarVehiculo(T vehiculo) {
        this.vehiculo.add(vehiculo);
    }

    /**
     * Suma todos los impuestos de los vehículos almacenados.
     * Aquí se aprovecha el polimorfismo porque cada objeto calcula su impuesto de acuerdo a su clase.
     */
    public double total() {
        double total = 0;
        for (T v : vehiculo) {
            total += v.calcularImpuesto();
        }
        return total;
    }
}
