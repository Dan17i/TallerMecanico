package co.edu.uniquindio.tallermacanico.dto;

import java.time.LocalDate;

/**
 * DTO para representar el reporte de facturas emitidas por cliente.
 */
public class FacturaReporteDTO {

    private int idFactura;
    private LocalDate fechaEmision;
    private double total;
    private String cliente;

    public FacturaReporteDTO() {}

    public FacturaReporteDTO(int idFactura, LocalDate fechaEmision, double total, String cliente) {
        this.idFactura = idFactura;
        this.fechaEmision = fechaEmision;
        this.total = total;
        this.cliente = cliente;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }
}

