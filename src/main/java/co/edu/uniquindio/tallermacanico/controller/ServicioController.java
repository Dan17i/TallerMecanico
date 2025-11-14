package co.edu.uniquindio.tallermacanico.controller;


import co.edu.uniquindio.tallermacanico.model.Servicio;
import co.edu.uniquindio.tallermacanico.service.ServicioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicios")
public class ServicioController {

    private final ServicioService servicioService;

    public ServicioController(ServicioService servicioService) {
        this.servicioService = servicioService;
    }

    @GetMapping
    public ResponseEntity<List<Servicio>> obtenerServicios() {
        return ResponseEntity.ok(servicioService.listarServicios());
    }

    @PostMapping
    public ResponseEntity<String> registrarServicio(@RequestBody Servicio servicio) {
        try {
            servicioService.registrarServicio(servicio);
            return ResponseEntity.ok("Servicio registrado correctamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servicio> buscarPorId(@PathVariable int id) {
        try {
            return ResponseEntity.ok(servicioService.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarServicio(@PathVariable int id) {
        try {
            servicioService.eliminarServicio(id);
            return ResponseEntity.ok("Servicio eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}

