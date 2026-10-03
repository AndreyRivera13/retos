package pragma.model;

import java.math.BigDecimal;
import java.util.Objects;

public final class Dinero {
    private final BigDecimal monto;
    private final String moneda;

    public Dinero(BigDecimal monto, String moneda) {
        if (monto == null || moneda == null || moneda.isBlank()) {
            throw new IllegalArgumentException("El monto y la moneda son obligatorios");
        }
        if (monto.signum() < 0) {
            throw new IllegalArgumentException("El monto no puede ser negativo");
        }
        this.monto = monto;
        this.moneda = moneda;
    }

    public Dinero sumar(Dinero otro) {
        exigirMismaMoneda(otro);
        return new Dinero(monto.add(otro.monto), moneda);
    }

    public Dinero restar(Dinero otro) {
        exigirMismaMoneda(otro);
        return new Dinero(monto.subtract(otro.monto), moneda);
    }

    public boolean esMayorQue(Dinero otro) {
        exigirMismaMoneda(otro);
        return monto.compareTo(otro.monto) > 0;
    }

    public boolean esCero() {
        return monto.signum() == 0;
    }

    public BigDecimal getMonto() { return monto; }
    public String getMoneda() { return moneda; }

    private void exigirMismaMoneda(Dinero otro) {
        if (!moneda.equals(otro.moneda)) {
            throw new IllegalArgumentException("No se pueden operar monedas distintas: " + moneda + " y " + otro.moneda);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dinero otro)) {
            return false;
        }
        return monto.compareTo(otro.monto) == 0 && moneda.equals(otro.moneda);
    }

    @Override
    public int hashCode() {
        return Objects.hash(monto.stripTrailingZeros(), moneda);
    }

    @Override
    public String toString() {
        return monto.toPlainString() + " " + moneda;
    }
}
