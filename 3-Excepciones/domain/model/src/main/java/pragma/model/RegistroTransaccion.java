package pragma.model;

public class RegistroTransaccion implements AutoCloseable {

    @Override
    public void close() {
        System.out.println("RegistroTransaccion cerrado");
    }
}
