package co.edu.uniquindio.tallermacanico.reportes.estadisticos.controller;

import co.edu.uniquindio.tallermacanico.reportes.estadisticos.service.ReportePDFService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * Controlador REST para la generación y descarga de reportes en formato PDF.
 * <p>
 * Este controlador expone endpoints para generar diversos tipos de reportes del sistema
 * de gestión de taller automotriz MotorPlus, incluyendo reportes simples, intermedios,
 * complejos y estadísticos.
 * </p>
 * <p>
 * Todos los endpoints retornan archivos PDF descargables con el tipo de contenido
 * {@code application/pdf} y encabezados HTTP apropiados para forzar la descarga.
 * </p>
 *
 * @author Sistema MotorPlus
 * @version 1.0
 * @since 2025-01-17
 */
@RestController
@RequestMapping("/api/reportes/pdf")
@CrossOrigin(origins = "*")
public class ReportePDFController {

    private final ReportePDFService reportePDFService;

    /**
     * Constructor del controlador con inyección de dependencias.
     *
     * @param reportePDFService servicio encargado de generar los PDFs
     */
    public ReportePDFController(ReportePDFService reportePDFService) {
        this.reportePDFService = reportePDFService;
    }

    // =====================================================
    // REPORTES SIMPLES
    // =====================================================

    /**
     * Genera y descarga un reporte PDF con el listado completo de clientes registrados.
     * <p>
     * El reporte incluye: ID, nombre completo, teléfono y correo electrónico de cada cliente.
     * </p>
     *
     * @return ResponseEntity con el archivo PDF como arreglo de bytes
     * @throws RuntimeException si ocurre un error al generar el PDF
     *
     * <p><b>Ejemplo de uso:</b></p>
     * <pre>
     * GET /api/reportes/pdf/clientes
     * </pre>
     *
     * <p><b>Respuesta:</b> Archivo PDF descargable "Reporte_Clientes.pdf"</p>
     */
    @GetMapping("/clientes")
    public ResponseEntity<byte[]> descargarReporteClientes() {
        byte[] pdfBytes = reportePDFService.generarPDFClientes();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "Reporte_Clientes.pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    /**
     * Genera y descarga un reporte PDF con el listado de vehículos registrados.
     * <p>
     * El reporte incluye: placa, marca, modelo y nombre del propietario de cada vehículo.
     * </p>
     *
     * @return ResponseEntity con el archivo PDF como arreglo de bytes
     * @throws RuntimeException si ocurre un error al generar el PDF
     *
     * <p><b>Ejemplo de uso:</b></p>
     * <pre>
     * GET /api/reportes/pdf/vehiculos
     * </pre>
     *
     * <p><b>Respuesta:</b> Archivo PDF descargable "Reporte_Vehiculos.pdf"</p>
     */
    @GetMapping("/vehiculos")
    public ResponseEntity<byte[]> descargarReporteVehiculos() {
        byte[] pdfBytes = reportePDFService.generarPDFVehiculos();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "Reporte_Vehiculos.pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    /**
     * Genera y descarga un reporte PDF con el catálogo de servicios disponibles.
     * <p>
     * El reporte incluye: nombre del servicio, descripción y precio base.
     * </p>
     *
     * @return ResponseEntity con el archivo PDF como arreglo de bytes
     * @throws RuntimeException si ocurre un error al generar el PDF
     *
     * <p><b>Ejemplo de uso:</b></p>
     * <pre>
     * GET /api/reportes/pdf/servicios
     * </pre>
     *
     * <p><b>Respuesta:</b> Archivo PDF descargable "Catalogo_Servicios.pdf"</p>
     */
    @GetMapping("/servicios")
    public ResponseEntity<byte[]> descargarReporteServicios() {
        byte[] pdfBytes = reportePDFService.generarPDFServicios();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "Catalogo_Servicios.pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    // =====================================================
    // REPORTES INTERMEDIOS
    // =====================================================

    /**
     * Genera y descarga un reporte PDF de órdenes de trabajo filtradas por rango de fechas.
     * <p>
     * El reporte incluye estadísticas del período (completadas, en proceso, pendientes)
     * y un listado detallado de cada orden con: ID, vehículo, fechas, diagnóstico y estado.
     * </p>
     *
     * @param fechaInicio fecha de inicio del período (formato: yyyy-MM-dd)
     * @param fechaFin    fecha de fin del período (formato: yyyy-MM-dd)
     * @return ResponseEntity con el archivo PDF como arreglo de bytes
     * @throws RuntimeException si ocurre un error al generar el PDF
     *
     * <p><b>Ejemplo de uso:</b></p>
     * <pre>
     * GET /api/reportes/pdf/ordenes-trabajo?fechaInicio=2025-01-01&fechaFin=2025-01-31
     * </pre>
     *
     * <p><b>Respuesta:</b> Archivo PDF descargable "Reporte_Ordenes_Trabajo.pdf"</p>
     */
    @GetMapping("/ordenes-trabajo")
    public ResponseEntity<byte[]> descargarReporteOrdenesTrabajo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {

        byte[] pdfBytes = reportePDFService.generarPDFOrdenesTrabajo(fechaInicio, fechaFin);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "Reporte_Ordenes_Trabajo.pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    /**
     * Genera y descarga un reporte PDF de facturas emitidas para un cliente específico.
     * <p>
     * El reporte incluye: número de factura, fecha de emisión y monto total de cada factura,
     * además del total acumulado de todas las facturas del cliente.
     * </p>
     *
     * @param idCliente identificador único del cliente
     * @return ResponseEntity con el archivo PDF como arreglo de bytes
     * @throws RuntimeException si ocurre un error al generar el PDF
     *
     * <p><b>Ejemplo de uso:</b></p>
     * <pre>
     * GET /api/reportes/pdf/facturas-cliente/5
     * </pre>
     *
     * <p><b>Respuesta:</b> Archivo PDF descargable "Facturas_Cliente_5.pdf"</p>
     */
    @GetMapping("/facturas-cliente/{idCliente}")
    public ResponseEntity<byte[]> descargarReporteFacturasCliente(
            @PathVariable int idCliente) {

        byte[] pdfBytes = reportePDFService.generarPDFFacturasCliente(idCliente);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "Facturas_Cliente_" + idCliente + ".pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    /**
     * Genera y descarga un reporte PDF de movimientos de inventario para un repuesto específico.
     * <p>
     * El reporte incluye: ID del movimiento, tipo (entrada/salida/ajuste), cantidad,
     * fecha del movimiento y nombre del repuesto.
     * </p>
     *
     * @param idRepuesto identificador único del repuesto
     * @return ResponseEntity con el archivo PDF como arreglo de bytes
     * @throws RuntimeException si ocurre un error al generar el PDF
     *
     * <p><b>Ejemplo de uso:</b></p>
     * <pre>
     * GET /api/reportes/pdf/inventario-repuesto/12
     * </pre>
     *
     * <p><b>Respuesta:</b> Archivo PDF descargable "Inventario_Repuesto_12.pdf"</p>
     */
    @GetMapping("/inventario-repuesto/{idRepuesto}")
    public ResponseEntity<byte[]> descargarReporteInventarioRepuesto(
            @PathVariable int idRepuesto) {

        //byte[] pdfBytes = reportePDFService.generarPDFMovimientosInventario(idRepuesto);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "Inventario_Repuesto_" + idRepuesto + ".pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
          return null;
        //return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
    // =====================================================
    // REPORTES COMPLEJOS
    // =====================================================


    /**
     * Genera y descarga un reporte PDF de repuestos utilizados en una orden de servicio.
     * <p>
     * El reporte incluye: ID de orden de servicio, nombre del repuesto,
     * cantidad utilizada, tipo de movimiento y fecha.
     * </p>
     *
     * @param idOrdenServicio identificador único de la orden de servicio
     * @return ResponseEntity con el archivo PDF como arreglo de bytes
     * @throws RuntimeException si ocurre un error al generar el PDF
     *
     * <p><b>Ejemplo de uso:</b></p>
     * <pre>
     * GET /api/reportes/pdf/repuestos-orden/45
     * </pre>
     *
     * <p><b>Respuesta:</b> Archivo PDF descargable "Repuestos_Orden_45.pdf"</p>
     */
    @GetMapping("/repuestos-orden/{idOrdenServicio}")
    public ResponseEntity<byte[]> descargarReporteRepuestosOrden(
            @PathVariable int idOrdenServicio) {

        //byte[] pdfBytes = reportePDFService.generarPDFRepuestosPorOrdenServicio(idOrdenServicio);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "Repuestos_Orden_" + idOrdenServicio + ".pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        return null;
        //return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }


    /**
     * Genera y descarga un reporte PDF de productividad de mecánicos supervisores.
     * <p>
     * El reporte incluye un resumen del total de supervisores y una tabla detallada
     * con: nombre del supervisor, total de supervisiones realizadas y total de servicios ejecutados.
     * Además, incluye totales consolidados globales.
     * </p>
     *
     * @return ResponseEntity con el archivo PDF como arreglo de bytes
     * @throws RuntimeException si ocurre un error al generar el PDF
     *
     * <p><b>Ejemplo de uso:</b></p>
     * <pre>
     * GET /api/reportes/pdf/productividad-supervisores
     * </pre>
     *
     * <p><b>Respuesta:</b> Archivo PDF descargable "Productividad_Supervisores.pdf"</p>
     */
    @GetMapping("/productividad-supervisores")
    public ResponseEntity<byte[]> descargarReporteProductividadSupervisores() {
        byte[] pdfBytes = reportePDFService.generarPDFProductividadSupervisores();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "Productividad_Supervisores.pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    // =====================================================
    // REPORTES ESTADÍSTICOS
    // =====================================================

    /**
     * Genera y descarga un reporte PDF estadístico de los servicios más solicitados.
     * <p>
     * El reporte presenta un ranking de servicios ordenados por cantidad de solicitudes,
     * mostrando el nombre del servicio y el total de veces que ha sido solicitado.
     * Útil para identificar servicios de mayor demanda y optimizar recursos.
     * </p>
     *
     * @return ResponseEntity con el archivo PDF como arreglo de bytes
     * @throws RuntimeException si ocurre un error al generar el PDF
     *
     * <p><b>Ejemplo de uso:</b></p>
     * <pre>
     * GET /api/reportes/pdf/estadisticas/servicios-mas-solicitados
     * </pre>
     *
     * <p><b>Respuesta:</b> Archivo PDF descargable "Servicios_Mas_Solicitados.pdf"</p>
     */
    @GetMapping("/estadisticas/servicios-mas-solicitados")
    public ResponseEntity<byte[]> descargarReporteServiciosMasSolicitados() {
        byte[] pdfBytes = reportePDFService.generarPDFServiciosMasSolicitados();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "Servicios_Mas_Solicitados.pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    /**
     * Genera y descarga un reporte PDF estadístico de los repuestos más utilizados.
     * <p>
     * El reporte presenta un ranking de repuestos ordenados por frecuencia de uso,
     * mostrando el nombre del repuesto y el total de veces que ha sido utilizado.
     * Útil para gestión de inventario y planificación de compras.
     * </p>
     *
     * @return ResponseEntity con el archivo PDF como arreglo de bytes
     * @throws RuntimeException si ocurre un error al generar el PDF
     *
     * <p><b>Ejemplo de uso:</b></p>
     * <pre>
     * GET /api/reportes/pdf/estadisticas/repuestos-mas-usados
     * </pre>
     *
     * <p><b>Respuesta:</b> Archivo PDF descargable "Repuestos_Mas_Usados.pdf"</p>
     */
    @GetMapping("/estadisticas/repuestos-mas-usados")
    public ResponseEntity<byte[]> descargarReporteRepuestosMasUsados() {
        byte[] pdfBytes = reportePDFService.generarPDFRepuestosMasUsados();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "Repuestos_Mas_Usados.pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    /**
     * Genera y descarga un reporte PDF estadístico de análisis de ingresos mensuales.
     * <p>
     * El reporte muestra los ingresos totales agrupados por mes a partir de facturas pagadas,
     * presentando: mes (formato YYYY-MM) y total de ingresos.
     * Útil para análisis financiero y proyecciones de negocio.
     * </p>
     *
     * @return ResponseEntity con el archivo PDF como arreglo de bytes
     * @throws RuntimeException si ocurre un error al generar el PDF
     *
     * <p><b>Ejemplo de uso:</b></p>
     * <pre>
     * GET /api/reportes/pdf/estadisticas/ingresos-mensuales
     * </pre>
     *
     * <p><b>Respuesta:</b> Archivo PDF descargable "Ingresos_Mensuales.pdf"</p>
     */
    @GetMapping("/estadisticas/ingresos-mensuales")
    public ResponseEntity<byte[]> descargarReporteIngresosMensuales() {
        byte[] pdfBytes = reportePDFService.generarPDFIngresosMensuales();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "Ingresos_Mensuales.pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    // =====================================================
    // ENDPOINT DE SALUD Y PRUEBA
    // =====================================================

    /**
     * Endpoint de prueba para verificar que el servicio de reportes está operativo.
     * <p>
     * Retorna un mensaje simple indicando que el servicio está funcionando correctamente.
     * </p>
     *
     * @return ResponseEntity con mensaje de confirmación
     *
     * <p><b>Ejemplo de uso:</b></p>
     * <pre>
     * GET /api/reportes/pdf/health
     * </pre>
     *
     * <p><b>Respuesta:</b></p>
     * <pre>
     * {
     *   "status": "OK",
     *   "message": "Servicio de reportes PDF operativo"
     * }
     * </pre>
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Servicio de reportes PDF operativo - MotorPlus");
    }
}
