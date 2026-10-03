package pragma.model;

import java.util.Locale;

public class ExportadorPDF implements ExportadorReporte {
    @Override
    public String exportar(Reporte reporte) {
        StringBuilder salida = new StringBuilder("%PDF-SIMULADO\n");
        for (SeccionReporte seccion : reporte.getSecciones()) {
            salida.append('[').append(seccion.getTipo().toUpperCase(Locale.ROOT)).append("] ")
                    .append(seccion.getContenido()).append('\n');
        }
        return salida.append("%%EOF").toString();
    }
}
