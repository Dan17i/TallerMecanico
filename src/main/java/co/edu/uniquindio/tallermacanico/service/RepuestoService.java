package co.edu.uniquindio.tallermacanico.service;


import co.edu.uniquindio.tallermacanico.model.Repuesto;

import java.util.List;

/**
 * Servicio para definir operaciones sobre repuestos.
 * Encapsula la lógica de negocio entre el controlador y el repositorio.
 */
public interface RepuestoService {

    /**
     * Lista todos los repuestos disponibles.
     *
     * @return lista de objetos {@link Repuesto}
     */
    List<Repuesto> listarRepuestos();

    /**
     * Registra un nuevo repuesto.
     *
     * @param repuesto objeto {@link Repuesto} con los datos a insertar
     * @return objeto {@link Repuesto} con el ID generado
     */
    Repuesto registrarRepuesto(Repuesto repuesto);

    /**
     * Obtiene un repuesto por su ID.
     *
     * @param id identificador del repuesto
     * @return objeto {@link Repuesto} si existe, o {@code null} si no se encuentra
     */
    Repuesto obtenerPorId(int id);

    /**
     * Elimina un repuesto por su ID.
     *
     * @param id identificador del repuesto
     */
    void eliminarRepuesto(int id);

    /**
     * Actualiza el stock de un repuesto.
     *
     * @param id identificador del repuesto
     * @param nuevoStock nuevo valor de stock
     */
    void actualizarStock(int id, double nuevoStock);
}

