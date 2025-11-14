package co.edu.uniquindio.tallermacanico.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Representa una orden de trabajo asociada a un vehículo.
 * Contiene el diagnóstico inicial y el estado actual de la orden.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdenTrabajo {
    private int idOrdenTrabajo;
    private int idVehiculo;
    private LocalDate fechaIngreso;
    private LocalDate fechaSalida;
    private String diagnosticoInicial;
    private int idEstadoOrden;

    // Getters y setters
}
