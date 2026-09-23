package pragma.garage;

/**
 * Clase base que representa a cualquier tipo de vehículo.
 *
 * Esta clase demuestra varios conceptos de POO:
 * - Abstracción: define lo común que tienen todos los vehículos.
 * - Encapsulamiento: los atributos son privados y solo se accede por getters y setters.
 * - Herencia: Carro y Moto heredan de esta clase.
 * - Polimorfismo: cada subclase puede definir su propio comportamiento para calcular el impuesto.
 */
public abstract class Vehiculo {
    // Atributos comunes a todos los vehículos.
    private String placa;
    private String marca;

    /**
     * Constructor que inicializa la información básica del vehículo.
     * Todas las clases hijas usan este constructor con super(...).
     */
    public Vehiculo(String placa, String marca) {
        this.placa = placa;
        this.marca = marca;
    }

    /**
     * Obtiene la placa del vehículo.
     */
    public String getPlaca() {
        return placa;
    }

    /**
     * Modifica la placa del vehículo.
     */
    public void setPlaca(String placa) {
        this.placa = placa;
    }

    /**
     * Obtiene la marca del vehículo.
     */
    public String getMarca() {
        return marca;
    }

    /**
     * Modifica la marca del vehículo.
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * Método abstracto: cada tipo de vehículo define cómo calcular su impuesto.
     * Esto es un ejemplo claro de polimorfismo.
     */
    public abstract double calcularImpuesto();
}

