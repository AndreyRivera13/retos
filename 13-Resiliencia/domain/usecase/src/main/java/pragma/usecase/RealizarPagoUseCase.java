package pragma.usecase;

import pragma.model.Pago;
import pragma.model.RespuestaPago;
import pragma.pasarela.ProcesarPagoPort;

public class RealizarPagoUseCase {
    private final ProcesarPagoPort pasarela;

    public RealizarPagoUseCase(ProcesarPagoPort pasarela) {
        this.pasarela = pasarela;
    }

    public RespuestaPago ejecutar(Pago pago) {
        return pasarela.procesar(pago);
    }
}
