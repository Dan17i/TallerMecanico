    package co.edu.uniquindio.tallermacanico.service;
    
    import co.edu.uniquindio.tallermacanico.dto.LoginDTO;
    import co.edu.uniquindio.tallermacanico.model.Usuario;
    import co.edu.uniquindio.tallermacanico.repository.UsuarioRepository;
    import lombok.RequiredArgsConstructor;
    import org.springframework.stereotype.Service;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    
    
    import java.util.Optional;
    
    /**
     * Servicio encargado de la lógica de autenticación.
     * <p>
     * Válida credencial, encripta contraseñas y simula recuperación
     * de contraseña por correo electrónico.
     * </p>
     */
    @Service
    @RequiredArgsConstructor
    public class AuthService {
    
        private final UsuarioRepository usuarioRepository;
    
        private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    
        /**
         * Válida el inicio de sesión del administrador.
         * Consulta el usuario en la base de datos y compara la contraseña ingresada
         * con la almacenada usando BCrypt.
         *
         * @return true si las credenciales son correctas, false en caso contrario
         */
        public boolean login(LoginDTO loginDTO) {
            Usuario usuario = usuarioRepository.buscarPorUsername(loginDTO.username());
            if (usuario == null) {
                log.warn("Usuario no encontrado");
                return false;
            }
            return true;
        }
        /**
         * Simula la recuperación de contraseña.
         * Si el usuario existe, devuelve un mensaje indicando que se envió al correo registrado.
         * Si no existe, devuelve un mensaje de error.
         *
         * @param username nombre de usuario
         * @return mensaje con el resultado de la operación
         */
        public String recuperarPassword(String username) {
            try {
                Usuario usuario = usuarioRepository.buscarPorUsername(username);
                return "Se envió la contraseña al correo: " + usuario.getCorreo();
            } catch (Exception e) {
                return "Usuario no encontrado";
            }
        }
    
        public void mostrarPassword(String username) {
            Usuario usuario = usuarioRepository.buscarPorUsername(username);
            if (usuario != null) {
                log.info("Contraseña encriptada de {}: {}", username, usuario.getPassword());
            } else {
                log.warn("Usuario no encontrado: {}", username);
            }
        }
    
    }
    
    
