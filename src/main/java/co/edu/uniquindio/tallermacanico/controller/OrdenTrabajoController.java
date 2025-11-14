package co.edu.uniquindio.tallermacanico.controller;

import co.edu.uniquindio.tallermacanico.dto.OrdenTrabajoDTO;
import co.edu.uniquindio.tallermacanico.model.OrdenTrabajo;
import co.edu.uniquindio.tallermacanico.repository.EstadoOrdenRepository;
import co.edu.uniquindio.tallermacanico.repository.OrdenTrabajoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ordenes")
public class OrdenTrabajoController {
    private final OrdenTrabajoRepository ordenTrabajoRepository;
    private final EstadoOrdenRepository estadoOrdenRepository;

    public OrdenTrabajoController(OrdenTrabajoRepository ordenTrabajoRepository,
                                  EstadoOrdenRepository estadoOrdenRepository) {
        this.ordenTrabajoRepository = ordenTrabajoRepository;
        this.estadoOrdenRepository = estadoOrdenRepository;
    }

    @GetMapping
    public ResponseEntity<List<OrdenTrabajoDTO>> obtenerOrdenes() {
        List<OrdenTrabajo> ordenes = ordenTrabajoRepository.listarOrdenesTrabajo();

        List<OrdenTrabajoDTO> dtos = ordenes.stream()
                .map(orden -> new OrdenTrabajoDTO(
                        orden.getIdOrdenTrabajo(),
                        orden.getIdVehiculo(),
                        orden.getFechaIngreso(),
                        orden.getFechaSalida(),
                        orden.getDiagnosticoInicial(),
                        estadoOrdenRepository.obtenerNombrePorId(orden.getIdEstadoOrden())
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

}
