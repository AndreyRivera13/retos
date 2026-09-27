package pragma.model;

/**
 * TODO: implementa procesar(Pago pago) usando try-with-resources sobre un
 * RegistroTransaccion:
 *  - si pago.getMonto() <= 0            -> lanza PagoInvalidoException
 *  - si pago.getMonto() > saldoDisponible -> lanza SaldoInsuficienteException
 *  - si todo está bien, no hace falta devolver nada (void)
 * Antes de escribir código, respondé (sin mirar el documento de conceptos):
 * si procesar() lanza la excepción DENTRO del try-with-resources, ¿el recurso
 * se cierra igual? ¿en qué orden pasa todo esto?
 * **Sí, se cierra de forma garantizada.**
 * **2. ¿En qué orden ocurre todo el flujo?**
 * Cuando una excepción se genera dentro del bloque try:
 * 1. **Inicialización del recurso:** Se instancia o asigna el recurso en la cabecera `try (RegistroTransaccion registro = ...)`.
 * 2. **Ejecución del cuerpo:** Se ejecutan las instrucciones del bloque `try` hasta el punto donde se evalúa y lanza la excepción (por ejemplo, `PagoInvalidoException` o `SaldoInsuficienteException`).
 * 3. **Interrupción y cierre prioritario del recurso:** En el instante en que se genera la excepción, se detiene el flujo normal del bloque y **de inmediato se invoca automáticamente el método `close()`** del recurso (si hubiera múltiples recursos declarados, se cierran en orden inverso a su declaración).
 * 4. **Preservación y supresión de excepciones (si aplica):**
 *    - Si `close()` finaliza sin errores, la excepción original del bloque `try` continúa su propagación.
 *    - Si `close()` también llegase a lanzar una excepción, el mecanismo prioriza la causa raíz: la excepción primaria sigue siendo la del cuerpo del `try`, y la excepción ocurrida durante el `close()` se adjunta automáticamente como una excepción suprimida (`suppressed exception`, accesible mediante `getSuppressed()`). Esto maximiza el valor de diagnóstico al no ocultar el problema principal que detonó el fallo.
 * 5. **Propagación final:** La excepción (junto con la información suprimida, si existiese) sale del método `procesar()` hacia el llamador o hacia los bloques `catch`/`finally` correspondientes.
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