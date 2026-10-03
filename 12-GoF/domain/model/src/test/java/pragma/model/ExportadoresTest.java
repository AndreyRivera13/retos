package pragma.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ExportadoresTest {
    private final Reporte reporte = new Reporte.Builder()
            .conEncabezado("Ventas Q1")
            .conTabla("a|b")
            .build();

    @Test
    void pdfSimuladoListaCadaSeccion() {
        String salida = new ExportadorPDF().exportar(reporte);

        assertEquals("%PDF-SIMULADO\n[ENCABEZADO] Ventas Q1\n[TABLA] a|b\n%%EOF", salida);
    }

    @Test
    void csvTieneCabeceraYUnaFilaPorSeccion() {
        String salida = new ExportadorCSV().exportar(reporte);

        assertEquals("tipo,contenido\nencabezado,\"Ventas Q1\"\ntabla,\"a|b\"", salida);
    }

    @Test
    void csvEscapaComasYComillasEnElContenido() {
        Reporte conComas = new Reporte.Builder().conTabla("dijo \"hola\", y salió").build();

        String salida = new ExportadorCSV().exportar(conComas);

        assertEquals("tipo,contenido\ntabla,\"dijo \"\"hola\"\", y salió\"", salida);
    }

    @Test
    void elMismoReporteSeExportaEnFormatosDistintosSegunLaEstrategia() {
        ExportadorReporte pdf = new ExportadorPDF();
        ExportadorReporte csv = new ExportadorCSV();

        assertNotEquals(pdf.exportar(reporte), csv.exportar(reporte));
    }
}
