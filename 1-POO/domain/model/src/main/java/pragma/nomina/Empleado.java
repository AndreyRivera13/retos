package pragma.nomina;

public abstract class Empleado {
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
