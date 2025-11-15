package co.edu.uniquindio.tallermacanico.repository;

import co.edu.uniquindio.tallermacanico.model.Usuario;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para gestionar operaciones sobre la tabla {@code usuarios}.
 * Aquí se definen las consultas SQL relacionadas con usuarios.
 */
@Repository
public class UsuarioRepository {

    private final JdbcTemplate jdbcTemplate;

    public UsuarioRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Busca un usuario por su nombre de usuario.
     *
     * @param username nombre de usuario
     * @return el objeto {@link Usuario} si existe, de lo contrario null
     */
    /**
     * Busca un usuario por su nombre de usuario.
     *
     * @param username nombre de usuario
     * @return el objeto {@link Usuario} si existe, de lo contrario null
     */
    public Usuario buscarPorUsername(String username) {
        String sql = "SELECT id_usuario AS idUsuario, username, password, correo, rol FROM usuarios WHERE username = ?";
        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    new BeanPropertyRowMapper<>(Usuario.class),
                    username
            );
        } catch (Exception e) {
            return null; // si no encuentra el usuario
        }
    }


    /**
     * Registra un nuevo usuario en la base de datos.
     *
     * @param usuario objeto con los datos del usuario
     */
    public void registrarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (username, password, correo, rol) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                usuario.getUsername(),
                usuario.getPassword(),
                usuario.getCorreo(),
                usuario.getRol());
    }

    /**
     * Elimina un usuario por su ID.
     *
     * @param idUsuario identificador del usuario
     */
    public void eliminarUsuario(Long idUsuario) {
        String sql = "DELETE FROM usuarios WHERE id_usuario = ?";
        jdbcTemplate.update(sql, idUsuario);
    }
}

