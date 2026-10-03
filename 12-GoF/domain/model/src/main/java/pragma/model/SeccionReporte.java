package pragma.model;

public class SeccionReporte {
    private final String tipo;
    private final String contenido;

    public SeccionReporte(String tipo, String contenido) {
        this.tipo = tipo;
        this.contenido = contenido;
    }

    public String getTipo() { return tipo; }
    public String getContenido() { return contenido; }
}
