package pragma.model;

/** DTO de un pago a procesar. Ya está completo. */
public class Pago {
    private final double monto;
    private final double saldoDisponible;

    public Pago(double monto, double saldoDisponible) {
        this.monto = monto;
        this.saldoDisponible = saldoDisponible;
    }

    public double getMonto() { return monto; }
    public double getSaldoDisponible() { return saldoDisponible; }
}
