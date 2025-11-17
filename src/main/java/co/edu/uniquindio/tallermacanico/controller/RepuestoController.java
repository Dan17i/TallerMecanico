package co.edu.uniquindio.tallermacanico.controller;

import co.edu.uniquindio.tallermacanico.model.Repuesto;
import co.edu.uniquindio.tallermacanico.repository.RepuestoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/repuestos")
public class RepuestoController {

    private final RepuestoRepository repuestoRepository;

    public RepuestoController(RepuestoRepository repuestoRepository) {
        this.repuestoRepository = repuestoRepository;
    }

    @GetMapping
    public ResponseEntity<List<Repuesto>> obtenerRepuestos() {
        return ResponseEntity.ok(repuestoRepository.listarRepuestos());
    }

    @PostMapping
    public ResponseEntity<String> crearRepuesto(@RequestBody Repuesto repuesto) {
        int filasAfectadas = repuestoRepository.crearRepuesto(repuesto);
        if (filasAfectadas > 0) {
            return ResponseEntity.ok("Repuesto creado exitosamente");
        } else {
            return ResponseEntity.badRequest().body("No se pudo crear el repuesto");
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Repuesto> obtenerRepuestoPorId(@PathVariable int id) {
        Repuesto repuesto = repuestoRepository.buscarPorId(id);
        if (repuesto != null) {
            return ResponseEntity.ok(repuesto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarRepuesto(@PathVariable int id, @RequestBody Repuesto repuesto) {
        int filas = repuestoRepository.actualizarRepuesto(id, repuesto);
        if (filas > 0) {
            return ResponseEntity.ok("Repuesto actualizado correctamente");
        } else {
            return ResponseEntity.badRequest().body("No se pudo actualizar el repuesto");
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarRepuesto(@PathVariable int id) {
        int filas = repuestoRepository.eliminarRepuesto(id);
        if (filas > 0) {
            return ResponseEntity.ok("Repuesto eliminado correctamente");
        } else {
            return ResponseEntity.badRequest().body("No se pudo eliminar el repuesto");
        }
    }


}

