package co.edu.uniquindio.tallermacanico.repository;

import co.edu.uniquindio.tallermacanico.model.Mecanico;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MecanicoRepository {

    private final JdbcTemplate jdbcTemplate;

    public MecanicoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Mecanico> listarMecanicos() {
        String sql = "SELECT * FROM mecanico";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Mecanico.class));
    }
}
