package pragma.model;

/** Ya está completo. */
public class Documento {
    private final Long id;
    private final Long usuarioId;
    private final String contenido;

    public Documento(Long id, Long usuarioId, String contenido) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.contenido = contenido;
    }

    public Long getId() { return id; }
    public Long getUsuarioId() { return usuarioId; }
    public String getContenido() { return contenido; }
}
