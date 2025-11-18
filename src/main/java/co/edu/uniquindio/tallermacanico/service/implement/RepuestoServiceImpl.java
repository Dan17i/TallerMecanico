package co.edu.uniquindio.tallermacanico.service.implement;

import co.edu.uniquindio.tallermacanico.model.Repuesto;
import co.edu.uniquindio.tallermacanico.repository.RepuestoRepository;
import co.edu.uniquindio.tallermacanico.service.RepuestoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación del servicio {@link RepuestoService}.
 * Aplica lógica de negocio y delega operaciones al repositorio.
 */
@Service
@RequiredArgsConstructor
public class RepuestoServiceImpl implements RepuestoService {

    private final RepuestoRepository repuestoRepository;

    @Override
    public List<Repuesto> listarRepuestos() {
        return repuestoRepository.listarRepuestos();
    }

    @Override
    public Repuesto registrarRepuesto(Repuesto repuesto) {
        int id = repuestoRepository.crearRepuesto(repuesto);
        repuesto.setIdRepuesto(id);
        return repuesto;
    }


    @Override
    public Repuesto obtenerPorId(int id) {
        return repuestoRepository.buscarPorId(id);
    }

    @Override
    public void eliminarRepuesto(int id) {
        repuestoRepository.eliminarRepuesto(id);
    }

    @Override
    public void actualizarStock(int id, double nuevoStock) {
        repuestoRepository.actualizarStock(id, nuevoStock);
    }
}

