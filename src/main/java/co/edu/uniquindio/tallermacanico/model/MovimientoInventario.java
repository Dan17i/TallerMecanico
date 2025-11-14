package co.edu.uniquindio.tallermacanico.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Representa un movimiento de inventario (entrada o salida) de un repuesto.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoInventario {
    private int idMovimiento;
    private int idRepuesto;
    private String tipoMovimiento;
    private double cantidad;
    private LocalDate fechaMovimiento;
    private String observaciones;
}

