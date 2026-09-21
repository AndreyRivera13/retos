package pragma.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Aggregate Root. TODO:
 *  - depositar(Dinero monto): suma al saldo (valida que la cuenta no esté BLOQUEADA).
 *  - retirar(Dinero monto): lanza IllegalStateException si la cuenta está BLOQUEADA,
 *    o si el monto excede el saldo disponible; si todo bien, resta del saldo
 *    y agrega un RetiroRealizado a la lista de eventos.
 *
 * Los eventos (lista `eventos`) son la forma en que este agregado comunica al
 * resto del sistema que "pasó algo importante", sin acoplarse directamente
 * a quien vaya a reaccionar (ej. enviar notificación, actualizar un reporte).
 */
public class CuentaBancaria {
    private final String id;
    private Dinero saldo;
    private EstadoCuenta estado;
    private final List<Object> eventos = new ArrayList<>();

    public CuentaBancaria(String id, Dinero saldoInicial) {
        this.id = id;
        this.saldo = saldoInicial;
        this.estado = EstadoCuenta.ACTIVA;
    }

    public void depositar(Dinero monto) {
        throw new UnsupportedOperationException("TODO: implementar CuentaBancaria.depositar");
    }

    public void retirar(Dinero monto) {
        throw new UnsupportedOperationException("TODO: implementar CuentaBancaria.retirar");
    }

    public Dinero getSaldo() { return saldo; }
    public EstadoCuenta getEstado() { return estado; }
    public List<Object> getEventos() { return eventos; }
}
