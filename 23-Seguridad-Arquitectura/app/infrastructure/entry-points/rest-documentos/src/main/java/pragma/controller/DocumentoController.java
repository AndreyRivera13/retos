package pragma.controller;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pragma.model.Documento;
import pragma.repository.DocumentoRepository;

import java.util.List;

@RestController
public class DocumentoController {
    private final DocumentoRepository documentoRepository;

    public DocumentoController(DocumentoRepository documentoRepository) {
        this.documentoRepository = documentoRepository;
    }

    @GetMapping("/usuarios/{id}/documentos")
    public List<Documento> ver(@PathVariable("id") Long id, Authentication autenticado) {
        if (!esDueno(autenticado, id)) {
            throw new AccessDeniedException("No puede ver los documentos de otro usuario");
        }
        return documentoRepository.findByUsuarioId(id);
    }

    private boolean esDueno(Authentication autenticado, Long id) {
        return autenticado != null && String.valueOf(id).equals(autenticado.getName());
    }
}
