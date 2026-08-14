package pragma.model;

public class Carro extends Vehiculo implements Mantenible {
    public Carro(String placa, String marca) {
        super(placa, marca);
    }

    @Override
    public void revisar() {
        System.out.println("Revisando el carro");
    }

    public double calcularImpuesto() {
        return 500000;
    }


}
