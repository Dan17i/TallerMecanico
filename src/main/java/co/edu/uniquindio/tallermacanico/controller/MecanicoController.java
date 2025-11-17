package co.edu.uniquindio.tallermacanico.controller;

import co.edu.uniquindio.tallermacanico.model.Mecanico;
import co.edu.uniquindio.tallermacanico.repository.MecanicoRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mecanicos")
public class MecanicoController {

    private final MecanicoRepository mecanicoRepository;

    public MecanicoController(MecanicoRepository mecanicoRepository) {
        this.mecanicoRepository = mecanicoRepository;
    }

    // Obtener todos los mecánicos
    @GetMapping
    public ResponseEntity<List<Mecanico>> obtenerMecanicos() {
        return ResponseEntity.ok(mecanicoRepository.listarMecanicos());
    }

    // Registrar nuevo mecánico con validación
    @PostMapping
    public ResponseEntity<Mecanico> registrarMecanico(@RequestBody @Valid Mecanico mecanico) {
        int idGenerado = mecanicoRepository.registrarMecanico(mecanico);
        mecanico.setIdMecanico(idGenerado);
        return ResponseEntity.ok(mecanico);
    }

    // Consultar mecánico por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerMecanicoPorId(@PathVariable int id) {
        Mecanico mecanico = mecanicoRepository.buscarPorId(id);
        if (mecanico == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mecanico);
    }

    // Eliminar mecánico por ID
    @DeleteMapping("/completo/{id}")
    public ResponseEntity<?> eliminarMecanicoConRelaciones(@PathVariable int id) {
        boolean eliminado = mecanicoRepository.eliminarMecanicoConRelaciones(id);
        if (!eliminado) {
            return ResponseEntity.status(409).body("No se pudo eliminar el mecánico ni sus relaciones");
        }
        return ResponseEntity.ok().build();
    }

}


