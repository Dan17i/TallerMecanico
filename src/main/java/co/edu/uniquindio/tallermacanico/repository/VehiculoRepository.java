package co.edu.uniquindio.tallermacanico.repository;
import co.edu.uniquindio.tallermacanico.model.Vehiculo;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para gestionar operaciones sobre la tabla {@code vehiculo}.
 * Proporciona métodos CRUD usando JdbcTemplate.
 */
@Repository
public class VehiculoRepository {

    private final JdbcTemplate jdbcTemplate;

    public VehiculoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Obtiene todos los vehículos registrados en la base de datos.
     *
     * @return lista de objetos Vehiculo
     */
    public List<Vehiculo> listarVehiculos() {
        String sql = "SELECT id_vehiculo AS idVehiculo, id_cliente AS idCliente, placa, marca, modelo, anio, color FROM vehiculo";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Vehiculo.class));
    }

    /**
     * Registra un nuevo vehículo en la base de datos.
     *
     * @param vehiculo objeto Vehiculo con los datos a insertar
     */
    public void registrarVehiculo(Vehiculo vehiculo) {
        String sql = "INSERT INTO vehiculo (id_cliente, placa, marca, modelo, anio, color) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                vehiculo.getIdCliente(),
                vehiculo.getPlaca(),
                vehiculo.getMarca(),
                vehiculo.getModelo(),
                vehiculo.getAnio(),
                vehiculo.getColor());
    }

    /**
     * Busca un vehículo por su identificador único.
     *
     * @param id identificador del vehículo
     * @return objeto Vehiculo encontrado o null si no existe
     */
    public Vehiculo buscarPorId(int id) {
        String sql = "SELECT id_vehiculo AS idVehiculo, id_cliente AS idCliente, placa, marca, modelo, anio, color FROM vehiculo WHERE id_vehiculo = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Vehiculo.class), id);
        } catch (Exception e) {
            return null; // Manejo simple: retorna null si no se encuentra
        }
    }

    /**
     * Elimina un vehículo de la base de datos por su identificador.
     *
     * @param id identificador del vehículo a eliminar
     */
    public void eliminarVehiculo(int id) {
        String sql = "DELETE FROM vehiculo WHERE id_vehiculo = ?";
        jdbcTemplate.update(sql, id);
    }
}
