package co.edu.uniquindio.tallermacanico.reportes.repository;


import co.edu.uniquindio.tallermacanico.dto.*;
import co.edu.uniquindio.tallermacanico.model.Cliente;
import co.edu.uniquindio.tallermacanico.reportes.estadisticos.dto.IngresoMensualDTO;
import co.edu.uniquindio.tallermacanico.reportes.estadisticos.dto.RepuestoEstadisticoDTO;
import co.edu.uniquindio.tallermacanico.reportes.estadisticos.dto.ServicioEstadisticoDTO;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repositorio de reportes: concentra consultas especializadas y agregaciones.
 * Trabaja con JdbcTemplate (sin JPA).
 */
@Repository
public class ReporteRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReporteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Reporte simple: listado de clientes registrados.
     *
     * @return lista de clientes
     */
    public List<Cliente> listarClientes() {
        String sql = "SELECT id_cliente, nombre, cedula, telefono, correo FROM cliente";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Cliente.class));
    }

    /**
     * Reporte simple: listado de vehículos registrados con su cliente asociado.
     * @return lista de DTOs de vehículos
     */
    public List<VehiculoReporteDTO> listarVehiculosDTO() {
        String sql = """
        SELECT v.placa, v.marca, v.modelo, c.nombre AS propietario
        FROM vehiculo v
        JOIN cliente c ON v.id_cliente = c.id_cliente
    """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> new VehiculoReporteDTO(
                rs.getString("placa"),
                rs.getString("marca"),
                rs.getString("modelo"),
                rs.getString("propietario")
        ));
    }
    /**
     * Reporte simple: listado de servicios disponibles.
     * @return lista de DTOs de servicios
     */
    public List<ServicioReporteDTO> listarServiciosDTO() {
        String sql = "SELECT nombre, descripcion, precio FROM servicio";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new ServicioReporteDTO(
                rs.getString("nombre"),
                rs.getString("descripcion"),
                rs.getDouble("precio")
        ));

    }


    /**
     * Reporte intermedio: órdenes de trabajo por rango de fechas.
     * @param fechaInicio fecha inicial del rango
     * @param fechaFin fecha final del rango
     * @return lista de DTOs de órdenes de trabajo
     */
    public List<OrdenTrabajoDTO> listarOrdenesTrabajoPorFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        String sql = """
        SELECT ot.id_orden_trabajo, ot.id_vehiculo, ot.fecha_ingreso, ot.fecha_salida,
               ot.diagnostico_inicial, eo.nombre_estado
        FROM orden_trabajo ot
        JOIN estado_orden eo ON ot.id_estado = eo.id_estado
        WHERE ot.fecha_ingreso BETWEEN ? AND ?
        ORDER BY ot.fecha_ingreso
    """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new OrdenTrabajoDTO(
                rs.getInt("id_orden_trabajo"),
                rs.getInt("id_vehiculo"),
                rs.getDate("fecha_ingreso").toLocalDate(),
                rs.getDate("fecha_salida") != null ? rs.getDate("fecha_salida").toLocalDate() : null,
                rs.getString("diagnostico_inicial"),
                rs.getString("nombre_estado")
        ), fechaInicio, fechaFin);
    }

    /**
     * Reporte intermedio: facturas emitidas por un cliente específico.
     * @param idCliente identificador del cliente
     * @return lista de DTOs de facturas
     */
    public List<FacturaReporteDTO> listarFacturasPorCliente(int idCliente) {
        String sql = """
        SELECT f.id_factura, f.fecha_emision, f.total, c.nombre AS cliente
        FROM factura f
        JOIN cliente c ON f.id_cliente = c.id_cliente
        WHERE f.id_cliente = ?
        ORDER BY f.fecha_emision DESC
    """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new FacturaReporteDTO(
                rs.getInt("id_factura"),
                rs.getDate("fecha_emision").toLocalDate(),
                rs.getDouble("total"),
                rs.getString("cliente")
        ), idCliente);
    }

    /**
     * Reporte intermedio: movimientos de inventario por repuesto.
     * @param idRepuesto identificador del repuesto
     * @return lista de DTOs de movimientos de inventario
     */
    public List<InventarioReporteDTO> listarMovimientosPorRepuesto(int idRepuesto) {
        String sql = """
        SELECT mi.id_movimiento, mi.tipo_movimiento, mi.cantidad, mi.fecha, r.nombre AS nombre_repuesto
        FROM movimiento_inventario mi
        JOIN repuesto r ON mi.id_repuesto = r.id_repuesto
        WHERE mi.id_repuesto = ?
        ORDER BY mi.fecha DESC
    """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new InventarioReporteDTO(
                rs.getInt("id_movimiento"),
                rs.getString("tipo_movimiento"),
                rs.getInt("cantidad"),
                rs.getDate("fecha").toLocalDate(),
                rs.getString("nombre_repuesto")
        ), idRepuesto);

     }

    /**
     * Reporte intermedio: supervisiones realizadas por un mecánico supervisor.
     * @param idSupervisor identificador del mecánico supervisor
     * @return lista de DTOs de supervisiones
     */
    public List<SupervisionReporteDTO> listarSupervisionesPorMecanico(int idSupervisor) {
        String sql = """
        SELECT s.id_supervision, sup.nombre AS supervisor, mec.nombre AS mecanico_supervisado,
               s.fecha, s.observaciones
        FROM supervision s
        JOIN mecanico sup ON s.id_mecanico_supervisor = sup.id_mecanico
        JOIN mecanico mec ON s.id_mecanico_supervisado = mec.id_mecanico
        WHERE s.id_mecanico_supervisor = ?
        ORDER BY s.fecha DESC
    """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new SupervisionReporteDTO(
                rs.getInt("id_supervision"),
                rs.getString("supervisor"),
                rs.getString("mecanico_supervisado"),
                rs.getDate("fecha").toLocalDate(),
                rs.getString("observaciones")
        ), idSupervisor);
    }

    /**
     * Reporte complejo: servicios realizados por un mecánico en órdenes de trabajo.
     * @param idMecanico identificador del mecánico
     * @return lista de DTOs con servicios realizados
     */
    public List<ServicioMecanicoReporteDTO> listarServiciosPorMecanico(int idMecanico) {
        String sql = """
        SELECT os.id_orden_servicio, m.nombre AS nombre_mecanico, s.nombre AS nombre_servicio, os.fecha
        FROM orden_servicio_mecanico osm
        JOIN orden_servicio os ON osm.id_orden_servicio = os.id_orden_servicio
        JOIN mecanico m ON osm.id_mecanico = m.id_mecanico
        JOIN servicio s ON os.id_servicio = s.id_servicio
        WHERE osm.id_mecanico = ?
        ORDER BY os.fecha DESC
    """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new ServicioMecanicoReporteDTO(
                rs.getInt("id_orden_servicio"),
                rs.getString("nombre_mecanico"),
                rs.getString("nombre_servicio"),
                rs.getDate("fecha").toLocalDate()
        ), idMecanico);
    }

    /**
     * Reporte complejo: repuestos utilizados en una orden de servicio.
     * @param idOrdenServicio identificador de la orden de servicio
     * @return lista de DTOs con repuestos utilizados
     */
    public List<RepuestoOrdenReporteDTO> listarRepuestosPorOrdenServicio(int idOrdenServicio) {
        String sql = """
        SELECT os.id_orden_servicio, r.nombre AS nombre_repuesto, mi.cantidad, mi.tipo_movimiento, mi.fecha
        FROM movimiento_inventario mi
        JOIN repuesto r ON mi.id_repuesto = r.id_repuesto
        JOIN orden_servicio os ON mi.id_orden_servicio = os.id_orden_servicio
        WHERE os.id_orden_servicio = ?
        ORDER BY mi.fecha DESC
    """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new RepuestoOrdenReporteDTO(
                rs.getInt("id_orden_servicio"),
                rs.getString("nombre_repuesto"),
                rs.getInt("cantidad"),
                rs.getString("tipo_movimiento"),
                rs.getDate("fecha").toLocalDate()
        ), idOrdenServicio);
    }

    /**
     * Reporte complejo: productividad de mecánicos supervisores.
     * Incluye cantidad de supervisiones y servicios realizados.
     * @return lista de DTOs con productividad por supervisor
     */
    public List<ProductividadSupervisorDTO> listarProductividadSupervisores() {
        String sql = """
        SELECT m.nombre AS nombre_supervisor,
               (SELECT COUNT(*) 
                FROM supervision s 
                WHERE s.id_mecanico_supervisor = m.id_mecanico) AS total_supervisiones,
               (SELECT COUNT(*) 
                FROM orden_servicio_mecanico osm
                WHERE osm.id_mecanico = m.id_mecanico) AS total_servicios
        FROM mecanico m
        WHERE m.es_supervisor = 1
        ORDER BY total_supervisiones DESC
    """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new ProductividadSupervisorDTO(
                rs.getString("nombre_supervisor"),
                rs.getInt("total_supervisiones"),
                rs.getInt("total_servicios")
        ));
    }

    /**
     * Consulta estadística: obtiene la distribución de servicios más solicitados.
     * <p>
     * Agrupa las órdenes de servicio por nombre de servicio y cuenta cuántas veces
     * fue solicitado cada uno.
     * </p>
     *
     * @return lista de {@link ServicioEstadisticoDTO} con nombre del servicio y total de solicitudes
     */
    public List<ServicioEstadisticoDTO> obtenerServiciosMasSolicitados() {
        String sql = """
            SELECT s.nombre AS nombre_servicio, COUNT(*) AS total_solicitudes
            FROM orden_servicio os
            JOIN servicio s ON os.id_servicio = s.id_servicio
            GROUP BY s.nombre
            ORDER BY total_solicitudes DESC
        """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new ServicioEstadisticoDTO(
                rs.getString("nombre_servicio"),
                rs.getLong("total_solicitudes")
        ));
    }

    /**
     * Obtiene la lista de repuestos más usados en órdenes de servicio.
     * <p>
     * La consulta cuenta cuántas veces se registró un repuesto en movimientos
     * de tipo 'SALIDA' dentro de la tabla movimiento_inventario.
     * </p>
     *
     * @return lista de {@link RepuestoEstadisticoDTO} con nombre del repuesto y total de usos
     */
    public List<RepuestoEstadisticoDTO> obtenerRepuestosMasUsados() {
        String sql = """
        SELECT r.nombre AS nombre_repuesto, COUNT(*) AS total_usos
        FROM movimiento_inventario mi
        JOIN repuesto r ON mi.id_repuesto = r.id_repuesto
        WHERE mi.tipo_movimiento = 'SALIDA'
        GROUP BY r.nombre
        ORDER BY total_usos DESC
    """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new RepuestoEstadisticoDTO(
                rs.getString("nombre_repuesto"),
                rs.getLong("total_usos")
        ));
    }

    /**
     * Obtiene los ingresos totales agrupados por mes a partir de las órdenes de servicio.
     * <p>
     * La consulta agrupa por año y mes usando {@code TO_CHAR(fecha, 'YYYY-MM')} y suma el campo {@code total}.
     * Solo considera la tabla {@code orden_servicio}.
     * </p>
     *
     * @return lista de {@link IngresoMensualDTO} con el mes y el total de ingresos correspondientes
     */
    public List<IngresoMensualDTO> obtenerIngresosPorMes() {
        String sql = """
        SELECT TO_CHAR(fecha, 'YYYY-MM') AS mes, SUM(total) AS total_ingresos
        FROM orden_servicio
        GROUP BY TO_CHAR(fecha, 'YYYY-MM')
        ORDER BY mes
    """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new IngresoMensualDTO(
                rs.getString("mes"),
                rs.getBigDecimal("total_ingresos")
        ));
    }


}
