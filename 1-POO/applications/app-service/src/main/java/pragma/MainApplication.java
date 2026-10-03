package pragma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import pragma.garage.Carro;
import pragma.garage.Garage;
import pragma.garage.Vehiculo;
import pragma.nomina.Empleado;
import pragma.nomina.EmpleadoFijo;
import pragma.nomina.EmpleadoPorHoras;
import pragma.nomina.Nomina;

@SpringBootApplication
@ConfigurationPropertiesScan
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);

        EmpleadoFijo fijo = new EmpleadoFijo("Brayam Oliveros", "1004802764", 3000000);
        EmpleadoPorHoras porHoras = new EmpleadoPorHoras("Andrey Rivera", "1004802765", 20, 15000);

        Nomina<Empleado> nomina = new Nomina<>();
        nomina.agregar(fijo);
        nomina.agregar(porHoras);

        System.out.println("Salario empleado fijo: " + fijo.calcularSalario());
        System.out.println("Salario empleado por horas: " + porHoras.calcularSalario());
        System.out.println("Total a pagar: " + nomina.totalAPagar());
        System.out.println("Bono de " + fijo.getNombre() + ": " + fijo.calcularBono());
    }
}
