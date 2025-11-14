package co.edu.uniquindio.tallermacanico.reportes.estadisticos.controller;

import co.edu.uniquindio.tallermacanico.reportes.estadisticos.dto.ServicioEstadisticoDTO;
import co.edu.uniquindio.tallermacanico.reportes.estadisticos.service.EstadisticoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador REST para exponer los reportes estadísticos.
 * <p>
 * Este controlador expone endpoints que devuelven datos estadísticos
 * listos para ser consumidos por el frontend y graficados.
 * </p>
 *
 * <h2>Endpoints:</h2>
 * <ul>
 *     <li><b>/api/reportes/estadisticos/servicios</b> → Distribución de servicios más solicitados.</li>
 * </ul>
 *
 * @author Daniel
 * @version 1.0
 */
@RestController
@RequestMapping("/api/reportes/estadisticos")
public class EstadisticoController {

    private final EstadisticoService estadisticoService;

    /**
     * Constructor que inyecta el servicio estadístico.
     *
     * @param estadisticoService instancia de {@link EstadisticoService}
     */
    public EstadisticoController(EstadisticoService estadisticoService) {
        this.estadisticoService = estadisticoService;
    }

    /**
     * Endpoint para obtener la distribución de servicios más solicitados.
     *
     * @return lista de {@link ServicioEstadisticoDTO} en formato JSON
     */
    @GetMapping("/servicios")
    public List<ServicioEstadisticoDTO> distribucionServicios() {
        return estadisticoService.getDistribucionServicios();
    }
}

