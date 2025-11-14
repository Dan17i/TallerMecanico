package co.edu.uniquindio.tallermacanico.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa una supervisión entre mecánicos dentro de un servicio.
 * Registra quién supervisa a quién, en qué especialidad y con qué observaciones.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Supervision {
    private int idOrdenServicio;
    private int idMecanicoSupervisor;
    private int idMecanicoSupervisado;
    private int idEspecialidad;
    private String observaciones;
}

