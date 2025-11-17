package co.edu.uniquindio.tallermacanico.controller;

import co.edu.uniquindio.tallermacanico.model.Supervision;
import co.edu.uniquindio.tallermacanico.service.SupervisionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controlador REST para gestionar supervisiones entre mecánicos en servicios específicos.
 * Expone endpoints para listar, registrar, consultar y eliminar supervisiones.
 *
 * <p>Ruta base: {@code /api/supervision}</p>
 *
 * <p>Relación con la base de datos:</p>
 * - Utiliza la tabla {@code supervision} con PK compuesta: id_orden_servicio, id_mecanico_supervisor, id_mecanico_supervisado.
 * - Campos adicionales: id_especialidad, observaciones.
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
     *
     * @return lista de objetos {@link Supervision}
     */
    @GetMapping
    public ResponseEntity<List<Supervision>> listar() {
        return ResponseEntity.ok(supervisionService.listarSupervisiones());
    }

    /**
     * Consulta una supervisión específica por IDs compuestos.
     *
     * @param idOrdenServicio ID de la orden de servicio
     * @param idSupervisor ID del mecánico supervisor
     * @param idSupervisado ID del mecánico supervisado
     * @return objeto {@link Supervision} si existe, o {@code 404 Not Found} si no se encuentra
     */
    @GetMapping("/{idOrdenServicio}/{idSupervisor}/{idSupervisado}")
    public ResponseEntity<?> buscar(@PathVariable int idOrdenServicio,
                                    @PathVariable int idSupervisor,
                                    @PathVariable int idSupervisado) {
        Supervision supervision = supervisionService.buscarSupervision(idOrdenServicio, idSupervisor, idSupervisado);
        if (supervision == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(supervision);
    }

    /**
     * Registra una nueva supervisión entre mecánicos.
     *
     * @param supervision objeto {@link Supervision} con los datos a insertar
     * @return mensaje de confirmación o {@code 400 Bad Request} si los datos son inválidos
     */
    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody Supervision supervision) {
        try {
            supervisionService.registrarSupervision(supervision);
            return ResponseEntity.ok(Map.of("mensaje", "Supervisión registrada correctamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Elimina una supervisión por IDs compuestos.
     *
     * @param idOrdenServicio ID de la orden de servicio
     * @param idSupervisor ID del mecánico supervisor
     * @param idSupervisado ID del mecánico supervisado
     * @return mensaje de confirmación o {@code 404 Not Found} si no existe
     */
    @DeleteMapping("/{idOrdenServicio}/{idSupervisor}/{idSupervisado}")
    public ResponseEntity<?> eliminar(@PathVariable int idOrdenServicio,
                                      @PathVariable int idSupervisor,
                                      @PathVariable int idSupervisado) {
        Supervision supervision = supervisionService.buscarSupervision(idOrdenServicio, idSupervisor, idSupervisado);
        if (supervision == null) {
            return ResponseEntity.notFound().build();
        }
        supervisionService.eliminarSupervision(idOrdenServicio, idSupervisor, idSupervisado);
        return ResponseEntity.ok(Map.of("mensaje", "Supervisión eliminada correctamente"));
    }
}


