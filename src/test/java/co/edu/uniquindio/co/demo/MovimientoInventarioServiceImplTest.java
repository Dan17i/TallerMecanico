package co.edu.uniquindio.co.demo;

// Importaciones de las clases reales del proyecto
import co.edu.uniquindio.tallermacanico.model.MovimientoInventario;
import co.edu.uniquindio.tallermacanico.repository.MovimientoInventarioRepository;
// Importación de la clase a probar
import co.edu.uniquindio.tallermacanico.service.implement.MovimientoInventarioServiceImpl;

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
@DisplayName("Tests Unitarios para MovimientoInventarioServiceImpl")
public class MovimientoInventarioServiceImplTest {

    // Simula la dependencia MovimientoInventarioRepository
    @Mock
    private MovimientoInventarioRepository repository;

    // Inyecta el mock en el servicio a probar
    @InjectMocks
    private MovimientoInventarioServiceImpl movimientoService;

    private MovimientoInventario movimientoValido;

    @BeforeEach
    void setUp() {
        // Inicializa un movimiento válido (Entrada) usando el AllArgsConstructor (7 campos)
        movimientoValido = new MovimientoInventario(
                0,                          // idMovimiento (0 para registro inicial)
                101,                        // idRepuesto
                "entrada",                  // tipoMovimiento
                50.0,                       // cantidad
                LocalDate.now(),            // fechaMovimiento
                "REF456",                   // referencia
                "Compra a proveedor"        // observaciones
        );
    }

    // -----------------------------------------------------------------
    // Tests para listarMovimientos()
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería retornar una lista de movimientos no vacía")
    void testListarMovimientos_Exitoso() {
        // Configurar Mock
        MovimientoInventario mov2 = new MovimientoInventario(2, 102, "SALIDA", 5.0, LocalDate.now(), "REF100", "Venta");
        movimientoValido.setIdMovimiento(1);
        List<MovimientoInventario> listaMovimientos = Arrays.asList(movimientoValido, mov2);
        when(repository.listarMovimientos()).thenReturn(listaMovimientos);

        // Ejecutar y Verificar
        List<MovimientoInventario> resultado = movimientoService.listarMovimientos();
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(repository, times(1)).listarMovimientos();
    }

    // -----------------------------------------------------------------
    // Tests para buscarPorId(int id)
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería encontrar un movimiento por ID válido")
    void testBuscarPorId_IDValido() {
        // Configurar Mock
        movimientoValido.setIdMovimiento(1);
        when(repository.buscarPorId(1)).thenReturn(movimientoValido);

        // Ejecutar y Verificar
        MovimientoInventario encontrado = movimientoService.buscarPorId(1);
        assertNotNull(encontrado);
        verify(repository, times(1)).buscarPorId(1);
    }

    @Test
    @DisplayName("Debería lanzar excepción para ID no positivo al buscar")
    void testBuscarPorId_IDNoValido() {
        // Ejecutar y verificar
        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            movimientoService.buscarPorId(0);
        });
        assertEquals("El ID del movimiento debe ser mayor que cero", excepcion.getMessage());
        verify(repository, never()).buscarPorId(anyInt());
    }

    // -----------------------------------------------------------------
    // Tests para registrarMovimiento(MovimientoInventario movimiento) - Validaciones
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería registrar un movimiento válido (Entrada, Salida, Ajuste) y actualizar su ID")
    void testRegistrarMovimiento_Valido() {
        final int ID_GENERADO = 20;

        // 1. Entrada
        MovimientoInventario movEntrada = new MovimientoInventario(0, 10, "ENTRADA", 10.0, LocalDate.now(), "R1", "obs");
        when(repository.registrarMovimiento(movEntrada)).thenReturn(ID_GENERADO);
        movimientoService.registrarMovimiento(movEntrada);
        assertEquals(ID_GENERADO, movEntrada.getIdMovimiento());
        verify(repository, times(1)).registrarMovimiento(movEntrada);

        // 2. Salida
        MovimientoInventario movSalida = new MovimientoInventario(0, 20, "salida", 5.0, LocalDate.now(), "R2", "obs");
        when(repository.registrarMovimiento(movSalida)).thenReturn(ID_GENERADO + 1);
        movimientoService.registrarMovimiento(movSalida);
        assertEquals(ID_GENERADO + 1, movSalida.getIdMovimiento());
        verify(repository, times(1)).registrarMovimiento(movSalida);

        // 3. Ajuste
        MovimientoInventario movAjuste = new MovimientoInventario(0, 30, "AjuSte", 1.0, LocalDate.now(), "R3", "obs");
        when(repository.registrarMovimiento(movAjuste)).thenReturn(ID_GENERADO + 2);
        movimientoService.registrarMovimiento(movAjuste);
        assertEquals(ID_GENERADO + 2, movAjuste.getIdMovimiento());
        verify(repository, times(1)).registrarMovimiento(movAjuste);
    }

    @Test
    @DisplayName("Debería fallar si el ID de Repuesto es menor o igual a cero")
    void testRegistrarMovimiento_IdRepuestoInvalido() {
        movimientoValido.setIdRepuesto(0);

        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            movimientoService.registrarMovimiento(movimientoValido);
        });
        assertEquals("El ID del repuesto es obligatorio y debe ser válido", excepcion.getMessage());
        verify(repository, never()).registrarMovimiento(any());
    }

    @Test
    @DisplayName("Debería fallar si el Tipo de Movimiento es nulo o vacío")
    void testRegistrarMovimiento_TipoMovimientoInvalido() {
        movimientoValido.setTipoMovimiento(null);

        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            movimientoService.registrarMovimiento(movimientoValido);
        });
        assertEquals("El tipo de movimiento es obligatorio", excepcion.getMessage());
        verify(repository, never()).registrarMovimiento(any());
    }

    @Test
    @DisplayName("Debería fallar si el Tipo de Movimiento no es Entrada, Salida o Ajuste")
    void testRegistrarMovimiento_TipoMovimientoNoPermitido() {
        movimientoValido.setTipoMovimiento("transferencia");

        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            movimientoService.registrarMovimiento(movimientoValido);
        });
        assertEquals("El tipo de movimiento debe ser 'entrada', 'salida' o 'ajuste'", excepcion.getMessage());
        verify(repository, never()).registrarMovimiento(any());
    }

    @Test
    @DisplayName("Debería fallar si la Cantidad es menor o igual a cero")
    void testRegistrarMovimiento_CantidadInvalida() {
        movimientoValido.setCantidad(0.0);

        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            movimientoService.registrarMovimiento(movimientoValido);
        });
        assertEquals("La cantidad debe ser mayor que cero", excepcion.getMessage());
        verify(repository, never()).registrarMovimiento(any());
    }

    @Test
    @DisplayName("Debería fallar si la Fecha de Movimiento es nula")
    void testRegistrarMovimiento_FechaNula() {
        movimientoValido.setFechaMovimiento(null);

        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            movimientoService.registrarMovimiento(movimientoValido);
        });
        assertEquals("La fecha del movimiento es obligatoria", excepcion.getMessage());
        verify(repository, never()).registrarMovimiento(any());
    }

    // -----------------------------------------------------------------
    // Tests para eliminarMovimiento(int id)
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería eliminar un movimiento con ID válido")
    void testEliminarMovimiento_Exitoso() {
        // Ejecutar (No debe lanzar excepción)
        assertDoesNotThrow(() -> movimientoService.eliminarMovimiento(5));

        // Verificar
        verify(repository, times(1)).eliminarMovimiento(5);
    }

    @Test
    @DisplayName("Debería lanzar excepción para ID no positivo al eliminar")
    void testEliminarMovimiento_IDNoValido() {
        // Ejecutar y verificar
        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            movimientoService.eliminarMovimiento(-1);
        });

        assertEquals("El ID del movimiento debe ser mayor que cero", excepcion.getMessage());
        verify(repository, never()).eliminarMovimiento(anyInt());
    }
}
