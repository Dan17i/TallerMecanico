package co.edu.uniquindio.tallermacanico.controller;

import co.edu.uniquindio.tallermacanico.dto.ApiErrorResponse;
import co.edu.uniquindio.tallermacanico.model.Cliente;
import co.edu.uniquindio.tallermacanico.repository.ClienteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    /**
     * Obtiene todos los clientes registrados.
     */
    @GetMapping
    public ResponseEntity<List<Cliente>> obtenerClientes() {
        return ResponseEntity.ok(clienteRepository.listarClientes());
    }

    /**
     * Registra un nuevo cliente y devuelve el ID generado.
     */
    @PostMapping
    public ResponseEntity<Cliente> registrarCliente(@RequestBody Cliente cliente) {
        int idGenerado = clienteRepository.registrarCliente(cliente);
        cliente.setIdCliente(idGenerado);
        return ResponseEntity.ok(cliente);
    }


    /**
     * Busca un cliente por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        Cliente cliente = clienteRepository.buscarPorId(id);
        if (cliente != null) {
            return ResponseEntity.ok(cliente);
        }
        return ResponseEntity.status(404).body(
                new ApiErrorResponse(
                        "Cliente no encontrado",
                        "ID: " + id + ". Verifica si el cliente fue registrado o consulta /api/clientes para ver los disponibles."
                )
        );
    }


    /**
     * Elimina un cliente por su ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCliente(@PathVariable int id) {
        boolean eliminado = clienteRepository.eliminarCliente(id);
        if (eliminado) {
            return ResponseEntity.ok("Cliente eliminado correctamente");
        }
        return ResponseEntity.status(404).body(
                new ApiErrorResponse(
                        "Cliente no encontrado",
                        "ID: " + id + ". Verifica si el cliente existe o consulta /api/clientes para ver los disponibles."
                )
        );
    }


}

