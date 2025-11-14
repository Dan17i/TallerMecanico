package co.edu.uniquindio.tallermacanico.reportes.estadisticos.controller;


import co.edu.uniquindio.tallermacanico.reportes.estadisticos.dto.IngresoMensualDTO;
import co.edu.uniquindio.tallermacanico.reportes.estadisticos.service.EstadisticoIngresoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador REST para exponer el reporte estadístico de ingresos mensuales.
 */
@RestController
@RequestMapping("/api/reportes/estadisticos")
public class EstadisticoIngresoController {

    private final EstadisticoIngresoService estadisticoIngresoService;

    public EstadisticoIngresoController(EstadisticoIngresoService estadisticoIngresoService) {
        this.estadisticoIngresoService = estadisticoIngresoService;
    }

    /**
     * Endpoint para obtener los ingresos agrupados por mes.
     *
     * @return lista de {@link IngresoMensualDTO} en formato JSON
     */
    @GetMapping("/ingresos")
    public List<IngresoMensualDTO> ingresosPorMes() {
        return estadisticoIngresoService.getIngresosPorMes();
    }
}
