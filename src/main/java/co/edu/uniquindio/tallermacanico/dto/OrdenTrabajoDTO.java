package co.edu.uniquindio.tallermacanico.dto;

import java.time.LocalDate;

/**
 * DTO que representa una orden de trabajo con el nombre del estado incluido.
 */
public class OrdenTrabajoDTO {
    private int idOrdenTrabajo;
    private int idVehiculo;
    private LocalDate fechaIngreso;
    private LocalDate fechaSalida;
    private String diagnosticoInicial;
    private String nombreEstado;

    public OrdenTrabajoDTO(int idOrdenTrabajo, int idVehiculo, LocalDate fechaIngreso,
                           LocalDate fechaSalida, String diagnosticoInicial, String nombreEstado) {
        this.idOrdenTrabajo = idOrdenTrabajo;
        this.idVehiculo = idVehiculo;
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = fechaSalida;
        this.diagnosticoInicial = diagnosticoInicial;
        this.nombreEstado = nombreEstado;
    }

    // Getters y setters
}

