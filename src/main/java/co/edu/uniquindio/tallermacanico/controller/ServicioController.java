package co.edu.uniquindio.tallermacanico.controller;

import co.edu.uniquindio.tallermacanico.model.Servicio;
import co.edu.uniquindio.tallermacanico.service.ServicioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controlador REST para gestionar operaciones sobre los servicios del taller.
 * Expone endpoints para listar, registrar, consultar, eliminar y actualizar precio.
 *
 * <p>Ruta base: {@code /api/servicios}</p>
 *
 * <p>Relación con la base de datos:</p>
 * - Utiliza la tabla {@code servicio} con campos: id_servicio, nombre, descripcion, precio_base.
 * - El campo {@code id_servicio} se genera automáticamente mediante identidad.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/servicios")
public class ServicioController {

    private final ServicioService servicioService;

    /**
     * Lista todos los servicios registrados en la base de datos.
     *
     * @return lista de objetos {@link Servicio}
     */
    @GetMapping
    public ResponseEntity<List<Servicio>> obtenerServicios() {
        return ResponseEntity.ok(servicioService.listarServicios());
    }

    /**
     * Registra un nuevo servicio en la base de datos.
     *
     * @param servicio objeto {@link Servicio} con los datos a insertar
     * @return objeto {@link Servicio} con el ID generado
     */
    @PostMapping
    public ResponseEntity<Servicio> registrarServicio(@RequestBody Servicio servicio) {
        System.out.println("Servicio registrado 1");
        servicioService.registrarServicio(servicio);
        System.out.println("Servicio registrado 2");
        return ResponseEntity.ok(servicio);
    }

    /**
     * Consulta un servicio por su identificador único.
     *
     * @param id identificador del servicio
     * @return objeto {@link Servicio} si existe, o {@code 404 Not Found} si no se encuentra
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        Servicio servicio = servicioService.buscarPorId(id);
        if (servicio == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(servicio);
    }

    /**
     * Elimina un servicio por su identificador.
     *
     * @param id identificador del servicio
     * @return {@code 200 OK} si se elimina correctamente, o {@code 404 Not Found} si no existe
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarServicio(@PathVariable int id) {
        Servicio servicio = servicioService.buscarPorId(id);
        if (servicio == null) {
            return ResponseEntity.notFound().build();
        }
        servicioService.eliminarServicio(id);
        return ResponseEntity.ok(Map.of("mensaje", "Servicio eliminado correctamente"));
    }

    /**
     * Actualiza el precio base de un servicio existente.
     *
     * @param id identificador del servicio
     * @param nuevoPrecio nuevo valor para el precio base
     * @return {@code 200 OK} si se actualiza correctamente, o {@code 400 Bad Request} si los datos son inválidos
     */
    @PatchMapping("/{id}/precio")
    public ResponseEntity<?> actualizarPrecio(@PathVariable int id, @RequestParam double nuevoPrecio) {
        try {
            servicioService.actualizarPrecioBase(id, nuevoPrecio);
            return ResponseEntity.ok(Map.of("mensaje", "Precio actualizado correctamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}



