package pragma.model;

public class SaldoInsuficienteException extends Exception {
    public SaldoInsuficienteException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
