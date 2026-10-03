package pragma.usecase;

import pragma.model.Cita;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDate;
import java.util.concurrent.TimeoutException;

public class GestionarCitasReactivoUseCase {
    private static final Duration LATENCIA_POR_DEFECTO = Duration.ofMillis(200);
    private static final Duration TIMEOUT_POR_DEFECTO = Duration.ofSeconds(2);

    private final Duration latenciaOrigen;
    private final Duration timeout;

    public GestionarCitasReactivoUseCase() {
        this(LATENCIA_POR_DEFECTO, TIMEOUT_POR_DEFECTO);
    }

    public GestionarCitasReactivoUseCase(Duration latenciaOrigen, Duration timeout) {
        this.latenciaOrigen = latenciaOrigen;
        this.timeout = timeout;
    }

    public Mono<Cita> reservar(String doctorId, LocalDate fecha) {
        return Mono.defer(() -> {
            if (doctorId == null || doctorId.isBlank()) {
                return Mono.error(new IllegalArgumentException("El doctor es obligatorio"));
            }
            if (fecha == null) {
                return Mono.error(new IllegalArgumentException("La fecha es obligatoria"));
            }
            return Mono.just(new Cita(doctorId, fecha));
        });
    }

    public Flux<Cita> citasDelDia(LocalDate fecha) {
        return consultarCitasLento(fecha)
                .timeout(timeout)
                .onErrorResume(TimeoutException.class, error -> Flux.empty());
    }

    private Flux<Cita> consultarCitasLento(LocalDate fecha) {
        return Flux.just(new Cita("dra-lopez", fecha), new Cita("dr-perez", fecha))
                .delaySubscription(latenciaOrigen);
    }
}
