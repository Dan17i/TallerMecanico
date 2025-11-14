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

    public void registrarMecanico(Mecanico mecanico) {
        String sql = "INSERT INTO mecanico (nombre, apellido, telefono, experiencia_anios) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                mecanico.getNombre(),
                mecanico.getApellido(),
                mecanico.getTelefono(),
                mecanico.getExperienciaAnios());
    }

    public Mecanico buscarPorId(int id) {
        String sql = "SELECT * FROM mecanico WHERE id_mecanico = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Mecanico.class), id);
    }

    public void eliminarMecanico(int id) {
        String sql = "DELETE FROM mecanico WHERE id_mecanico = ?";
        jdbcTemplate.update(sql, id);
    }
}
