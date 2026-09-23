package pragma.model;

/**
 * TODO (SRP): implementa la regla "los ingresos mensuales deben ser >= 1.500.000".
 * Ajusta el umbral si quieres, lo importante es que la regla viva SOLO aquí.
 */
public class ReglaIngresos implements ReglaValidacion {

    @Override
    public boolean aplica(Solicitud solicitud) {
        return true;
    }

    @Override
    public ResultadoValidacion validar(Solicitud solicitud) {
        if (solicitud.getIngresosMensuales() >= 1500000){
            return new ResultadoValidacion(true, "OK");
        }
        else {
            return new ResultadoValidacion(false, "El solicitante debe tener ingresos mensuales mayores o iguales a 1.500.000");
        }
    }
}
