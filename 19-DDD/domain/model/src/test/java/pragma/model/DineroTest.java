package pragma.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DineroTest {

    private static Dinero cop(String monto) {
        return new Dinero(new BigDecimal(monto), "COP");
    }

    @Test
    void sumarDevuelveUnDineroNuevoYNoModificaLosOriginales() {
        Dinero a = cop("100");
        Dinero b = cop("50");

        Dinero suma = a.sumar(b);

        assertEquals(cop("150"), suma);
        assertEquals(cop("100"), a);
        assertEquals(cop("50"), b);
        assertNotSame(a, suma);
    }

    @Test
    void restarDevuelveUnDineroNuevo() {
        assertEquals(cop("70"), cop("100").restar(cop("30")));
    }

    @Test
    void noSePuedenOperarMonedasDistintas() {
        Dinero cop = cop("100");
        Dinero usd = new Dinero(new BigDecimal("100"), "USD");

        assertThrows(IllegalArgumentException.class, () -> cop.sumar(usd));
        assertThrows(IllegalArgumentException.class, () -> cop.restar(usd));
        assertThrows(IllegalArgumentException.class, () -> cop.esMayorQue(usd));
    }

    @Test
    void dosDineroConElMismoValorSonIgualesAunqueLaEscalaDifiera() {
        Dinero a = cop("100");
        Dinero b = cop("100.00");

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void mismoMontoEnOtraMonedaNoEsIgual() {
        assertFalse(cop("100").equals(new Dinero(new BigDecimal("100"), "USD")));
    }

    @Test
    void rechazaNullsYMontosNegativos() {
        assertThrows(IllegalArgumentException.class, () -> new Dinero(null, "COP"));
        assertThrows(IllegalArgumentException.class, () -> new Dinero(BigDecimal.TEN, null));
        assertThrows(IllegalArgumentException.class, () -> new Dinero(BigDecimal.TEN, " "));
        assertThrows(IllegalArgumentException.class, () -> new Dinero(new BigDecimal("-1"), "COP"));
        assertThrows(IllegalArgumentException.class, () -> cop("10").restar(cop("20")));
    }

    @Test
    void esMayorQueCompara() {
        assertTrue(cop("100").esMayorQue(cop("99")));
        assertFalse(cop("100").esMayorQue(cop("100")));
    }
}
