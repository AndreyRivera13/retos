package pragma.model;

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
