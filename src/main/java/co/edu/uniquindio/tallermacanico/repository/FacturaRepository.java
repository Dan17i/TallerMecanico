package co.edu.uniquindio.tallermacanico.repository;

import co.edu.uniquindio.tallermacanico.model.Factura;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para gestionar operaciones sobre la tabla Factura.
 * Proporciona métodos CRUD usando JdbcTemplate.
 */
@Repository
public class FacturaRepository {

    private final JdbcTemplate jdbcTemplate;

    public FacturaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Obtiene todas las facturas registradas en la base de datos.
     *
     * @return lista de objetos Factura
     */
    public List<Factura> listarFacturas() {
        String sql = "SELECT * FROM factura";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Factura.class));
    }

    /**
     * Busca una factura por su identificador único.
     *
     * @param id identificador de la factura
     * @return objeto Factura encontrado o null si no existe
     */
    public Factura buscarPorId(int id) {
        String sql = "SELECT * FROM factura WHERE id_factura = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Factura.class), id);
        } catch (Exception e) {
            return null; // Manejo simple: retorna null si no se encuentra
        }
    }

    /**
     * Registra una nueva factura en la base de datos.
     *
     * @param factura objeto Factura con los datos a insertar
     */
    public int registrarFactura(Factura factura) {
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

        // Recuperar el último ID generado
        return jdbcTemplate.queryForObject("SELECT MAX(id_factura) FROM factura", Integer.class);
    }


    /**
     * Elimina una factura de la base de datos por su identificador.
     *
     * @param id identificador de la factura a eliminar
     */
    public boolean eliminarFactura(int id) {
        String sql = "DELETE FROM factura WHERE id_factura = ?";
        int filas = jdbcTemplate.update(sql, id);
        return filas > 0; // true si se eliminó, false si no existía
    }

}
