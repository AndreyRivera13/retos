package pragma.model;

/** Strategy: cada formato de exportación implementa esto. */
public interface ExportadorReporte {
    String exportar(Reporte reporte);
}
