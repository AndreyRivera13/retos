package pragma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import pragma.model.Carro;
import pragma.model.Garage;
import pragma.model.Vehiculo;

@SpringBootApplication
@ConfigurationPropertiesScan
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
        Garage<Vehiculo> garage = new Garage<>();
        garage.agregarVehiculo(new Carro("ABC123", "Toyota"));
        garage.agregarVehiculo(new Carro("DEF456", "Honda"));
        garage.agregarVehiculo(new Carro("GHI789", "Ford"));
        garage.agregarVehiculo(new Carro("JKL012", "Chevrolet"));
        System.out.println("Total impuesto: " + garage.total());
    }
}
