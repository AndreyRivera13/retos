package pragma.model;

/**
 * TODO (SRP): implementa la regla "el solicitante debe ser mayor o igual a 18 años".
 * Mira el ejemplo de DescuentoEstudiante en Retos_Assessment_Andrey.md (tema 2)
 * para la forma del patrón — pero esta lógica la escribes tú.
 */
public class ReglaEdad implements ReglaValidacion {

    @Override
    public boolean aplica(Solicitud solicitud) {
        // TODO: normalmente true, esta regla siempre se evalúa
        throw new UnsupportedOperationException("TODO: implementar ReglaEdad.aplica");
    }

    @Override
    public ResultadoValidacion validar(Solicitud solicitud) {
        // TODO: si edad < 18 -> ResultadoValidacion(false, "..."), si no -> (true, "OK")
        throw new UnsupportedOperationException("TODO: implementar ReglaEdad.validar");
    }
}
