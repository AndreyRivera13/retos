package pragma.usecase;

import pragma.model.EntradaCache;
import pragma.model.Precio;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO: implementa Cache-Aside completo con TTL de 30 segundos:
 *  - obtenerPrecio(id): si hay HIT y no expiró, retorna del cache;
 *    si hay MISS o expiró, consulta consultarOrigenLento(id) (ya dado, simula
 *    ir a una BD), guarda en cache y retorna.
 *  - actualizarPrecio(id, nuevoPrecio): actualiza el "origen" y ADEMÁS invalida
 *    el cache (cache.remove(id)) para esa clave.
 *
 * Cuando termines el código, escribe (en un archivo aparte WRITE-THROUGH.md,
 * sin código) cómo cambiaría este mismo catálogo si usaras Write-Through en
 * vez de Cache-Aside, y en qué escenario real de tu proyecto usarías cada una.
 */
public class CatalogoPreciosUseCase {
    private final Map<String, EntradaCache<Double>> cache = new HashMap<>();
    private final Map<String, Double> origenDeDatos = new HashMap<>(); // simula tu BD
    private final long ttlMillis = 30_000;

    public double obtenerPrecio(String productoId) {
        throw new UnsupportedOperationException("TODO: implementar obtenerPrecio (Cache-Aside + TTL)");
    }

    public void actualizarPrecio(String productoId, double nuevoPrecio) {
        throw new UnsupportedOperationException("TODO: implementar actualizarPrecio (debe invalidar cache)");
    }

    private double consultarOrigenLento(String productoId) {
        // Ya está dado: simula una consulta lenta a la BD real.
        return origenDeDatos.getOrDefault(productoId, 0.0);
    }
}
