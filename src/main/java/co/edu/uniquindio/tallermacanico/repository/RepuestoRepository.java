package co.edu.uniquindio.tallermacanico.repository;

import co.edu.uniquindio.tallermacanico.model.Repuesto;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepuestoRepository {

    private final JdbcTemplate jdbcTemplate;

    public RepuestoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Repuesto> listarRepuestos() {
        String sql = "SELECT * FROM repuesto";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Repuesto.class));
    }
}
