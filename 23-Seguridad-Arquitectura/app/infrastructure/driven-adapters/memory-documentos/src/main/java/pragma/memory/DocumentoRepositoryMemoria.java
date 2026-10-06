package pragma.memory;

import org.springframework.stereotype.Repository;
import pragma.model.Documento;
import pragma.repository.DocumentoRepository;

import java.util.List;

@Repository
public class DocumentoRepositoryMemoria implements DocumentoRepository {
    private final List<Documento> documentos = List.of(
            new Documento(1L, 1L, "Extracto de cuenta de Ana"),
            new Documento(2L, 1L, "Contrato de Ana"),
            new Documento(3L, 2L, "Extracto de cuenta de Luis"));

    @Override
    public List<Documento> findByUsuarioId(Long usuarioId) {
        return documentos.stream().filter(d -> d.getUsuarioId().equals(usuarioId)).toList();
    }
}
