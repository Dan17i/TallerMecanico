package co.edu.uniquindio.tallermacanico.reportes.estadisticos.service;

import co.edu.uniquindio.tallermacanico.reportes.estadisticos.dto.RepuestoEstadisticoDTO;
import co.edu.uniquindio.tallermacanico.reportes.estadisticos.dto.ServicioEstadisticoDTO;
import co.edu.uniquindio.tallermacanico.reportes.repository.ReporteRepository;

import java.util.List;

public class EstadisticoRepuestoService {
    private final ReporteRepository reporteRepository;

    public EstadisticoRepuestoService(ReporteRepository reporteRepository) {
        this.reporteRepository = reporteRepository;
    }


    /**
     * Obtiene la distribución de servicios más solicitados.
     *
     * @return lista de {@link ServicioEstadisticoDTO}
     */
    public List<ServicioEstadisticoDTO> getDistribucionServicios() {
        return reporteRepository.obtenerServiciosMasSolicitados();
    }

    /**
     * Obtiene la distribución de repuestos más usados.
     *
     * @return lista de {@link RepuestoEstadisticoDTO}
     */
    public List<RepuestoEstadisticoDTO> getDistribucionRepuestos() {
        return reporteRepository.obtenerRepuestosMasUsados();
    }
}
