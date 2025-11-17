package co.edu.uniquindio.tallermacanico.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa un repuesto disponible en el inventario del taller mecánico.
 * Cada repuesto tiene un identificador único, nombre, descripción, stock actual y unidad de medida.
 *
 * <p>Corresponde a la tabla {@code repuesto} en la base de datos Oracle.</p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Repuesto {

    /**
     * Identificador único del repuesto. Se genera automáticamente en la base de datos.
     */
    private int idRepuesto;

    /**
     * Nombre del repuesto.
     */
    private String nombre;

    /**
     * Descripción detallada del repuesto.
     */
    private String descripcion;

    /**
     * Cantidad actual disponible en inventario.
     */
    private double stockActual;

    /**
     * Unidad de medida del repuesto (ej. unidades, litros, metros).
     */
    private String unidadMedida;
}
