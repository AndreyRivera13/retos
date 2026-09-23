package pragma.nomina;

/**
 * === Plantilla del reto 1 (POO) tal cual lo pide el enunciado real ===
 * Este paquete es INDEPENDIENTE de pragma.model (tu solución con vehículos,
 * que sigue intacta). Aquí vas a modelar el caso literal: nómina/Empleado.
 *
 * Clase abstracta base. TODO: define los atributos comunes (ej. nombre,
 * documento) y deja calcularSalario() abstracto — cada subtipo decide cómo
 * se calcula el suyo, sin que Nomina<T> necesite saber cuál es cuál.
 */
public abstract class Empleado {
    // TODO: atributos comunes (nombre, documento, etc.)

    public abstract double calcularSalario();
}
