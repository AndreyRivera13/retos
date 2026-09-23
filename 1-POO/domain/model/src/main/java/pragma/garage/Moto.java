package pragma.model;

/**
 * Clase concreta que representa una moto.
 *
 * Aunque comparte la estructura base con cualquier vehículo, su impuesto es diferente.
 * Eso es un ejemplo de herencia y polimorfismo: misma clase base, comportamiento distinto.
 */
public class Moto extends Vehiculo{
    /**
     * Constructor de la moto. Usa la inicialización de la clase padre.
     */
    public Moto(String placa, String marca) {
        super(placa, marca);
    }

    /**
     * Cada vehículo define su impuesto de forma distinta.
     * La moto tiene un valor diferente al carro, pero ambas son Vehiculo.
     */
    @Override
    public double calcularImpuesto() {
        return 200000;
    }
}
