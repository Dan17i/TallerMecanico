package co.edu.uniquindio.co.demo;

// Importaciones de las clases reales del proyecto
import co.edu.uniquindio.tallermacanico.model.Vehiculo;
import co.edu.uniquindio.tallermacanico.repository.VehiculoRepository;
// Importación de la clase a probar
import co.edu.uniquindio.tallermacanico.service.implement.VehiculoServiceImpl;

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
@DisplayName("Tests Unitarios para VehiculoServiceImpl")
public class VehiculoServiceImplTest {

    // Simula la dependencia VehiculoRepository
    @Mock
    private VehiculoRepository vehiculoRepository;

    // Inyecta el mock en el servicio a probar
    @InjectMocks
    private VehiculoServiceImpl vehiculoService;

    private Vehiculo vehiculoValido;
    private final int ID_VALIDO = 1;
    private final int ID_CLIENTE_VALIDO = 5;

    @BeforeEach
    void setUp() {
        // Inicializa un vehículo válido
        // Constructor requerido: idVehiculo, idCliente, placa, marca, modelo, anio, color
        vehiculoValido = new Vehiculo(
                0,                      // idVehiculo (0 para registro inicial)
                ID_CLIENTE_VALIDO,      // idCliente
                "ABC-123",              // placa
                "Toyota",               // marca
                "Corolla",              // modelo
                2018,                   // anio (dentro del rango 1900-2100)
                "Rojo"                  // color
        );
    }

    // -----------------------------------------------------------------
    // Tests para listarVehiculos()
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería retornar una lista de vehículos no vacía")
    void testListarVehiculos_Exitoso() {
        // Configurar Mock
        Vehiculo vehiculo2 = new Vehiculo(2, 6, "XYZ-789", "Mazda", "3", 2020, "Negro");
        vehiculoValido.setIdVehiculo(ID_VALIDO);
        List<Vehiculo> listaVehiculos = Arrays.asList(vehiculoValido, vehiculo2);
        when(vehiculoRepository.listarVehiculos()).thenReturn(listaVehiculos);

        // Ejecutar y Verificar
        List<Vehiculo> resultado = vehiculoService.listarVehiculos();
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(vehiculoRepository, times(1)).listarVehiculos();
    }

    // -----------------------------------------------------------------
    // Tests para buscarPorId(int id)
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería encontrar un vehículo por ID válido")
    void testBuscarPorId_IDValido() {
        // Configurar Mock
        vehiculoValido.setIdVehiculo(ID_VALIDO);
        when(vehiculoRepository.buscarPorId(ID_VALIDO)).thenReturn(vehiculoValido);

        // Ejecutar y Verificar
        Vehiculo encontrado = vehiculoService.buscarPorId(ID_VALIDO);
        assertNotNull(encontrado);
        verify(vehiculoRepository, times(1)).buscarPorId(ID_VALIDO);
    }

    @Test
    @DisplayName("Debería lanzar excepción para ID no positivo al buscar")
    void testBuscarPorId_IDNoValido() {
        // Ejecutar y verificar
        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            vehiculoService.buscarPorId(0);
        });
        assertEquals("El ID del vehículo debe ser mayor que cero", excepcion.getMessage());
        verify(vehiculoRepository, never()).buscarPorId(anyInt());
    }

    // -----------------------------------------------------------------
    // Tests para registrarVehiculo(Vehiculo vehiculo) - Validaciones
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería registrar un vehículo con datos válidos")
    void testRegistrarVehiculo_Valido() {
        // Ejecutar
        vehiculoService.registrarVehiculo(vehiculoValido);

        // Verificar
        verify(vehiculoRepository, times(1)).registrarVehiculo(vehiculoValido);
    }

    @Test
    @DisplayName("Debería fallar si la placa es nula o vacía")
    void testRegistrarVehiculo_PlacaInvalida() {
        vehiculoValido.setPlaca(null);

        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            vehiculoService.registrarVehiculo(vehiculoValido);
        });
        assertEquals("La placa del vehículo es obligatoria", excepcion.getMessage());
        verify(vehiculoRepository, never()).registrarVehiculo(any());

        vehiculoValido.setPlaca(" ");
        assertThrows(IllegalArgumentException.class, () -> {
            vehiculoService.registrarVehiculo(vehiculoValido);
        });
    }

    @Test
    @DisplayName("Debería fallar si el ID del cliente es inválido (no positivo)")
    void testRegistrarVehiculo_IdClienteInvalido() {
        vehiculoValido.setIdCliente(0);

        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            vehiculoService.registrarVehiculo(vehiculoValido);
        });
        assertEquals("El ID del cliente debe ser válido", excepcion.getMessage());
        verify(vehiculoRepository, never()).registrarVehiculo(any());
    }

    @Test
    @DisplayName("Debería fallar si el año es menor a 1900")
    void testRegistrarVehiculo_AnioDemasiadoAntiguo() {
        vehiculoValido.setAnio(1899);

        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            vehiculoService.registrarVehiculo(vehiculoValido);
        });
        assertEquals("El año del vehículo debe estar entre 1900 y 2100", excepcion.getMessage());
        verify(vehiculoRepository, never()).registrarVehiculo(any());
    }

    @Test
    @DisplayName("Debería fallar si el año es mayor a 2100")
    void testRegistrarVehiculo_AnioDemasiadoFuturo() {
        vehiculoValido.setAnio(2101);

        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            vehiculoService.registrarVehiculo(vehiculoValido);
        });
        assertEquals("El año del vehículo debe estar entre 1900 y 2100", excepcion.getMessage());
        verify(vehiculoRepository, never()).registrarVehiculo(any());
    }

    // -----------------------------------------------------------------
    // Tests para eliminarVehiculo(int id)
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería eliminar un vehículo con ID válido")
    void testEliminarVehiculo_Exitoso() {
        // Ejecutar (No debe lanzar excepción)
        assertDoesNotThrow(() -> vehiculoService.eliminarVehiculo(ID_VALIDO));

        // Verificar
        verify(vehiculoRepository, times(1)).eliminarVehiculo(ID_VALIDO);
    }

    @Test
    @DisplayName("Debería lanzar excepción para ID no positivo al eliminar")
    void testEliminarVehiculo_IDNoValido() {
        // Ejecutar y verificar
        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            vehiculoService.eliminarVehiculo(-1);
        });

        assertEquals("El ID del vehículo debe ser mayor que cero", excepcion.getMessage());
        verify(vehiculoRepository, never()).eliminarVehiculo(anyInt());
    }
}
