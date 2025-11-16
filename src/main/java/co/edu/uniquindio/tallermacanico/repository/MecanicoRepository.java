package co.edu.uniquindio.tallermacanico.repository;

import co.edu.uniquindio.tallermacanico.model.Mecanico;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para gestionar operaciones sobre la tabla Mecanico.
 * Proporciona métodos CRUD usando JdbcTemplate.
 */
@Repository
public class MecanicoRepository {

    private final JdbcTemplate jdbcTemplate;

    public MecanicoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Obtiene todos los mecánicos registrados en la base de datos.
     *
     * @return lista de objetos Mecanico
     */
    public List<Mecanico> listarMecanicos() {
        String sql = "SELECT * FROM mecanico";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Mecanico.class));
    }

    /**
     * Registra un nuevo mecánico en la base de datos.
     *
     * @param mecanico objeto Mecanico con los datos a insertar
     */
    public void registrarMecanico(Mecanico mecanico) {
        String sql = "INSERT INTO mecanico (nombre, apellido, telefono, experiencia_anios) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                mecanico.getNombre(),
                mecanico.getApellido(),
                mecanico.getTelefono(),
                mecanico.getExperienciaAnios());
    }

    /**
     * Busca un mecánico por su identificador único.
     *
     * @param id identificador del mecánico
     * @return objeto Mecanico encontrado o null si no existe
     */
    public Mecanico buscarPorId(int id) {
        String sql = "SELECT * FROM mecanico WHERE id_mecanico = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Mecanico.class), id);
        } catch (Exception e) {
            return null; // Manejo simple: retorna null si no se encuentra
        }
    }

    /**
     * Elimina un mecánico de la base de datos por su identificador.
     *
     * @param id identificador del mecánico a eliminar
     */
    public void eliminarMecanico(int id) {
        String sql = "DELETE FROM mecanico WHERE id_mecanico = ?";
        jdbcTemplate.update(sql, id);
    }
}
