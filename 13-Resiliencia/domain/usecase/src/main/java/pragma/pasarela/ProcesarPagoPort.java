package pragma.pasarela;

import pragma.model.Pago;
import pragma.model.RespuestaPago;

/** Puerto hacia la pasarela de pagos externa. Ya está completo. */
public interface ProcesarPagoPort {
    RespuestaPago procesar(Pago pago);
}
