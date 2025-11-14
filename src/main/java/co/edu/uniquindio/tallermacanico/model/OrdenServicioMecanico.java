package co.edu.uniquindio.tallermacanico.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa la asignación de un mecánico a un servicio dentro de una orden de trabajo.
 * Incluye la especialidad y el rol desempeñado.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdenServicioMecanico {
    private int idOrdenServicio;
    private int idMecanico;
    private int idEspecialidad;
    private String rolEnServicio;
}

