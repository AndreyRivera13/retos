package pragma.model;

/**
 * TODO: igual que EntradaCache<T> del ejemplo de clima (tema 10 del documento
 * de retos), pero genérica para cualquier valor guardado con marca de tiempo.
 */
public class EntradaCache<T> {
    private final T valor;
    private final long guardadoEnMillis;

    public EntradaCache(T valor) {
        this.valor = valor;
        this.guardadoEnMillis = System.currentTimeMillis();
    }

    /** TODO: true si han pasado más de ttlMillis desde que se guardó. */
    public boolean expiro(long ttlMillis) {
        throw new UnsupportedOperationException("TODO: implementar EntradaCache.expiro");
    }

    public T getValor() { return valor; }
}
