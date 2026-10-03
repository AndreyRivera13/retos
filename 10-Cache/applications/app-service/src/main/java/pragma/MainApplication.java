package pragma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import pragma.usecase.CatalogoPreciosUseCase;

@SpringBootApplication
@ConfigurationPropertiesScan
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);

        pruebaManualCache();
    }

    private static void pruebaManualCache() {
        CatalogoPreciosUseCase catalogo = new CatalogoPreciosUseCase();

        System.out.println("1. Producto inexistente  -> " + catalogo.obtenerPrecio("p-1") + " (esperado: 0.0)");

        catalogo.actualizarPrecio("p-1", 100.0);
        System.out.println("2. Primera lectura (miss) -> " + catalogo.obtenerPrecio("p-1") + " (esperado: 100.0)");

        System.out.println("3. Segunda lectura (hit)  -> " + catalogo.obtenerPrecio("p-1") + " (esperado: 100.0)");

        catalogo.actualizarPrecio("p-1", 150.0);
        System.out.println("4. Tras actualizar        -> " + catalogo.obtenerPrecio("p-1") + " (esperado: 150.0)");

        catalogo.actualizarPrecio("p-2", 50.0);
        System.out.println("5. Otro producto          -> " + catalogo.obtenerPrecio("p-2") + " (esperado: 50.0)");
    }
}
