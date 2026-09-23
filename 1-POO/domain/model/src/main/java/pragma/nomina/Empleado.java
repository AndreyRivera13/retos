package pragma.nomina;

/**
 * Clase abstracta base. TODO: define los atributos comunes (ej. nombre,
 * documento) y deja calcularSalario() abstracto — cada subtipo decide cómo
 * se calcula el suyo, sin que Nomina<T> necesite saber cuál es cuál.
 */
public abstract class Empleado {
    // TODO: atributos comunes (nombre, documento, etc.)
    private String nombre;
    private String documento;

    public Empleado(String nombre, String documento) {
        this.nombre = nombre;
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public abstract double calcularSalario();
}
