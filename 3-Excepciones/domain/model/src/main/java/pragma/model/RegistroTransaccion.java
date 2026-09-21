package pragma.model;

/**
 * Recurso simulado que se debe "cerrar" siempre, incluso si procesar() lanza
 * una excepción — por eso ProcesadorPagos debe abrirlo con try-with-resources.
 *
 * TODO: implementa close() (puede ser tan simple como un System.out.println,
 * pero debe existir para que puedas comprobar en tus pruebas que sí se cerró).
 */
public class RegistroTransaccion implements AutoCloseable {

    @Override
    public void close() {
        throw new UnsupportedOperationException("TODO: implementar RegistroTransaccion.close");
    }
}
