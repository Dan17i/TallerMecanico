package co.edu.uniquindio.tallermacanico.service;

import co.edu.uniquindio.tallermacanico.model.Cliente;

import java.util.List;

/**
 * Interfaz que define las operaciones del servicio para la entidad Cliente.
 * Permite gestionar los clientes registrados en el sistema.
 */
public interface ClienteService {

    /**
     * Lista todos los clientes registrados.
     * @return lista de clientes
     */
    List<Cliente> listarClientes();

    /**
     * Busca un cliente por su ID.
     * @param id identificador del cliente
     * @return cliente encontrado
     * @throws IllegalArgumentException si el ID es inválido
     */
    Cliente buscarPorId(int id);

    /**
     * Registra un nuevo cliente en el sistema.
     * @param cliente objeto con los datos del cliente
     * @throws IllegalArgumentException si los datos son inválidos
     */
    void registrarCliente(Cliente cliente);

    /**
     * Elimina un cliente por su ID.
     * @param id identificador del cliente
     * @throws IllegalArgumentException si el ID es inválido
     */
    void eliminarCliente(int id);
}
