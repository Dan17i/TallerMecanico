package co.edu.uniquindio.tallermacanico.controller;

import co.edu.uniquindio.tallermacanico.model.OrdenServicioMecanico;
import co.edu.uniquindio.tallermacanico.service.OrdenServicioMecanicoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controlador REST para gestionar la asignación de mecánicos a servicios dentro de una orden de trabajo.
 */
@RestController
@RequestMapping("/api/orden-servicio-mecanico")
public class OrdenServicioMecanicoController {

    private final OrdenServicioMecanicoService ordenServicioMecanicoService;

    public OrdenServicioMecanicoController(OrdenServicioMecanicoService ordenServicioMecanicoService) {
        this.ordenServicioMecanicoService = ordenServicioMecanicoService;
    }

    /**
     * Lista todas las asignaciones de mecánicos en servicios.
     * @return lista de asignaciones
     */
    @GetMapping
    public ResponseEntity<List<OrdenServicioMecanico>> listar() {
        return ResponseEntity.ok(ordenServicioMecanicoService.listarAsignaciones());
    }

    /**
     * Busca una asignación específica por ID de orden-servicio y ID de mecánico.
     * @param idOrdenServicio ID del servicio
     * @param idMecanico ID del mecánico
     * @return asignación encontrada o 404 si no existe
     */
    @GetMapping("/{idOrdenServicio}/{idMecanico}")
    public ResponseEntity<?> buscar(@PathVariable int idOrdenServicio, @PathVariable int idMecanico) {
        OrdenServicioMecanico asignacion = ordenServicioMecanicoService.buscarPorOrdenYPorMecanico(idOrdenServicio, idMecanico);
        if (asignacion == null) {
            return ResponseEntity.status(404).body(Map.of("error", "Asignación no encontrada"));
        }
        return ResponseEntity.ok(asignacion);
    }

    /**
     * Registra una nueva asignación de mecánico a un servicio.
     * @param asignacion objeto con los datos de la asignación
     * @return mensaje de confirmación o error de validación
     */
    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody OrdenServicioMecanico asignacion) {
        try {
            ordenServicioMecanicoService.registrarAsignacion(asignacion);
            return ResponseEntity.ok(Map.of("mensaje", "Asignación registrada correctamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Elimina una asignación por ID de orden-servicio y ID de mecánico.
     * @param idOrdenServicio ID del servicio
     * @param idMecanico ID del mecánico
     * @return mensaje de confirmación o 404 si no existe
     */
    @DeleteMapping("/{idOrdenServicio}/{idMecanico}")
    public ResponseEntity<?> eliminar(@PathVariable int idOrdenServicio, @PathVariable int idMecanico) {
        OrdenServicioMecanico asignacion = ordenServicioMecanicoService.buscarPorOrdenYPorMecanico(idOrdenServicio, idMecanico);
        if (asignacion == null) {
            return ResponseEntity.status(404).body(Map.of("error", "Asignación no encontrada"));
        }
        ordenServicioMecanicoService.eliminarAsignacion(idOrdenServicio, idMecanico);
        return ResponseEntity.ok(Map.of("mensaje", "Asignación eliminada correctamente"));
    }
}


