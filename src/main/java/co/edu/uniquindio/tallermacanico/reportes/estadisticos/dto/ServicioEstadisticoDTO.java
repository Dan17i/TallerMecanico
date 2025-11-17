package co.edu.uniquindio.tallermacanico.reportes.estadisticos.dto;
/**
 * DTO para representar la distribución de servicios más solicitados.
 * Contiene el nombre del servicio y la cantidad de veces que fue solicitado.
 */
public class ServicioEstadisticoDTO {

    private String nombreServicio; // ✅ Eje X o Etiquetas
    private long totalSolicitudes; // ✅ Eje Y o Valores

    public ServicioEstadisticoDTO() {}

    public ServicioEstadisticoDTO(String nombreServicio, long totalSolicitudes) {
        this.nombreServicio = nombreServicio;
        this.totalSolicitudes = totalSolicitudes;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    public long getTotalSolicitudes() {
        return totalSolicitudes;
    }

    public void setTotalSolicitudes(long totalSolicitudes) {
        this.totalSolicitudes = totalSolicitudes;
    }
}


