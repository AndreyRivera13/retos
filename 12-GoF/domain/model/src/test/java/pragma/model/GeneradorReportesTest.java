package pragma.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class GeneradorReportesTest {
    private final Reporte reporte = new Reporte.Builder().conEncabezado("x").conTabla("y").build();

    @Test
    void notificaATodosLosObservadoresRegistradosCadaVezQueSeGeneraUnReporte() {
        GeneradorReportes generador = new GeneradorReportes();
        ContadorReportes contador = new ContadorReportes();
        LogReportes log = new LogReportes();
        generador.agregarObservador(contador);
        generador.agregarObservador(log);

        generador.generar(reporte);
        generador.generar(reporte);

        assertEquals(2, contador.getTotal());
        assertEquals(2, log.getRegistros().size());
        assertEquals("Reporte generado con 2 secciones", log.getRegistros().get(0));
    }

    @Test
    void generarConExportadorDevuelveElResultadoDeLaEstrategiaYNotifica() {
        GeneradorReportes generador = new GeneradorReportes();
        ContadorReportes contador = new ContadorReportes();
        generador.agregarObservador(contador);

        String salida = generador.generar(reporte, new ExportadorCSV());

        assertEquals("tipo,contenido\nencabezado,\"x\"\ntabla,\"y\"", salida);
        assertEquals(1, contador.getTotal());
    }

    @Test
    void sinObservadoresGenerarNoFalla() {
        assertDoesNotThrow(() -> new GeneradorReportes().generar(reporte));
    }
}
