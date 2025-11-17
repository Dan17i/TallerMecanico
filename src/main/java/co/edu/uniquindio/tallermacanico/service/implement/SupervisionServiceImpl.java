package co.edu.uniquindio.tallermacanico.service.implement;


import co.edu.uniquindio.tallermacanico.model.Supervision;
import co.edu.uniquindio.tallermacanico.repository.SupervisionRepository;
import co.edu.uniquindio.tallermacanico.service.SupervisionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación del servicio para la entidad Supervision.
 * Contiene validaciones de negocio y delega operaciones al repositorio.
 */
@RequiredArgsConstructor
@Service
public class SupervisionServiceImpl implements SupervisionService {

    private final SupervisionRepository repository;

    @Override
    public List<Supervision> listarSupervisiones() {
        return repository.listarSupervisiones();
    }

    @Override
    public Supervision buscarSupervision(int idOrdenServicio, int idSupervisor, int idSupervisado) {
        if (idOrdenServicio <= 0 || idSupervisor <= 0 || idSupervisado <= 0) {
            throw new IllegalArgumentException("Todos los IDs deben ser mayores que cero");
        }
        return repository.buscarSupervision(idOrdenServicio, idSupervisor, idSupervisado);
    }

    @Override
    public void registrarSupervision(Supervision supervision) {
        if (supervision.getIdOrdenServicio() <= 0) {
            throw new IllegalArgumentException("El ID del servicio es obligatorio y debe ser válido");
        }
        if (supervision.getIdMecanicoSupervisor() <= 0) {
            throw new IllegalArgumentException("El ID del supervisor es obligatorio y debe ser válido");
        }
        if (supervision.getIdMecanicoSupervisado() <= 0) {
            throw new IllegalArgumentException("El ID del supervisado es obligatorio y debe ser válido");
        }
        if (supervision.getIdEspecialidad() <= 0) {
            throw new IllegalArgumentException("La especialidad es obligatoria y debe ser válida");
        }
        repository.registrarSupervision(supervision);
    }

    @Override
    public void eliminarSupervision(int idOrdenServicio, int idSupervisor, int idSupervisado) {
        if (idOrdenServicio <= 0 || idSupervisor <= 0 || idSupervisado <= 0) {
            throw new IllegalArgumentException("Todos los IDs deben ser mayores que cero");
        }
        repository.eliminarSupervision(idOrdenServicio, idSupervisor, idSupervisado);
    }
}

