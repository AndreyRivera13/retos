package pragma.model;

/**
 * Contrato tipo Strategy: cada regla de negocio (edad, ingresos, historial...)
 * se implementa como una clase separada que cumple esta interfaz.
 *
 * Esto es lo que te permite, en el reto, agregar una regla nueva creando
 * SOLO una clase nueva, sin tocar ValidadorSolicitud (Open/Closed Principle).
 */
public interface ReglaValidacion {
    /** ¿Esta regla debe evaluarse para esta solicitud? (casi siempre true, pero da flexibilidad) */
    boolean aplica(Solicitud solicitud);

    /** Evalúa la regla. Si falla, debe devolver un ResultadoValidacion con aprobada=false y el motivo. */
    ResultadoValidacion validar(Solicitud solicitud);
}
