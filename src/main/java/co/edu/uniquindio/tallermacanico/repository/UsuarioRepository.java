package co.edu.uniquindio.tallermacanico.repository;

import co.edu.uniquindio.tallermacanico.model.Usuario;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para gestionar operaciones sobre la tabla {@code usuarios}.
 * Proporciona métodos CRUD básicos para la entidad Usuario usando JdbcTemplate.
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
    public Usuario buscarPorUsername(String username) {
        String sql = "SELECT id_usuario AS idUsuario, username, password, correo, rol FROM usuarios WHERE username = ?";
        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    new BeanPropertyRowMapper<>(Usuario.class),
                    username
            );
        } catch (Exception e) {
            return null; // Manejo simple: retorna null si no se encuentra
        }
    }

    /**
     * Registra un nuevo usuario en la base de datos.
     *
     * @param usuario objeto Usuario con los datos a insertar
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
     * Elimina un usuario de la base de datos por su identificador.
     *
     * @param idUsuario identificador del usuario a eliminar
     */
    public void eliminarUsuario(Long idUsuario) {
        String sql = "DELETE FROM usuarios WHERE id_usuario = ?";
        jdbcTemplate.update(sql, idUsuario);
    }
}
