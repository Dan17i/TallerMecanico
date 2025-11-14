package co.edu.uniquindio.tallermacanico.repository;


import co.edu.uniquindio.tallermacanico.model.Vehiculo;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VehiculoRepository {

    private final JdbcTemplate jdbcTemplate;

    public VehiculoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Vehiculo> listarVehiculos() {
        String sql = "SELECT * FROM vehiculo";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Vehiculo.class));
    }

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
}