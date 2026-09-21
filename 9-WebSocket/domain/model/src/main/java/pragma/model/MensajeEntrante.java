package pragma.model;

/**
 * DTO del mensaje JSON que manda un cliente. Ya está completo.
 * tipo: "alerta" (broadcast a todos) o "privado" (solo a `destino`, el id de sesión).
 */
public record MensajeEntrante(String tipo, String destino, String texto) {
}
