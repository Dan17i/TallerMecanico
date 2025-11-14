package co.edu.uniquindio.tallermacanico.service;


import co.edu.uniquindio.tallermacanico.model.Supervision;

import java.util.List;

/**
 * Interfaz que define las operaciones del servicio para la entidad Supervision.
 * Permite gestionar las supervisiones entre mecánicos en servicios específicos.
 */
public interface SupervisionService {

    /**
     * Lista todas las supervisiones registradas.
     * @return lista de supervisiones
     */
    List<Supervision> listarSupervisiones();

    /**
     * Busca una supervisión específica por IDs compuestos.
     * @param idOrdenServicio ID del servicio
     * @param idSupervisor ID del mecánico supervisor
     * @param idSupervisado ID del mecánico supervisado
     * @return supervisión encontrada
     * @throws IllegalArgumentException si los IDs son inválidos
     */
    Supervision buscarSupervision(int idOrdenServicio, int idSupervisor, int idSupervisado);

    /**
     * Registra una nueva supervisión entre mecánicos.
     * @param supervision objeto con los datos de la supervisión
     * @throws IllegalArgumentException si los datos son inválidos
     */
    void registrarSupervision(Supervision supervision);

    /**
     * Elimina una supervisión por IDs compuestos.
     * @param idOrdenServicio ID del servicio
     * @param idSupervisor ID del mecánico supervisor
     * @param idSupervisado ID del mecánico supervisado
     * @throws IllegalArgumentException si los IDs son inválidos
     */
    void eliminarSupervision(int idOrdenServicio, int idSupervisor, int idSupervisado);
}

