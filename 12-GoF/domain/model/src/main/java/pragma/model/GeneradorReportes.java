package pragma.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Sujeto observable (Observer): Reporte NO conoce a sus observadores concretos;
 * esta clase orquesta la generación y avisa a quien esté suscrito.
 *
 * También es el punto donde se elige el formato en runtime (Strategy): quien
 * llama decide qué {@link ExportadorReporte} usar en cada generación.
 */
public class GeneradorReportes {
    private final List<ObservadorReporte> observadores = new ArrayList<>();

    public void agregarObservador(ObservadorReporte observador) {
        observadores.add(observador);
    }

    public void generar(Reporte reporte) {
        observadores.forEach(observador -> observador.notificar(reporte));
    }

    /** Exporta con la estrategia indicada, notifica a los observadores y devuelve el resultado. */
    public String generar(Reporte reporte, ExportadorReporte exportador) {
        String salida = exportador.exportar(reporte);
        generar(reporte);
        return salida;
    }
}
