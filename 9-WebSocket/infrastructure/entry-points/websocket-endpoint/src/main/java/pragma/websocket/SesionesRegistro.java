package pragma.websocket;

import jakarta.websocket.Session;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class SesionesRegistro {
    private final Set<Session> sesiones = new CopyOnWriteArraySet<>();

    public void agregar(Session session) {
        sesiones.add(session);
    }

    public void quitar(Session session) {
        sesiones.remove(session);
    }

    public Set<Session> obtenerTodas() {
        return sesiones;
    }
}
