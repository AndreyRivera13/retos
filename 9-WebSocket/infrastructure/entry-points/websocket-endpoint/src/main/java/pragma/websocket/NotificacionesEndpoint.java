package pragma.websocket;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;
import pragma.model.MensajeEntrante;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
@ServerEndpoint("/notificaciones")
public class NotificacionesEndpoint {
    private static final SesionesRegistro sesiones = new SesionesRegistro();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @OnOpen
    public void onOpen(Session session) {
        sesiones.agregar(session);
        try {
            session.getBasicRemote().sendText(
                    "Bienvenido. Tu ID de sesión es: " + session.getId()
            );
        } catch (Exception e) {
            sesiones.quitar(session);
        }
    }

    @OnMessage
    public void onMessage(String mensajeJson, Session session) {
        try {
            MensajeEntrante mensaje =
                    objectMapper.readValue(mensajeJson, MensajeEntrante.class);
            if ("alerta".equals(mensaje.tipo())) {
                sesiones.obtenerTodas().stream()
                        .filter(s -> !s.equals(session))
                        .forEach(s -> {
                            try {
                                s.getAsyncRemote().sendText(mensaje.texto());
                            } catch (Exception e) {
                                sesiones.quitar(s);
                            }
                        });
            } else if ("privado".equals(mensaje.tipo())) {
                sesiones.obtenerTodas().stream()
                        .filter(s -> !s.equals(session))
                        .forEach(s -> s.getAsyncRemote().sendText(mensaje.texto()));
            }
        } catch (Exception e) {
            try {
                session.getBasicRemote().sendText(
                        "Error procesando el mensaje: " + e.getMessage()
                );
            } catch (Exception ignored) {
                sesiones.quitar(session);
            }
        }
    }

    @OnClose
    public void onClose(Session session) {
        sesiones.quitar(session);
    }
}
