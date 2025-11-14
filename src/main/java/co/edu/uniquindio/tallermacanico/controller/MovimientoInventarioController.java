package co.edu.uniquindio.tallermacanico.controller;


import co.edu.uniquindio.tallermacanico.model.MovimientoInventario;
import co.edu.uniquindio.tallermacanico.service.MovimientoInventarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar los movimientos de inventario (entrada y salida de repuestos).
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
     * @return lista de movimientos
     */
    @GetMapping
    public ResponseEntity<List<MovimientoInventario>> listar() {
        return ResponseEntity.ok(movimientoInventarioService.listarMovimientos());
    }

    /**
     * Busca un movimiento por su ID.
     * @param id identificador del movimiento
     * @return movimiento encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<MovimientoInventario> buscar(@PathVariable int id) {
        return ResponseEntity.ok(movimientoInventarioService.buscarPorId(id));
    }

    /**
     * Registra un nuevo movimiento de inventario.
     * @param movimiento objeto con los datos del movimiento
     * @return respuesta vacía si se registra correctamente
     */
    @PostMapping
    public ResponseEntity<Void> registrar(@RequestBody MovimientoInventario movimiento) {
        movimientoInventarioService.registrarMovimiento(movimiento);
        return ResponseEntity.ok().build();
    }

    /**
     * Elimina un movimiento por su ID.
     * @param id identificador del movimiento
     * @return respuesta vacía si se elimina correctamente
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        movimientoInventarioService.eliminarMovimiento(id);
        return ResponseEntity.ok().build();
    }
}

