package pragma.model;

public interface ReglaValidacion {
    boolean aplica(Solicitud solicitud);

    ResultadoValidacion validar(Solicitud solicitud);
}
