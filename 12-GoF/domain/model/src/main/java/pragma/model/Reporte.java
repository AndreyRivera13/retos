package pragma.model;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO (Builder): completa el Builder para que puedas construir un Reporte
 * con secciones opcionales sin un constructor de N parámetros. Fíjate en el
 * ejemplo de Pizza.Builder del documento de retos (tema 12).
 *
 * Uso esperado:
 *   Reporte r = new Reporte.Builder()
 *       .conEncabezado("Ventas Q1")
 *       .conTabla("...")
 *       .build();
 */
public class Reporte {
    private final List<SeccionReporte> secciones = new ArrayList<>();

    public List<SeccionReporte> getSecciones() {
        return secciones;
    }

    public static class Builder {
        private final Reporte reporte = new Reporte();

        public Builder conEncabezado(String texto) {
            // TODO: agrega una SeccionReporte tipo "encabezado" y retorna this
            throw new UnsupportedOperationException("TODO: implementar conEncabezado");
        }

        public Builder conTabla(String contenido) {
            // TODO
            throw new UnsupportedOperationException("TODO: implementar conTabla");
        }

        public Builder conGrafico(String contenido) {
            // TODO
            throw new UnsupportedOperationException("TODO: implementar conGrafico");
        }

        public Builder conPiePagina(String texto) {
            // TODO
            throw new UnsupportedOperationException("TODO: implementar conPiePagina");
        }

        public Reporte build() {
            return reporte;
        }
    }
}
