package pragma.model;

/**
 * TODO: implementa procesar(Pago pago) usando try-with-resources sobre un
 * RegistroTransaccion:
 *  - si pago.getMonto() <= 0            -> lanza PagoInvalidoException
 *  - si pago.getMonto() > saldoDisponible -> lanza SaldoInsuficienteException
 *  - si todo está bien, no hace falta devolver nada (void)
 */
public class ProcesadorPagos {

    public void procesar(Pago pago) throws SaldoInsuficienteException, PagoInvalidoException {
        try (RegistroTransaccion registro = new RegistroTransaccion()) {
            if (pago.getMonto() <= 0) {
                throw new PagoInvalidoException("Monto inválido", null);
            }
            if (pago.getMonto() > pago.getSaldoDisponible()) {
                throw new SaldoInsuficienteException("Saldo insuficiente", null);
            }
        }
    }
}