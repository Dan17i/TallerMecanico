package co.edu.uniquindio.tallermacanico.repository;

import co.edu.uniquindio.tallermacanico.model.Repuesto;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepuestoRepository {

    private final JdbcTemplate jdbcTemplate;

    public RepuestoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Repuesto> listarRepuestos() {
        String sql = "SELECT * FROM repuesto";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Repuesto.class));
    }
    public int crearRepuesto(Repuesto repuesto) {
        String sql = "INSERT INTO repuesto (nombre, descripcion, stock_actual, unidad_medida) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                repuesto.getNombre(),
                repuesto.getDescripcion(),
                repuesto.getStockActual(),
                repuesto.getUnidadMedida()
        );
    }
    public Repuesto buscarPorId(int id) {
        String sql = "SELECT * FROM repuesto WHERE id_repuesto = ?";
        List<Repuesto> resultado = jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(Repuesto.class),
                id);
        return resultado.isEmpty() ? null : resultado.get(0);
    }

    public int actualizarRepuesto(int id, Repuesto repuesto) {
        String sql = "UPDATE repuesto SET nombre = ?, descripcion = ?, stock_actual = ?, unidad_medida = ? WHERE id_repuesto = ?";
        return jdbcTemplate.update(sql,
                repuesto.getNombre(),
                repuesto.getDescripcion(),
                repuesto.getStockActual(),
                repuesto.getUnidadMedida(),
                id);
    }

    public int eliminarRepuesto(int id) {
        String sql = "DELETE FROM repuesto WHERE id_repuesto = ?";
        return jdbcTemplate.update(sql, id);
    }
}
