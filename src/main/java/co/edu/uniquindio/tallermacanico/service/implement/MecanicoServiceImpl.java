package co.edu.uniquindio.tallermacanico.service.implement;


import co.edu.uniquindio.tallermacanico.model.Mecanico;
import co.edu.uniquindio.tallermacanico.repository.MecanicoRepository;
import co.edu.uniquindio.tallermacanico.service.MecanicoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación del servicio para la entidad Mecanico.
 * Contiene validaciones de negocio y delega operaciones al repositorio.
 */
@Service
public class MecanicoServiceImpl implements MecanicoService {

    private final MecanicoRepository mecanicoRepository;

    public MecanicoServiceImpl(MecanicoRepository mecanicoRepository) {
        this.mecanicoRepository = mecanicoRepository;
    }

    /**
     * Lista todos los mecánicos registrados en la base de datos.
     * @return lista de mecánicos
     */
    @Override
    public List<Mecanico> listarMecanicos() {
        return mecanicoRepository.listarMecanicos();
    }

    /**
     * Busca un mecánico por su ID, validando que sea positivo.
     * @param id identificador del mecánico
     * @return mecánico encontrado
     * @throws IllegalArgumentException si el ID es inválido
     */
    @Override
    public Mecanico buscarPorId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID del mecánico debe ser mayor que cero");
        }
        return mecanicoRepository.buscarPorId(id);
    }

    /**
     * Registra un nuevo mecánico validando los campos obligatorios.
     * @param mecanico objeto con los datos del mecánico
     * @throws IllegalArgumentException si los datos son inválidos
     */
    @Override
    public void registrarMecanico(Mecanico mecanico) {
        if (mecanico.getNombre() == null || mecanico.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del mecánico es obligatorio");
        }
        if (mecanico.getApellido() == null || mecanico.getApellido().isBlank()) {
            throw new IllegalArgumentException("El apellido del mecánico es obligatorio");
        }
        if (mecanico.getExperienciaAnios() < 0 || mecanico.getExperienciaAnios() > 99) {
            throw new IllegalArgumentException("La experiencia debe estar entre 0 y 99 años");
        }
        mecanicoRepository.registrarMecanico(mecanico);
    }

    /**
     * Elimina un mecánico por su ID, validando que sea positivo.
     * @param id identificador del mecánico
     * @throws IllegalArgumentException si el ID es inválido
     */
    @Override
    public void eliminarMecanico(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID del mecánico debe ser mayor que cero");
        }
        boolean eliminado = mecanicoRepository.eliminarMecanicoConRelaciones(id);
        if (!eliminado) {
            throw new IllegalStateException("No se pudo eliminar el mecánico ni sus relaciones");
        }

    }
}

