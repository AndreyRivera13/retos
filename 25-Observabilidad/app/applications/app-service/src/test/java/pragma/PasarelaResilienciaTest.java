package pragma;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadRegistry;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import pragma.model.Pago;
import pragma.model.RespuestaPago;
import pragma.pasarela.FallaSimulada;
import pragma.usecase.RealizarPagoUseCase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(properties = {
        "resilience4j.circuitbreaker.instances.pasarelaPagos.wait-duration-in-open-state=300ms",
        "resilience4j.retry.instances.pasarelaPagos.wait-duration=1ms"
})
class PasarelaResilienciaTest {

    static final AtomicBoolean FALLANDO = new AtomicBoolean(false);
    static final AtomicInteger LLAMADAS_A_LA_PASARELA = new AtomicInteger();

    @TestConfiguration
    static class FallaControlable {
        @Bean
        @Primary
        FallaSimulada fallaSimulada() {
            return () -> {
                LLAMADAS_A_LA_PASARELA.incrementAndGet();
                return FALLANDO.get();
            };
        }
    }

    @Autowired
    RealizarPagoUseCase useCase;
    @Autowired
    CircuitBreakerRegistry circuitBreakers;
    @Autowired
    BulkheadRegistry bulkheads;

    @BeforeEach
    void reiniciar() {
        FALLANDO.set(false);
        LLAMADAS_A_LA_PASARELA.set(0);
        circuitBreakers.circuitBreaker("pasarelaPagos").reset();
    }

    @Test
    void conPasarelaSanaRespondeAprobado() {
        RespuestaPago r = useCase.ejecutar(new Pago("p-1", 50));

        assertEquals("APROBADO", r.estado());
        assertEquals(1, LLAMADAS_A_LA_PASARELA.get());
    }

    @Test
    void conPasarelaCaidaReintentaTresVecesYResponderEnProcesoEnVezDeError() {
        FALLANDO.set(true);

        RespuestaPago r = useCase.ejecutar(new Pago("p-2", 50));

        assertEquals("EN_PROCESO", r.estado());
        assertEquals("pago en proceso, se confirmará luego", r.mensaje());
        assertEquals(3, LLAMADAS_A_LA_PASARELA.get());
    }

    @Test
    void elCircuitoPasaPorLosTresEstados() throws InterruptedException {
        CircuitBreaker cb = circuitBreakers.circuitBreaker("pasarelaPagos");
        List<String> transiciones = new ArrayList<>();
        cb.getEventPublisher().onStateTransition(e -> transiciones.add(e.getStateTransition().name()));
        assertEquals(CircuitBreaker.State.CLOSED, cb.getState());

        FALLANDO.set(true);
        for (int i = 0; i < 4; i++) {
            useCase.ejecutar(new Pago("p-" + i, 10));
        }
        assertEquals(CircuitBreaker.State.OPEN, cb.getState());

        int llamadasAntes = LLAMADAS_A_LA_PASARELA.get();
        RespuestaPago conCircuitoAbierto = useCase.ejecutar(new Pago("p-abierto", 10));
        assertEquals("EN_PROCESO", conCircuitoAbierto.estado());
        assertEquals(llamadasAntes, LLAMADAS_A_LA_PASARELA.get());

        FALLANDO.set(false);
        Thread.sleep(500);
        assertEquals(CircuitBreaker.State.HALF_OPEN, cb.getState());

        for (int i = 0; i < 3; i++) {
            assertEquals("APROBADO", useCase.ejecutar(new Pago("p-ok-" + i, 10)).estado());
        }
        assertEquals(CircuitBreaker.State.CLOSED, cb.getState());

        System.out.println("TRANSICIONES DEL CIRCUITO: " + transiciones);
        assertEquals(List.of("CLOSED_TO_OPEN", "OPEN_TO_HALF_OPEN", "HALF_OPEN_TO_CLOSED"), transiciones);
    }

    @Test
    void conElBulkheadLleno_laSextaLlamadaConcurrenteRecibeFallbackSinTocarLaPasarela() {
        Bulkhead bulkhead = bulkheads.bulkhead("pasarelaPagos");
        assertEquals(5, bulkhead.getBulkheadConfig().getMaxConcurrentCalls());
        for (int i = 0; i < 5; i++) {
            assertTrue(bulkhead.tryAcquirePermission());
        }

        try {
            RespuestaPago r = useCase.ejecutar(new Pago("p-6", 10));

            assertEquals("EN_PROCESO", r.estado());
            assertEquals(0, LLAMADAS_A_LA_PASARELA.get());
        } finally {
            for (int i = 0; i < 5; i++) {
                bulkhead.onComplete();
            }
        }
        assertFalse(bulkhead.getMetrics().getAvailableConcurrentCalls() < 5);
    }
}
