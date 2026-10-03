package pragma.repository;

import pragma.model.Documento;

import java.util.List;

public interface DocumentoRepository {
    List<Documento> findByUsuarioId(Long usuarioId);
}
