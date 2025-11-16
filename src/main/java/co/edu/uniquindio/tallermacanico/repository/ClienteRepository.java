package co.edu.uniquindio.tallermacanico.repository;

import co.edu.uniquindio.tallermacanico.model.Cliente;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para gestionar operaciones sobre la tabla Cliente.
 * Proporciona métodos CRUD usando JdbcTemplate.
 */
@Repository
public class ClienteRepository {

    private final JdbcTemplate jdbcTemplate;

    public ClienteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Obtiene todos los clientes registrados en la base de datos.
     *
     * @return lista de objetos Cliente
     */
    public List<Cliente> listarClientes() {
        String sql = "SELECT * FROM cliente";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Cliente.class));
    }

    /**
     * Registra un nuevo cliente en la base de datos.
     *
     * @param cliente objeto Cliente con los datos a insertar
     */
    public void registrarCliente(Cliente cliente) {
        String sql = "INSERT INTO cliente (nombre, apellido, direccion, telefono, email) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                cliente.getNombre(),
                cliente.getApellido(),
                cliente.getDireccion(),
                cliente.getTelefono(),
                cliente.getEmail());
    }

    /**
     * Busca un cliente por su identificador único.
     *
     * @param idCliente identificador del cliente
     * @return objeto Cliente encontrado
     */
    public Cliente buscarPorId(int idCliente) {
        String sql = "SELECT * FROM cliente WHERE id_cliente = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Cliente.class), idCliente);
    }

    /**
     * Elimina un cliente de la base de datos por su identificador.
     *
     * @param idCliente identificador del cliente a eliminar
     */
    public void eliminarCliente(int idCliente) {
        String sql = "DELETE FROM cliente WHERE id_cliente = ?";
        jdbcTemplate.update(sql, idCliente);
    }
}
