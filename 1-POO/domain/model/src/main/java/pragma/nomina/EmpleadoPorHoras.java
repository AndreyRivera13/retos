package pragma.nomina;

/**
 * TODO: salario = horasTrabajadas * tarifaPorHora.
 * OJO: a propósito NO implementa Bonificable (ese tipo de empleado no tiene bono).
 */
public class EmpleadoPorHoras extends Empleado {
    // TODO: atributos horasTrabajadas, tarifaPorHora
    private double horasTrabajadas;
    private double tarifaPorHora;

    public EmpleadoPorHoras(String nombre, String documento, double horasTrabajadas, double tarifaPorHora) {
        super(nombre, documento);
        this.horasTrabajadas = horasTrabajadas;
        this.tarifaPorHora = tarifaPorHora;
    }

    public double getHorasTrabajadas() {
        return horasTrabajadas;
    }

    public void setHorasTrabajadas(double horasTrabajadas) {
        this.horasTrabajadas = horasTrabajadas;
    }

    public double getTarifaPorHora() {
        return tarifaPorHora;
    }

    public void setTarifaPorHora(double tarifaPorHora) {
        this.tarifaPorHora = tarifaPorHora;
    }

    @Override
    public double calcularSalario() {
        return horasTrabajadas * tarifaPorHora;
    }
}
