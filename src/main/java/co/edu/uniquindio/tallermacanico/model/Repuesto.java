package co.edu.uniquindio.tallermacanico.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa un repuesto disponible en el inventario del taller.
 * Puede estar asociado a proveedores y a órdenes de trabajo.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Repuesto {
    private int idRepuesto;
    private String nombre;
    private String descripcion;
    private double stockActual;
    private String unidadMedida;

    // Getters y setters
}
