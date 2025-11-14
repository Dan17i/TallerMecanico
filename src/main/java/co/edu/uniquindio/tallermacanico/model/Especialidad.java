package co.edu.uniquindio.tallermacanico.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa una especialidad técnica que puede tener un mecánico (ej. electricidad, frenos).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Especialidad {
    private int idEspecialidad;
    private String nombre;

    // Getters y setters
}
