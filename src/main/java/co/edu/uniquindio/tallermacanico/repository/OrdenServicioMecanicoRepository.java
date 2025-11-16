package co.edu.uniquindio.tallermacanico.repository;

import co.edu.uniquindio.tallermacanico.model.OrdenServicioMecanico;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para gestionar asignaciones de mecánicos a servicios.
 * Proporciona métodos CRUD usando JdbcTemplate.
 */
@Repository
public class OrdenServicioMecanicoRepository {

    private final JdbcTemplate jdbcTemplate;

    public OrdenServicioMecanicoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Obtiene todas las asignaciones de mecánicos a servicios.
     *
     * @return lista de objetos OrdenServicioMecanico
     */
    public List<OrdenServicioMecanico> listarAsignaciones() {
        String sql = "SELECT * FROM orden_servicio_mecanico";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(OrdenServicioMecanico.class));
    }

    /**
     * Busca una asignación específica por orden de servicio y mecánico.
     *
     * @param idOrdenServicio identificador de la orden de servicio
     * @param idMecanico identificador del mecánico
     * @return objeto OrdenServicioMecanico encontrado o null si no existe
     */
    public OrdenServicioMecanico buscarPorOrdenYPorMecanico(int idOrdenServicio, int idMecanico) {
        String sql = "SELECT * FROM orden_servicio_mecanico WHERE id_orden_servicio = ? AND id_mecanico = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(OrdenServicioMecanico.class), idOrdenServicio, idMecanico);
        } catch (Exception e) {
            return null; // Manejo simple: retorna null si no se encuentra
        }
    }

    /**
     * Registra una nueva asignación de mecánico a servicio.
     *
     * @param asignacion objeto OrdenServicioMecanico con los datos a insertar
     */
    public void registrarAsignacion(OrdenServicioMecanico asignacion) {
        String sql = "INSERT INTO orden_servicio_mecanico (id_orden_servicio, id_mecanico, id_especialidad, rol_en_servicio) " +
                "VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                asignacion.getIdOrdenServicio(),
                asignacion.getIdMecanico(),
                asignacion.getIdEspecialidad(),
                asignacion.getRolEnServicio());
    }

    /**
     * Elimina una asignación de mecánico a servicio.
     *
     * @param idOrdenServicio identificador de la orden de servicio
     * @param idMecanico identificador del mecánico
     */
    public void eliminarAsignacion(int idOrdenServicio, int idMecanico) {
        String sql = "DELETE FROM orden_servicio_mecanico WHERE id_orden_servicio = ? AND id_mecanico = ?";
        jdbcTemplate.update(sql, idOrdenServicio, idMecanico);
    }
}
