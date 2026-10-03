package pragma.usecase;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CatalogoPreciosUseCaseTest {
    private static final long TTL_LARGO = 30_000;
    private static final long TTL_CORTO = 20;

    @Test
    void miss_consultaElOrigenYDevuelveElPrecio() {
        Map<String, Double> origen = new HashMap<>(Map.of("p-1", 100.0));
        CatalogoPreciosUseCase catalogo = new CatalogoPreciosUseCase(TTL_LARGO, origen);

        assertEquals(100.0, catalogo.obtenerPrecio("p-1"));
    }

    @Test
    void productoInexistente_devuelveCeroDelOrigen() {
        CatalogoPreciosUseCase catalogo = new CatalogoPreciosUseCase(TTL_LARGO, new HashMap<>());

        assertEquals(0.0, catalogo.obtenerPrecio("no-existe"));
    }

    @Test
    void hit_dentroDelTtlNoVuelveAlOrigen() {
        Map<String, Double> origen = new HashMap<>(Map.of("p-1", 100.0));
        CatalogoPreciosUseCase catalogo = new CatalogoPreciosUseCase(TTL_LARGO, origen);
        catalogo.obtenerPrecio("p-1");

        origen.put("p-1", 999.0);

        assertEquals(100.0, catalogo.obtenerPrecio("p-1"));
    }

    @Test
    void actualizarPrecio_invalidaElCache() {
        CatalogoPreciosUseCase catalogo = new CatalogoPreciosUseCase(TTL_LARGO);
        catalogo.actualizarPrecio("p-1", 100.0);
        assertEquals(100.0, catalogo.obtenerPrecio("p-1"));

        catalogo.actualizarPrecio("p-1", 150.0);

        assertEquals(150.0, catalogo.obtenerPrecio("p-1"));
    }

    @Test
    void expirado_despuesDelTtlVuelveAlOrigen() throws InterruptedException {
        Map<String, Double> origen = new HashMap<>(Map.of("p-1", 100.0));
        CatalogoPreciosUseCase catalogo = new CatalogoPreciosUseCase(TTL_CORTO, origen);
        catalogo.obtenerPrecio("p-1");
        origen.put("p-1", 200.0);

        Thread.sleep(TTL_CORTO * 3);

        assertEquals(200.0, catalogo.obtenerPrecio("p-1"));
    }
}
