package co.edu.uniquindio.tallermacanico.service.implement;



import co.edu.uniquindio.tallermacanico.model.Servicio;
import co.edu.uniquindio.tallermacanico.repository.ServicioRepository;
import co.edu.uniquindio.tallermacanico.service.ServicioService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioServiceImpl implements ServicioService {

    private final ServicioRepository servicioRepository;

    public ServicioServiceImpl(ServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }

    @Override
    public List<Servicio> listarServicios() {
        return servicioRepository.listarServicios();
    }

    @Override
    public Servicio buscarPorId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor que cero");
        }
        return servicioRepository.buscarPorId(id);
    }

    @Override
    public void registrarServicio(Servicio servicio) {
        if (servicio.getNombre() == null || servicio.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del servicio es obligatorio");
        }
        if (servicio.getPrecioBase() <= 0) {
            throw new IllegalArgumentException("El precio base debe ser mayor que cero");
        }
        servicioRepository.registrarServicio(servicio);
    }

    @Override
    public void eliminarServicio(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor que cero");
        }
        servicioRepository.eliminarServicio(id);
    }
}
