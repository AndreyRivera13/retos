package pragma.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Reporte inmutable, construido siempre mediante {@link Builder} (patrón Builder):
 * las secciones son opcionales y no hay un constructor de N parámetros.
 *
 * Uso:
 *   Reporte r = new Reporte.Builder()
 *       .conEncabezado("Ventas Q1")
 *       .conTabla("...")
 *       .build();
 *
 * El constructor es privado y la lista es inmutable: una vez construido, el
 * Reporte no cambia aunque se siga usando el Builder o se intente modificar
 * la lista que devuelve getSecciones().
 */
public class Reporte {
    private final List<SeccionReporte> secciones;

    private Reporte(List<SeccionReporte> secciones) {
        this.secciones = List.copyOf(secciones);
    }

    public List<SeccionReporte> getSecciones() {
        return secciones;
    }

    public static class Builder {
        private final List<SeccionReporte> secciones = new ArrayList<>();

        public Builder conEncabezado(String texto) {
            secciones.add(new SeccionReporte("encabezado", texto));
            return this;
        }

        public Builder conTabla(String contenido) {
            secciones.add(new SeccionReporte("tabla", contenido));
            return this;
        }

        public Builder conGrafico(String contenido) {
            secciones.add(new SeccionReporte("grafico", contenido));
            return this;
        }

        public Builder conPiePagina(String texto) {
            secciones.add(new SeccionReporte("piePagina", texto));
            return this;
        }

        /** Cada llamada devuelve un Reporte nuevo e independiente del Builder. */
        public Reporte build() {
            return new Reporte(secciones);
        }
    }
}
