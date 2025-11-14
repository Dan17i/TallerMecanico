package co.edu.uniquindio.tallermacanico.reportes.estadisticos.controller;

import co.edu.uniquindio.tallermacanico.reportes.estadisticos.dto.RepuestoEstadisticoDTO;
import co.edu.uniquindio.tallermacanico.reportes.estadisticos.service.EstadisticoRepuestoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador REST para exponer el reporte estadístico de repuestos más usados.
 * <p>
 * Este controlador utiliza el servicio {@link EstadisticoRepuestoService}
 * para obtener los datos y devolverlos en formato JSON.
 * </p>
 *
 * <h2>Endpoints:</h2>
 * <ul>
 *     <li><b>/api/reportes/estadisticos/repuestos</b> → Distribución de repuestos más usados.</li>
 * </ul>
 *
 * @author Daniel
 * @version 1.0
 */
@RestController
@RequestMapping("/api/reportes/estadisticos")
public class EstadisticoRepuestoController {

    private final EstadisticoRepuestoService estadisticoRepuestoService;

    /**
     * Constructor que inyecta el servicio de repuestos estadísticos.
     *
     * @param estadisticoRepuestoService instancia de {@link EstadisticoRepuestoService}
     */
    public EstadisticoRepuestoController(EstadisticoRepuestoService estadisticoRepuestoService) {
        this.estadisticoRepuestoService = estadisticoRepuestoService;
    }

    /**
     * Endpoint para obtener la distribución de repuestos más usados.
     *
     * @return lista de {@link RepuestoEstadisticoDTO} en formato JSON
     */
    @GetMapping("/repuestos")
    public List<RepuestoEstadisticoDTO> distribucionRepuestos() {
        return estadisticoRepuestoService.getDistribucionRepuestos();
    }
}

