package pragma.usecase;

import pragma.model.Pago;
import pragma.model.RespuestaPago;
import pragma.pasarela.ProcesarPagoPort;

/**
 * TODO: simplemente delega en el puerto (la resiliencia vive en el
 * adaptador PasarelaPagosAdapter, no aquí). Este UseCase es intencionalmente
 * "tonto" — su trabajo es orquestar, no manejar reintentos ni circuitos.
 */
public class RealizarPagoUseCase {
    private final ProcesarPagoPort pasarela;

    public RealizarPagoUseCase(ProcesarPagoPort pasarela) {
        this.pasarela = pasarela;
    }

    public RespuestaPago ejecutar(Pago pago) {
        throw new UnsupportedOperationException("TODO: implementar RealizarPagoUseCase.ejecutar");
    }
}
