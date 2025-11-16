package co.edu.uniquindio.tallermacanico.repository;

import co.edu.uniquindio.tallermacanico.model.Supervision;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para gestionar operaciones sobre la tabla supervision.
 * Permite registrar, consultar y eliminar supervisiones entre mecánicos en servicios específicos.
 */
@Repository
public class SupervisionRepository {

    private final JdbcTemplate jdbcTemplate;

    public SupervisionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Lista todas las supervisiones registradas en la base de datos.
     *
     * @return lista de objetos Supervision
     */
    public List<Supervision> listarSupervisiones() {
        String sql = "SELECT id_orden_servicio AS idOrdenServicio, " +
                "id_mecanico_supervisor AS idMecanicoSupervisor, " +
                "id_mecanico_supervisado AS idMecanicoSupervisado, " +
                "id_especialidad AS idEspecialidad, " +
                "observaciones " +
                "FROM supervision";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Supervision.class));
    }

    /**
     * Busca una supervisión específica por IDs compuestos.
     *
     * @param idOrdenServicio identificador de la orden de servicio
     * @param idSupervisor identificador del mecánico supervisor
     * @param idSupervisado identificador del mecánico supervisado
     * @return objeto Supervision encontrado o null si no existe
     */
    public Supervision buscarSupervision(int idOrdenServicio, int idSupervisor, int idSupervisado) {
        String sql = "SELECT id_orden_servicio AS idOrdenServicio, " +
                "id_mecanico_supervisor AS idMecanicoSupervisor, " +
                "id_mecanico_supervisado AS idMecanicoSupervisado, " +
                "id_especialidad AS idEspecialidad, " +
                "observaciones " +
                "FROM supervision WHERE id_orden_servicio = ? AND id_mecanico_supervisor = ? AND id_mecanico_supervisado = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Supervision.class),
                    idOrdenServicio, idSupervisor, idSupervisado);
        } catch (Exception e) {
            return null; // Manejo simple: retorna null si no se encuentra
        }
    }

    /**
     * Registra una nueva supervisión entre mecánicos.
     *
     * @param supervision objeto Supervision con los datos a insertar
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
     *
     * @param idOrdenServicio identificador de la orden de servicio
     * @param idSupervisor identificador del mecánico supervisor
     * @param idSupervisado identificador del mecánico supervisado
     */
    public void eliminarSupervision(int idOrdenServicio, int idSupervisor, int idSupervisado) {
        String sql = "DELETE FROM supervision WHERE id_orden_servicio = ? AND id_mecanico_supervisor = ? AND id_mecanico_supervisado = ?";
        jdbcTemplate.update(sql, idOrdenServicio, idSupervisor, idSupervisado);
    }
}
