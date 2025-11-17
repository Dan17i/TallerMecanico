package co.edu.uniquindio.co.demo;

// Importaciones necesarias
import co.edu.uniquindio.tallermacanico.dto.LoginDTO;
import co.edu.uniquindio.tallermacanico.model.Usuario;
import co.edu.uniquindio.tallermacanico.repository.UsuarioRepository;
import co.edu.uniquindio.tallermacanico.service.AuthService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests Unitarios para AuthService (Admin Quemado)")
public class AuthServiceTest {

    // Simula la dependencia para aislar el servicio
    @Mock
    private UsuarioRepository usuarioRepository;

    // Inyecta el mock en el servicio a probar
    @InjectMocks
    private AuthService authService;

    private Usuario adminUsuario;
    private LoginDTO loginExitosoDTO;

    // -- DATOS DEL ÚNICO USUARIO QUEMADO --
    private final String USERNAME = "admin";
    private final String PASSWORD = "admin123";
    private final String CORREO = "admin@motorplus.com";

    @BeforeEach
    void setUp() {
        // EL PROBLEMA ESTABA AQUÍ: Se corrige el constructor para que coincida con @AllArgsConstructor
        // Constructor requerido: (Long idUsuario, String username, String password, String correo, String rol)
        adminUsuario = new Usuario(
                1L,                         // idUsuario (Long)
                USERNAME,                   // username
                PASSWORD,                   // password
                CORREO,                     // correo
                "ADMIN"                     // rol
        );

        // DTO con credenciales correctas para el login
        loginExitosoDTO = new LoginDTO(USERNAME, PASSWORD);
    }

    // -----------------------------------------------------------------
    // Tests para login(LoginDTO loginDTO)
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería retornar TRUE si el admin usa las credenciales correctas")
    void testLogin_CredencialesCorrectas() {
        // Simular que el repositorio encuentra el ADMIN
        when(usuarioRepository.buscarPorUsername(USERNAME)).thenReturn(adminUsuario);

        // Ejecutar y Verificar
        boolean resultado = authService.login(loginExitosoDTO);
        assertTrue(resultado, "El login del admin debe ser exitoso");
        verify(usuarioRepository, times(1)).buscarPorUsername(USERNAME);
    }

    @Test
    @DisplayName("Debería retornar FALSE con contraseña incorrecta")
    void testLogin_ContrasenaIncorrecta() {
        LoginDTO loginIncorrecto = new LoginDTO(USERNAME, "claveIncorrecta");

        // Simular que el repositorio encuentra el ADMIN, pero la contraseña no coincide
        when(usuarioRepository.buscarPorUsername(USERNAME)).thenReturn(adminUsuario);

        // Ejecutar y Verificar
        boolean resultado = authService.login(loginIncorrecto);
        assertFalse(resultado, "El login debe fallar si la contraseña no coincide");
        verify(usuarioRepository, times(1)).buscarPorUsername(USERNAME);
    }

    @Test
    @DisplayName("Debería retornar FALSE si el usuario buscado no es el admin quemado")
    void testLogin_UsuarioNoExiste() {
        final String USUARIO_INEXISTENTE = "otroUser";
        LoginDTO loginInexistente = new LoginDTO(USUARIO_INEXISTENTE, PASSWORD);

        // Simular que el repositorio retorna null para cualquier otro usuario
        when(usuarioRepository.buscarPorUsername(USUARIO_INEXISTENTE)).thenReturn(null);

        // Ejecutar y Verificar
        boolean resultado = authService.login(loginInexistente);
        assertFalse(resultado, "El login debe fallar si el usuario no es el admin y no existe");
        verify(usuarioRepository, times(1)).buscarPorUsername(USUARIO_INEXISTENTE);
    }

    // -----------------------------------------------------------------
    // Tests para recuperarPassword(String username)
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería retornar mensaje con el correo del admin para recuperación")
    void testRecuperarPassword_AdminExiste() {
        // Simular que el repositorio encuentra al ADMIN
        when(usuarioRepository.buscarPorUsername(USERNAME)).thenReturn(adminUsuario);

        // Ejecutar y Verificar
        String mensaje = authService.recuperarPassword(USERNAME);
        String esperado = "Se envió la contraseña al correo: " + CORREO;
        assertEquals(esperado, mensaje, "El mensaje debe indicar que se envió el correo al admin");
    }

    @Test
    @DisplayName("Debería retornar 'Usuario no encontrado' si se busca otro usuario")
    void testRecuperarPassword_OtroUsuario() {
        final String OTRO_USUARIO = "guest";

        // Simular que el repositorio retorna null (ya que el admin es el único)
        when(usuarioRepository.buscarPorUsername(OTRO_USUARIO)).thenReturn(null);

        // Ejecutar y Verificar
        String mensaje = authService.recuperarPassword(OTRO_USUARIO);
        assertEquals("Usuario no encontrado", mensaje, "Debe indicar que no se encontró el usuario");
    }
}