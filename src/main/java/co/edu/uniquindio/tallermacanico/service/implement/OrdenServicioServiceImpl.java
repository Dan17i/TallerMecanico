package co.edu.uniquindio.tallermacanico.service.implement;

import co.edu.uniquindio.tallermacanico.model.OrdenServicio;
import co.edu.uniquindio.tallermacanico.repository.OrdenServicioRepository;
import co.edu.uniquindio.tallermacanico.service.OrdenServicioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación del servicio para la entidad OrdenServicio.
 * Contiene validaciones de negocio y delega operaciones al repositorio.
 */
@RequiredArgsConstructor
@Service
public class OrdenServicioServiceImpl implements OrdenServicioService {

    private final OrdenServicioRepository ordenServicioRepository;

    /**
     * Lista todos los registros de servicios aplicados en órdenes de trabajo.
     * @return lista de orden-servicio
     */
    @Override
    public List<OrdenServicio> listarOrdenesServicio() {
        return ordenServicioRepository.listarOrdenesServicio();
    }

    /**
     * Busca un registro de orden-servicio por su ID, validando que sea positivo.
     * @param id identificador del registro
     * @return orden-servicio encontrada
     * @throws IllegalArgumentException si el ID es inválido
     */
    @Override
    public OrdenServicio buscarPorId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID del registro debe ser mayor que cero");
        }
        return ordenServicioRepository.buscarPorId(id);
    }

    /**
     * Registra un nuevo servicio aplicado a una orden de trabajo, validando los campos obligatorios.
     * @param ordenServicio objeto con los datos del servicio aplicado
     * @throws IllegalArgumentException si los datos son inválidos
     */
    @Override
    public void registrarOrdenServicio(OrdenServicio ordenServicio) {
        if (ordenServicio.getIdOrdenTrabajo() <= 0) {
            throw new IllegalArgumentException("El ID de la orden de trabajo es obligatorio y debe ser válido");
        }
        if (ordenServicio.getIdServicio() <= 0) {
            throw new IllegalArgumentException("El ID del servicio es obligatorio y debe ser válido");
        }
        if (ordenServicio.getEstado() == null || ordenServicio.getEstado().isBlank()) {
            throw new IllegalArgumentException("El estado del servicio es obligatorio");
        }
        if (ordenServicio.getPrecioFinal() < 0) {
            throw new IllegalArgumentException("El precio final no puede ser negativo");
        }
        ordenServicioRepository.registrarOrdenServicio(ordenServicio);
    }

    /**
     * Elimina un registro de orden-servicio por su ID, validando que sea positivo.
     * @param id identificador del registro
     * @throws IllegalArgumentException si el ID es inválido
     */
    @Override
    public void eliminarOrdenServicio(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID del registro debe ser mayor que cero");
        }
        ordenServicioRepository.eliminarOrdenServicio(id);
    }
}

