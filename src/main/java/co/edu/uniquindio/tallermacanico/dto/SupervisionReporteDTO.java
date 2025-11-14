package co.edu.uniquindio.tallermacanico.dto;


import java.time.LocalDate;

/**
 * DTO para representar el reporte de supervisiones realizadas por un mecánico.
 */
public class SupervisionReporteDTO {

    private int idSupervision;
    private String supervisor;
    private String mecanicoSupervisado;
    private LocalDate fecha;
    private String observaciones;

    public SupervisionReporteDTO() {}

    public SupervisionReporteDTO(int idSupervision, String supervisor, String mecanicoSupervisado,
                                 LocalDate fecha, String observaciones) {
        this.idSupervision = idSupervision;
        this.supervisor = supervisor;
        this.mecanicoSupervisado = mecanicoSupervisado;
        this.fecha = fecha;
        this.observaciones = observaciones;
    }

    public int getIdSupervision() {
        return idSupervision;
    }

    public void setIdSupervision(int idSupervision) {
        this.idSupervision = idSupervision;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getMecanicoSupervisado() {
        return mecanicoSupervisado;
    }

    public void setMecanicoSupervisado(String mecanicoSupervisado) {
        this.mecanicoSupervisado = mecanicoSupervisado;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}

