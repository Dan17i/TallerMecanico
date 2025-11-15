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
    private Long idUsuario;
    private String username;
    private String password;
    private String correo;
    // El rol no se guarda en la base, siempre es ADMIN
    public String getRol() {
        return "ADMIN";
    }
}


