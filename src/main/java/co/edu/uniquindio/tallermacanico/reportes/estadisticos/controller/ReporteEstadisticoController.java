package co.edu.uniquindio.tallermacanico.reportes.estadisticos.controller;

import co.edu.uniquindio.tallermacanico.reportes.estadisticos.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reportes/estadisticos")
public class ReporteEstadisticoController {

    private final EstadisticoIngresoService ingresoService;
    private final EstadisticoRepuestoService repuestoService;
    private final EstadisticoService servicioService;
    private final ReportePDFService reportePDFService;

    @GetMapping("/ingresos/pdf")
    public ResponseEntity<byte[]> descargarIngresosMensuales() {
        byte[] pdf = reportePDFService.generarPDFIngresosMensuales(ingresoService.getIngresosPorMes());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ingresos.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @GetMapping("/servicios/pdf")
    public ResponseEntity<byte[]> descargarServiciosSolicitados() {
        byte[] pdf = reportePDFService.generarPDFServiciosSolicitados(servicioService.getDistribucionServicios());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=servicios.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @GetMapping("/repuestos/pdf")
    public ResponseEntity<byte[]> descargarRepuestosUsados() {
        byte[] pdf = reportePDFService.generarPDFRepuestosUsados(repuestoService.getDistribucionRepuestos());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=repuestos.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}

