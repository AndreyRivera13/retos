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

        // 1. Producto que no existe en el origen: el origen devuelve 0.0 por defecto.
        System.out.println("1. Producto inexistente  -> " + catalogo.obtenerPrecio("p-1") + " (esperado: 0.0)");

        // 2. Se carga el precio en el origen y se lee: miss, consulta el origen y cachea.
        catalogo.actualizarPrecio("p-1", 100.0);
        System.out.println("2. Primera lectura (miss) -> " + catalogo.obtenerPrecio("p-1") + " (esperado: 100.0)");

        // 3. Segunda lectura inmediata: hit, sale del cache.
        System.out.println("3. Segunda lectura (hit)  -> " + catalogo.obtenerPrecio("p-1") + " (esperado: 100.0)");

        // 4. Se actualiza el precio: debe invalidar el cache. Si devuelve 100.0, el cache quedó viejo.
        catalogo.actualizarPrecio("p-1", 150.0);
        System.out.println("4. Tras actualizar        -> " + catalogo.obtenerPrecio("p-1") + " (esperado: 150.0)");

        // 5. Otro producto no se ve afectado por la invalidación del primero.
        catalogo.actualizarPrecio("p-2", 50.0);
        System.out.println("5. Otro producto          -> " + catalogo.obtenerPrecio("p-2") + " (esperado: 50.0)");
    }
}
