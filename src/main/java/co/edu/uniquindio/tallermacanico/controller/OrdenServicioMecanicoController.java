package co.edu.uniquindio.tallermacanico.controller;

import co.edu.uniquindio.tallermacanico.model.OrdenServicioMecanico;
import co.edu.uniquindio.tallermacanico.service.OrdenServicioMecanicoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * @return asignación encontrada
     */
    @GetMapping("/{idOrdenServicio}/{idMecanico}")
    public ResponseEntity<OrdenServicioMecanico> buscar(@PathVariable int idOrdenServicio, @PathVariable int idMecanico) {
        return ResponseEntity.ok(ordenServicioMecanicoService.buscarPorOrdenYPorMecanico(idOrdenServicio, idMecanico));
    }

    /**
     * Registra una nueva asignación de mecánico a un servicio.
     * @param asignacion objeto con los datos de la asignación
     * @return respuesta vacía si se registra correctamente
     */
    @PostMapping
    public ResponseEntity<Void> registrar(@RequestBody OrdenServicioMecanico asignacion) {
        ordenServicioMecanicoService.registrarAsignacion(asignacion);
        return ResponseEntity.ok().build();
    }

    /**
     * Elimina una asignación por ID de orden-servicio y ID de mecánico.
     * @param idOrdenServicio ID del servicio
     * @param idMecanico ID del mecánico
     * @return respuesta vacía si se elimina correctamente
     */
    @DeleteMapping("/{idOrdenServicio}/{idMecanico}")
    public ResponseEntity<Void> eliminar(@PathVariable int idOrdenServicio, @PathVariable int idMecanico) {
        ordenServicioMecanicoService.eliminarAsignacion(idOrdenServicio, idMecanico);
        return ResponseEntity.ok().build();
    }
}

