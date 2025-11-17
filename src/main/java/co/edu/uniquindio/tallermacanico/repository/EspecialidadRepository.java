package co.edu.uniquindio.tallermacanico.repository;

import co.edu.uniquindio.tallermacanico.model.Especialidad;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para gestionar las especialidades de mecánicos.
 */
@Repository
public class EspecialidadRepository {

    private final JdbcTemplate jdbcTemplate;

    public EspecialidadRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Listar todas las especialidades
    public List<Especialidad> listarEspecialidades() {
        String sql = "SELECT id_especialidad AS idEspecialidad, nombre FROM especialidad";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Especialidad.class));
    }

    // Buscar especialidad por ID
    public Especialidad buscarPorId(int id) {
        String sql = "SELECT id_especialidad AS idEspecialidad, nombre FROM especialidad WHERE id_especialidad = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Especialidad.class), id);
        } catch (Exception e) {
            return null;
        }
    }

    // Registrar nueva especialidad y devolver ID generado
    public int registrarEspecialidad(Especialidad especialidad) {
        String sql = "INSERT INTO especialidad (nombre) VALUES (?)";
        jdbcTemplate.update(sql, especialidad.getNombre());
        return jdbcTemplate.queryForObject("SELECT MAX(id_especialidad) FROM especialidad", Integer.class);
    }

    // Eliminar especialidad por ID
    public boolean eliminarEspecialidad(int id) {
        String sql = "DELETE FROM especialidad WHERE id_especialidad = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }
}
