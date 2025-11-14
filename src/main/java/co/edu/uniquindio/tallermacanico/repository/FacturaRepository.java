package co.edu.uniquindio.tallermacanico.repository;

import co.edu.uniquindio.tallermacanico.model.Factura;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FacturaRepository {

    private final JdbcTemplate jdbcTemplate;

    public FacturaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Factura> listarFacturas() {
        String sql = "SELECT * FROM factura";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Factura.class));
    }

    public Factura buscarPorId(int id) {
        String sql = "SELECT * FROM factura WHERE id_factura = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Factura.class), id);
    }

    public void registrarFactura(Factura factura) {
        String sql = "INSERT INTO factura (id_orden_trabajo, id_estado_pago, fecha_emision, subtotal_servicios, subtotal_repuestos, impuestos_total, descuento_total, total) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                factura.getIdOrdenTrabajo(),
                factura.getIdEstadoPago(),
                factura.getFechaEmision(),
                factura.getSubtotalServicios(),
                factura.getSubtotalRepuestos(),
                factura.getImpuestosTotal(),
                factura.getDescuentoTotal(),
                factura.getTotal());
    }


    public void eliminarFactura(int id) {
        String sql = "DELETE FROM factura WHERE id_factura = ?";
        jdbcTemplate.update(sql, id);
    }
}
