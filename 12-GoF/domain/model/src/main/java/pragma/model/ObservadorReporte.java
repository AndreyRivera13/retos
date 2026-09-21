package pragma.model;

/** Observer: se notifica cada vez que se genera un reporte. */
public interface ObservadorReporte {
    void notificar(Reporte reporte);
}
