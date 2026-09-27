package pragma.model;

public class PagoInvalidoException extends Exception {
    public PagoInvalidoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
