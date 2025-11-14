package co.edu.uniquindio.tallermacanico.dto;

/**
 * DTO para representar el reporte de servicios disponibles.
 */
public class ServicioReporteDTO {

    private String nombre;
    private String descripcion;
    private double precio;

    public ServicioReporteDTO() {}

    public ServicioReporteDTO(String nombre, String descripcion, double precio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}

