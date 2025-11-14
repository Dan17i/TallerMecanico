package co.edu.uniquindio.tallermacanico.service;


import co.edu.uniquindio.tallermacanico.model.MovimientoInventario;

import java.util.List;

/**
 * Interfaz que define las operaciones del servicio para la entidad MovimientoInventario.
 * Permite gestionar los movimientos de entrada y salida de repuestos en el inventario.
 */
public interface MovimientoInventarioService {

    /**
     * Lista todos los movimientos registrados en el inventario.
     * @return lista de movimientos
     */
    List<MovimientoInventario> listarMovimientos();

    /**
     * Busca un movimiento por su ID.
     * @param id identificador del movimiento
     * @return movimiento encontrado
     * @throws IllegalArgumentException si el ID es inválido
     */
    MovimientoInventario buscarPorId(int id);

    /**
     * Registra un nuevo movimiento de inventario.
     * @param movimiento objeto con los datos del movimiento
     * @throws IllegalArgumentException si los datos son inválidos
     */
    void registrarMovimiento(MovimientoInventario movimiento);

    /**
     * Elimina un movimiento por su ID.
     * @param id identificador del movimiento
     * @throws IllegalArgumentException si el ID es inválido
     */
    void eliminarMovimiento(int id);
}
