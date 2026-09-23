package pragma.nomina;

/**
 * TODO: salario fijo (un valor que no depende de horas trabajadas).
 * Implementa Bonificable — este tipo sí tiene bono, EmpleadoPorHoras no.
 */
public class EmpleadoFijo extends Empleado implements Bonificable {
    // TODO: atributo salarioBase

    @Override
    public double calcularSalario() {
        throw new UnsupportedOperationException("TODO: implementar EmpleadoFijo.calcularSalario");
    }

    @Override
    public double calcularBono() {
        throw new UnsupportedOperationException("TODO: implementar EmpleadoFijo.calcularBono");
    }
}
