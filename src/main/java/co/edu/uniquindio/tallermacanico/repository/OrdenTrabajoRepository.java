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

    public List<OrdenTrabajo> listarOrdenes() {
        String sql = "SELECT * FROM orden_trabajo";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(OrdenTrabajo.class));
    }
}
