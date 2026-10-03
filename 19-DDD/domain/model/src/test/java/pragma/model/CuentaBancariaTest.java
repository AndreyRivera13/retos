package pragma.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CuentaBancariaTest {

    private static Dinero cop(String monto) {
        return new Dinero(new BigDecimal(monto), "COP");
    }

    private CuentaBancaria cuenta() {
        return new CuentaBancaria("cta-1", cop("1000"));
    }

    @Test
    void depositarSumaAlSaldo() {
        CuentaBancaria c = cuenta();

        c.depositar(cop("500"));

        assertEquals(cop("1500"), c.getSaldo());
    }

    @Test
    void retirarRestaDelSaldoYEmiteRetiroRealizado() {
        CuentaBancaria c = cuenta();

        c.retirar(cop("300"));

        assertEquals(cop("700"), c.getSaldo());
        assertEquals(1, c.getEventos().size());
        RetiroRealizado evento = assertInstanceOf(RetiroRealizado.class, c.getEventos().get(0));
        assertEquals("cta-1", evento.cuentaId());
        assertEquals(cop("300"), evento.monto());
    }

    @Test
    void sePuedeRetirarExactamenteElSaldo() {
        CuentaBancaria c = cuenta();

        c.retirar(cop("1000"));

        assertTrue(c.getSaldo().esCero());
    }

    @Test
    void retirarMasDelSaldoFallaYNoCambiaNadaNiEmiteEventos() {
        CuentaBancaria c = cuenta();

        assertThrows(IllegalStateException.class, () -> c.retirar(cop("1001")));

        assertEquals(cop("1000"), c.getSaldo());
        assertTrue(c.getEventos().isEmpty());
    }

    @Test
    void unaCuentaBloqueadaNoPermiteRetirarNiDepositar() {
        CuentaBancaria c = cuenta();
        c.bloquear();

        assertThrows(IllegalStateException.class, () -> c.retirar(cop("10")));
        assertThrows(IllegalStateException.class, () -> c.depositar(cop("10")));
        assertEquals(cop("1000"), c.getSaldo());
        assertTrue(c.getEventos().isEmpty());
    }

    @Test
    void noSePuedeOperarConOtraMonedaNiConMontoCero() {
        CuentaBancaria c = cuenta();

        assertThrows(IllegalArgumentException.class, () -> c.depositar(new Dinero(BigDecimal.TEN, "USD")));
        assertThrows(IllegalArgumentException.class, () -> c.retirar(new Dinero(BigDecimal.TEN, "USD")));
        assertThrows(IllegalArgumentException.class, () -> c.retirar(cop("0")));
        assertThrows(IllegalArgumentException.class, () -> c.depositar(null));
    }

    @Test
    void extraerEventosEntregaLosPendientesYLosLimpia() {
        CuentaBancaria c = cuenta();
        c.retirar(cop("100"));
        c.retirar(cop("200"));

        List<EventoDominio> pendientes = c.extraerEventos();

        assertEquals(2, pendientes.size());
        assertTrue(c.getEventos().isEmpty());
    }

    @Test
    void laListaDeEventosExpuestaNoSePuedeModificarDesdeAfuera() {
        CuentaBancaria c = cuenta();

        assertThrows(UnsupportedOperationException.class, () -> c.getEventos().add(new RetiroRealizado("x", cop("1"), null)));
    }
}
