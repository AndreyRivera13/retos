package pragma.model;

/**
 * TODO: implementa procesar(Pago pago) usando try-with-resources sobre un
 * RegistroTransaccion:
 *  - si pago.getMonto() <= 0            -> lanza PagoInvalidoException
 *  - si pago.getMonto() > saldoDisponible -> lanza SaldoInsuficienteException
 *  - si todo está bien, no hace falta devolver nada (void)
 *
 * Antes de escribir código, respondé (sin mirar el documento de conceptos):
 * si procesar() lanza la excepción DENTRO del try-with-resources, ¿el recurso
 * se cierra igual? ¿en qué orden pasa todo esto?
 */
public class ProcesadorPagos {

    public void procesar(Pago pago) throws SaldoInsuficienteException, PagoInvalidoException {
        throw new UnsupportedOperationException("TODO: implementar ProcesadorPagos.procesar");
    }
}
