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

    // Getters
    public int getIdOrdenTrabajo() {
        return idOrdenTrabajo;
    }

    public int getIdVehiculo() {
        return idVehiculo;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public String getDiagnosticoInicial() {
        return diagnosticoInicial;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    // Setters
    public void setIdOrdenTrabajo(int idOrdenTrabajo) {
        this.idOrdenTrabajo = idOrdenTrabajo;
    }

    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public void setDiagnosticoInicial(String diagnosticoInicial) {
        this.diagnosticoInicial = diagnosticoInicial;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }
}

