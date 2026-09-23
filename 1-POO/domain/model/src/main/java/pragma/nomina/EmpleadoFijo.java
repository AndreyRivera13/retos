package pragma.nomina;

/**
 * TODO: salario fijo (un valor que no depende de horas trabajadas).
 * Implementa Bonificable — este tipo sí tiene bono, EmpleadoPorHoras no.
 */
public class EmpleadoFijo extends Empleado implements Bonificable {
    private final double salarioBase;

    public EmpleadoFijo(String nombre, String documento,double salarioBase ) {
        super(nombre, documento);
        this.salarioBase = salarioBase;
    }

    @Override
    public double calcularSalario() {
        return salarioBase * 2;
    }

    @Override
    public double calcularBono() {
        return salarioBase * 0.10;
    }
}
