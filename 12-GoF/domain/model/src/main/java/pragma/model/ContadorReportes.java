package pragma.model;

/** Observer concreto: cuenta cuántos reportes se han generado. */
public class ContadorReportes implements ObservadorReporte {
    private int total;

    @Override
    public void notificar(Reporte reporte) {
        total++;
    }

    public int getTotal() {
        return total;
    }
}
