package pragma.model;

/**
 * Strategy: "exporta" el reporte a CSV simulado, una fila por sección.
 * El contenido va siempre entre comillas, con las comillas internas duplicadas,
 * para que comas o comillas dentro del texto no rompan el formato.
 */
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
