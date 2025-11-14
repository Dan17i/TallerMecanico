package co.edu.uniquindio.tallermacanico.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa un vehículo registrado por un cliente.
 * Cada vehículo puede tener múltiples órdenes de trabajo.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehiculo {
    private int idVehiculo;
    private int idCliente;
    private String placa;
    private String marca;
    private String modelo;
    private int anio;
    private String color;

    // Getters y setters
}

