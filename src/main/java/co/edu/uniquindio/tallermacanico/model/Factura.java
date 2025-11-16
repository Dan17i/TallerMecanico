package co.edu.uniquindio.tallermacanico.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Representa una factura generada a partir de una orden de trabajo.
 * Incluye subtotales, impuestos, descuentos y estado de pago.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Factura {
    private int idFactura;
    private int idOrdenTrabajo;
    private LocalDate fechaEmision;
    private int idEstadoPago;
    private double subtotalServicios;
    private double subtotalRepuestos;
    private double impuestosTotal;
    private double descuentoTotal;
    private double total;

    // Getters y setters
}
