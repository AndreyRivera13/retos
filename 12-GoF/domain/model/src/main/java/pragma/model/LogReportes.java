package pragma.model;

import java.util.ArrayList;
import java.util.List;

public class LogReportes implements ObservadorReporte {
    private final List<String> registros = new ArrayList<>();

    @Override
    public void notificar(Reporte reporte) {
        registros.add("Reporte generado con " + reporte.getSecciones().size() + " secciones");
    }

    public List<String> getRegistros() {
        return List.copyOf(registros);
    }
}
