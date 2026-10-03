package pragma;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ControlDeAccesoTest {

    @LocalServerPort
    int puerto;

    @Test
    void elDuenoVeSusDocumentos() throws Exception {
        HttpResponse<String> r = pedir(1, "1", "clave-ana");

        assertEquals(200, r.statusCode());
        assertTrue(r.body().contains("Extracto de cuenta de Ana"));
        assertFalse(r.body().contains("Luis"));
    }

    @Test
    void unUsuarioAutenticadoNoPuedeVerLosDocumentosDeOtro() throws Exception {
        HttpResponse<String> r = pedir(2, "1", "clave-ana");

        assertEquals(403, r.statusCode());
        assertFalse(r.body().contains("Luis"));
    }

    @Test
    void sinCredencialesResponde401() throws Exception {
        assertEquals(401, pedir(1, null, null).statusCode());
    }

    @Test
    void conClaveIncorrectaResponde401() throws Exception {
        assertEquals(401, pedir(1, "1", "otra-clave").statusCode());
    }

    private HttpResponse<String> pedir(long id, String usuario, String clave) throws Exception {
        HttpRequest.Builder req = HttpRequest.newBuilder(
                URI.create("http://localhost:" + puerto + "/usuarios/" + id + "/documentos"));
        if (usuario != null) {
            req.header("Authorization", "Basic "
                    + Base64.getEncoder().encodeToString((usuario + ":" + clave).getBytes()));
        }
        return HttpClient.newHttpClient().send(req.build(), HttpResponse.BodyHandlers.ofString());
    }
}
