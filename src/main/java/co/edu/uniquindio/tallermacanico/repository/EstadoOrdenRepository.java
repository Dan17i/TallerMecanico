package co.edu.uniquindio.tallermacanico.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para gestionar operaciones sobre la tabla estado_orden.
 * Permite consultar información relacionada con los estados de las órdenes.
 */
@Repository
public class EstadoOrdenRepository {

    private final JdbcTemplate jdbcTemplate;

    public EstadoOrdenRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Obtiene el nombre del estado de una orden dado su identificador.
     *
     * @param idEstadoOrden identificador único del estado de la orden
     * @return nombre del estado como String, o null si no existe
     */
    public String obtenerNombrePorId(int idEstadoOrden) {
        String sql = "SELECT nombre_estado FROM estado_orden WHERE id_estado_orden = ?";
        try {
            return jdbcTemplate.queryForObject(sql, String.class, idEstadoOrden);
        } catch (Exception e) {
            // Manejo simple: si no existe, retorna null
            return null;
        }
    }
}


