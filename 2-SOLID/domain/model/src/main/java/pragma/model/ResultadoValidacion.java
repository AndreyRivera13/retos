package pragma.model;

/** Resultado de validar una Solicitud. Ya está completa. */
public class ResultadoValidacion {
    private final boolean aprobada;
    private final String mensaje;

    public ResultadoValidacion(boolean aprobada, String mensaje) {
        this.aprobada = aprobada;
        this.mensaje = mensaje;
    }

    public boolean isAprobada() { return aprobada; }
    public String getMensaje() { return mensaje; }
}
