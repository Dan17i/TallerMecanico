package co.edu.uniquindio.tallermacanico.controller;

import co.edu.uniquindio.tallermacanico.model.Mecanico;
import co.edu.uniquindio.tallermacanico.repository.MecanicoRepository;
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

    @GetMapping
    public ResponseEntity<List<Mecanico>> obtenerMecanicos() {
        return ResponseEntity.ok(mecanicoRepository.listarMecanicos());
    }
}

