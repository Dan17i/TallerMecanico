package co.edu.uniquindio.tallermacanico.controller;

import co.edu.uniquindio.tallermacanico.dto.ApiErrorResponse;
import co.edu.uniquindio.tallermacanico.model.Factura;
import co.edu.uniquindio.tallermacanico.repository.FacturaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar las operaciones relacionadas con las facturas.
 * Expone endpoints para listar, consultar, registrar y eliminar facturas.
 *
 * <p>Las facturas están asociadas a órdenes de trabajo y estados de pago,
 * según la estructura definida en la base de datos.</p>
 *
 * @author Daniel
 */
@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    private final FacturaRepository facturaRepository;

    /**
     * Constructor que inyecta el repositorio de facturas.
     *
     * @param facturaRepository repositorio encargado de las operaciones sobre la tabla factura
     */
    public FacturaController(FacturaRepository facturaRepository) {
        this.facturaRepository = facturaRepository;
    }

    /**
     * Obtiene todas las facturas registradas en la base de datos.
     *
     * @return ResponseEntity con la lista de facturas y código HTTP 200
     */
    @GetMapping
    public ResponseEntity<List<Factura>> obtenerFacturas() {
        return ResponseEntity.ok(facturaRepository.listarFacturas());
    }

    /**
     * Busca una factura por su identificador único.
     *
     * @param id identificador de la factura
     * @return ResponseEntity con la factura encontrada y código HTTP 200,
     *         o un objeto {@link ApiErrorResponse} con código HTTP 404 si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        Factura factura = facturaRepository.buscarPorId(id);
        if (factura != null) {
            return ResponseEntity.ok(factura);
        }
        return ResponseEntity.status(404).body(new ApiErrorResponse("Factura no encontrada", "ID: " + id));
    }

    /**
     * Registra una nueva factura en la base de datos.
     *
     * @param factura objeto Factura con los datos a insertar
     * @return ResponseEntity con la factura registrada (incluyendo el ID generado) y código HTTP 200
     */
    @PostMapping
    public ResponseEntity<Factura> registrarFactura(@RequestBody Factura factura) {
        int idGenerado = facturaRepository.registrarFactura(factura);
        factura.setIdFactura(idGenerado);
        return ResponseEntity.ok(factura);
    }

    /**
     * Elimina una factura existente por su identificador.
     *
     * @param id identificador de la factura a eliminar
     * @return ResponseEntity con mensaje de éxito y código HTTP 200 si se eliminó,
     *         o un objeto {@link ApiErrorResponse} con código HTTP 404 si no existe
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarFactura(@PathVariable int id) {
        boolean eliminado = facturaRepository.eliminarFactura(id);
        if (eliminado) {
            return ResponseEntity.ok("Factura eliminada correctamente");
        }
        return ResponseEntity.status(404).body(new ApiErrorResponse("Factura no encontrada", "ID: " + id));
    }
}
