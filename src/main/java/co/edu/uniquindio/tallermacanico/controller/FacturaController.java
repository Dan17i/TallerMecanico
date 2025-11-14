package co.edu.uniquindio.tallermacanico.controller;

import co.edu.uniquindio.tallermacanico.model.Factura;
import co.edu.uniquindio.tallermacanico.repository.FacturaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    private final FacturaRepository facturaRepository;

    public FacturaController(FacturaRepository facturaRepository) {
        this.facturaRepository = facturaRepository;
    }

    @GetMapping
    public ResponseEntity<List<Factura>> obtenerFacturas() {
        return ResponseEntity.ok(facturaRepository.listarFacturas());
    }
}

