package co.edu.uniquindio.tallermacanico.dto;

/**
 * DTO para representar la productividad de un mecánico supervisor.
 */
public class ProductividadSupervisorDTO {

    private String nombreSupervisor;
    private int totalSupervisiones;
    private int totalServicios;

    public ProductividadSupervisorDTO() {}

    public ProductividadSupervisorDTO(String nombreSupervisor, int totalSupervisiones, int totalServicios) {
        this.nombreSupervisor = nombreSupervisor;
        this.totalSupervisiones = totalSupervisiones;
        this.totalServicios = totalServicios;
    }

    public String getNombreSupervisor() {
        return nombreSupervisor;
    }

    public void setNombreSupervisor(String nombreSupervisor) {
        this.nombreSupervisor = nombreSupervisor;
    }

    public int getTotalSupervisiones() {
        return totalSupervisiones;
    }

    public void setTotalSupervisiones(int totalSupervisiones) {
        this.totalSupervisiones = totalSupervisiones;
    }

    public int getTotalServicios() {
        return totalServicios;
    }

    public void setTotalServicios(int totalServicios) {
        this.totalServicios = totalServicios;
    }
}

