package pragma.model;

import java.time.Instant;

/**
 * Evento de dominio. Ya está completo (es un simple record de datos) —
 * lo que tienes que decidir es DÓNDE se crea/emite (ver CuentaBancaria.retirar).
 */
public record RetiroRealizado(String cuentaId, Dinero monto, Instant ocurridoEn) {
}
