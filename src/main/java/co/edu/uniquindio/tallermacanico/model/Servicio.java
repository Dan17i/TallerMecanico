package co.edu.uniquindio.tallermacanico.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa un servicio ofrecido por el taller (ej. alineación, cambio de aceite).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Servicio {
    private int idServicio;
    private String nombre;
    private String descripcion;
    private double precioBase;

    // Getters y setters
}
