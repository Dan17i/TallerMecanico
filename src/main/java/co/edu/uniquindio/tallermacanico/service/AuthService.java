package co.edu.uniquindio.tallermacanico.service;

import co.edu.uniquindio.tallermacanico.dto.LoginDTO;
import co.edu.uniquindio.tallermacanico.model.Usuario;
import co.edu.uniquindio.tallermacanico.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servicio encargado de la lógica de autenticación.
 * <p>
 * En este caso solo se maneja un usuario administrador
 * con datos quemados en la base de datos.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    /**
     * Valida el inicio de sesión del administrador.
     * Consulta el usuario en la base de datos y compara la contraseña ingresada
     * con la almacenada en texto plano (solo para datos quemados).
     *
     * @param loginDTO objeto con username y password ingresados
     * @return true si las credenciales son correctas, false en caso contrario
     */
    public boolean login(LoginDTO loginDTO) {
        Usuario usuario = usuarioRepository.buscarPorUsername(loginDTO.username());
        if (usuario == null) {
            log.warn("Usuario no encontrado: {}", loginDTO.username());
            return false;
        }
        boolean matches = loginDTO.password().equals(usuario.getPassword());
        if (!matches) {
            log.warn("Contraseña incorrecta para usuario: {}", loginDTO.username());
        }
        return matches;
    }

    /**
     * Simula la recuperación de contraseña.
     * En este caso solo devuelve un mensaje indicando que se envió al correo registrado.
     *
     * @param username nombre de usuario
     * @return mensaje con el resultado de la operación
     */
    public String recuperarPassword(String username) {
        Usuario usuario = usuarioRepository.buscarPorUsername(username);
        if (usuario != null) {
            return "Se envió la contraseña al correo: " + usuario.getCorreo();
        }
        return "Usuario no encontrado";
    }
}
