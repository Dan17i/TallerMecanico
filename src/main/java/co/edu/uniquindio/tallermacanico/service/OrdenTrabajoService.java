package co.edu.uniquindio.tallermacanico.service;

import co.edu.uniquindio.tallermacanico.model.OrdenTrabajo;

import java.util.List;

/**
 * Interfaz que define las operaciones del servicio para la entidad OrdenTrabajo.
 * Permite gestionar las órdenes de trabajo generadas en el taller.
 */
public interface OrdenTrabajoService {

    /**
     * Lista todas las órdenes de trabajo registradas.
     * @return lista de órdenes de trabajo
     */
    List<OrdenTrabajo> listarOrdenesTrabajo();

    /**
     * Busca una orden de trabajo por su ID.
     * @param id identificador de la orden
     * @return orden encontrada
     * @throws IllegalArgumentException si el ID es inválido
     */
    OrdenTrabajo buscarPorId(int id);

    /**
     * Registra una nueva orden de trabajo en el sistema.
     * @param orden objeto con los datos de la orden
     * @throws IllegalArgumentException si los datos son inválidos
     */
    void registrarOrdenTrabajo(OrdenTrabajo orden);

    /**
     * Elimina una orden de trabajo por su ID.
     * @param id identificador de la orden
     * @throws IllegalArgumentException si el ID es inválido
     */
    void eliminarOrdenTrabajo(int id);
}

