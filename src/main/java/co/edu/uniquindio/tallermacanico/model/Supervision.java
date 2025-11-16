package co.edu.uniquindio.tallermacanico.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa una supervisión entre mecánicos dentro de un servicio.
 * La clave primaria está compuesta por:
 * - idOrdenServicio
 * - idMecanicoSupervisor
 * - idMecanicoSupervisado
 *
 * Además, registra la especialidad en la que se supervisa y observaciones adicionales.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Supervision {

    /** Identificador de la orden de servicio supervisada (FK hacia orden_servicio). */
    private int idOrdenServicio;

    /** Identificador del mecánico que actúa como supervisor (FK hacia mecanico). */
    private int idMecanicoSupervisor;

    /** Identificador del mecánico que es supervisado (FK hacia mecanico). */
    private int idMecanicoSupervisado;

    /** Identificador de la especialidad en la que se realiza la supervisión (FK hacia especialidad). */
    private int idEspecialidad;

    /** Observaciones adicionales sobre la supervisión. */
    private String observaciones;
}
