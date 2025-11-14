package co.edu.uniquindio.tallermacanico.controller;

import co.edu.uniquindio.tallermacanico.model.OrdenTrabajo;
import co.edu.uniquindio.tallermacanico.repository.OrdenTrabajoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordenes")
public class OrdenTrabajoController {

    private final OrdenTrabajoRepository ordenTrabajoRepository;

    public OrdenTrabajoController(OrdenTrabajoRepository ordenTrabajoRepository) {
        this.ordenTrabajoRepository = ordenTrabajoRepository;
    }

    @GetMapping
    public ResponseEntity<List<OrdenTrabajo>> obtenerOrdenes() {
        return ResponseEntity.ok(ordenTrabajoRepository.listarOrdenes());
    }
}
