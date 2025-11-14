package co.edu.uniquindio.tallermacanico.repository;


import co.edu.uniquindio.tallermacanico.model.Cliente;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para gestionar operaciones sobre la tabla Cliente.
 * Aquí se definen las consultas SQL relacionadas con clientes.
 */
@Repository
public class ClienteRepository {

    private final JdbcTemplate jdbcTemplate;

    public ClienteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Obtener todos los clientes
    public List<Cliente> listarClientes() {
        String sql = "SELECT * FROM cliente";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Cliente.class));
    }

    // Registrar un nuevo cliente
    public void registrarCliente(Cliente cliente) {
        String sql = "INSERT INTO cliente (nombre, apellido, direccion, telefono, email) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                cliente.getNombre(),
                cliente.getApellido(),
                cliente.getDireccion(),
                cliente.getTelefono(),
                cliente.getEmail());
    }

    // Buscar cliente por ID
    public Cliente buscarPorId(int idCliente) {
        String sql = "SELECT * FROM cliente WHERE id_cliente = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Cliente.class), idCliente);
    }

    // Eliminar cliente
    public void eliminarCliente(int idCliente) {
        String sql = "DELETE FROM cliente WHERE id_cliente = ?";
        jdbcTemplate.update(sql, idCliente);
    }
}

