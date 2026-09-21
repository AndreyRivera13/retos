package pragma.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pragma.model.Documento;
import pragma.repository.DocumentoRepository;

import java.util.List;

/**
 * === Este endpoint está VULNERABLE A PROPÓSITO — así llega en el reto ===
 *
 * TODO (esto es lo que debes entregar, no lo implementes aún):
 *  1. En un comentario aquí abajo, identifica: categoría OWASP Top 10 + CWE
 *     exacto de esta vulnerabilidad, y describe el vector de ataque
 *     (¿qué haría un atacante, paso a paso, con esta URL?).
 *  2. Corrige el método `ver` agregando la verificación de que el usuario
 *     autenticado (Authentication/Principal, inyéctalo como parámetro) sea
 *     el dueño del `id` solicitado. Si no lo es, lanza AccessDeniedException.
 *  3. Explica (para el evaluador) la diferencia entre este fallo y una
 *     "autenticación rota" — no son lo mismo y muchos los confunden.
 *
 * No mires el ejemplo de /facturas/{id} del documento de retos hasta que ya
 * hayas escrito tu propia respuesta a los 3 puntos de arriba.
 */
@RestController
public class DocumentoController {
    private final DocumentoRepository documentoRepository;

    public DocumentoController(DocumentoRepository documentoRepository) {
        this.documentoRepository = documentoRepository;
    }

    // TODO: categoría OWASP = ???   CWE = ???

    @GetMapping("/usuarios/{id}/documentos")
    public List<Documento> ver(@PathVariable Long id) {
        // TODO: agregar verificación de dueño antes de retornar
        return documentoRepository.findByUsuarioId(id);
    }
}
