package co.edu.uniquindio.tallermacanico.repository;

import co.edu.uniquindio.tallermacanico.model.MovimientoInventario;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para gestionar operaciones sobre la tabla movimiento_inventario.
 * Proporciona métodos CRUD usando JdbcTemplate.
 */
@Repository
public class MovimientoInventarioRepository {

    private final JdbcTemplate jdbcTemplate;

    public MovimientoInventarioRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Obtiene todos los movimientos de inventario registrados.
     *
     * @return lista de objetos MovimientoInventario
     */
    public List<MovimientoInventario> listarMovimientos() {
        String sql = "SELECT * FROM movimiento_inventario";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(MovimientoInventario.class));
    }

    /**
     * Busca un movimiento de inventario por su identificador único.
     *
     * @param id identificador del movimiento
     * @return objeto MovimientoInventario encontrado o null si no existe
     */
    public MovimientoInventario buscarPorId(int id) {
        String sql = "SELECT * FROM movimiento_inventario WHERE id_movimiento = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(MovimientoInventario.class), id);
        } catch (Exception e) {
            return null; // Manejo simple: retorna null si no se encuentra
        }
    }

    /**
     * Registra un nuevo movimiento de inventario en la base de datos.
     *
     * @param movimiento objeto MovimientoInventario con los datos a insertar
     */
    public void registrarMovimiento(MovimientoInventario movimiento) {
        String sql = "INSERT INTO movimiento_inventario (id_repuesto, tipo_movimiento, cantidad, fecha_movimiento, observaciones) " +
                "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                movimiento.getIdRepuesto(),
                movimiento.getTipoMovimiento(),
                movimiento.getCantidad(),
                movimiento.getFechaMovimiento(),
                movimiento.getObservaciones());
    }

    /**
     * Elimina un movimiento de inventario por su identificador.
     *
     * @param id identificador del movimiento a eliminar
     */
    public void eliminarMovimiento(int id) {
        String sql = "DELETE FROM movimiento_inventario WHERE id_movimiento = ?";
        jdbcTemplate.update(sql, id);
    }
}
