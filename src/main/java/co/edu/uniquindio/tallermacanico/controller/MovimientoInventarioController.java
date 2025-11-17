package co.edu.uniquindio.tallermacanico.controller;

import co.edu.uniquindio.tallermacanico.model.MovimientoInventario;
import co.edu.uniquindio.tallermacanico.service.MovimientoInventarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controlador REST para gestionar los movimientos de inventario (entrada, salida y ajuste de repuestos).
 * Expone endpoints para listar, registrar, consultar y eliminar movimientos.
 *
 * <p>Ruta base: {@code /api/movimiento-inventario}</p>
 *
 * <p>Relación con la base de datos:</p>
 * - Utiliza la tabla {@code movimiento_inventario} con campos: id_movimiento, id_repuesto, tipo_movimiento, cantidad, fecha_movimiento, referencia, observaciones.
 * - El campo {@code id_movimiento} se genera automáticamente mediante identidad.
 */
@RestController
@RequestMapping("/api/movimiento-inventario")
public class MovimientoInventarioController {

    private final MovimientoInventarioService movimientoInventarioService;

    public MovimientoInventarioController(MovimientoInventarioService movimientoInventarioService) {
        this.movimientoInventarioService = movimientoInventarioService;
    }

    /**
     * Lista todos los movimientos registrados en el inventario.
     *
     * @return lista de objetos {@link MovimientoInventario}
     */
    @GetMapping
    public ResponseEntity<List<MovimientoInventario>> listar() {
        return ResponseEntity.ok(movimientoInventarioService.listarMovimientos());
    }

    /**
     * Consulta un movimiento por su identificador único.
     *
     * @param id identificador del movimiento
     * @return objeto {@link MovimientoInventario} si existe, o {@code 404 Not Found} si no se encuentra
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable int id) {
        MovimientoInventario movimiento = movimientoInventarioService.buscarPorId(id);
        if (movimiento == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movimiento);
    }

    /**
     * Registra un nuevo movimiento de inventario en la base de datos.
     *
     * @param movimiento objeto {@link MovimientoInventario} con los datos a insertar
     * @return objeto {@link MovimientoInventario} con el ID generado
     */
    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody MovimientoInventario movimiento) {
        try {
            movimientoInventarioService.registrarMovimiento(movimiento);
            return ResponseEntity.ok(movimiento);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Elimina un movimiento de inventario por su identificador.
     *
     * @param id identificador del movimiento
     * @return mensaje de confirmación o {@code 404 Not Found} si no existe
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable int id) {
        MovimientoInventario movimiento = movimientoInventarioService.buscarPorId(id);
        if (movimiento == null) {
            return ResponseEntity.notFound().build();
        }
        movimientoInventarioService.eliminarMovimiento(id);
        return ResponseEntity.ok(Map.of("mensaje", "Movimiento eliminado correctamente"));
    }
}
