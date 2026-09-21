package pragma.model;

/** Ya está completo. */
public class Precio {
    private final String productoId;
    private final double valor;

    public Precio(String productoId, double valor) {
        this.productoId = productoId;
        this.valor = valor;
    }

    public String getProductoId() { return productoId; }
    public double getValor() { return valor; }
}
