package co.edu.uniquindio.tallermacanico.repository;

import co.edu.uniquindio.tallermacanico.model.OrdenServicio;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para gestionar operaciones sobre la entidad OrdenServicio.
 */
@Repository
public class OrdenServicioRepository {

    private final JdbcTemplate jdbcTemplate;

    public OrdenServicioRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<OrdenServicio> listarOrdenesServicio() {
        String sql = "SELECT * FROM orden_servicio";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(OrdenServicio.class));
    }

    public OrdenServicio buscarPorId(int id) {
        String sql = "SELECT * FROM orden_servicio WHERE id_orden_servicio = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(OrdenServicio.class), id);
    }

    public void registrarOrdenServicio(OrdenServicio ordenServicio) {
        String sql = "INSERT INTO orden_servicio (id_orden_trabajo, id_servicio, estado, precio_final) " +
                "VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                ordenServicio.getIdOrdenTrabajo(),
                ordenServicio.getIdServicio(),
                ordenServicio.getEstado(),
                ordenServicio.getPrecioFinal());
    }

    public void eliminarOrdenServicio(int id) {
        String sql = "DELETE FROM orden_servicio WHERE id_orden_servicio = ?";
        jdbcTemplate.update(sql, id);
    }
}

