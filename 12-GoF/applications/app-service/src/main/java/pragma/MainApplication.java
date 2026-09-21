package pragma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * Punto de arranque del reto "12-GoF".
 * No necesitas tocar esta clase: úsala solo para probar manualmente
 * lo que vayas implementando (agrega tu propio código de prueba en el main
 * o, mejor, escribe pruebas JUnit en src/test).
 */
@SpringBootApplication
@ConfigurationPropertiesScan
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
        // TODO (opcional): cuando tengas tus clases implementadas, arma aquí
        // una pequeña prueba manual, igual a como se hizo en 1-POO/MainApplication.java
    }
}
