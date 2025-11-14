package co.edu.uniquindio.tallermacanico.dto;

import java.time.LocalDate;

/**
 * DTO para representar el reporte de movimientos de inventario por repuesto.
 */
public class InventarioReporteDTO {

    private int idMovimiento;
    private String tipoMovimiento; // Entrada o Salida
    private int cantidad;
    private LocalDate fecha;
    private String nombreRepuesto;

    public InventarioReporteDTO() {}

    public InventarioReporteDTO(int idMovimiento, String tipoMovimiento, int cantidad, LocalDate fecha, String nombreRepuesto) {
        this.idMovimiento = idMovimiento;
        this.tipoMovimiento = tipoMovimiento;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.nombreRepuesto = nombreRepuesto;
    }

    public int getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(int idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getNombreRepuesto() {
        return nombreRepuesto;
    }

    public void setNombreRepuesto(String nombreRepuesto) {
        this.nombreRepuesto = nombreRepuesto;
    }
}

