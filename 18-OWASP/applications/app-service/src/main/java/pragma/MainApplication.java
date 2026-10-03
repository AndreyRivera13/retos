package pragma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

@SpringBootApplication
@ConfigurationPropertiesScan
public class MainApplication {
    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext ctx = SpringApplication.run(MainApplication.class, args);
        String puerto = ctx.getEnvironment().getProperty("local.server.port");
        pruebaManual("http://localhost:" + puerto);
    }

    private static void pruebaManual(String base) throws Exception {
        System.out.println("=== PRUEBA MANUAL: control de acceso en /usuarios/{id}/documentos ===");
        mostrar("1. Ana (1) pide sus documentos", base, "1", "clave-ana", 1);
        mostrar("2. Ana (1) pide los de Luis (2)", base, "1", "clave-ana", 2);
        mostrar("3. Luis (2) pide sus documentos", base, "2", "clave-luis", 2);
        mostrar("4. Sin credenciales", base, null, null, 1);
    }

    private static void mostrar(String caso, String base, String usuario, String clave, long id) throws Exception {
        HttpRequest.Builder req = HttpRequest.newBuilder(URI.create(base + "/usuarios/" + id + "/documentos"));
        if (usuario != null) {
            String token = Base64.getEncoder().encodeToString((usuario + ":" + clave).getBytes());
            req.header("Authorization", "Basic " + token);
        }
        HttpResponse<String> r = HttpClient.newHttpClient().send(req.build(), HttpResponse.BodyHandlers.ofString());
        System.out.println(caso + " -> HTTP " + r.statusCode());
    }
}
