package co.edu.uniquindio.co.demo;

// Importaciones de las clases reales del proyecto
import co.edu.uniquindio.tallermacanico.model.Cliente;
import co.edu.uniquindio.tallermacanico.repository.ClienteRepository;
// Importación de la clase a probar
import co.edu.uniquindio.tallermacanico.service.implement.ClienteServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests Unitarios para ClienteServiceImpl")
public class ClienteServiceImplTest {

    // Simula la dependencia ClienteRepository
    @Mock
    private ClienteRepository clienteRepository;

    // Inyecta el mock en el servicio a probar
    @InjectMocks
    private ClienteServiceImpl clienteService;

    private Cliente clienteValido;

    @BeforeEach
    void setUp() {
        // Inicializa un cliente válido usando el AllArgsConstructor (6 campos)
        clienteValido = new Cliente(
                1,                  // idCliente
                "Carlos",           // nombre
                "Gómez",            // apellido
                "Calle 10 # 5-20",  // direccion
                "3101234567",       // telefono
                "carlos.gomez@test.com" // email
        );
    }

    // -----------------------------------------------------------------
    // Tests para listarClientes()
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería retornar una lista de clientes no vacía")
    void testListarClientes_Exitoso() {
        // Configurar Mock
        Cliente cliente2 = new Cliente(2, "Ana", "Díaz", "Carrera 2", "3119876543", "ana.diaz@test.com");
        List<Cliente> listaClientes = Arrays.asList(clienteValido, cliente2);
        when(clienteRepository.listarClientes()).thenReturn(listaClientes);

        // Ejecutar y Verificar
        List<Cliente> resultado = clienteService.listarClientes();
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(clienteRepository, times(1)).listarClientes(); // Verifica que el método del repositorio fue llamado
    }

    // -----------------------------------------------------------------
    // Tests para buscarPorId(int id)
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería encontrar un cliente por ID válido")
    void testBuscarPorId_IDValido() {
        // Configurar Mock
        when(clienteRepository.buscarPorId(1)).thenReturn(clienteValido);

        // Ejecutar y Verificar
        Cliente encontrado = clienteService.buscarPorId(1);
        assertNotNull(encontrado);
        verify(clienteRepository, times(1)).buscarPorId(1);
    }

    @Test
    @DisplayName("Debería lanzar excepción para ID no positivo al buscar")
    void testBuscarPorId_IDNoValido() {
        // Ejecutar y verificar
        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            clienteService.buscarPorId(0); // ID no válido
        });
        assertEquals("El ID del cliente debe ser mayor que cero", excepcion.getMessage());
        verify(clienteRepository, never()).buscarPorId(anyInt()); // Verifica que el repositorio NO fue llamado
    }

    // -----------------------------------------------------------------
    // Tests para registrarCliente(Cliente cliente) - Validaciones
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería registrar un cliente válido")
    void testRegistrarCliente_Valido() {
        clienteService.registrarCliente(clienteValido);
        verify(clienteRepository, times(1)).registrarCliente(clienteValido);
    }

    @Test
    @DisplayName("Debería fallar si el nombre es nulo o vacío")
    void testRegistrarCliente_NombreInvalido() {
        // Nombre nulo
        Cliente clienteInvalido = new Cliente(1, null, "Gómez", "Dir", "310", "a@b.com");

        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            clienteService.registrarCliente(clienteInvalido);
        });
        assertEquals("El nombre del cliente es obligatorio", excepcion.getMessage());
        verify(clienteRepository, never()).registrarCliente(any());
    }

    @Test
    @DisplayName("Debería fallar si el apellido está nulo o vacío")
    void testRegistrarCliente_ApellidoInvalido() {
        // Apellido vacío
        Cliente clienteInvalido = new Cliente(1, "Carlos", "", "Dir", "310", "a@b.com");

        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            clienteService.registrarCliente(clienteInvalido);
        });
        assertEquals("El apellido del cliente es obligatorio", excepcion.getMessage());
        verify(clienteRepository, never()).registrarCliente(any());
    }

    @Test
    @DisplayName("Debería fallar con email con formato inválido")
    void testRegistrarCliente_EmailInvalido() {
        // Email sin arroba
        Cliente clienteInvalido = new Cliente(1, "Carlos", "Gómez", "Dir", "310", "correoinvalido");

        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            clienteService.registrarCliente(clienteInvalido);
        });
        assertEquals("El correo electrónico no tiene un formato válido", excepcion.getMessage());
        verify(clienteRepository, never()).registrarCliente(any());
    }

    @Test
    @DisplayName("Debería fallar si el teléfono es muy largo (>20 caracteres)")
    void testRegistrarCliente_TelefonoLargo() {
        String telefonoLargo = "123456789012345678901"; // 21 caracteres
        Cliente clienteInvalido = new Cliente(1, "Carlos", "Gómez", "Dir", telefonoLargo, "a@b.com");

        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            clienteService.registrarCliente(clienteInvalido);
        });
        assertEquals("El teléfono no puede superar los 20 caracteres", excepcion.getMessage());
        verify(clienteRepository, never()).registrarCliente(any());
    }

    // -----------------------------------------------------------------
    // Tests para eliminarCliente(int id)
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería eliminar un cliente con ID válido")
    void testEliminarCliente_IDValido() {
        clienteService.eliminarCliente(5);
        verify(clienteRepository, times(1)).eliminarCliente(5);
    }

    @Test
    @DisplayName("Debería lanzar excepción para ID no positivo al eliminar")
    void testEliminarCliente_IDNoValido() {
        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            clienteService.eliminarCliente(-1);
        });

        assertEquals("El ID del cliente debe ser mayor que cero", excepcion.getMessage());
        verify(clienteRepository, never()).eliminarCliente(anyInt());
    }
}