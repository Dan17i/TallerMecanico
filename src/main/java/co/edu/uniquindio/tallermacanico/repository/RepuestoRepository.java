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
 * Repositorio para gestionar operaciones CRUD sobre la tabla {@code repuesto}.
 * Utiliza JdbcTemplate para interactuar con la base de datos Oracle.
 */
@Repository
public class RepuestoRepository {

    private final JdbcTemplate jdbcTemplate;

    public RepuestoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Lista todos los repuestos registrados en la base de datos.
     * <p>
     * Nota: Utiliza alias (AS) para mapear {@code id_repuesto}, {@code stock_actual}
     * y {@code unidad_medida} a los campos camelCase del DTO/Model.
     * </p>
     *
     * @return lista de objetos {@link Repuesto}
     */
    public List<Repuesto> listarRepuestos() {
        String sql = "SELECT id_repuesto AS idRepuesto, nombre, descripcion, stock_actual AS stockActual, unidad_medida AS unidadMedida FROM repuesto";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Repuesto.class));
    }

    /**
     * Registra un nuevo repuesto en la base de datos y recupera el ID generado.
     * (Método consolidado a partir de {@code crearRepuesto} y {@code registrarRepuesto}).
     *
     * @param repuesto objeto {@link Repuesto} con los datos a insertar
     * @return ID generado del nuevo repuesto
     */
    public int crearRepuesto(Repuesto repuesto) {
        String sql = "INSERT INTO repuesto (nombre, descripcion, stock_actual, unidad_medida) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            // Especificar la columna de la clave generada
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id_repuesto"});
            ps.setString(1, repuesto.getNombre());
            ps.setString(2, repuesto.getDescripcion());
            ps.setDouble(3, repuesto.getStockActual());
            ps.setString(4, repuesto.getUnidadMedida());
            return ps;
        }, keyHolder);

        // Devolver la clave generada
        return keyHolder.getKey().intValue();
    }

    /**
     * Busca un repuesto por su identificador único.
     * (Método consolidado a partir de las dos versiones de {@code buscarPorId}).
     *
     * @param id identificador del repuesto
     * @return objeto {@link Repuesto} si existe, o {@code null} si no se encuentra
     */
    public Repuesto buscarPorId(int id) {
        // Usa alias para mapear correctamente
        String sql = "SELECT id_repuesto AS idRepuesto, nombre, descripcion, stock_actual AS stockActual, unidad_medida AS unidadMedida FROM repuesto WHERE id_repuesto = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Repuesto.class), id);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            // Manejar correctamente el caso donde no se encuentra el objeto
            return null;
        }
    }

    /**
     * Actualiza todos los campos de un repuesto existente, excepto el ID.
     *
     * @param id identificador del repuesto a actualizar
     * @param repuesto objeto {@link Repuesto} con los nuevos datos
     * @return número de filas afectadas (1 si fue exitoso, 0 si no se encontró el ID)
     */
    public int actualizarRepuesto(int id, Repuesto repuesto) {
        String sql = "UPDATE repuesto SET nombre = ?, descripcion = ?, stock_actual = ?, unidad_medida = ? WHERE id_repuesto = ?";
        return jdbcTemplate.update(sql,
                repuesto.getNombre(),
                repuesto.getDescripcion(),
                repuesto.getStockActual(),
                repuesto.getUnidadMedida(),
                id);
    }

    /**
     * Actualiza únicamente el stock de un repuesto existente.
     *
     * @param idRepuesto identificador del repuesto
     * @param nuevoStock nuevo valor de stock
     */
    public void actualizarStock(int idRepuesto, double nuevoStock) {
        String sql = "UPDATE repuesto SET stock_actual = ? WHERE id_repuesto = ?";
        jdbcTemplate.update(sql, nuevoStock, idRepuesto);
    }

    /**
     * Elimina un repuesto por su identificador.
     * (Método consolidado a partir de las dos versiones de {@code eliminarRepuesto}).
     *
     * @param id identificador del repuesto
     * @return número de filas afectadas (1 si fue exitoso, 0 si no se encontró el ID)
     */
    public int eliminarRepuesto(int id) {
        String sql = "DELETE FROM repuesto WHERE id_repuesto = ?";
        return jdbcTemplate.update(sql, id);
    }
}