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

    public List<Cliente> listarClientes() {
        String sql = "SELECT * FROM cliente";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Cliente.class));
    }

    /**
     * Registra un nuevo cliente y devuelve el ID generado.
     */
    public int registrarCliente(Cliente cliente) {
        String sql = "INSERT INTO cliente (nombre, apellido, direccion, telefono, email) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                cliente.getNombre(),
                cliente.getApellido(),
                cliente.getDireccion(),
                cliente.getTelefono(),
                cliente.getEmail());

        // Recuperar el último ID generado (Oracle/SQL estándar)
        return jdbcTemplate.queryForObject("SELECT MAX(id_cliente) FROM cliente", Integer.class);
    }

    public Cliente buscarPorId(int idCliente) {
        String sql = "SELECT * FROM cliente WHERE id_cliente = ?";
        List<Cliente> clientes = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Cliente.class), idCliente);
        return clientes.isEmpty() ? null : clientes.get(0);
    }

    /**
     * Elimina un cliente y devuelve true si se eliminó, false si no existía.
     */
    public boolean eliminarCliente(int idCliente) {
        String sql = "DELETE FROM cliente WHERE id_cliente = ?";
        int filas = jdbcTemplate.update(sql, idCliente);
        return filas > 0;
    }
}

