package pragma.model;

import lombok.Getter;

@Getter
public class EntradaCache<T> {
    private final T valor;
    private final long guardadoEnMillis;

    public EntradaCache(T valor) {
        this.valor = valor;
        this.guardadoEnMillis = System.currentTimeMillis();
    }

    public boolean expiro(long ttlMillis) {
        return System.currentTimeMillis() - guardadoEnMillis > ttlMillis;
    }
}
