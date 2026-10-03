package pragma.model;

import java.util.ArrayList;
import java.util.List;

public class GeneradorReportes {
    private final List<ObservadorReporte> observadores = new ArrayList<>();

    public void agregarObservador(ObservadorReporte observador) {
        observadores.add(observador);
    }

    public void generar(Reporte reporte) {
        observadores.forEach(observador -> observador.notificar(reporte));
    }

    public String generar(Reporte reporte, ExportadorReporte exportador) {
        String salida = exportador.exportar(reporte);
        generar(reporte);
        return salida;
    }
}
