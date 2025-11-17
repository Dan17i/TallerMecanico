package co.edu.uniquindio.tallermacanico.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa a un usuario del sistema.
 * <p>
 * Se mapea a la tabla {@code usuarios} en la base de datos Oracle.
 * Incluye credenciales y rol para el manejo de autenticación.
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    private Long idUsuario;   // PK autogenerada
    private String username;  // nombre de usuario único
    private String password;  // contraseña encriptada
    private String correo;    // correo único
    private String rol;       // rol del usuario (por defecto ADMIN en BD)
}