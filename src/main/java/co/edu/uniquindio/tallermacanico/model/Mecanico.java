package co.edu.uniquindio.tallermacanico.model;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
    @NotBlank
    private String nombre;
    private String apellido;
    @Pattern(regexp = "\\d{10}")
    private String telefono;
    @Min(0)
    @Max(99)
    private int experienciaAnios;

    // Getters y setters
}

