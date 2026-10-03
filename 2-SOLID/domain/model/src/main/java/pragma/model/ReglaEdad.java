package pragma.model;

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
