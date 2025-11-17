package co.edu.uniquindio.tallermacanico.service.implement;


import co.edu.uniquindio.tallermacanico.model.Vehiculo;
import co.edu.uniquindio.tallermacanico.repository.VehiculoRepository;
import co.edu.uniquindio.tallermacanico.service.VehiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación del servicio para la entidad Vehiculo.
 * Contiene validaciones de negocio y delega operaciones al repositorio.
 */
@RequiredArgsConstructor
@Service
public class VehiculoServiceImpl implements VehiculoService {

    private final VehiculoRepository vehiculoRepository;

    @Override
    public List<Vehiculo> listarVehiculos() {
        return vehiculoRepository.listarVehiculos();
    }

    @Override
    public Vehiculo buscarPorId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID del vehículo debe ser mayor que cero");
        }
        return vehiculoRepository.buscarPorId(id);
    }

    @Override
    public void registrarVehiculo(Vehiculo vehiculo) {
        if (vehiculo.getPlaca() == null || vehiculo.getPlaca().isBlank()) {
            throw new IllegalArgumentException("La placa del vehículo es obligatoria");
        }
        if (vehiculo.getIdCliente() <= 0) {
            throw new IllegalArgumentException("El ID del cliente debe ser válido");
        }
        if (vehiculo.getAnio() < 1900 || vehiculo.getAnio() > 2100) {
            throw new IllegalArgumentException("El año del vehículo debe estar entre 1900 y 2100");
        }
        vehiculoRepository.registrarVehiculo(vehiculo);
    }

    @Override
    public void eliminarVehiculo(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID del vehículo debe ser mayor que cero");
        }
        vehiculoRepository.eliminarVehiculo(id);
    }
}

