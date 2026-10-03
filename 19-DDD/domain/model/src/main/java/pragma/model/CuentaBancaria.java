package pragma.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CuentaBancaria {
    private final String id;
    private Dinero saldo;
    private EstadoCuenta estado;
    private final List<EventoDominio> eventos = new ArrayList<>();

    public CuentaBancaria(String id, Dinero saldoInicial) {
        if (id == null || id.isBlank() || saldoInicial == null) {
            throw new IllegalArgumentException("El id y el saldo inicial son obligatorios");
        }
        this.id = id;
        this.saldo = saldoInicial;
        this.estado = EstadoCuenta.ACTIVA;
    }

    public void depositar(Dinero monto) {
        exigirActiva();
        exigirMontoPositivo(monto);
        saldo = saldo.sumar(monto);
    }

    public void retirar(Dinero monto) {
        exigirActiva();
        exigirMontoPositivo(monto);
        if (monto.esMayorQue(saldo)) {
            throw new IllegalStateException("Saldo insuficiente: saldo " + saldo + ", retiro " + monto);
        }
        saldo = saldo.restar(monto);
        eventos.add(new RetiroRealizado(id, monto, Instant.now()));
    }

    public void bloquear() {
        estado = EstadoCuenta.BLOQUEADA;
    }

    public void desbloquear() {
        estado = EstadoCuenta.ACTIVA;
    }

    public List<EventoDominio> extraerEventos() {
        List<EventoDominio> pendientes = List.copyOf(eventos);
        eventos.clear();
        return pendientes;
    }

    private void exigirActiva() {
        if (estado == EstadoCuenta.BLOQUEADA) {
            throw new IllegalStateException("La cuenta " + id + " está bloqueada");
        }
    }

    private void exigirMontoPositivo(Dinero monto) {
        if (monto == null || monto.esCero()) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero");
        }
    }

    public String getId() { return id; }
    public Dinero getSaldo() { return saldo; }
    public EstadoCuenta getEstado() { return estado; }
    public List<EventoDominio> getEventos() { return Collections.unmodifiableList(eventos); }
}
