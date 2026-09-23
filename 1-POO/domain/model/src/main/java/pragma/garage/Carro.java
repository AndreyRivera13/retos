package pragma.garage;

/**
 * Clase concreta que representa un carro.
 *
 * Hereda de Vehiculo y además implementa la interfaz Mantenible,
 * lo que significa que todos los carros pueden realizar una revisión.
 */
public class Carro extends Vehiculo implements Mantenible {
    /**
     * Constructor del carro. Reutiliza el constructor de la clase padre.
     */
    public Carro(String placa, String marca) {
        super(placa, marca);
    }

    /**
     * Implementación del contrato de mantenimiento.
     * La interfaz define que cualquier objeto mantenible debe tener este método.
     */
    @Override
    public void revisar() {
        System.out.println("Revisando el carro");
    }

    /**
     * Calcula el impuesto según la regla del negocio para un carro.
     * Aquí se demuestra el polimorfismo porque el método tiene el mismo nombre
     * que en la clase base, pero la lógica puede ser distinta por tipo de vehículo.
     */
    @Override
    public double calcularImpuesto() {
        return 500000;
    }
}
