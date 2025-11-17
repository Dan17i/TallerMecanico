package co.edu.uniquindio.tallermacanico.controller;

import co.edu.uniquindio.tallermacanico.model.Especialidad;
import co.edu.uniquindio.tallermacanico.service.EspecialidadService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controlador REST para gestionar especialidades de mecánicos.
 */
@RestController
@RequestMapping("/api/especialidades")
public class EspecialidadController {

    private final EspecialidadService especialidadService;

    public EspecialidadController(EspecialidadService especialidadService) {
        this.especialidadService = especialidadService;
    }

    @GetMapping
    public ResponseEntity<List<Especialidad>> listarEspecialidades() {
        return ResponseEntity.ok(especialidadService.listarEspecialidades());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable int id) {
        Especialidad especialidad = especialidadService.buscarPorId(id);
        if (especialidad == null) {
            return ResponseEntity.status(404).body(Map.of("error", "Especialidad no encontrada"));
        }
        return ResponseEntity.ok(especialidad);
    }

    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody @Valid Especialidad especialidad) {
        try {
            int idGenerado = especialidadService.registrarEspecialidad(especialidad);
            especialidad.setIdEspecialidad(idGenerado);
            return ResponseEntity.ok(especialidad);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable int id) {
        boolean eliminado = especialidadService.eliminarEspecialidad(id);
        if (!eliminado) {
            return ResponseEntity.status(404).body(Map.of("error", "No se pudo eliminar la especialidad"));
        }
        return ResponseEntity.ok(Map.of("mensaje", "Especialidad eliminada correctamente"));
    }
}
