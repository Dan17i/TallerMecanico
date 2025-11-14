package co.edu.uniquindio.tallermacanico.dto;

import java.time.LocalDate;

/**
 * DTO para representar el reporte de servicios realizados por un mecánico en órdenes de trabajo.
 */
public class ServicioMecanicoReporteDTO {

    private int idOrdenServicio;
    private String nombreMecanico;
    private String nombreServicio;
    private LocalDate fecha;

    public ServicioMecanicoReporteDTO() {}

    public ServicioMecanicoReporteDTO(int idOrdenServicio, String nombreMecanico, String nombreServicio, LocalDate fecha) {
        this.idOrdenServicio = idOrdenServicio;
        this.nombreMecanico = nombreMecanico;
        this.nombreServicio = nombreServicio;
        this.fecha = fecha;
    }

    public int getIdOrdenServicio() {
        return idOrdenServicio;
    }

    public void setIdOrdenServicio(int idOrdenServicio) {
        this.idOrdenServicio = idOrdenServicio;
    }

    public String getNombreMecanico() {
        return nombreMecanico;
    }

    public void setNombreMecanico(String nombreMecanico) {
        this.nombreMecanico = nombreMecanico;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}

