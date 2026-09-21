package pragma.usecase;

import pragma.model.Cita;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDate;

/**
 * TODO: convierte a reactivo:
 *  - reservar(...): retorna Mono<Cita>.
 *  - citasDelDia(fecha): retorna Flux<Cita>; si no emite nada en 2 segundos
 *    (usa .timeout(Duration.ofSeconds(2))), cae a un valor/lista por defecto
 *    en vez de propagar el error (revisa .onErrorResume / .switchIfEmpty
 *    en el ejemplo de buscarUsuario del documento de retos, tema 15).
 *
 * Antes de programar, respóndete: si nadie llama .subscribe() sobre el
 * Mono/Flux que devuelves, ¿pasa algo? ¿por qué?
 */
public class GestionarCitasReactivoUseCase {

    public Mono<Cita> reservar(String doctorId, LocalDate fecha) {
        throw new UnsupportedOperationException("TODO: implementar reservar reactivo");
    }

    public Flux<Cita> citasDelDia(LocalDate fecha) {
        throw new UnsupportedOperationException("TODO: implementar citasDelDia reactivo");
    }
}
