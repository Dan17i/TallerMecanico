package co.edu.uniquindio.tallermacanico.reportes.estadisticos.service;


import co.edu.uniquindio.tallermacanico.reportes.estadisticos.dto.IngresoMensualDTO;
import co.edu.uniquindio.tallermacanico.reportes.repository.ReporteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio encargado de gestionar el reporte estadístico de ingresos mensuales.
 */
@Service
public class EstadisticoIngresoService {

    private final ReporteRepository reporteRepository;

    public EstadisticoIngresoService(ReporteRepository reporteRepository) {
        this.reporteRepository = reporteRepository;
    }

    /**
     * Obtiene los ingresos agrupados por mes.
     *
     * @return lista de {@link IngresoMensualDTO} con mes y total de ingresos
     */
    public List<IngresoMensualDTO> getIngresosPorMes() {
        return reporteRepository.obtenerIngresosPorMes();
    }
}

