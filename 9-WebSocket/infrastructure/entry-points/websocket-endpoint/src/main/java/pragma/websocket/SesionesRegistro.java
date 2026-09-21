package pragma.websocket;

import jakarta.websocket.Session;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * TODO: implementa un registro de sesiones seguro ante acceso concurrente
 * (mira CopyOnWriteArraySet en el ejemplo VisitantesEndpoint del documento
 * de retos, tema 9). Debes poder explicar por qué este tipo de Set es seguro
 * aquí y qué pasaría si usaras un HashSet normal.
 */
public class SesionesRegistro {
    private final Set<Session> sesiones = new CopyOnWriteArraySet<>();

    public void agregar(Session session) {
        throw new UnsupportedOperationException("TODO: implementar agregar");
    }

    public void quitar(Session session) {
        throw new UnsupportedOperationException("TODO: implementar quitar");
    }

    public Set<Session> obtenerTodas() {
        throw new UnsupportedOperationException("TODO: implementar obtenerTodas");
    }
}
