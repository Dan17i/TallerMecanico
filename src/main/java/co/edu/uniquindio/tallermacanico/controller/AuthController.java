package co.edu.uniquindio.tallermacanico.controller;

import co.edu.uniquindio.tallermacanico.dto.LoginDTO;
import co.edu.uniquindio.tallermacanico.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para la autenticación.
 * Expone endpoints para iniciar sesión y recuperar contraseña.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * Inicia sesión del administrador.
     *
     * @return mensaje de éxito o error
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        System.out.println("aca bien");

        if (authService.login(loginDTO)) {
            System.out.println("aca bien");
            return ResponseEntity.ok("Login exitoso como ADMIN");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
    }

    /**
     * Recupera la contraseña del usuario.
     *
     * @param username nombre de usuario
     * @return mensaje con el resultado de la operación
     */
    @GetMapping("/recuperar")
    public ResponseEntity<String> recuperar(@RequestParam String username) {
        String mensaje = authService.recuperarPassword(username);
        return ResponseEntity.ok(mensaje);
    }
}
