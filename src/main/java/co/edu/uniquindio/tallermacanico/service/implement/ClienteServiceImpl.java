package co.edu.uniquindio.tallermacanico.service.implement;

import co.edu.uniquindio.tallermacanico.model.Cliente;
import co.edu.uniquindio.tallermacanico.repository.ClienteRepository;
import co.edu.uniquindio.tallermacanico.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación del servicio para la entidad Cliente.
 * Contiene validaciones de negocio y delega operaciones al repositorio.
 */
@RequiredArgsConstructor
@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    /**
     * Lista todos los clientes registrados en la base de datos.
     * @return lista de clientes
     */
    @Override
    public List<Cliente> listarClientes() {
        return clienteRepository.listarClientes();
    }

    /**
     * Busca un cliente por su ID, validando que sea positivo.
     * @param id identificador del cliente
     * @return cliente encontrado
     * @throws IllegalArgumentException si el ID es inválido
     */
    @Override
    public Cliente buscarPorId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID del cliente debe ser mayor que cero");
        }
        return clienteRepository.buscarPorId(id);
    }

    /**
     * Registra un nuevo cliente validando los campos obligatorios.
     * @param cliente objeto con los datos del cliente
     * @throws IllegalArgumentException si los datos son inválidos
     */
    @Override
    public void registrarCliente(Cliente cliente) {
        if (cliente.getNombre() == null || cliente.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del cliente es obligatorio");
        }
        if (cliente.getApellido() == null || cliente.getApellido().isBlank()) {
            throw new IllegalArgumentException("El apellido del cliente es obligatorio");
        }
        if (cliente.getEmail() != null && !cliente.getEmail().isBlank()) {
            if (!cliente.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                throw new IllegalArgumentException("El correo electrónico no tiene un formato válido");
            }
        }
        if (cliente.getTelefono() != null && cliente.getTelefono().length() > 20) {
            throw new IllegalArgumentException("El teléfono no puede superar los 20 caracteres");
        }
        clienteRepository.registrarCliente(cliente);
    }

    /**
     * Elimina un cliente por su ID, validando que sea positivo.
     * @param id identificador del cliente
     * @throws IllegalArgumentException si el ID es inválido
     */
    @Override
    public void eliminarCliente(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID del cliente debe ser mayor que cero");
        }
        clienteRepository.eliminarCliente(id);
    }
}

