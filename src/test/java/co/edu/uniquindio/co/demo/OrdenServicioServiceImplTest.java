package co.edu.uniquindio.co.demo;

// Importaciones de las clases reales del proyecto
import co.edu.uniquindio.tallermacanico.model.OrdenServicio;
import co.edu.uniquindio.tallermacanico.repository.OrdenServicioRepository;
// Importación de la clase a probar
import co.edu.uniquindio.tallermacanico.service.implement.OrdenServicioServiceImpl;

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
@DisplayName("Tests Unitarios para OrdenServicioServiceImpl")
public class OrdenServicioServiceImplTest {

    // Simula la dependencia OrdenServicioRepository
    @Mock
    private OrdenServicioRepository ordenServicioRepository;

    // Inyecta el mock en el servicio a probar
    @InjectMocks
    private OrdenServicioServiceImpl ordenServicioService;

    private OrdenServicio ordenServicioValida;
    private final int ID_REGISTRO = 1;

    @BeforeEach
    void setUp() {
        // Inicializa un registro de OrdenServicio válido (5 campos)
        ordenServicioValida = new OrdenServicio(
                0,                      // idOrdenServicio (0 para registro inicial)
                10,                     // idOrdenTrabajo
                5,                      // idServicio (ej. Cambio de aceite)
                "PENDIENTE",            // estado
                45.50                   // precioFinal
        );
    }

    // -----------------------------------------------------------------
    // Tests para listarOrdenesServicio()
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería retornar una lista de órdenes de servicio no vacía")
    void testListarOrdenesServicio_Exitoso() {
        // Configurar Mock
        OrdenServicio os2 = new OrdenServicio(2, 11, 6, "COMPLETADO", 150.0);
        ordenServicioValida.setIdOrdenServicio(ID_REGISTRO);
        List<OrdenServicio> listaOrdenes = Arrays.asList(ordenServicioValida, os2);
        when(ordenServicioRepository.listarOrdenesServicio()).thenReturn(listaOrdenes);

        // Ejecutar y Verificar
        List<OrdenServicio> resultado = ordenServicioService.listarOrdenesServicio();
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(ordenServicioRepository, times(1)).listarOrdenesServicio();
    }

    // -----------------------------------------------------------------
    // Tests para buscarPorId(int id)
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería encontrar un registro por ID válido")
    void testBuscarPorId_IDValido() {
        // Configurar Mock
        ordenServicioValida.setIdOrdenServicio(ID_REGISTRO);
        when(ordenServicioRepository.buscarPorId(ID_REGISTRO)).thenReturn(ordenServicioValida);

        // Ejecutar y Verificar
        OrdenServicio encontrado = ordenServicioService.buscarPorId(ID_REGISTRO);
        assertNotNull(encontrado);
        verify(ordenServicioRepository, times(1)).buscarPorId(ID_REGISTRO);
    }

    @Test
    @DisplayName("Debería lanzar excepción para ID no positivo al buscar")
    void testBuscarPorId_IDNoValido() {
        // Ejecutar y verificar
        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            ordenServicioService.buscarPorId(0);
        });
        assertEquals("El ID del registro debe ser mayor que cero", excepcion.getMessage());
        verify(ordenServicioRepository, never()).buscarPorId(anyInt());
    }

    // -----------------------------------------------------------------
    // Tests para registrarOrdenServicio(OrdenServicio ordenServicio) - Validaciones
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería registrar un registro válido")
    void testRegistrarOrdenServicio_Valida() {
        ordenServicioService.registrarOrdenServicio(ordenServicioValida);
        verify(ordenServicioRepository, times(1)).registrarOrdenServicio(ordenServicioValida);
    }

    @Test
    @DisplayName("Debería fallar si el ID de Orden de Trabajo es inválido")
    void testRegistrarOrdenServicio_IdOrdenTrabajoInvalido() {
        ordenServicioValida.setIdOrdenTrabajo(0);

        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            ordenServicioService.registrarOrdenServicio(ordenServicioValida);
        });
        assertEquals("El ID de la orden de trabajo es obligatorio y debe ser válido", excepcion.getMessage());
        verify(ordenServicioRepository, never()).registrarOrdenServicio(any());
    }

    @Test
    @DisplayName("Debería fallar si el ID de Servicio es inválido")
    void testRegistrarOrdenServicio_IdServicioInvalido() {
        ordenServicioValida.setIdServicio(-1);

        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            ordenServicioService.registrarOrdenServicio(ordenServicioValida);
        });
        assertEquals("El ID del servicio es obligatorio y debe ser válido", excepcion.getMessage());
        verify(ordenServicioRepository, never()).registrarOrdenServicio(any());
    }

    @Test
    @DisplayName("Debería fallar si el Estado es nulo o vacío")
    void testRegistrarOrdenServicio_EstadoInvalido() {
        ordenServicioValida.setEstado(null);

        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            ordenServicioService.registrarOrdenServicio(ordenServicioValida);
        });
        assertEquals("El estado del servicio es obligatorio", excepcion.getMessage());
        verify(ordenServicioRepository, never()).registrarOrdenServicio(any());

        ordenServicioValida.setEstado(" ");
        assertThrows(IllegalArgumentException.class, () -> {
            ordenServicioService.registrarOrdenServicio(ordenServicioValida);
        });
    }

    @Test
    @DisplayName("Debería fallar si el Precio Final es negativo")
    void testRegistrarOrdenServicio_PrecioFinalNegativo() {
        ordenServicioValida.setPrecioFinal(-0.01);

        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            ordenServicioService.registrarOrdenServicio(ordenServicioValida);
        });
        assertEquals("El precio final no puede ser negativo", excepcion.getMessage());
        verify(ordenServicioRepository, never()).registrarOrdenServicio(any());
    }

    // -----------------------------------------------------------------
    // Tests para eliminarOrdenServicio(int id)
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería eliminar un registro con ID válido")
    void testEliminarOrdenServicio_Exitoso() {
        // Ejecutar (No debe lanzar excepción)
        assertDoesNotThrow(() -> ordenServicioService.eliminarOrdenServicio(ID_REGISTRO));

        // Verificar
        verify(ordenServicioRepository, times(1)).eliminarOrdenServicio(ID_REGISTRO);
    }

    @Test
    @DisplayName("Debería lanzar excepción para ID no positivo al eliminar")
    void testEliminarOrdenServicio_IDNoValido() {
        // Ejecutar y verificar
        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            ordenServicioService.eliminarOrdenServicio(-1);
        });

        assertEquals("El ID del registro debe ser mayor que cero", excepcion.getMessage());
        verify(ordenServicioRepository, never()).eliminarOrdenServicio(anyInt());
    }
}
