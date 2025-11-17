package co.edu.uniquindio.tallermacanico.service.implement;

import co.edu.uniquindio.tallermacanico.model.Especialidad;
import co.edu.uniquindio.tallermacanico.repository.EspecialidadRepository;
import co.edu.uniquindio.tallermacanico.service.EspecialidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación del servicio de Especialidad.
 */
@RequiredArgsConstructor
@Service
public class EspecialidadServiceImpl implements EspecialidadService {

    private final EspecialidadRepository repository;

    @Override
    public List<Especialidad> listarEspecialidades() {
        return repository.listarEspecialidades();
    }

    @Override
    public Especialidad buscarPorId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor que cero");
        }
        return repository.buscarPorId(id);
    }

    @Override
    public int registrarEspecialidad(Especialidad especialidad) {
        if (especialidad.getNombre() == null || especialidad.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre de la especialidad es obligatorio");
        }
        return repository.registrarEspecialidad(especialidad);
    }

    @Override
    public boolean eliminarEspecialidad(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor que cero");
        }
        return repository.eliminarEspecialidad(id);
    }
}
