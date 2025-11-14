package co.edu.uniquindio.tallermacanico.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para consultar estados de orden.
 */
@Repository
public class EstadoOrdenRepository {

    private final JdbcTemplate jdbcTemplate;

    public EstadoOrdenRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String obtenerNombrePorId(int idEstadoOrden) {
        String sql = "SELECT nombre_estado FROM estado_orden WHERE id_estado_orden = ?";
        return jdbcTemplate.queryForObject(sql, String.class, idEstadoOrden);
    }
}

