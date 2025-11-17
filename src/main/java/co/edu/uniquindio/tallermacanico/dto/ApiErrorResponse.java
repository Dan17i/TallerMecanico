package co.edu.uniquindio.tallermacanico.dto;

/**
 * DTO para representar mensajes de error en formato JSON.
 */
public class ApiErrorResponse {

    private String error;
    private String detalle;

    // Constructor público
    public ApiErrorResponse(String error, String detalle) {
        this.error = error;
        this.detalle = detalle;
    }

    // Getters y setters
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
}

