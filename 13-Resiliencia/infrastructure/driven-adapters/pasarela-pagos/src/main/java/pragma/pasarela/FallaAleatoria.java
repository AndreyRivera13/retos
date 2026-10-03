package pragma.pasarela;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class FallaAleatoria implements FallaSimulada {
    private final double probabilidadFallo;

    public FallaAleatoria(@Value("${pasarela.probabilidad-fallo:0.4}") double probabilidadFallo) {
        this.probabilidadFallo = probabilidadFallo;
    }

    @Override
    public boolean debeFallar() {
        return ThreadLocalRandom.current().nextDouble() < probabilidadFallo;
    }
}
