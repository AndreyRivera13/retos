package pragma.model;

import java.util.List;

public class ValidadorSolicitud {
    private final List<ReglaValidacion> reglas;

    public ValidadorSolicitud(List<ReglaValidacion> reglas) {
        this.reglas = reglas;
    }

    public ResultadoValidacion validar(Solicitud solicitud) {
        for (ReglaValidacion regla : reglas) {
            if (regla.aplica(solicitud)) {
                ResultadoValidacion resultado = regla.validar(solicitud);
                if (!resultado.isAprobada()) {
                    return resultado;
                }
            }
        }
        return new ResultadoValidacion(true, "OK");
    }
}
