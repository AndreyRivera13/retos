package pragma.pasarela;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Component;
import pragma.model.Pago;
import pragma.model.RespuestaPago;

/**
 * Simula una pasarela externa que falla 40% de las veces.
 *
 * TODO:
 *  1. en procesar(): if (Math.random() < 0.4) throw new RuntimeException("timeout simulado");
 *     si no falla, retorna un RespuestaPago("APROBADO", "ok").
 *  2. implementa fallback(...) con la MISMA firma + (Exception e) al final,
 *     devolviendo RespuestaPago("EN_PROCESO", "pago en proceso, se confirmará luego").
 *  3. ajusta el application.yaml de app-service con los valores exactos del
 *     reto: Retry 3 intentos + backoff exponencial, CircuitBreaker >50% fallos
 *     en ventana de 10, Bulkhead máximo 5 llamadas concurrentes.
 *
 * Debes poder explicar por qué combinaste Bulkhead + CircuitBreaker (qué
 * problema distinto resuelve cada uno aquí).
 */
@Component
public class PasarelaPagosAdapter implements ProcesarPagoPort {

    @Retry(name = "pasarelaPagos")
    @CircuitBreaker(name = "pasarelaPagos", fallbackMethod = "fallback")
    @Bulkhead(name = "pasarelaPagos")
    @Override
    public RespuestaPago procesar(Pago pago) {
        throw new UnsupportedOperationException("TODO: implementar PasarelaPagosAdapter.procesar");
    }

    public RespuestaPago fallback(Pago pago, Exception e) {
        throw new UnsupportedOperationException("TODO: implementar fallback");
    }
}
