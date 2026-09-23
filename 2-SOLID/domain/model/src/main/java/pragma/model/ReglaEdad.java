package pragma.model;

/**
 * TODO (SRP): implementa la regla "el solicitante debe ser mayor o igual a 18 años".
 * Mira el ejemplo de DescuentoEstudiante en Retos_Assessment_Andrey.md (tema 2)
 * para la forma del patrón — pero esta lógica la escribes tú.
 */
public class ReglaEdad implements ReglaValidacion {

    @Override
    public boolean aplica(Solicitud solicitud) {
        return true;
    }

    @Override
    public ResultadoValidacion validar(Solicitud solicitud) {
        if (solicitud.getEdad() >= 18){
            return new ResultadoValidacion(true, "OK");
        }
        else {
            return new ResultadoValidacion(false, "El solicitante debe ser mayor o igual a 18 años");
        }
    }
}
