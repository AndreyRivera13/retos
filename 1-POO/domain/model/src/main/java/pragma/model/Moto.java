package pragma.model;

public class Moto extends Vehiculo{
    public Moto(String placa, String marca) {
        super(placa, marca);
    }

    @Override
    public double calcularImpuesto() {
        return 200000;
    }
}
