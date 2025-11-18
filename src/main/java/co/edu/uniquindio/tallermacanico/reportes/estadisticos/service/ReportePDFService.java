package co.edu.uniquindio.tallermacanico.reportes.estadisticos.service;

import co.edu.uniquindio.tallermacanico.dto.*;
import co.edu.uniquindio.tallermacanico.model.Cliente;
import co.edu.uniquindio.tallermacanico.reportes.estadisticos.dto.*;
import co.edu.uniquindio.tallermacanico.reportes.repository.ReporteRepository;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Servicio encargado de generar reportes PDF a partir de datos estadísticos.
 * Utiliza la librería iText para construir documentos en memoria.
 */
@Service
public class ReportePDFService {

    private final ReporteRepository reporteRepository;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ReportePDFService(ReporteRepository reporteRepository) {
        this.reporteRepository = reporteRepository;
    }
    /**
     * Método auxiliar para formatear fechas de manera consistente
     */
    private String formatearFecha(LocalDate fecha) {
        return fecha != null ? fecha.format(DATE_FORMATTER) : "N/A";
    }

    // ==================== REPORTES SIMPLES (3) ====================

    /**
     * REPORTE 1: 1. Listado de Clientes Registrados
     */
    public byte[] generarPDFClientes() {
        List<Cliente> clientes = reporteRepository.listarClientes();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

            // Header
            agregarHeader(document, "LISTADO DE CLIENTES REGISTRADOS", boldFont);

            // Tabla
            Table table = new Table(new float[]{1, 3, 2, 2, 3});
            table.setWidth(UnitValue.createPercentValue(100));

            // Headers
            agregarHeaderCell(table, "ID", boldFont);
            agregarHeaderCell(table, "NOMBRE", boldFont);
            agregarHeaderCell(table, "CÉDULA", boldFont);
            agregarHeaderCell(table, "TELÉFONO", boldFont);
            agregarHeaderCell(table, "CORREO", boldFont);

            // Datos
            for (Cliente cliente : clientes) {
                table.addCell(createCell(String.valueOf(cliente.getIdCliente()), font));
                table.addCell(createCell(cliente.getNombre(), font));
                table.addCell(createCell(cliente.getTelefono(), font));
            }

            document.add(table);

            // Total
            document.add(new Paragraph("\nTotal de Clientes Registrados: " + clientes.size())
                    .setFont(boldFont).setFontSize(12));

            agregarFooter(document, font);
            document.close();

        } catch (IOException e) {
            throw new RuntimeException("Error al generar PDF de clientes", e);
        }

        return baos.toByteArray();
    }

    /**
     * REPORTE 2: Listado de Vehículos con Propietario
     */
    public byte[] generarPDFVehiculos() {
        List<VehiculoReporteDTO> vehiculos = reporteRepository.listarVehiculosDTO();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

            agregarHeader(document, "VEHÍCULOS REGISTRADOS CON PROPIETARIO", boldFont);

            Table table = new Table(new float[]{2, 2, 2, 3});
            table.setWidth(UnitValue.createPercentValue(100));

            agregarHeaderCell(table, "PLACA", boldFont);
            agregarHeaderCell(table, "MARCA", boldFont);
            agregarHeaderCell(table, "MODELO", boldFont);
            agregarHeaderCell(table, "PROPIETARIO", boldFont);

            for (VehiculoReporteDTO vehiculo : vehiculos) {
                table.addCell(createCell(vehiculo.getPlaca(), font));
                table.addCell(createCell(vehiculo.getMarca(), font));
                table.addCell(createCell(vehiculo.getModelo(), font));
                table.addCell(createCell(vehiculo.getPropietario(), font));
            }

            document.add(table);
            document.add(new Paragraph("\nTotal de Vehículos: " + vehiculos.size())
                    .setFont(boldFont).setFontSize(12));

            agregarFooter(document, font);
            document.close();

        } catch (IOException e) {
            throw new RuntimeException("Error al generar PDF de vehículos", e);
        }

        return baos.toByteArray();
    }

    /**
     * REPORTE 3: Listado de Servicios Disponibles
     */
    public byte[] generarPDFServicios() {
        List<ServicioReporteDTO> servicios = reporteRepository.listarServiciosDTO();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

            agregarHeader(document, "CATÁLOGO DE SERVICIOS DISPONIBLES", boldFont);

            Table table = new Table(new float[]{3, 5, 2});
            table.setWidth(UnitValue.createPercentValue(100));

            agregarHeaderCell(table, "NOMBRE", boldFont);
            agregarHeaderCell(table, "DESCRIPCIÓN", boldFont);
            agregarHeaderCell(table, "PRECIO BASE", boldFont);

            double totalPrecios = 0;
            for (ServicioReporteDTO servicio : servicios) {
                table.addCell(createCell(servicio.getNombre(), font));
                table.addCell(createCell(servicio.getDescripcion(), font));
                table.addCell(createCell("$" + String.format("%,.0f", servicio.getPrecio()), font));
                totalPrecios += servicio.getPrecio();
            }

            document.add(table);
            document.add(new Paragraph("\nTotal de Servicios: " + servicios.size())
                    .setFont(boldFont).setFontSize(12));
            document.add(new Paragraph("Precio Promedio: $" +
                    String.format("%,.0f", totalPrecios / servicios.size()))
                    .setFont(boldFont).setFontSize(12));

            agregarFooter(document, font);
            document.close();

        } catch (IOException e) {
            throw new RuntimeException("Error al generar PDF de servicios", e);
        }

        return baos.toByteArray();
    }

    // =====================================================
    // REPORTES INTERMEDIOS (4)
    // =====================================================

    /**
     * REPORTE 4: Órdenes de Trabajo por Rango de Fechas
     */
    public byte[] generarPDFOrdenesTrabajo(LocalDate fechaInicio, LocalDate fechaFin) {
        List<OrdenTrabajoDTO> ordenes = reporteRepository.listarOrdenesTrabajoPorFechas(fechaInicio, fechaFin);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

            agregarHeader(document, "ÓRDENES DE TRABAJO POR PERÍODO", boldFont);
            document.add(new Paragraph("Período: " + fechaInicio + " - " + fechaFin)
                    .setFont(font).setFontSize(11).setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("\n"));

            // Resumen estadístico
            long completadas = ordenes.stream()
                    .filter(o -> "COMPLETADO".equalsIgnoreCase(o.getNombreEstado())).count();
            long enProceso = ordenes.stream()
                    .filter(o -> "EN PROCESO".equalsIgnoreCase(o.getNombreEstado())).count();
            long pendientes = ordenes.stream()
                    .filter(o -> "PENDIENTE".equalsIgnoreCase(o.getNombreEstado())).count();

            Table resumen = new Table(1);
            resumen.setWidth(UnitValue.createPercentValue(100));
            resumen.addCell(createHeaderCellSingle("RESUMEN DEL PERÍODO", boldFont));
            resumen.addCell(createCell("Total Órdenes: " + ordenes.size(), font));
            resumen.addCell(createCell("Completadas: " + completadas +
                    " (" + (ordenes.size() > 0 ? (completadas * 100 / ordenes.size()) : 0) + "%)", font));
            resumen.addCell(createCell("En Proceso: " + enProceso +
                    " (" + (ordenes.size() > 0 ? (enProceso * 100 / ordenes.size()) : 0) + "%)", font));
            resumen.addCell(createCell("Pendientes: " + pendientes +
                    " (" + (ordenes.size() > 0 ? (pendientes * 100 / ordenes.size()) : 0) + "%)", font));

            document.add(resumen);
            document.add(new Paragraph("\n"));

            // Tabla de órdenes
            Table table = new Table(new float[]{1, 2, 2, 2, 3, 2});
            table.setWidth(UnitValue.createPercentValue(100));

            agregarHeaderCell(table, "# OT", boldFont);
            agregarHeaderCell(table, "VEHÍCULO", boldFont);
            agregarHeaderCell(table, "F.INGRESO", boldFont);
            agregarHeaderCell(table, "F.SALIDA", boldFont);
            agregarHeaderCell(table, "DIAGNÓSTICO", boldFont);
            agregarHeaderCell(table, "ESTADO", boldFont);

            for (OrdenTrabajoDTO orden : ordenes) {
                table.addCell(createCell(String.valueOf(orden.getIdOrdenTrabajo()), font));
                table.addCell(createCell(String.valueOf(orden.getIdVehiculo()), font));
                table.addCell(createCell(orden.getFechaIngreso().toString(), font));
                table.addCell(createCell(orden.getFechaSalida() != null ?
                        orden.getFechaSalida().toString() : "-", font));
                table.addCell(createCell(orden.getDiagnosticoInicial(), font));
                table.addCell(createCell(orden.getNombreEstado(), font));
            }

            document.add(table);
            agregarFooter(document, font);
            document.close();

        } catch (IOException e) {
            throw new RuntimeException("Error al generar PDF de órdenes de trabajo", e);
        }

        return baos.toByteArray();
    }

    /**
     * REPORTE 5: Facturas por Cliente
     */
    public byte[] generarPDFFacturasCliente(int idCliente) {
        List<FacturaReporteDTO> facturas = reporteRepository.listarFacturasPorCliente(idCliente);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

            agregarHeader(document, "HISTORIAL DE FACTURAS POR CLIENTE", boldFont);

            // Info del cliente
            String nombreCliente = facturas.isEmpty() ? "Cliente Desconocido" :
                    facturas.get(0).getCliente();

            Table infoCliente = new Table(1);
            infoCliente.setWidth(UnitValue.createPercentValue(100));
            infoCliente.addCell(createHeaderCellSingle("INFORMACIÓN DEL CLIENTE", boldFont));
            infoCliente.addCell(createCell("Nombre: " + nombreCliente, font));
            infoCliente.addCell(createCell("ID Cliente: " + idCliente, font));

            document.add(infoCliente);
            document.add(new Paragraph("\n"));

            // Tabla de facturas
            Table table = new Table(new float[]{2, 3, 3});
            table.setWidth(UnitValue.createPercentValue(100));

            agregarHeaderCell(table, "FACTURA", boldFont);
            agregarHeaderCell(table, "FECHA EMISIÓN", boldFont);
            agregarHeaderCell(table, "TOTAL", boldFont);

            double totalFacturado = 0;

            for (FacturaReporteDTO factura : facturas) {
                table.addCell(createCell("F-" + String.format("%03d", factura.getIdFactura()), font));
                table.addCell(createCell(factura.getFechaEmision().toString(), font));
                table.addCell(createCell("$" + String.format("%,.0f", factura.getTotal()), font));
                totalFacturado += factura.getTotal();
            }

            document.add(table);

            // Resumen financiero
            document.add(new Paragraph("\n"));
            Table resumen = new Table(1);
            resumen.setWidth(UnitValue.createPercentValue(100));
            resumen.addCell(createHeaderCellSingle("RESUMEN FINANCIERO", boldFont));
            resumen.addCell(createCell("Total Facturado: $" +
                    String.format("%,.0f", totalFacturado) + " COP", font));
            resumen.addCell(createCell("Número de Facturas: " + facturas.size(), font));
            resumen.addCell(createCell("Promedio por Factura: $" +
                    String.format("%,.0f", facturas.size() > 0 ? totalFacturado / facturas.size() : 0) +
                    " COP", font));

            document.add(resumen);
            agregarFooter(document, font);
            document.close();

        } catch (IOException e) {
            throw new RuntimeException("Error al generar PDF de facturas", e);
        }

        return baos.toByteArray();
    }
    /**
     * REPORTE 6: Historial de Movimientos de Inventario por Repuesto
     */
    public byte[] generarPDFMovimientosRepuesto(int idRepuesto) {
        List<InventarioReporteDTO> movimientos = reporteRepository.listarMovimientosPorRepuesto(idRepuesto);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

            agregarHeader(document, "HISTORIAL DE MOVIMIENTOS POR REPUESTO", boldFont);

            // Info del Repuesto
            String nombreRepuesto = movimientos.isEmpty() ? "Repuesto Desconocido" :
                    movimientos.get(0).getNombreRepuesto();

            Table infoRepuesto = new Table(1);
            infoRepuesto.setWidth(UnitValue.createPercentValue(100));
            infoRepuesto.addCell(createHeaderCellSingle("INFORMACIÓN DEL REPUESTO", boldFont));
            infoRepuesto.addCell(createCell("Nombre: " + nombreRepuesto, font));
            infoRepuesto.addCell(createCell("ID Repuesto: R-" + String.format("%04d", idRepuesto), font));

            document.add(infoRepuesto);
            document.add(new Paragraph("\n"));

            // Tabla de Movimientos
            Table table = new Table(new float[]{2, 3, 3, 3, 3});
            table.setWidth(UnitValue.createPercentValue(100));

            agregarHeaderCell(table, "MOVIMIENTO ID", boldFont);
            agregarHeaderCell(table, "TIPO", boldFont);
            agregarHeaderCell(table, "CANTIDAD", boldFont);
            agregarHeaderCell(table, "FECHA", boldFont); // Corregido: La columna se llena con 'fecha'
            agregarHeaderCell(table, "REPUESTO", boldFont);

            int totalEntradas = 0;
            int totalSalidas = 0;

            for (InventarioReporteDTO mov : movimientos) {
                table.addCell(createCell("M-" + String.format("%05d", mov.getIdMovimiento()), font));
                table.addCell(createCell(mov.getTipoMovimiento(), font));
                table.addCell(createCell(String.valueOf(mov.getCantidad()), font));
                // ¡ESTA ES LA LÍNEA CORREGIDA!
                table.addCell(createCell(mov.getFecha().toString(), font));
                // Fin de la línea corregida
                table.addCell(createCell(mov.getNombreRepuesto(), font));

                if ("ENTRADA".equals(mov.getTipoMovimiento())) {
                    totalEntradas += mov.getCantidad();
                } else if ("SALIDA".equals(mov.getTipoMovimiento())) {
                    totalSalidas += mov.getCantidad();
                }
            }

            document.add(table);

            // Resumen de Inventario
            document.add(new Paragraph("\n"));
            Table resumen = new Table(1);
            resumen.setWidth(UnitValue.createPercentValue(100));
            resumen.addCell(createHeaderCellSingle("RESUMEN DE MOVIMIENTOS", boldFont));
            resumen.addCell(createCell("Total de Movimientos: " + movimientos.size(), font));
            resumen.addCell(createCell("Total Cantidad (Entradas): " + totalEntradas, font));
            resumen.addCell(createCell("Total Cantidad (Salidas): " + totalSalidas, font));

            document.add(resumen);
            agregarFooter(document, font);
            document.close();

        } catch (IOException e) {
            throw new RuntimeException("Error al generar PDF de movimientos de inventario", e);
        }

        return baos.toByteArray();
    }
    /**
     * REPORTE 7: Supervisiones por Mecánico
     */
    public byte[] generarPDFSupervisiones(int idSupervisor) {
        List<SupervisionReporteDTO> supervisiones = reporteRepository.listarSupervisionesPorMecanico(idSupervisor);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

            agregarHeader(document, "SUPERVISIONES REALIZADAS", boldFont);

            // Info del supervisor
            String nombreSupervisor = supervisiones.isEmpty() ? "Supervisor Desconocido" :
                    supervisiones.get(0).getSupervisor();

            Table infoSupervisor = new Table(1);
            infoSupervisor.setWidth(UnitValue.createPercentValue(100));
            infoSupervisor.addCell(createHeaderCellSingle("INFORMACIÓN DEL SUPERVISOR", boldFont));
            infoSupervisor.addCell(createCell("Nombre: " + nombreSupervisor, font));
            infoSupervisor.addCell(createCell("ID Supervisor: " + idSupervisor, font));

            document.add(infoSupervisor);
            document.add(new Paragraph("\n"));

            // Tabla
            Table table = new Table(new float[]{1, 2, 3, 5});
            table.setWidth(UnitValue.createPercentValue(100));

            agregarHeaderCell(table, "ID", boldFont);
            agregarHeaderCell(table, "FECHA", boldFont);
            agregarHeaderCell(table, "MECÁNICO SUPERVISADO", boldFont);
            agregarHeaderCell(table, "OBSERVACIONES", boldFont);

            for (SupervisionReporteDTO sup : supervisiones) {
                table.addCell(createCell(String.valueOf(sup.getIdSupervision()), font));
                table.addCell(createCell(sup.getFecha().toString(), font));
                table.addCell(createCell(sup.getMecanicoSupervisado(), font));
                table.addCell(createCell(sup.getObservaciones(), font));
            }

            document.add(table);
            document.add(new Paragraph("\nTotal Supervisiones: " + supervisiones.size())
                    .setFont(boldFont).setFontSize(12));

            agregarFooter(document, font);
            document.close();

        } catch (IOException e) {
            throw new RuntimeException("Error al generar PDF de supervisiones", e);
        }

        return baos.toByteArray();
    }

    // ==================== REPORTES COMPLEJOS (3) ====================
    /**
     * REPORTE 8: Servicios Realizados por Mecánico
     */
    public byte[] generarPDFServiciosMecanico(int idMecanico) {
        List<ServicioMecanicoReporteDTO> servicios = reporteRepository.listarServiciosPorMecanico(idMecanico);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

            agregarHeader(document, "PRODUCTIVIDAD: SERVICIOS POR MECÁNICO", boldFont);

            // Info del mecánico
            String nombreMecanico = servicios.isEmpty() ? "Mecánico Desconocido" :
                    servicios.get(0).getNombreMecanico();

            Table infoMecanico = new Table(1);
            infoMecanico.setWidth(UnitValue.createPercentValue(100));
            infoMecanico.addCell(createHeaderCellSingle("PERFIL DEL MECÁNICO", boldFont));
            infoMecanico.addCell(createCell("Nombre: " + nombreMecanico, font));
            infoMecanico.addCell(createCell("ID: M-" + String.format("%03d", idMecanico), font));

            document.add(infoMecanico);
            document.add(new Paragraph("\n"));

            // Tabla
            Table table = new Table(new float[]{2, 2, 4});
            table.setWidth(UnitValue.createPercentValue(100));

            agregarHeaderCell(table, "ORDEN", boldFont);
            agregarHeaderCell(table, "FECHA", boldFont);
            agregarHeaderCell(table, "SERVICIO REALIZADO", boldFont);

            for (ServicioMecanicoReporteDTO servicio : servicios) {
                table.addCell(createCell("OS-" + String.format("%03d", servicio.getIdOrdenServicio()), font));
                table.addCell(createCell(servicio.getFecha().toString(), font));
                table.addCell(createCell(servicio.getNombreServicio(), font));
            }

            document.add(table);

            // Indicadores
            document.add(new Paragraph("\n"));
            Table indicadores = new Table(1);
            indicadores.setWidth(UnitValue.createPercentValue(100));
            indicadores.addCell(createHeaderCellSingle("INDICADORES DE DESEMPEÑO", boldFont));
            indicadores.addCell(createCell("Servicios Completados: " + servicios.size(), font));

            document.add(indicadores);
            agregarFooter(document, font);
            document.close();

        } catch (IOException e) {
            throw new RuntimeException("Error al generar PDF de servicios por mecánico", e);
        }

        return baos.toByteArray();
    }

    /**
     * REPORTE 9: Repuestos Utilizados en Orden de Servicio
     */
    public byte[] generarPDFRepuestosOrden(int idOrdenServicio) {
        List<RepuestoOrdenReporteDTO> repuestos = reporteRepository.listarRepuestosPorOrdenServicio(idOrdenServicio);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

            agregarHeader(document, "DETALLE DE REPUESTOS POR ORDEN DE SERVICIO", boldFont);

            // Info de la orden
            Table infoOrden = new Table(1);
            infoOrden.setWidth(UnitValue.createPercentValue(100));
            infoOrden.addCell(createHeaderCellSingle("INFORMACIÓN DE LA ORDEN", boldFont));
            infoOrden.addCell(createCell("Orden de Servicio: OS-" +
                    String.format("%03d", idOrdenServicio), font));

            document.add(infoOrden);
            document.add(new Paragraph("\n"));

            // Tabla
            Table table = new Table(new float[]{4, 2, 2, 2});
            table.setWidth(UnitValue.createPercentValue(100));

            agregarHeaderCell(table, "REPUESTO", boldFont);
            agregarHeaderCell(table, "CANTIDAD", boldFont);
            agregarHeaderCell(table, "TIPO MOV", boldFont);
            agregarHeaderCell(table, "FECHA", boldFont);

            for (RepuestoOrdenReporteDTO repuesto : repuestos) {
                table.addCell(createCell(repuesto.getNombreRepuesto(), font));
                table.addCell(createCell(String.valueOf(repuesto.getCantidad()), font));
                table.addCell(createCell(repuesto.getTipoMovimiento(), font));
                table.addCell(createCell(repuesto.getFecha().toString(), font));
            }

            document.add(table);
            document.add(new Paragraph("\nTotal de Repuestos: " + repuestos.size() + " ítems")
                    .setFont(boldFont).setFontSize(12));

            agregarFooter(document, font);
            document.close();

        } catch (IOException e) {
            throw new RuntimeException("Error al generar PDF de repuestos por orden", e);
        }

        return baos.toByteArray();
    }
    /**
     * REPORTE 10: Productividad de Supervisores
     */
    public byte[] generarPDFProductividadSupervisores() {
        List<ProductividadSupervisorDTO> productividad = reporteRepository.listarProductividadSupervisores();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

            agregarHeader(document, "PRODUCTIVIDAD DE MECÁNICOS SUPERVISORES", boldFont);

            // Indicadores Generales
            Table indicadoresGenerales = new Table(1);
            indicadoresGenerales.setWidth(UnitValue.createPercentValue(100));
            indicadoresGenerales.addCell(createHeaderCellSingle("RESUMEN DE SUPERVISORES", boldFont));
            indicadoresGenerales.addCell(createCell("Total de Supervisores: " + productividad.size(), font));
            document.add(indicadoresGenerales);
            document.add(new Paragraph("\n"));

            // Tabla de Productividad
            Table table = new Table(new float[]{5, 3, 3});
            table.setWidth(UnitValue.createPercentValue(100));

            // Encabezados de la tabla
            agregarHeaderCell(table, "SUPERVISOR", boldFont);
            agregarHeaderCell(table, "TOTAL SUPERVISIONES", boldFont);
            agregarHeaderCell(table, "TOTAL SERVICIOS", boldFont);

            // Filas de datos
            for (ProductividadSupervisorDTO dto : productividad) {
                table.addCell(createCell(dto.getNombreSupervisor(), font));
                table.addCell(createCell(String.valueOf(dto.getTotalSupervisiones()), font));
                table.addCell(createCell(String.valueOf(dto.getTotalServicios()), font));
            }

            document.add(table);

            // Indicadores detallados (opcional: totales o promedios si se requieren)
            document.add(new Paragraph("\n"));
            long totalSupervisionesGlobal = productividad.stream()
                    .mapToLong(ProductividadSupervisorDTO::getTotalSupervisiones)
                    .sum();

            Table indicadoresDetalle = new Table(1);
            indicadoresDetalle.setWidth(UnitValue.createPercentValue(100));
            indicadoresDetalle.addCell(createHeaderCellSingle("TOTALES CONSOLIDADOS", boldFont));
            indicadoresDetalle.addCell(createCell("Supervisiones Globales: " + totalSupervisionesGlobal, font));
            document.add(indicadoresDetalle);

            agregarFooter(document, font);
            document.close();

        } catch (IOException e) {
            throw new RuntimeException("Error al generar PDF de productividad de supervisores", e);
        }

        return baos.toByteArray();
    }

    // =====================================================
    // REPORTES ESTADÍSTICOS
    // =====================================================


    /**
     * REPORTE 11: Servicios Más Solicitados (Estadístico)
     */
    public byte[] generarPDFServiciosMasSolicitados() {
        List<ServicioEstadisticoDTO> servicios = reporteRepository.obtenerServiciosMasSolicitados();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

            agregarHeader(document, "SERVICIOS MÁS SOLICITADOS", boldFont);

            Table table = new Table(new float[]{7, 3});
            table.setWidth(UnitValue.createPercentValue(100));

            agregarHeaderCell(table, "SERVICIO", boldFont);
            agregarHeaderCell(table, "TOTAL SOLICITUDES", boldFont);

            for (ServicioEstadisticoDTO servicio : servicios) {
                table.addCell(createCell(servicio.getNombreServicio(), font));
                table.addCell(createCell(String.valueOf(servicio.getTotalSolicitudes()), font));
            }

            document.add(table);
            agregarFooter(document, font);
            document.close();

        } catch (IOException e) {
            throw new RuntimeException("Error al generar PDF de servicios más solicitados", e);
        }

        return baos.toByteArray();
    }

    /**
     * REPORTE 12: Repuestos Más Usados (Estadístico)
     */
    public byte[] generarPDFRepuestosMasUsados() {
        List<RepuestoEstadisticoDTO> repuestos = reporteRepository.obtenerRepuestosMasUsados();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

            agregarHeader(document, "REPUESTOS MÁS UTILIZADOS", boldFont);

            Table table = new Table(new float[]{7, 3});
            table.setWidth(UnitValue.createPercentValue(100));

            agregarHeaderCell(table, "REPUESTO", boldFont);
            agregarHeaderCell(table, "TOTAL USOS", boldFont);

            for (RepuestoEstadisticoDTO repuesto : repuestos) {
                table.addCell(createCell(repuesto.getNombreRepuesto(), font));
                table.addCell(createCell(String.valueOf(repuesto.getTotalUsos()), font));
            }

            document.add(table);
            agregarFooter(document, font);
            document.close();

        } catch (IOException e) {
            throw new RuntimeException("Error al generar PDF de repuestos más usados", e);
        }

        return baos.toByteArray();
    }

    /**
     * REPORTE 13: Ingresos Mensuales (Estadístico)
     */
    public byte[] generarPDFIngresosMensuales() {
        List<IngresoMensualDTO> ingresos = reporteRepository.obtenerIngresosPorMes();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

            agregarHeader(document, "ANÁLISIS DE INGRESOS MENSUALES", boldFont);

            Table table = new Table(new float[]{5, 5});
            table.setWidth(UnitValue.createPercentValue(100));

            agregarHeaderCell(table, "MES", boldFont);
            agregarHeaderCell(table, "TOTAL INGRESOS", boldFont);

            for (IngresoMensualDTO ingreso : ingresos) {
                table.addCell(createCell(ingreso.getMes(), font));
                table.addCell(createCell(String.format("$%.2f", ingreso.getTotalIngresos()), font));
            }

            document.add(table);
            agregarFooter(document, font);
            document.close();

        } catch (IOException e) {
            throw new RuntimeException("Error al generar PDF de ingresos mensuales", e);
        }

        return baos.toByteArray();
    }
    // =====================================================
    // MÉTODOS AUXILIARES PARA CONSTRUIR ELEMENTOS DEL PDF
    // =====================================================
    /**
     * Agrega el encabezado al documento PDF
     */
    private void agregarHeader(Document document, String titulo, PdfFont boldFont) {
        Paragraph header = new Paragraph("TALLER AUTOMOTRIZ - MOTORPLUS")
                .setFont(boldFont)
                .setFontSize(18)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontColor(ColorConstants.BLUE);

        Paragraph subtitulo = new Paragraph(titulo)
                .setFont(boldFont)
                .setFontSize(14)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(10);

        Paragraph fecha = new Paragraph("Fecha de emisión: " + LocalDate.now().format(DATE_FORMATTER))
                .setFontSize(10)
                .setTextAlignment(TextAlignment.RIGHT)
                .setMarginBottom(20);

        document.add(header);
        document.add(subtitulo);
        document.add(fecha);
    }

    /**
     * Agrega el pie de página al documento
     */
    private void agregarFooter(Document document, PdfFont font) {
        document.add(new Paragraph("\n"));
        Paragraph footer = new Paragraph("Documento generado automáticamente por MotorPlus - Sistema de Gestión de Taller")
                .setFont(font)
                .setFontSize(8)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontColor(ColorConstants.GRAY);
        document.add(footer);
    }

    /**
     * Crea una celda de encabezado para tablas
     */
    private void agregarHeaderCell(Table table, String texto, PdfFont boldFont) {
        Cell cell = new Cell()
                .add(new Paragraph(texto).setFont(boldFont))
                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                .setTextAlignment(TextAlignment.CENTER)
                .setPadding(5);
        table.addHeaderCell(cell);
    }

    /**
     * Crea una celda normal para tablas
     */
    private Cell createCell(String texto, PdfFont font) {
        return new Cell()
                .add(new Paragraph(texto).setFont(font))
                .setPadding(5)
                .setTextAlignment(TextAlignment.LEFT);
    }

    /**
     * Crea una celda de encabezado única (para secciones)
     */
    private Cell createHeaderCellSingle(String texto, PdfFont boldFont) {
        return new Cell()
                .add(new Paragraph(texto).setFont(boldFont))
                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                .setTextAlignment(TextAlignment.CENTER)
                .setPadding(8)
                .setBold();
    }



}
