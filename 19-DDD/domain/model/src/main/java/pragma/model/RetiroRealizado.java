package pragma.model;

import java.time.Instant;

public record RetiroRealizado(String cuentaId, Dinero monto, Instant ocurridoEn) implements EventoDominio {
}
