package co.edu.uniquindio.tallermacanico.repository;

import co.edu.uniquindio.tallermacanico.model.Supervision;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para gestionar supervisiones entre mecánicos en servicios específicos.
 */
@Repository
public class SupervisionRepository {

    private final JdbcTemplate jdbcTemplate;

    public SupervisionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Lista todas las supervisiones registradas.
     * @return lista de supervisiones
     */
    public List<Supervision> listarSupervisiones() {
        String sql = "SELECT * FROM supervision";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Supervision.class));
    }

    /**
     * Busca una supervisión específica por IDs compuestos.
     * @param idOrdenServicio ID del servicio
     * @param idSupervisor ID del mecánico supervisor
     * @param idSupervisado ID del mecánico supervisado
     * @return supervisión encontrada
     */
    public Supervision buscarSupervision(int idOrdenServicio, int idSupervisor, int idSupervisado) {
        String sql = "SELECT * FROM supervision WHERE id_orden_servicio = ? AND id_mecanico_supervisor = ? AND id_mecanico_supervisado = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Supervision.class),
                idOrdenServicio, idSupervisor, idSupervisado);
    }

    /**
     * Registra una nueva supervisión entre mecánicos.
     * @param supervision objeto con los datos de la supervisión
     */
    public void registrarSupervision(Supervision supervision) {
        String sql = "INSERT INTO supervision (id_orden_servicio, id_mecanico_supervisor, id_mecanico_supervisado, id_especialidad, observaciones) " +
                "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                supervision.getIdOrdenServicio(),
                supervision.getIdMecanicoSupervisor(),
                supervision.getIdMecanicoSupervisado(),
                supervision.getIdEspecialidad(),
                supervision.getObservaciones());
    }

    /**
     * Elimina una supervisión por IDs compuestos.
     * @param idOrdenServicio ID del servicio
     * @param idSupervisor ID del mecánico supervisor
     * @param idSupervisado ID del mecánico supervisado
     */
    public void eliminarSupervision(int idOrdenServicio, int idSupervisor, int idSupervisado) {
        String sql = "DELETE FROM supervision WHERE id_orden_servicio = ? AND id_mecanico_supervisor = ? AND id_mecanico_supervisado = ?";
        jdbcTemplate.update(sql, idOrdenServicio, idSupervisor, idSupervisado);
    }
}

