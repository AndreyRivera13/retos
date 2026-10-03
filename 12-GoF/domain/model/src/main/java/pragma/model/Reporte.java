package pragma.model;

import java.util.ArrayList;
import java.util.List;

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

        public Reporte build() {
            return new Reporte(secciones);
        }
    }
}
