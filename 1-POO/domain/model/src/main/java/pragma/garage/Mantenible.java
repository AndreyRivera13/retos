/**
 * Interfaz que define un contrato para los objetos que pueden ser revisados.
 *
 * La interfaz permite aplicar abstracción y polimorfismo:
 * - define qué comportamiento debe tener una clase,
 * - pero cada clase decide cómo implementarlo.
 */
package pragma.garage;

public interface Mantenible {
    /**
     * Método que representa la acción de revisar un vehículo.
     */
    void revisar();
}
