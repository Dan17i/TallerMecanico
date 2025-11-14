package co.edu.uniquindio.tallermacanico.service;


import co.edu.uniquindio.tallermacanico.model.OrdenServicioMecanico;

import java.util.List;

/**
 * Interfaz que define las operaciones del servicio para la entidad OrdenServicioMecanico.
 * Permite gestionar la asignación de mecánicos a servicios dentro de una orden de trabajo.
 */
public interface OrdenServicioMecanicoService {

    /**
     * Lista todas las asignaciones de mecánicos en servicios.
     * @return lista de asignaciones
     */
    List<OrdenServicioMecanico> listarAsignaciones();

    /**
     * Busca una asignación específica por ID de orden-servicio y ID de mecánico.
     * @param idOrdenServicio identificador del servicio
     * @param idMecanico identificador del mecánico
     * @return asignación encontrada
     * @throws IllegalArgumentException si los IDs son inválidos
     */
    OrdenServicioMecanico buscarPorOrdenYPorMecanico(int idOrdenServicio, int idMecanico);

    /**
     * Registra una nueva asignación de mecánico a un servicio.
     * @param asignacion objeto con los datos de la asignación
     * @throws IllegalArgumentException si los datos son inválidos
     */
    void registrarAsignacion(OrdenServicioMecanico asignacion);

    /**
     * Elimina una asignación por ID de orden-servicio y ID de mecánico.
     * @param idOrdenServicio identificador del servicio
     * @param idMecanico identificador del mecánico
     * @throws IllegalArgumentException si los IDs son inválidos
     */
    void eliminarAsignacion(int idOrdenServicio, int idMecanico);
}
