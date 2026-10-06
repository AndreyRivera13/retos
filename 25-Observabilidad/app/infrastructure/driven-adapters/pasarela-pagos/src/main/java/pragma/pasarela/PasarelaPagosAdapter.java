package pragma.pasarela;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Component;
import pragma.model.Pago;
import pragma.model.RespuestaPago;

@Component
public class PasarelaPagosAdapter implements ProcesarPagoPort {
    private final FallaSimulada falla;

    public PasarelaPagosAdapter(FallaSimulada falla) {
        this.falla = falla;
    }

    @Retry(name = "pasarelaPagos", fallbackMethod = "fallback")
    @CircuitBreaker(name = "pasarelaPagos")
    @Bulkhead(name = "pasarelaPagos")
    @Override
    public RespuestaPago procesar(Pago pago) {
        if (falla.debeFallar()) {
            throw new IllegalStateException("timeout simulado");
        }
        return new RespuestaPago("APROBADO", "ok");
    }

    public RespuestaPago fallback(Pago pago, Exception e) {
        return new RespuestaPago("EN_PROCESO", "pago en proceso, se confirmará luego");
    }
}
