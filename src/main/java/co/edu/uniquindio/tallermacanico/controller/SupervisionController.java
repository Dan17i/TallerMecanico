package co.edu.uniquindio.tallermacanico.controller;


import co.edu.uniquindio.tallermacanico.model.Supervision;
import co.edu.uniquindio.tallermacanico.service.SupervisionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar supervisiones entre mecánicos en servicios específicos.
 */
@RestController
@RequestMapping("/api/supervision")
public class SupervisionController {

    private final SupervisionService supervisionService;

    public SupervisionController(SupervisionService supervisionService) {
        this.supervisionService = supervisionService;
    }

    /**
     * Lista todas las supervisiones registradas.
     * @return lista de supervisiones
     */
    @GetMapping
    public ResponseEntity<List<Supervision>> listar() {
        return ResponseEntity.ok(supervisionService.listarSupervisiones());
    }

    /**
     * Busca una supervisión específica por IDs compuestos.
     * @param idOrdenServicio ID del servicio
     * @param idSupervisor ID del mecánico supervisor
     * @param idSupervisado ID del mecánico supervisado
     * @return supervisión encontrada
     */
    @GetMapping("/{idOrdenServicio}/{idSupervisor}/{idSupervisado}")
    public ResponseEntity<Supervision> buscar(@PathVariable int idOrdenServicio,
                                              @PathVariable int idSupervisor,
                                              @PathVariable int idSupervisado) {
        return ResponseEntity.ok(supervisionService.buscarSupervision(idOrdenServicio, idSupervisor, idSupervisado));
    }

    /**
     * Registra una nueva supervisión entre mecánicos.
     * @param supervision objeto con los datos de la supervisión
     * @return respuesta vacía si se registra correctamente
     */
    @PostMapping
    public ResponseEntity<Void> registrar(@RequestBody Supervision supervision) {
        supervisionService.registrarSupervision(supervision);
        return ResponseEntity.ok().build();
    }

    /**
     * Elimina una supervisión por IDs compuestos.
     * @param idOrdenServicio ID del servicio
     * @param idSupervisor ID del mecánico supervisor
     * @param idSupervisado ID del mecánico supervisado
     * @return respuesta vacía si se elimina correctamente
     */
    @DeleteMapping("/{idOrdenServicio}/{idSupervisor}/{idSupervisado}")
    public ResponseEntity<Void> eliminar(@PathVariable int idOrdenServicio,
                                         @PathVariable int idSupervisor,
                                         @PathVariable int idSupervisado) {
        supervisionService.eliminarSupervision(idOrdenServicio, idSupervisor, idSupervisado);
        return ResponseEntity.ok().build();
    }
}

