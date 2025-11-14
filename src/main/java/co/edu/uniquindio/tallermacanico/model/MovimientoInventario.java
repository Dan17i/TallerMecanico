package co.edu.uniquindio.tallermacanico.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa un movimiento de inventario (entrada, salida o ajuste) de un repuesto.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoInventario {
    private int idMovimiento;
    private int idRepuesto;
    private String tipoMovimiento;
    private double cantidad;
    private String fechaMovimiento;
    private String referencia;
    private String observaciones;

    // Getters y setters
}
