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
        catalogo.obtenerPrecio("p-1"); // miss: puebla el cache

        origen.put("p-1", 999.0); // otro servicio escribe directo en la BD, sin pasar por actualizarPrecio

        assertEquals(100.0, catalogo.obtenerPrecio("p-1")); // sigue saliendo del cache
    }

    @Test
    void actualizarPrecio_invalidaElCache() {
        CatalogoPreciosUseCase catalogo = new CatalogoPreciosUseCase(TTL_LARGO);
        catalogo.actualizarPrecio("p-1", 100.0);
        assertEquals(100.0, catalogo.obtenerPrecio("p-1")); // queda en cache

        catalogo.actualizarPrecio("p-1", 150.0);

        assertEquals(150.0, catalogo.obtenerPrecio("p-1"));
    }

    @Test
    void expirado_despuesDelTtlVuelveAlOrigen() throws InterruptedException {
        Map<String, Double> origen = new HashMap<>(Map.of("p-1", 100.0));
        CatalogoPreciosUseCase catalogo = new CatalogoPreciosUseCase(TTL_CORTO, origen);
        catalogo.obtenerPrecio("p-1"); // miss: puebla el cache
        origen.put("p-1", 200.0); // cambio directo en la BD

        Thread.sleep(TTL_CORTO * 3); // deja vencer el TTL

        assertEquals(200.0, catalogo.obtenerPrecio("p-1")); // TTL vencido: refresca desde el origen
    }
}
