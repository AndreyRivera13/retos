package pragma.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReporteTest {

    @Test
    void builderCreaLasSeccionesEnElOrdenIndicado() {
        Reporte reporte = new Reporte.Builder()
                .conEncabezado("Ventas Q1")
                .conTabla("a|b")
                .conPiePagina("fin")
                .build();

        assertEquals(3, reporte.getSecciones().size());
        assertEquals("encabezado", reporte.getSecciones().get(0).getTipo());
        assertEquals("tabla", reporte.getSecciones().get(1).getTipo());
        assertEquals("piePagina", reporte.getSecciones().get(2).getTipo());
    }

    @Test
    void lasSeccionesSonOpcionales() {
        assertTrue(new Reporte.Builder().build().getSecciones().isEmpty());
    }

    @Test
    void laListaDeSeccionesNoSePuedeModificar() {
        Reporte reporte = new Reporte.Builder().conEncabezado("x").build();

        assertThrows(UnsupportedOperationException.class,
                () -> reporte.getSecciones().add(new SeccionReporte("tabla", "y")));
    }

    @Test
    void unReporteYaConstruidoNoCambiaSiSeSigueUsandoElBuilder() {
        Reporte.Builder builder = new Reporte.Builder().conEncabezado("x");
        Reporte primero = builder.build();

        builder.conPiePagina("pie");
        Reporte segundo = builder.build();

        assertEquals(1, primero.getSecciones().size());
        assertEquals(2, segundo.getSecciones().size());
    }
}
