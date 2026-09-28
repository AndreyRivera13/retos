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

                for (Session destino : sesiones.obtenerTodas()) {
                    try {
                        destino.getBasicRemote().sendText(mensaje.texto());
                    } catch (Exception e) {
                        sesiones.quitar(destino);
                    }
                }

            } else if ("privado".equals(mensaje.tipo())) {

                for (Session destino : sesiones.obtenerTodas()) {
                    if (destino.getId().equals(mensaje.destino())) {
                        try {
                            destino.getBasicRemote().sendText(mensaje.texto());
                        } catch (Exception e) {
                            sesiones.quitar(destino);
                        }
                        break;
                    }
                }
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