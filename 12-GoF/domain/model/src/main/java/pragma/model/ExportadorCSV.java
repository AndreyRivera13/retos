package pragma.model;

public class ExportadorCSV implements ExportadorReporte {
    @Override
    public String exportar(Reporte reporte) {
        StringBuilder salida = new StringBuilder("tipo,contenido");
        for (SeccionReporte seccion : reporte.getSecciones()) {
            salida.append('\n').append(seccion.getTipo()).append(',').append(escapar(seccion.getContenido()));
        }
        return salida.toString();
    }

    private String escapar(String valor) {
        return "\"" + valor.replace("\"", "\"\"") + "\"";
    }
}
