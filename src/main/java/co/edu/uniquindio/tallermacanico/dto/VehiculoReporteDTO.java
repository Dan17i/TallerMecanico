package co.edu.uniquindio.tallermacanico.dto;

/**
 * DTO para representar el reporte de vehículos con su propietario.
 */
public class VehiculoReporteDTO {

    private String placa;
    private String marca;
    private String modelo;
    private String propietario;

    public VehiculoReporteDTO() {}

    public VehiculoReporteDTO(String placa, String marca, String modelo, String propietario) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.propietario = propietario;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }
}

