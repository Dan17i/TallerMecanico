package co.edu.uniquindio.tallermacanico.service.implement;

import co.edu.uniquindio.tallermacanico.model.Factura;
import co.edu.uniquindio.tallermacanico.repository.FacturaRepository;
import co.edu.uniquindio.tallermacanico.service.FacturaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación del servicio para la entidad Factura.
 * Contiene validaciones de negocio y delega operaciones al repositorio.
 */
@RequiredArgsConstructor
@Service
public class FacturaServiceImpl implements FacturaService {

    private final FacturaRepository facturaRepository;

    /**
     * Lista todas las facturas registradas en la base de datos.
     * @return lista de facturas
     */
    @Override
    public List<Factura> listarFacturas() {
        return facturaRepository.listarFacturas();
    }

    /**
     * Busca una factura por su ID, validando que sea positivo.
     * @param id identificador de la factura
     * @return factura encontrada
     * @throws IllegalArgumentException si el ID es inválido
     */
    @Override
    public Factura buscarPorId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID de la factura debe ser mayor que cero");
        }
        return facturaRepository.buscarPorId(id);
    }

    /**
     * Registra una nueva factura validando los campos obligatorios.
     * @param factura objeto con los datos de la factura
     * @throws IllegalArgumentException si los datos son inválidos
     */
    @Override
    public void registrarFactura(Factura factura) {
        if (factura.getIdOrdenTrabajo() <= 0) {
            throw new IllegalArgumentException("El ID de la orden de trabajo es obligatorio y debe ser válido");
        }
        if (factura.getIdEstadoPago() <= 0) {
            throw new IllegalArgumentException("El ID del estado de pago es obligatorio y debe ser válido");
        }
        if (factura.getSubtotalServicios() < 0) {
            throw new IllegalArgumentException("El subtotal de servicios no puede ser negativo");
        }
        if (factura.getSubtotalRepuestos() < 0) {
            throw new IllegalArgumentException("El subtotal de repuestos no puede ser negativo");
        }
        if (factura.getImpuestosTotal() < 0) {
            throw new IllegalArgumentException("Los impuestos no pueden ser negativos");
        }
        if (factura.getDescuentoTotal() < 0) {
            throw new IllegalArgumentException("El descuento no puede ser negativo");
        }
        if (factura.getTotal() < 0) {
            throw new IllegalArgumentException("El total no puede ser negativo");
        }
        facturaRepository.registrarFactura(factura);
    }

    /**
     * Elimina una factura por su ID, validando que sea positivo.
     * @param id identificador de la factura
     * @throws IllegalArgumentException si el ID es inválido
     */
    @Override
    public void eliminarFactura(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID de la factura debe ser mayor que cero");
        }
        facturaRepository.eliminarFactura(id);
    }
}


