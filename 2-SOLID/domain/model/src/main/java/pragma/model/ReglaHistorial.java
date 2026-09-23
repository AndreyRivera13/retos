package pragma.model;

/**
 * TODO (SRP): implementa la regla "el historial crediticio debe estar OK".
 */
public class ReglaHistorial implements ReglaValidacion {

    @Override
    public boolean aplica(Solicitud solicitud) {
        return true;
    }

    @Override
    public ResultadoValidacion validar(Solicitud solicitud) {
        if (solicitud.isHistorialCrediticioOk()){
            return new ResultadoValidacion(true, "OK");
        }
        else {
            return new ResultadoValidacion(false, "El historial crediticio debe estar OK");
        }
    }
}
