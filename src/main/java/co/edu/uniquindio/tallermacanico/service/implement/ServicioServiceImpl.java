package co.edu.uniquindio.tallermacanico.service.implement;

import co.edu.uniquindio.tallermacanico.model.Servicio;
import co.edu.uniquindio.tallermacanico.repository.ServicioRepository;
import co.edu.uniquindio.tallermacanico.service.ServicioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class ServicioServiceImpl implements ServicioService {

    private final ServicioRepository servicioRepository;

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
        int idGenerado = servicioRepository.registrarServicio(servicio);
        servicio.setIdServicio(idGenerado);
    }


    @Override
    public void eliminarServicio(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor que cero");
        }
        servicioRepository.eliminarServicio(id);
    }
    @Override
    public void actualizarPrecioBase(int idServicio, double nuevoPrecio) {
        if (idServicio <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor que cero");
        }
        if (nuevoPrecio <= 0) {
            throw new IllegalArgumentException("El precio base debe ser mayor que cero");
        }
        Servicio existente = servicioRepository.buscarPorId(idServicio);
        if (existente == null) {
            throw new IllegalArgumentException("El servicio con ID " + idServicio + " no existe");
        }
        servicioRepository.actualizarPrecioBase(idServicio, nuevoPrecio);
    }
}
