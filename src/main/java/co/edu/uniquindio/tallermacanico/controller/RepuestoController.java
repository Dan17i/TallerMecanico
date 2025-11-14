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
}

