package co.edu.uniquindio.tallermacanico.repository;

import co.edu.uniquindio.tallermacanico.model.Repuesto;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

/**
 * Repositorio para gestionar operaciones sobre la tabla repuesto.
 * Proporciona métodos CRUD usando JdbcTemplate.
 */
@Repository
public class RepuestoRepository {

    private final JdbcTemplate jdbcTemplate;

    public RepuestoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Obtiene todos los repuestos registrados en la base de datos.
     *
     * @return lista de objetos Repuesto
     */
    public List<Repuesto> listarRepuestos() {
        String sql = "SELECT id_repuesto AS idRepuesto, nombre, descripcion, stock_actual AS stockActual, unidad_medida AS unidadMedida FROM repuesto";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Repuesto.class));
    }

    /**
     * Registra un nuevo repuesto en la base de datos.
     *
     * @param repuesto objeto Repuesto con los datos a insertar
     */

    public int registrarRepuesto(Repuesto repuesto) {
        String sql = "INSERT INTO repuesto (nombre, descripcion, stock_actual, unidad_medida) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id_repuesto"});
            ps.setString(1, repuesto.getNombre());
            ps.setString(2, repuesto.getDescripcion());
            ps.setDouble(3, repuesto.getStockActual());
            ps.setString(4, repuesto.getUnidadMedida());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue(); // 👈 ahora sí devuelve el ID
    }


    /**
     * Busca un repuesto por su identificador único.
     *
     * @param id identificador del repuesto
     * @return objeto Repuesto encontrado o null si no existe
     */
    public Repuesto buscarPorId(int id) {
        String sql = "SELECT id_repuesto AS idRepuesto, nombre, descripcion, stock_actual AS stockActual, unidad_medida AS unidadMedida FROM repuesto WHERE id_repuesto = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Repuesto.class), id);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Actualiza el stock de un repuesto.
     *
     * @param idRepuesto identificador del repuesto
     * @param nuevoStock nuevo valor de stock
     */
    public void actualizarStock(int idRepuesto, double nuevoStock) {
        String sql = "UPDATE repuesto SET stock_actual = ? WHERE id_repuesto = ?";
        jdbcTemplate.update(sql, nuevoStock, idRepuesto);
    }

    /**
     * Elimina un repuesto de la base de datos por su identificador.
     *
     * @param id identificador del repuesto a eliminar
     */
    public void eliminarRepuesto(int id) {
        String sql = "DELETE FROM repuesto WHERE id_repuesto = ?";
        jdbcTemplate.update(sql, id);
    }
}
