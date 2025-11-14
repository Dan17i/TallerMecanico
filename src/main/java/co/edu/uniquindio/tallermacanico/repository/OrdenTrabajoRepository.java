package co.edu.uniquindio.tallermacanico.repository;

import co.edu.uniquindio.tallermacanico.model.OrdenTrabajo;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrdenTrabajoRepository {

    private final JdbcTemplate jdbcTemplate;

    public OrdenTrabajoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Lista todas las órdenes de trabajo registradas en la base de datos.
     * @return lista de órdenes de trabajo
     */
    public List<OrdenTrabajo> listarOrdenesTrabajo() {
        String sql = "SELECT * FROM orden_trabajo";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(OrdenTrabajo.class));
    }

    /**
     * Busca una orden de trabajo por su ID.
     * @param id identificador de la orden
     * @return orden encontrada
     */
    public OrdenTrabajo buscarPorId(int id) {
        String sql = "SELECT * FROM orden_trabajo WHERE id_orden_trabajo = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(OrdenTrabajo.class), id);
    }

    /**
     * Registra una nueva orden de trabajo en la base de datos.
     * @param orden objeto con los datos de la orden
     */
    public void registrarOrdenTrabajo(OrdenTrabajo orden) {
        String sql = "INSERT INTO orden_trabajo (id_vehiculo, fecha_ingreso, fecha_salida, diagnostico_inicial, id_estado_orden) " +
                "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                orden.getIdVehiculo(),
                orden.getFechaIngreso(),
                orden.getFechaSalida(),
                orden.getDiagnosticoInicial(),
                orden.getIdEstadoOrden());
    }

    /**
     * Elimina una orden de trabajo por su ID.
     * @param id identificador de la orden
     */
    public void eliminarOrdenTrabajo(int id) {
        String sql = "DELETE FROM orden_trabajo WHERE id_orden_trabajo = ?";
        jdbcTemplate.update(sql, id);
    }
}
