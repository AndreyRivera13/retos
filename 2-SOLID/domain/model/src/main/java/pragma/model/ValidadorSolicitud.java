package pragma.model;

import java.util.List;

/**
 * Orquestador (equivalente a MotorDescuentos en el ejemplo del documento de retos).
 * Recibe la lista de reglas por constructor y solo coordina — NO debe contener
 * ningún "if" de negocio (edad, ingresos, historial). Si sientes la tentación
 * de poner un if aquí, esa lógica pertenece a una ReglaValidacion.
 */
public class ValidadorSolicitud {
    private final List<ReglaValidacion> reglas;

    public ValidadorSolicitud(List<ReglaValidacion> reglas) {
        this.reglas = reglas;
    }

    /**
     * TODO: recorre las reglas que aplican; si alguna falla, retorna su
     * ResultadoValidacion inmediatamente. Si todas pasan, retorna aprobado=true.
     */
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
