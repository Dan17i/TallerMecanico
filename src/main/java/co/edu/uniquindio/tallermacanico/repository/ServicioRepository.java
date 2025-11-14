package co.edu.uniquindio.tallermacanico.repository;


import co.edu.uniquindio.tallermacanico.model.Servicio;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ServicioRepository {

    private final JdbcTemplate jdbcTemplate;

    public ServicioRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Servicio> listarServicios() {
        String sql = "SELECT * FROM servicio";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Servicio.class));
    }

    public Servicio buscarPorId(int id) {
        String sql = "SELECT * FROM servicio WHERE id_servicio = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Servicio.class), id);
    }

    public void registrarServicio(Servicio servicio) {
        String sql = "INSERT INTO servicio (nombre, descripcion, precio_base) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql,
                servicio.getNombre(),
                servicio.getDescripcion(),
                servicio.getPrecioBase());
    }

    public void eliminarServicio(int id) {
        String sql = "DELETE FROM servicio WHERE id_servicio = ?";
        jdbcTemplate.update(sql, id);
    }
}
