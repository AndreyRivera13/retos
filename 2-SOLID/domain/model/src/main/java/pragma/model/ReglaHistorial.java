package pragma.model;

/**
 * TODO (SRP): implementa la regla "el historial crediticio debe estar OK".
 */
public class ReglaHistorial implements ReglaValidacion {

    @Override
    public boolean aplica(Solicitud solicitud) {
        throw new UnsupportedOperationException("TODO: implementar ReglaHistorial.aplica");
    }

    @Override
    public ResultadoValidacion validar(Solicitud solicitud) {
        throw new UnsupportedOperationException("TODO: implementar ReglaHistorial.validar");
    }
}
