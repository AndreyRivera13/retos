package pragma.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Sujeto observable (equivalente a PedidoSujeto del ejemplo).
 * Reporte NO debe conocer a sus observadores concretos — por eso esta clase
 * es la que orquesta la generación y avisa a quien esté suscrito.
 *
 * TODO: implementa agregarObservador(...) y generar(...) (este último debe
 * llamar a notificar() de cada observador registrado).
 */
public class GeneradorReportes {
    private final List<ObservadorReporte> observadores = new ArrayList<>();

    public void agregarObservador(ObservadorReporte observador) {
        throw new UnsupportedOperationException("TODO: implementar agregarObservador");
    }

    public void generar(Reporte reporte) {
        throw new UnsupportedOperationException("TODO: implementar generar (debe notificar a los observadores)");
    }
}
