package co.edu.uniquindio.tallermacanico.service.implement;


import co.edu.uniquindio.tallermacanico.model.MovimientoInventario;
import co.edu.uniquindio.tallermacanico.repository.MovimientoInventarioRepository;
import co.edu.uniquindio.tallermacanico.service.MovimientoInventarioService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación del servicio para la entidad MovimientoInventario.
 * Contiene validaciones de negocio y delega operaciones al repositorio.
 */
@Service
public class MovimientoInventarioServiceImpl implements MovimientoInventarioService {

    private final MovimientoInventarioRepository repository;

    public MovimientoInventarioServiceImpl(MovimientoInventarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<MovimientoInventario> listarMovimientos() {
        return repository.listarMovimientos();
    }

    @Override
    public MovimientoInventario buscarPorId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID del movimiento debe ser mayor que cero");
        }
        return repository.buscarPorId(id);
    }

    @Override
    public void registrarMovimiento(MovimientoInventario movimiento) {
        if (movimiento.getIdRepuesto() <= 0) {
            throw new IllegalArgumentException("El ID del repuesto es obligatorio y debe ser válido");
        }
        if (movimiento.getTipoMovimiento() == null || movimiento.getTipoMovimiento().isBlank()) {
            throw new IllegalArgumentException("El tipo de movimiento es obligatorio");
        }
        if (!movimiento.getTipoMovimiento().equalsIgnoreCase("entrada") &&
                !movimiento.getTipoMovimiento().equalsIgnoreCase("salida")) {
            throw new IllegalArgumentException("El tipo de movimiento debe ser 'entrada' o 'salida'");
        }
        if (movimiento.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        }
        if (movimiento.getFechaMovimiento() == null) {
            throw new IllegalArgumentException("La fecha del movimiento es obligatoria");
        }
        repository.registrarMovimiento(movimiento);
    }

    @Override
    public void eliminarMovimiento(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID del movimiento debe ser mayor que cero");
        }
        repository.eliminarMovimiento(id);
    }
}

