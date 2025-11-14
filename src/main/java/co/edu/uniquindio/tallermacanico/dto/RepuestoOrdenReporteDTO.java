package co.edu.uniquindio.tallermacanico.dto;

import java.time.LocalDate;

/**
 * DTO para representar el reporte de repuestos utilizados en órdenes de servicio.
 */
public class RepuestoOrdenReporteDTO {

    private int idOrdenServicio;
    private String nombreRepuesto;
    private int cantidad;
    private String tipoMovimiento; // Entrada o Salida
    private LocalDate fecha;

    public RepuestoOrdenReporteDTO() {}

    public RepuestoOrdenReporteDTO(int idOrdenServicio, String nombreRepuesto, int cantidad,
                                   String tipoMovimiento, LocalDate fecha) {
        this.idOrdenServicio = idOrdenServicio;
        this.nombreRepuesto = nombreRepuesto;
        this.cantidad = cantidad;
        this.tipoMovimiento = tipoMovimiento;
        this.fecha = fecha;
    }

    public int getIdOrdenServicio() {
        return idOrdenServicio;
    }

    public void setIdOrdenServicio(int idOrdenServicio) {
        this.idOrdenServicio = idOrdenServicio;
    }

    public String getNombreRepuesto() {
        return nombreRepuesto;
    }

    public void setNombreRepuesto(String nombreRepuesto) {
        this.nombreRepuesto = nombreRepuesto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}

