package pragma.model;

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
