package co.edu.uniquindio.tallermacanico.reportes.estadisticos.dto;

/**
 * DTO para representar la distribución de repuestos más usados.
 * Contiene el nombre del repuesto y la cantidad de veces que fue utilizado.
 */
public class RepuestoEstadisticoDTO {

    private String nombreRepuesto;
    private long totalUsos;

    public RepuestoEstadisticoDTO() {}

    public RepuestoEstadisticoDTO(String nombreRepuesto, long totalUsos) {
        this.nombreRepuesto = nombreRepuesto;
        this.totalUsos = totalUsos;
    }

    public String getNombreRepuesto() {
        return nombreRepuesto;
    }

    public void setNombreRepuesto(String nombreRepuesto) {
        this.nombreRepuesto = nombreRepuesto;
    }

    public long getTotalUsos() {
        return totalUsos;
    }

    public void setTotalUsos(long totalUsos) {
        this.totalUsos = totalUsos;
    }
}

