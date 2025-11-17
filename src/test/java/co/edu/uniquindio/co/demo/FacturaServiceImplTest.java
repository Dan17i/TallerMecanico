package co.edu.uniquindio.co.demo;

// Importaciones de las clases reales del proyecto
import co.edu.uniquindio.tallermacanico.model.Factura;
import co.edu.uniquindio.tallermacanico.repository.FacturaRepository;
// Importación de la clase a probar
import co.edu.uniquindio.tallermacanico.service.implement.FacturaServiceImpl;

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
@DisplayName("Tests Unitarios para FacturaServiceImpl")
public class FacturaServiceImplTest {

    // Simula la dependencia FacturaRepository
    @Mock
    private FacturaRepository facturaRepository;

    // Inyecta el mock en el servicio a probar
    @InjectMocks
    private FacturaServiceImpl facturaService;

    private Factura facturaValida;

    @BeforeEach
    void setUp() {
        // Inicializa una factura válida
        facturaValida = new Factura(
                0,                          // idFactura (0 para registro inicial)
                50,                         // idOrdenTrabajo
                LocalDate.now(),                          // // fechaEmision
                1,            // idEstadoPago (ej. Pagada)
                100.00,                     // subtotalServicios
                50.00,                      // subtotalRepuestos
                19.00,                      // impuestosTotal (IVA)
                10.00,                      // descuentoTotal
                159.00                      // total (100+50+19-10)
        );
    }

    // -----------------------------------------------------------------
    // Tests para listarFacturas()
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería retornar una lista de facturas no vacía")
    void testListarFacturas_Exitoso() {
        // Configurar Mock
        Factura factura2 = new Factura(2, 51, LocalDate.now(), 2, 200.0, 0.0, 38.0, 0.0, 238.0);
        facturaValida.setIdFactura(1);
        List<Factura> listaFacturas = Arrays.asList(facturaValida, factura2);
        when(facturaRepository.listarFacturas()).thenReturn(listaFacturas);

        // Ejecutar y Verificar
        List<Factura> resultado = facturaService.listarFacturas();
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(facturaRepository, times(1)).listarFacturas();
    }

    // -----------------------------------------------------------------
    // Tests para buscarPorId(int id)
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería encontrar una factura por ID válido")
    void testBuscarPorId_IDValido() {
        // Configurar Mock
        facturaValida.setIdFactura(1);
        when(facturaRepository.buscarPorId(1)).thenReturn(facturaValida);

        // Ejecutar y Verificar
        Factura encontrado = facturaService.buscarPorId(1);
        assertNotNull(encontrado);
        verify(facturaRepository, times(1)).buscarPorId(1);
    }

    @Test
    @DisplayName("Debería lanzar excepción para ID no positivo al buscar")
    void testBuscarPorId_IDNoValido() {
        // Ejecutar y verificar
        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            facturaService.buscarPorId(0);
        });
        assertEquals("El ID de la factura debe ser mayor que cero", excepcion.getMessage());
        verify(facturaRepository, never()).buscarPorId(anyInt());
    }

    // -----------------------------------------------------------------
    // Tests para registrarFactura(Factura factura) - Validaciones
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería registrar una factura con datos válidos")
    void testRegistrarFactura_Valida() {
        // Ejecutar
        facturaService.registrarFactura(facturaValida);

        // Verificar
        verify(facturaRepository, times(1)).registrarFactura(facturaValida);
    }

    @Test
    @DisplayName("Debería fallar si el ID de Orden de Trabajo es inválido")
    void testRegistrarFactura_IdOrdenTrabajoInvalido() {
        facturaValida.setIdOrdenTrabajo(0);

        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            facturaService.registrarFactura(facturaValida);
        });
        assertEquals("El ID de la orden de trabajo es obligatorio y debe ser válido", excepcion.getMessage());
        verify(facturaRepository, never()).registrarFactura(any());
    }

    @Test
    @DisplayName("Debería fallar si el ID de Estado de Pago es inválido")
    void testRegistrarFactura_IdEstadoPagoInvalido() {
        facturaValida.setIdEstadoPago(-1);

        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            facturaService.registrarFactura(facturaValida);
        });
        assertEquals("El ID del estado de pago es obligatorio y debe ser válido", excepcion.getMessage());
        verify(facturaRepository, never()).registrarFactura(any());
    }

    @Test
    @DisplayName("Debería fallar si el Subtotal de Servicios es negativo")
    void testRegistrarFactura_SubtotalServiciosNegativo() {
        facturaValida.setSubtotalServicios(-1.0);

        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            facturaService.registrarFactura(facturaValida);
        });
        assertEquals("El subtotal de servicios no puede ser negativo", excepcion.getMessage());
        verify(facturaRepository, never()).registrarFactura(any());
    }

    @Test
    @DisplayName("Debería fallar si el Subtotal de Repuestos es negativo")
    void testRegistrarFactura_SubtotalRepuestosNegativo() {
        facturaValida.setSubtotalRepuestos(-0.01);

        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            facturaService.registrarFactura(facturaValida);
        });
        assertEquals("El subtotal de repuestos no puede ser negativo", excepcion.getMessage());
        verify(facturaRepository, never()).registrarFactura(any());
    }

    @Test
    @DisplayName("Debería fallar si los Impuestos son negativos")
    void testRegistrarFactura_ImpuestosNegativos() {
        facturaValida.setImpuestosTotal(-5.0);

        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            facturaService.registrarFactura(facturaValida);
        });
        assertEquals("Los impuestos no pueden ser negativos", excepcion.getMessage());
        verify(facturaRepository, never()).registrarFactura(any());
    }

    @Test
    @DisplayName("Debería fallar si el Descuento es negativo")
    void testRegistrarFactura_DescuentoNegativo() {
        facturaValida.setDescuentoTotal(-1.0);

        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            facturaService.registrarFactura(facturaValida);
        });
        assertEquals("El descuento no puede ser negativo", excepcion.getMessage());
        verify(facturaRepository, never()).registrarFactura(any());
    }

    @Test
    @DisplayName("Debería fallar si el Total es negativo")
    void testRegistrarFactura_TotalNegativo() {
        facturaValida.setTotal(-100.0);

        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            facturaService.registrarFactura(facturaValida);
        });
        assertEquals("El total no puede ser negativo", excepcion.getMessage());
        verify(facturaRepository, never()).registrarFactura(any());
    }

    // -----------------------------------------------------------------
    // Tests para eliminarFactura(int id)
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería eliminar una factura con ID válido")
    void testEliminarFactura_Exitoso() {
        // Ejecutar (No debe lanzar excepción)
        assertDoesNotThrow(() -> facturaService.eliminarFactura(5));

        // Verificar
        verify(facturaRepository, times(1)).eliminarFactura(5);
    }

    @Test
    @DisplayName("Debería lanzar excepción para ID no positivo al eliminar")
    void testEliminarFactura_IDNoValido() {
        // Ejecutar y verificar
        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            facturaService.eliminarFactura(0);
        });

        assertEquals("El ID de la factura debe ser mayor que cero", excepcion.getMessage());
        verify(facturaRepository, never()).eliminarFactura(anyInt());
    }
}
