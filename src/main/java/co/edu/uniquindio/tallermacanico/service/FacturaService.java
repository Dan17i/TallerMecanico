package co.edu.uniquindio.tallermacanico.service;


import co.edu.uniquindio.tallermacanico.model.Factura;

import java.util.List;

/**
 * Interfaz que define las operaciones del servicio para la entidad Factura.
 * Permite gestionar las facturas registradas en el sistema.
 */
public interface FacturaService {

    /**
     * Lista todas las facturas registradas.
     * @return lista de facturas
     */
    List<Factura> listarFacturas();

    /**
     * Busca una factura por su ID.
     * @param id identificador de la factura
     * @return factura encontrada
     * @throws IllegalArgumentException si el ID es inválido
     */
    Factura buscarPorId(int id);

    /**
     * Registra una nueva factura en el sistema.
     * @param factura objeto con los datos de la factura
     * @throws IllegalArgumentException si los datos son inválidos
     */
    void registrarFactura(Factura factura);

    /**
     * Elimina una factura por su ID.
     * @param id identificador de la factura
     * @throws IllegalArgumentException si el ID es inválido
     */
    void eliminarFactura(int id);
}
