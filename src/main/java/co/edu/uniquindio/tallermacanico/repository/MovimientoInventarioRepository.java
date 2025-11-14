package co.edu.uniquindio.tallermacanico.repository;


import co.edu.uniquindio.tallermacanico.model.MovimientoInventario;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para gestionar los movimientos de inventario.
 */
@Repository
public class MovimientoInventarioRepository {

    private final JdbcTemplate jdbcTemplate;

    public MovimientoInventarioRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<MovimientoInventario> listarMovimientos() {
        String sql = "SELECT * FROM movimiento_inventario";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(MovimientoInventario.class));
    }

    public MovimientoInventario buscarPorId(int id) {
        String sql = "SELECT * FROM movimiento_inventario WHERE id_movimiento = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(MovimientoInventario.class), id);
    }

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

    public void eliminarMovimiento(int id) {
        String sql = "DELETE FROM movimiento_inventario WHERE id_movimiento = ?";
        jdbcTemplate.update(sql, id);
    }
}
