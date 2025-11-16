package co.edu.uniquindio.tallermacanico.repository;

import co.edu.uniquindio.tallermacanico.model.Mecanico;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

/**
 * Repositorio encargado de gestionar las operaciones CRUD sobre la tabla {@code mecanico}.
 * Utiliza {@link JdbcTemplate} para interactuar directamente con la base de datos.
 *
 * Proporciona métodos para registrar, consultar, listar y eliminar mecánicos del sistema.
 * Esta clase es utilizada por el controlador {@code MecanicoController} para exponer los endpoints REST.
 *
 * <p>Relaciones:</p>
 * - Puede estar referenciado por la tabla {@code mecanico_especialidad}, lo que puede impedir su eliminación directa.
 *
 * <p>Buenas prácticas:</p>
 * - Se recomienda validar las restricciones de integridad antes de eliminar un mecánico.
 * - Los errores de base de datos se capturan y manejan de forma segura para evitar excepciones no controladas.
 */
@Repository
public class MecanicoRepository {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Constructor que inyecta el {@link JdbcTemplate} para ejecutar consultas SQL.
     *
     * @param jdbcTemplate instancia de JdbcTemplate configurada para el origen de datos
     */
    public MecanicoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Consulta todos los mecánicos registrados en la base de datos.
     *
     * @return lista de objetos {@link Mecanico} con los datos obtenidos
     */
    public List<Mecanico> listarMecanicos() {
        String sql = "SELECT * FROM mecanico";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Mecanico.class));
    }

    /**
     * Inserta un nuevo mecánico en la base de datos.
     *
     * @param mecanico objeto {@link Mecanico} con los datos a registrar
     */
    public int registrarMecanico(Mecanico mecanico) {
        // Usamos la secuencia para generar el ID
        String sql = "INSERT INTO mecanico (id_mecanico, nombre, apellido, telefono, experiencia_anios) " +
                "VALUES (seq_mecanico.NEXTVAL, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                mecanico.getNombre(),
                mecanico.getApellido(),
                mecanico.getTelefono(),
                mecanico.getExperienciaAnios());

        // Recuperamos el último valor generado por la secuencia
        return jdbcTemplate.queryForObject("SELECT seq_mecanico.CURRVAL FROM dual", Integer.class);
    }


    /**
     * Busca un mecánico por su identificador único.
     *
     * @param id identificador del mecánico
     * @return objeto {@link Mecanico} si se encuentra, o {@code null} si no existe
     */
    public Mecanico buscarPorId(int id) {
        String sql = "SELECT * FROM mecanico WHERE id_mecanico = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Mecanico.class), id);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Elimina un mecánico de la base de datos por su identificador.
     * Si el mecánico está referenciado en otras tablas, la operación puede fallar por restricciones de integridad.
     *
     * @param id identificador del mecánico a eliminar
     * @return {@code true} si se eliminó correctamente, {@code false} si ocurrió un error o no se eliminó
     */
    public boolean eliminarMecanicoConRelaciones(int id) {
        try {
            jdbcTemplate.update("DELETE FROM mecanico_especialidad WHERE id_mecanico = ?", id);
            jdbcTemplate.update("DELETE FROM mecanico WHERE id_mecanico = ?", id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
