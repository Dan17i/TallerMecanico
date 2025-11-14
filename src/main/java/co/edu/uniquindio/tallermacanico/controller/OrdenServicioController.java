package co.edu.uniquindio.tallermacanico.controller;


import co.edu.uniquindio.tallermacanico.model.OrdenServicio;
import co.edu.uniquindio.tallermacanico.service.OrdenServicioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar las órdenes de servicio.
 */
@RestController
@RequestMapping("/api/orden-servicio")
public class OrdenServicioController {

    private final OrdenServicioService ordenServicioService;

    public OrdenServicioController(OrdenServicioService ordenServicioService) {
        this.ordenServicioService = ordenServicioService;
    }

    @GetMapping
    public ResponseEntity<List<OrdenServicio>> listar() {
        return ResponseEntity.ok(ordenServicioService.listarOrdenesServicio());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenServicio> buscar(@PathVariable int id) {
        return ResponseEntity.ok(ordenServicioService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Void> registrar(@RequestBody OrdenServicio ordenServicio) {
        ordenServicioService.registrarOrdenServicio(ordenServicio);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        ordenServicioService.eliminarOrdenServicio(id);
        return ResponseEntity.ok().build();
    }
}
