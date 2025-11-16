package co.edu.uniquindio.tallermacanico.repository;

import co.edu.uniquindio.tallermacanico.model.Servicio;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para gestionar operaciones sobre la tabla servicio.
 * Proporciona métodos CRUD usando JdbcTemplate.
 */
@Repository
public class ServicioRepository {

    private final JdbcTemplate jdbcTemplate;

    public ServicioRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Obtiene todos los servicios registrados en la base de datos.
     *
     * @return lista de objetos Servicio
     */
    public List<Servicio> listarServicios() {
        String sql = "SELECT id_servicio AS idServicio, nombre, descripcion, precio_base AS precioBase FROM servicio";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Servicio.class));
    }

    /**
     * Busca un servicio por su identificador único.
     *
     * @param id identificador del servicio
     * @return objeto Servicio encontrado o null si no existe
     */
    public Servicio buscarPorId(int id) {
        String sql = "SELECT id_servicio AS idServicio, nombre, descripcion, precio_base AS precioBase FROM servicio WHERE id_servicio = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Servicio.class), id);
        } catch (Exception e) {
            return null; // Manejo simple: retorna null si no se encuentra
        }
    }

    /**
     * Registra un nuevo servicio en la base de datos.
     *
     * @param servicio objeto Servicio con los datos a insertar
     */
    public void registrarServicio(Servicio servicio) {
        String sql = "INSERT INTO servicio (nombre, descripcion, precio_base) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql,
                servicio.getNombre(),
                servicio.getDescripcion(),
                servicio.getPrecioBase());
    }

    /**
     * Elimina un servicio de la base de datos por su identificador.
     *
     * @param id identificador del servicio a eliminar
     */
    public void eliminarServicio(int id) {
        String sql = "DELETE FROM servicio WHERE id_servicio = ?";
        jdbcTemplate.update(sql, id);
    }

    /**
     * Actualiza el precio base de un servicio.
     *
     * @param idServicio identificador del servicio
     * @param nuevoPrecio nuevo valor para el precio base
     */
    public void actualizarPrecioBase(int idServicio, double nuevoPrecio) {
        String sql = "UPDATE servicio SET precio_base = ? WHERE id_servicio = ?";
        jdbcTemplate.update(sql, nuevoPrecio, idServicio);
    }
}

