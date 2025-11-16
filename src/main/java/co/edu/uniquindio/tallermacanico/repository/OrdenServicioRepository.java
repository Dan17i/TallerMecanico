package co.edu.uniquindio.tallermacanico.repository;

import co.edu.uniquindio.tallermacanico.model.OrdenServicio;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para gestionar operaciones sobre la tabla orden_servicio.
 * Proporciona métodos CRUD usando JdbcTemplate.
 */
@Repository
public class OrdenServicioRepository {

    private final JdbcTemplate jdbcTemplate;

    public OrdenServicioRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Obtiene todas las órdenes de servicio registradas.
     *
     * @return lista de objetos OrdenServicio
     */
    public List<OrdenServicio> listarOrdenesServicio() {
        String sql = "SELECT * FROM orden_servicio";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(OrdenServicio.class));
    }

    /**
     * Busca una orden de servicio por su identificador único.
     *
     * @param id identificador de la orden de servicio
     * @return objeto OrdenServicio encontrado o null si no existe
     */
    public OrdenServicio buscarPorId(int id) {
        String sql = "SELECT * FROM orden_servicio WHERE id_orden_servicio = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(OrdenServicio.class), id);
        } catch (Exception e) {
            return null; // Manejo simple: retorna null si no se encuentra
        }
    }

    /**
     * Registra una nueva orden de servicio en la base de datos.
     *
     * @param ordenServicio objeto OrdenServicio con los datos a insertar
     */
    public void registrarOrdenServicio(OrdenServicio ordenServicio) {
        String sql = "INSERT INTO orden_servicio (id_orden_trabajo, id_servicio, estado, precio_final) " +
                "VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                ordenServicio.getIdOrdenTrabajo(),
                ordenServicio.getIdServicio(),
                ordenServicio.getEstado(),
                ordenServicio.getPrecioFinal());
    }

    /**
     * Elimina una orden de servicio de la base de datos por su identificador.
     *
     * @param id identificador de la orden de servicio a eliminar
     */
    public void eliminarOrdenServicio(int id) {
        String sql = "DELETE FROM orden_servicio WHERE id_orden_servicio = ?";
        jdbcTemplate.update(sql, id);
    }
}
