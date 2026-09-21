package pragma.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import pragma.model.MensajeEntrante;

/**
 * TODO: implementa la lógica de:
 *  - onOpen: registra la sesión y le manda un mensaje de bienvenida.
 *  - onMessage: parsea el JSON a MensajeEntrante (usa objectMapper.readValue).
 *      si tipo=="alerta"  -> reenvía el texto a TODAS las sesiones registradas.
 *      si tipo=="privado" -> reenvía SOLO a la sesión cuyo session.getId()
 *                             sea igual a mensaje.destino().
 *  - onClose: quita la sesión del registro.
 *
 * Demuestra dominio si puedes explicar qué pasa si un cliente se desconecta
 * abruptamente SIN disparar onClose (pista: sendText a una sesión cerrada
 * lanza excepción — ¿cómo lo manejarías?).
 */
@ServerEndpoint("/notificaciones")
public class NotificacionesEndpoint {
    private static final SesionesRegistro sesiones = new SesionesRegistro();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @OnOpen
    public void onOpen(Session session) {
        throw new UnsupportedOperationException("TODO: implementar onOpen");
    }

    @OnMessage
    public void onMessage(String mensajeJson, Session session) {
        throw new UnsupportedOperationException("TODO: implementar onMessage");
    }

    @OnClose
    public void onClose(Session session) {
        throw new UnsupportedOperationException("TODO: implementar onClose");
    }
}
