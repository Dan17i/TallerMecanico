package co.edu.uniquindio.tallermacanico.repository;

import co.edu.uniquindio.tallermacanico.model.OrdenServicioMecanico;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para gestionar asignaciones de mecánicos a servicios.
 */
@Repository
public class OrdenServicioMecanicoRepository {

    private final JdbcTemplate jdbcTemplate;

    public OrdenServicioMecanicoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<OrdenServicioMecanico> listarAsignaciones() {
        String sql = "SELECT * FROM orden_servicio_mecanico";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(OrdenServicioMecanico.class));
    }

    public OrdenServicioMecanico buscarPorOrdenYPorMecanico(int idOrdenServicio, int idMecanico) {
        String sql = "SELECT * FROM orden_servicio_mecanico WHERE id_orden_servicio = ? AND id_mecanico = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(OrdenServicioMecanico.class), idOrdenServicio, idMecanico);
    }

    public void registrarAsignacion(OrdenServicioMecanico asignacion) {
        String sql = "INSERT INTO orden_servicio_mecanico (id_orden_servicio, id_mecanico, id_especialidad, rol_en_servicio) " +
                "VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                asignacion.getIdOrdenServicio(),
                asignacion.getIdMecanico(),
                asignacion.getIdEspecialidad(),
                asignacion.getRolEnServicio());
    }

    public void eliminarAsignacion(int idOrdenServicio, int idMecanico) {
        String sql = "DELETE FROM orden_servicio_mecanico WHERE id_orden_servicio = ? AND id_mecanico = ?";
        jdbcTemplate.update(sql, idOrdenServicio, idMecanico);
    }
}

