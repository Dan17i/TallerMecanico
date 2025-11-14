package co.edu.uniquindio.tallermacanico.controller;

import co.edu.uniquindio.tallermacanico.model.Vehiculo;
import co.edu.uniquindio.tallermacanico.repository.VehiculoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    private final VehiculoRepository vehiculoRepository;

    public VehiculoController(VehiculoRepository vehiculoRepository) {
        this.vehiculoRepository = vehiculoRepository;
    }

    @GetMapping
    public ResponseEntity<List<Vehiculo>> obtenerVehiculos() {
        return ResponseEntity.ok(vehiculoRepository.listarVehiculos());
    }

    @PostMapping
    public ResponseEntity<String> registrarVehiculo(@RequestBody Vehiculo vehiculo) {
        vehiculoRepository.registrarVehiculo(vehiculo);
        return ResponseEntity.ok("Vehículo registrado correctamente");
    }
}
