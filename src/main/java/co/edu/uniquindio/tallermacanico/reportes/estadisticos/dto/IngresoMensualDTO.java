package co.edu.uniquindio.tallermacanico.reportes.estadisticos.dto;

import java.math.BigDecimal;

/**
 * DTO para representar los ingresos mensuales generados por órdenes de servicio.
 */
public class IngresoMensualDTO {

    private String mes;               // ✅ Eje X (temporal)
    private BigDecimal totalIngresos; // ✅ Eje Y (monetario)

    public IngresoMensualDTO() {}

    public IngresoMensualDTO(String mes, BigDecimal totalIngresos) {
        this.mes = mes;
        this.totalIngresos = totalIngresos;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public BigDecimal getTotalIngresos() {
        return totalIngresos;
    }

    public void setTotalIngresos(BigDecimal totalIngresos) {
        this.totalIngresos = totalIngresos;
    }
}
