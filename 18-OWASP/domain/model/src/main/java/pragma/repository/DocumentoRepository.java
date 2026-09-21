package pragma.repository;

import pragma.model.Documento;

import java.util.List;

/** Ya está completo (puerto simple, sin implementación real para el ejercicio). */
public interface DocumentoRepository {
    List<Documento> findByUsuarioId(Long usuarioId);
}
