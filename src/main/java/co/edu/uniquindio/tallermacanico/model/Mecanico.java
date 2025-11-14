package co.edu.uniquindio.tallermacanico.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa un mecánico del taller.
 * Puede tener una o varias especialidades y participar en órdenes de servicio.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mecanico {
    private int idMecanico;
    private String nombre;
    private String apellido;
    private String telefono;
    private int experienciaAnios;

    // Getters y setters
}

