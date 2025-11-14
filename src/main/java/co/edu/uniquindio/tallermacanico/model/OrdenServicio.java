package co.edu.uniquindio.tallermacanico.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa la aplicación de un servicio dentro de una orden de trabajo.
 * Incluye estado y precio final del servicio aplicado.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdenServicio {
    private int idOrdenServicio;
    private int idOrdenTrabajo;
    private int idServicio;
    private String estado;
    private double precioFinal;
}

