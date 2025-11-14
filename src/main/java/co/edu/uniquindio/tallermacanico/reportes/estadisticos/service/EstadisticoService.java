package co.edu.uniquindio.tallermacanico.reportes.estadisticos.service;

import co.edu.uniquindio.tallermacanico.reportes.estadisticos.dto.ServicioEstadisticoDTO;
import co.edu.uniquindio.tallermacanico.reportes.repository.ReporteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio encargado de gestionar los reportes estadísticos de la aplicación.
 * <p>
 * Este servicio invoca las consultas definidas en {@link ReporteRepository}
 * y devuelve los resultados en forma de DTOs listos para ser consumidos
 * por la capa de presentación o el frontend.
 * </p>
 *
 * <h2>Responsabilidades:</h2>
 * <ul>
 *     <li>Obtener la distribución de servicios más solicitados.</li>
 * </ul>
 *
 * @author Daniel
 * @version 1.0
 */
@Service
public class EstadisticoService {

    private final ReporteRepository reporteRepository;

    /**
     * Constructor que inyecta el repositorio de reportes.
     *
     * @param reporteRepository instancia de {@link ReporteRepository}
     */
    public EstadisticoService(ReporteRepository reporteRepository) {
        this.reporteRepository = reporteRepository;
    }

    /**
     * Obtiene la distribución de servicios más solicitados en las órdenes de trabajo.
     *
     * @return lista de {@link ServicioEstadisticoDTO} con los servicios más solicitados
     */
    public List<ServicioEstadisticoDTO> getDistribucionServicios() {
        return reporteRepository.obtenerServiciosMasSolicitados();
    }
}
