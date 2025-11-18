package co.edu.uniquindio.tallermacanico.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TestConexion {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void probarConexion() {
        try {
            // 1️⃣ Prueba básica de conexión
            String resultado = jdbcTemplate.queryForObject(
                    "SELECT 'Conexión exitosa con Oracle' FROM dual",
                    String.class
            );
            System.out.println("✅ " + resultado);

            // 2️⃣ Esquema activo
            String esquema = jdbcTemplate.queryForObject(
                    "SELECT SYS_CONTEXT('USERENV','CURRENT_SCHEMA') FROM dual",
                    String.class
            );
            System.out.println("📦 Esquema activo: " + esquema);

            // 3️⃣ Nombre de la base de datos
            String baseDatos = jdbcTemplate.queryForObject(
                    "SELECT name FROM v$database",
                    String.class
            );
            System.out.println("🗄️ Base de datos activa: " + baseDatos);

            // 4️⃣ Conteo de clientes
            Integer totalClientes = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM CLIENTE",
                    Integer.class
            );
            System.out.println("👥 Clientes registrados: " + totalClientes);


        } catch (Exception e) {
            System.err.println("❌ Error al probar conexión o consulta:");
            e.printStackTrace();
        }
    }
}

