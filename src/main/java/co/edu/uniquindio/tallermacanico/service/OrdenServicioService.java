package co.edu.uniquindio.tallermacanico.service;

import co.edu.uniquindio.tallermacanico.model.OrdenServicio;

import java.util.List;

/**
 * Interfaz que define las operaciones del servicio para la entidad OrdenServicio.
 * Permite gestionar los servicios aplicados a una orden de trabajo.
 */
public interface OrdenServicioService {

    /**
     * Lista todos los registros de servicios aplicados en órdenes de trabajo.
     * @return lista de orden-servicio
     */
    List<OrdenServicio> listarOrdenesServicio();

    /**
     * Busca un registro de orden-servicio por su ID.
     * @param id identificador del registro
     * @return orden-servicio encontrada
     * @throws IllegalArgumentException si el ID es inválido
     */
    OrdenServicio buscarPorId(int id);

    /**
     * Registra un nuevo servicio aplicado a una orden de trabajo.
     * @param ordenServicio objeto con los datos del servicio aplicado
     * @throws IllegalArgumentException si los datos son inválidos
     */
    void registrarOrdenServicio(OrdenServicio ordenServicio);

    /**
     * Elimina un registro de orden-servicio por su ID.
     * @param id identificador del registro
     * @throws IllegalArgumentException si el ID es inválido
     */
    void eliminarOrdenServicio(int id);
}

