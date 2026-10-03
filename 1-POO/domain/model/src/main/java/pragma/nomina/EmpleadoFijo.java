package pragma.nomina;

public class EmpleadoFijo extends Empleado implements Bonificable {
    private final double salarioBase;

    public EmpleadoFijo(String nombre, String documento,double salarioBase ) {
        super(nombre, documento);
        this.salarioBase = salarioBase;
    }

    @Override
    public double calcularSalario() {
        return salarioBase;
    }

    @Override
    public double calcularBono() {
        return salarioBase * 0.10;
    }
}
