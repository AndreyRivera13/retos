package pragma.model;

/**
 * TODO (SRP): implementa la regla "los ingresos mensuales deben ser >= 1.500.000".
 * Ajusta el umbral si quieres, lo importante es que la regla viva SOLO aquí.
 */
public class ReglaIngresos implements ReglaValidacion {

    @Override
    public boolean aplica(Solicitud solicitud) {
        throw new UnsupportedOperationException("TODO: implementar ReglaIngresos.aplica");
    }

    @Override
    public ResultadoValidacion validar(Solicitud solicitud) {
        throw new UnsupportedOperationException("TODO: implementar ReglaIngresos.validar");
    }
}
