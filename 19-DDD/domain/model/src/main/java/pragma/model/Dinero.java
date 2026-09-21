package pragma.model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Value Object. TODO: hazlo inmutable de verdad:
 *  - atributos final, SIN setters.
 *  - constructor valida que monto y moneda no sean null.
 *  - sumar()/restar() deben lanzar IllegalArgumentException si las monedas
 *    son distintas (no puedes sumar 100 USD + 100 COP directamente).
 *  - equals()/hashCode() por monto+moneda (dos Dinero son iguales si valen lo mismo).
 *
 * Antes de programar, respóndete: ¿qué pasaría si esta clase tuviera un
 * setMonto() público? ¿qué garantía del dominio se rompería?
 */
public final class Dinero {
    private final BigDecimal monto;
    private final String moneda;

    public Dinero(BigDecimal monto, String moneda) {
        // TODO: validar nulls y asignar
        throw new UnsupportedOperationException("TODO: implementar constructor de Dinero");
    }

    public Dinero sumar(Dinero otro) {
        throw new UnsupportedOperationException("TODO: implementar Dinero.sumar");
    }

    public Dinero restar(Dinero otro) {
        throw new UnsupportedOperationException("TODO: implementar Dinero.restar");
    }

    public boolean esMayorQue(Dinero otro) {
        throw new UnsupportedOperationException("TODO: implementar Dinero.esMayorQue");
    }

    public BigDecimal getMonto() { return monto; }
    public String getMoneda() { return moneda; }

    // TODO: sobreescribe equals() y hashCode() por monto+moneda
}
