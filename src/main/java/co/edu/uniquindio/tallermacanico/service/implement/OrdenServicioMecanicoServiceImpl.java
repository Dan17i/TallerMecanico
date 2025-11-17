package co.edu.uniquindio.tallermacanico.service.implement;


import co.edu.uniquindio.tallermacanico.model.OrdenServicioMecanico;
import co.edu.uniquindio.tallermacanico.repository.OrdenServicioMecanicoRepository;
import co.edu.uniquindio.tallermacanico.service.OrdenServicioMecanicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación del servicio para la entidad OrdenServicioMecanico.
 * Contiene validaciones de negocio y delega operaciones al repositorio.
 */
@RequiredArgsConstructor
@Service
public class OrdenServicioMecanicoServiceImpl implements OrdenServicioMecanicoService {

    private final OrdenServicioMecanicoRepository repository;

    /**
     * Lista todas las asignaciones de mecánicos en servicios.
     * @return lista de asignaciones
     */
    @Override
    public List<OrdenServicioMecanico> listarAsignaciones() {
        return repository.listarAsignaciones();
    }

    /**
     * Busca una asignación específica por ID de orden-servicio y ID de mecánico.
     * @param idOrdenServicio identificador del servicio
     * @param idMecanico identificador del mecánico
     * @return asignación encontrada
     * @throws IllegalArgumentException si los IDs son inválidos
     */
    @Override
    public OrdenServicioMecanico buscarPorOrdenYPorMecanico(int idOrdenServicio, int idMecanico) {
        if (idOrdenServicio <= 0 || idMecanico <= 0) {
            throw new IllegalArgumentException("Los IDs deben ser mayores que cero");
        }
        return repository.buscarPorOrdenYPorMecanico(idOrdenServicio, idMecanico);
    }

    /**
     * Registra una nueva asignación de mecánico a un servicio, validando los campos obligatorios.
     * @param asignacion objeto con los datos de la asignación
     * @throws IllegalArgumentException si los datos son inválidos
     */
    @Override
    public void registrarAsignacion(OrdenServicioMecanico asignacion) {
        if (asignacion.getIdOrdenServicio() <= 0) {
            throw new IllegalArgumentException("El ID del servicio es obligatorio y debe ser válido");
        }
        if (asignacion.getIdMecanico() <= 0) {
            throw new IllegalArgumentException("El ID del mecánico es obligatorio y debe ser válido");
        }
        if (asignacion.getIdEspecialidad() <= 0) {
            throw new IllegalArgumentException("La especialidad es obligatoria y debe ser válida");
        }
        if (asignacion.getRolEnServicio() == null || asignacion.getRolEnServicio().isBlank()) {
            throw new IllegalArgumentException("El rol en el servicio es obligatorio");
        }
        repository.registrarAsignacion(asignacion);
    }

    /**
     * Elimina una asignación por ID de orden-servicio y ID de mecánico.
     * @param idOrdenServicio identificador del servicio
     * @param idMecanico identificador del mecánico
     * @throws IllegalArgumentException si los IDs son inválidos
     */
    @Override
    public void eliminarAsignacion(int idOrdenServicio, int idMecanico) {
        if (idOrdenServicio <= 0 || idMecanico <= 0) {
            throw new IllegalArgumentException("Los IDs deben ser mayores que cero");
        }
        repository.eliminarAsignacion(idOrdenServicio, idMecanico);
    }
}

