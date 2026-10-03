package pragma.pasarela;

import pragma.model.Pago;
import pragma.model.RespuestaPago;

public interface ProcesarPagoPort {
    RespuestaPago procesar(Pago pago);
}
