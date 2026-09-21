package pragma.model;

/**
 * Datos de una solicitud de crédito. Ya está completa, no la modifiques:
 * el reto es sobre ValidadorSolicitud y las reglas, no sobre este DTO.
 */
public class Solicitud {
    private final int edad;
    private final double ingresosMensuales;
    private final boolean historialCrediticioOk;

    public Solicitud(int edad, double ingresosMensuales, boolean historialCrediticioOk) {
        this.edad = edad;
        this.ingresosMensuales = ingresosMensuales;
        this.historialCrediticioOk = historialCrediticioOk;
    }

    public int getEdad() { return edad; }
    public double getIngresosMensuales() { return ingresosMensuales; }
    public boolean isHistorialCrediticioOk() { return historialCrediticioOk; }
}
