package pragma.usecase;

import pragma.model.EntradaCache;
import pragma.model.Precio;

import java.util.HashMap;
import java.util.Map;

public class CatalogoPreciosUseCase {
    private static final long TTL_POR_DEFECTO_MILLIS = 30_000;

    private final Map<String, EntradaCache<Double>> cache = new HashMap<>();
    private final Map<String, Double> origenDeDatos;
    private final long ttlMillis;

    public CatalogoPreciosUseCase() {
        this(TTL_POR_DEFECTO_MILLIS);
    }

    public CatalogoPreciosUseCase(long ttlMillis) {
        this(ttlMillis, new HashMap<>());
    }

    CatalogoPreciosUseCase(long ttlMillis, Map<String, Double> origenDeDatos) {
        this.ttlMillis = ttlMillis;
        this.origenDeDatos = origenDeDatos;
    }

    public double obtenerPrecio(String productoId) {
        EntradaCache<Double> entradaCache = cache.get(productoId);
        if (entradaCache != null && !entradaCache.expiro(ttlMillis)) {
            return entradaCache.getValor();
        }
        double precio = consultarOrigenLento(productoId);
        cache.put(productoId, new EntradaCache<>(precio));
        return precio;
    }

    public void actualizarPrecio(String productoId, double nuevoPrecio) {
        origenDeDatos.put(productoId, nuevoPrecio);
        cache.remove(productoId);
    }

    private double consultarOrigenLento(String productoId) {
        return origenDeDatos.getOrDefault(productoId, 0.0);
    }
}
