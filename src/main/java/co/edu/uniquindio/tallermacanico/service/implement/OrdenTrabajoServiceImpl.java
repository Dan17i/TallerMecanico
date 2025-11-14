package co.edu.uniquindio.tallermacanico.service.implement;


import co.edu.uniquindio.tallermacanico.model.OrdenTrabajo;
import co.edu.uniquindio.tallermacanico.repository.OrdenTrabajoRepository;
import co.edu.uniquindio.tallermacanico.service.OrdenTrabajoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación del servicio para la entidad OrdenTrabajo.
 * Contiene validaciones de negocio y delega operaciones al repositorio.
 */
@Service
public class OrdenTrabajoServiceImpl implements OrdenTrabajoService {

    private final OrdenTrabajoRepository ordenTrabajoRepository;

    public OrdenTrabajoServiceImpl(OrdenTrabajoRepository ordenTrabajoRepository) {
        this.ordenTrabajoRepository = ordenTrabajoRepository;
    }

    /**
     * Lista todas las órdenes de trabajo registradas en la base de datos.
     * @return lista de órdenes de trabajo
     */
    @Override
    public List<OrdenTrabajo> listarOrdenesTrabajo() {
        return ordenTrabajoRepository.listarOrdenesTrabajo();
    }

    /**
     * Busca una orden de trabajo por su ID, validando que sea positivo.
     * @param id identificador de la orden
     * @return orden encontrada
     * @throws IllegalArgumentException si el ID es inválido
     */
    @Override
    public OrdenTrabajo buscarPorId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID de la orden de trabajo debe ser mayor que cero");
        }
        return ordenTrabajoRepository.buscarPorId(id);
    }

    /**
     * Registra una nueva orden de trabajo validando los campos obligatorios.
     * @param orden objeto con los datos de la orden
     * @throws IllegalArgumentException si los datos son inválidos
     */
    @Override
    public void registrarOrdenTrabajo(OrdenTrabajo orden) {
        if (orden.getIdVehiculo() <= 0) {
            throw new IllegalArgumentException("El ID del vehículo es obligatorio y debe ser válido");
        }
        if (orden.getFechaIngreso() == null) {
            throw new IllegalArgumentException("La fecha de ingreso es obligatoria");
        }
        if (orden.getIdEstadoOrden() <= 0) {
            throw new IllegalArgumentException("El ID del estado de la orden es obligatorio y debe ser válido");
        }
        ordenTrabajoRepository.registrarOrdenTrabajo(orden);
    }

    /**
     * Elimina una orden de trabajo por su ID, validando que sea positivo.
     * @param id identificador de la orden
     * @throws IllegalArgumentException si el ID es inválido
     */
    @Override
    public void eliminarOrdenTrabajo(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID de la orden de trabajo debe ser mayor que cero");
        }
        ordenTrabajoRepository.eliminarOrdenTrabajo(id);
    }
}

