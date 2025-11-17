package co.edu.uniquindio.co.demo;


// Importaciones de las clases reales del proyecto
import co.edu.uniquindio.tallermacanico.model.OrdenTrabajo;
import co.edu.uniquindio.tallermacanico.repository.OrdenTrabajoRepository;
// Importación de la clase a probar
import co.edu.uniquindio.tallermacanico.service.implement.OrdenTrabajoServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests Unitarios para OrdenTrabajoServiceImpl")
public class OrdenTrabajoServiceImplTest {

    // Simula la dependencia OrdenTrabajoRepository
    @Mock
    private OrdenTrabajoRepository ordenTrabajoRepository;

    // Inyecta el mock en el servicio a probar
    @InjectMocks
    private OrdenTrabajoServiceImpl ordenTrabajoService;

    private OrdenTrabajo ordenValida;

    @BeforeEach
    void setUp() {
        // Inicializa una orden de trabajo válida, el orden del constructor es:
        // idOrdenTrabajo, idVehiculo, fechaIngreso, fechaSalida, diagnosticoInicial, idEstadoOrden
        ordenValida = new OrdenTrabajo(
                0,                              // 1. idOrdenTrabajo (0 para registro inicial)
                10,                             // 2. idVehiculo
                LocalDate.now(),                // 3. fechaIngreso
                null,                           // 4. fechaSalida (puede ser nula al inicio)
                "Falla en el sistema de frenos.", // 5. diagnosticoInicial
                1                               // 6. idEstadoOrden
        );
    }

    // -----------------------------------------------------------------
    // Tests para listarOrdenesTrabajo()
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería retornar una lista de órdenes de trabajo no vacía")
    void testListarOrdenesTrabajo_Exitoso() {
        // Configurar Mock
        OrdenTrabajo orden2 = new OrdenTrabajo(2, 11, LocalDate.now().minusDays(1), LocalDate.now(), "Cambio de aceite", 3);
        ordenValida.setIdOrdenTrabajo(1);
        List<OrdenTrabajo> listaOrdenes = Arrays.asList(ordenValida, orden2);
        when(ordenTrabajoRepository.listarOrdenesTrabajo()).thenReturn(listaOrdenes);

        // Ejecutar y Verificar
        List<OrdenTrabajo> resultado = ordenTrabajoService.listarOrdenesTrabajo();
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(ordenTrabajoRepository, times(1)).listarOrdenesTrabajo();
    }

    // -----------------------------------------------------------------
    // Tests para buscarPorId(int id)
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería encontrar una orden de trabajo por ID válido")
    void testBuscarPorId_IDValido() {
        // Configurar Mock
        ordenValida.setIdOrdenTrabajo(1);
        when(ordenTrabajoRepository.buscarPorId(1)).thenReturn(ordenValida);

        // Ejecutar y Verificar
        OrdenTrabajo encontrado = ordenTrabajoService.buscarPorId(1);
        assertNotNull(encontrado);
        verify(ordenTrabajoRepository, times(1)).buscarPorId(1);
    }

    @Test
    @DisplayName("Debería lanzar excepción para ID no positivo al buscar")
    void testBuscarPorId_IDNoValido() {
        // Ejecutar y verificar
        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            ordenTrabajoService.buscarPorId(0);
        });
        assertEquals("El ID de la orden de trabajo debe ser mayor que cero", excepcion.getMessage());
        verify(ordenTrabajoRepository, never()).buscarPorId(anyInt());
    }

    // -----------------------------------------------------------------
    // Tests para registrarOrdenTrabajo(OrdenTrabajo orden) - Validaciones
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería registrar una orden de trabajo con datos válidos")
    void testRegistrarOrdenTrabajo_Valida() {
        // Ejecutar
        ordenTrabajoService.registrarOrdenTrabajo(ordenValida);

        // Verificar
        verify(ordenTrabajoRepository, times(1)).registrarOrdenTrabajo(ordenValida);
    }

    @Test
    @DisplayName("Debería fallar si el ID de Vehículo es inválido")
    void testRegistrarOrdenTrabajo_IdVehiculoInvalido() {
        ordenValida.setIdVehiculo(0);

        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            ordenTrabajoService.registrarOrdenTrabajo(ordenValida);
        });
        assertEquals("El ID del vehículo es obligatorio y debe ser válido", excepcion.getMessage());
        verify(ordenTrabajoRepository, never()).registrarOrdenTrabajo(any());
    }

    @Test
    @DisplayName("Debería fallar si la Fecha de Ingreso es nula")
    void testRegistrarOrdenTrabajo_FechaIngresoNula() {
        ordenValida.setFechaIngreso(null);

        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            ordenTrabajoService.registrarOrdenTrabajo(ordenValida);
        });
        assertEquals("La fecha de ingreso es obligatoria", excepcion.getMessage());
        verify(ordenTrabajoRepository, never()).registrarOrdenTrabajo(any());
    }

    @Test
    @DisplayName("Debería fallar si el ID de Estado de Orden es inválido")
    void testRegistrarOrdenTrabajo_IdEstadoOrdenInvalido() {
        ordenValida.setIdEstadoOrden(-1);

        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            ordenTrabajoService.registrarOrdenTrabajo(ordenValida);
        });
        assertEquals("El ID del estado de la orden es obligatorio y debe ser válido", excepcion.getMessage());
        verify(ordenTrabajoRepository, never()).registrarOrdenTrabajo(any());
    }

    // -----------------------------------------------------------------
    // Tests para eliminarOrdenTrabajo(int id)
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería eliminar una orden de trabajo con ID válido")
    void testEliminarOrdenTrabajo_Exitoso() {
        // Ejecutar (No debe lanzar excepción)
        assertDoesNotThrow(() -> ordenTrabajoService.eliminarOrdenTrabajo(5));

        // Verificar
        verify(ordenTrabajoRepository, times(1)).eliminarOrdenTrabajo(5);
    }

    @Test
    @DisplayName("Debería lanzar excepción para ID no positivo al eliminar")
    void testEliminarOrdenTrabajo_IDNoValido() {
        // Ejecutar y verificar
        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            ordenTrabajoService.eliminarOrdenTrabajo(0);
        });

        assertEquals("El ID de la orden de trabajo debe ser mayor que cero", excepcion.getMessage());
        verify(ordenTrabajoRepository, never()).eliminarOrdenTrabajo(anyInt());
    }
}
