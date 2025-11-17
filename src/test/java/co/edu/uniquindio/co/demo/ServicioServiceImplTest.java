package co.edu.uniquindio.co.demo;


// Importaciones de las clases reales del proyecto
import co.edu.uniquindio.tallermacanico.model.Servicio;
import co.edu.uniquindio.tallermacanico.repository.ServicioRepository;
// Importación de la clase a probar
import co.edu.uniquindio.tallermacanico.service.implement.ServicioServiceImpl;

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
@DisplayName("Tests Unitarios para ServicioServiceImpl")
public class ServicioServiceImplTest {

    // Simula la dependencia ServicioRepository
    @Mock
    private ServicioRepository servicioRepository;

    // Inyecta el mock en el servicio a probar
    @InjectMocks
    private ServicioServiceImpl servicioService;

    private Servicio servicioValido;

    @BeforeEach
    void setUp() {
        // Inicializa un servicio válido usando el AllArgsConstructor (4 campos)
        servicioValido = new Servicio(
                0,                      // idServicio (0 porque se registrará)
                "Cambio de Aceite",     // nombre
                "Reemplazo de lubricante y filtro.", // descripcion
                50.00                   // precioBase
        );
    }

    // -----------------------------------------------------------------
    // Tests para listarServicios()
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería retornar una lista de servicios no vacía")
    void testListarServicios_Exitoso() {
        // Configurar Mock
        Servicio serv2 = new Servicio(2, "Alineación", "Ajuste de dirección", 80.00);
        servicioValido.setIdServicio(1); // Ajustar ID para el mock
        List<Servicio> listaServicios = Arrays.asList(servicioValido, serv2);
        when(servicioRepository.listarServicios()).thenReturn(listaServicios);

        // Ejecutar y Verificar
        List<Servicio> resultado = servicioService.listarServicios();
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(servicioRepository, times(1)).listarServicios();
    }

    // -----------------------------------------------------------------
    // Tests para buscarPorId(int id)
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería encontrar un servicio por ID válido")
    void testBuscarPorId_IDValido() {
        // Configurar Mock
        servicioValido.setIdServicio(1);
        when(servicioRepository.buscarPorId(1)).thenReturn(servicioValido);

        // Ejecutar y Verificar
        Servicio encontrado = servicioService.buscarPorId(1);
        assertNotNull(encontrado);
        verify(servicioRepository, times(1)).buscarPorId(1);
    }

    @Test
    @DisplayName("Debería lanzar excepción para ID no positivo al buscar")
    void testBuscarPorId_IDNoValido() {
        // Ejecutar y verificar
        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            servicioService.buscarPorId(0);
        });
        assertEquals("El ID debe ser mayor que cero", excepcion.getMessage());
        verify(servicioRepository, never()).buscarPorId(anyInt());
    }

    // -----------------------------------------------------------------
    // Tests para registrarServicio(Servicio servicio)
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería registrar un servicio y actualizar su ID")
    void testRegistrarServicio_AsignaID() {
        final int ID_GENERADO = 15;

        // Configurar Mock: El repositorio retorna el ID generado (15)
        when(servicioRepository.registrarServicio(servicioValido)).thenReturn(ID_GENERADO);

        // Ejecutar
        servicioService.registrarServicio(servicioValido);

        // Verificar 1: El repositorio fue llamado
        verify(servicioRepository, times(1)).registrarServicio(servicioValido);

        // Verificar 2: El objeto 'servicioValido' fue modificado con el ID generado
        assertEquals(ID_GENERADO, servicioValido.getIdServicio(), "El ID del objeto Servicio debe ser actualizado después del registro");
    }

    // -----------------------------------------------------------------
    // Tests para eliminarServicio(int id)
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería eliminar un servicio con ID válido")
    void testEliminarServicio_IDValido() {
        // Ejecutar (No debe lanzar excepción)
        assertDoesNotThrow(() -> servicioService.eliminarServicio(5));

        // Verificar
        verify(servicioRepository, times(1)).eliminarServicio(5);
    }

    @Test
    @DisplayName("Debería lanzar excepción para ID no positivo al eliminar")
    void testEliminarServicio_IDNoValido() {
        // Ejecutar y verificar
        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            servicioService.eliminarServicio(-1);
        });

        assertEquals("El ID debe ser mayor que cero", excepcion.getMessage());
        verify(servicioRepository, never()).eliminarServicio(anyInt());
    }

    // -----------------------------------------------------------------
    // Tests para actualizarPrecioBase(int idServicio, double nuevoPrecio)
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería actualizar el precio base con éxito")
    void testActualizarPrecioBase_Exitoso() {
        final int ID = 1;
        final double NUEVO_PRECIO = 65.50;

        // Configurar Mock: El servicio existe
        servicioValido.setIdServicio(ID);
        when(servicioRepository.buscarPorId(ID)).thenReturn(servicioValido);

        // Ejecutar (No debe lanzar excepción)
        assertDoesNotThrow(() -> servicioService.actualizarPrecioBase(ID, NUEVO_PRECIO));

        // Verificar: Se buscó y luego se actualizó
        verify(servicioRepository, times(1)).buscarPorId(ID);
        verify(servicioRepository, times(1)).actualizarPrecioBase(ID, NUEVO_PRECIO);
    }

    @Test
    @DisplayName("Debería fallar si el ID de servicio a actualizar no es válido")
    void testActualizarPrecioBase_IDInvalido() {
        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            servicioService.actualizarPrecioBase(-5, 100.0);
        });
        assertEquals("El ID debe ser mayor que cero", excepcion.getMessage());
        verify(servicioRepository, never()).buscarPorId(anyInt());
        verify(servicioRepository, never()).actualizarPrecioBase(anyInt(), anyDouble());
    }

    @Test
    @DisplayName("Debería fallar si el nuevo precio base es <= 0")
    void testActualizarPrecioBase_PrecioInvalido() {
        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            servicioService.actualizarPrecioBase(1, 0.0);
        });
        assertEquals("El precio base debe ser mayor que cero", excepcion.getMessage());
        verify(servicioRepository, never()).buscarPorId(anyInt());
        verify(servicioRepository, never()).actualizarPrecioBase(anyInt(), anyDouble());
    }

    @Test
    @DisplayName("Debería fallar si el servicio no existe")
    void testActualizarPrecioBase_ServicioNoExiste() {
        final int ID_INEXISTENTE = 99;

        // Configurar Mock: El servicio no existe (retorna null)
        when(servicioRepository.buscarPorId(ID_INEXISTENTE)).thenReturn(null);

        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            servicioService.actualizarPrecioBase(ID_INEXISTENTE, 100.0);
        });

        assertEquals("El servicio con ID 99 no existe", excepcion.getMessage());
        verify(servicioRepository, times(1)).buscarPorId(ID_INEXISTENTE);
        verify(servicioRepository, never()).actualizarPrecioBase(anyInt(), anyDouble());
    }
}
