package pragma.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * TODO: completa estas 3 pruebas (los nombres ya te dicen qué caso es cada una).
 * No cambies las firmas, solo el cuerpo.
 */
class ProcesadorPagosTest {

    private final ProcesadorPagos procesador = new ProcesadorPagos();

    @Test
    void pagoValidoNoLanzaExcepcion() {
        // TODO: arma un Pago válido (monto <= saldoDisponible, monto > 0)
        // y verifica con assertDoesNotThrow que procesar() no lanza nada.
    }

    @Test
    void pagoConMontoMayorAlSaldoLanzaSaldoInsuficiente() {
        // TODO: assertThrows(SaldoInsuficienteException.class, () -> procesador.procesar(pagoConMontoAlto));
    }

    @Test
    void pagoConMontoInvalidoLanzaPagoInvalido() {
        // TODO: assertThrows(PagoInvalidoException.class, () -> procesador.procesar(pagoConMontoCero));
    }
}
