package pragma.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProcesadorPagosTest {
    private final ProcesadorPagos procesador = new ProcesadorPagos();

    @Test
    void pagoValidoNoLanzaExcepcion() {
        assertDoesNotThrow(() -> procesador.procesar(new Pago(100, 200)));
    }

    @Test
    void pagoConMontoMayorAlSaldoLanzaSaldoInsuficiente() {
        assertThrows(SaldoInsuficienteException.class, () -> procesador.procesar(new Pago(300, 200)));
    }

    @Test
    void pagoConMontoInvalidoLanzaPagoInvalido() {
        assertThrows(PagoInvalidoException.class, () -> procesador.procesar(new Pago(0, 200)));
    }
}
