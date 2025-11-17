package co.edu.uniquindio.co.demo;

// Importaciones de las clases reales del proyecto
import co.edu.uniquindio.tallermacanico.model.OrdenServicioMecanico;
import co.edu.uniquindio.tallermacanico.repository.OrdenServicioMecanicoRepository;
// Importación de la clase a probar
import co.edu.uniquindio.tallermacanico.service.implement.OrdenServicioMecanicoServiceImpl;

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
@DisplayName("Tests Unitarios para OrdenServicioMecanicoServiceImpl")
public class OrdenServicioMecanicoServiceImplTest {

    // Simula la dependencia OrdenServicioMecanicoRepository
    @Mock
    private OrdenServicioMecanicoRepository repository;

    // Inyecta el mock en el servicio a probar
    @InjectMocks
    private OrdenServicioMecanicoServiceImpl asignacionService;

    private OrdenServicioMecanico asignacionValida;

    // IDs de prueba para la clave compuesta
    private final int ID_ORDEN_SERVICIO = 10;
    private final int ID_MECANICO = 5;
    private final int ID_ESPECIALIDAD = 1;

    @BeforeEach
    void setUp() {
        // Inicializa una asignación válida (4 campos)
        asignacionValida = new OrdenServicioMecanico(
                ID_ORDEN_SERVICIO,      // idOrdenServicio
                ID_MECANICO,            // idMecanico
                ID_ESPECIALIDAD,        // idEspecialidad
                "Líder de diagnóstico"  // rolEnServicio
        );
    }

    // -----------------------------------------------------------------
    // Tests para listarAsignaciones()
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería retornar una lista de asignaciones no vacía")
    void testListarAsignaciones_Exitoso() {
        // Configurar Mock
        OrdenServicioMecanico asignacion2 = new OrdenServicioMecanico(10, 6, 2, "Asistente");
        List<OrdenServicioMecanico> listaAsignaciones = Arrays.asList(asignacionValida, asignacion2);
        when(repository.listarAsignaciones()).thenReturn(listaAsignaciones);

        // Ejecutar y Verificar
        List<OrdenServicioMecanico> resultado = asignacionService.listarAsignaciones();
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(repository, times(1)).listarAsignaciones();
    }

    // -----------------------------------------------------------------
    // Tests para buscarPorOrdenYPorMecanico(int idOrdenServicio, int idMecanico)
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería encontrar una asignación por IDs válidos")
    void testBuscarPorOrdenYPorMecanico_IDsValidos() {
        // Configurar Mock
        when(repository.buscarPorOrdenYPorMecanico(ID_ORDEN_SERVICIO, ID_MECANICO)).thenReturn(asignacionValida);

        // Ejecutar y Verificar
        OrdenServicioMecanico encontrado = asignacionService.buscarPorOrdenYPorMecanico(ID_ORDEN_SERVICIO, ID_MECANICO);
        assertNotNull(encontrado);
        verify(repository, times(1)).buscarPorOrdenYPorMecanico(ID_ORDEN_SERVICIO, ID_MECANICO);
    }

    @Test
    @DisplayName("Debería lanzar excepción si el ID de Orden de Servicio no es positivo al buscar")
    void testBuscarPorOrdenYPorMecanico_IDOrdenInvalido() {
        // Ejecutar y verificar
        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            asignacionService.buscarPorOrdenYPorMecanico(0, ID_MECANICO);
        });
        assertEquals("Los IDs deben ser mayores que cero", excepcion.getMessage());
        verify(repository, never()).buscarPorOrdenYPorMecanico(anyInt(), anyInt());
    }

    @Test
    @DisplayName("Debería lanzar excepción si el ID de Mecánico no es positivo al buscar")
    void testBuscarPorOrdenYPorMecanico_IDMecanicoInvalido() {
        // Ejecutar y verificar
        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            asignacionService.buscarPorOrdenYPorMecanico(ID_ORDEN_SERVICIO, -1);
        });
        assertEquals("Los IDs deben ser mayores que cero", excepcion.getMessage());
        verify(repository, never()).buscarPorOrdenYPorMecanico(anyInt(), anyInt());
    }


    // -----------------------------------------------------------------
    // Tests para registrarAsignacion(OrdenServicioMecanico asignacion) - Validaciones
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería registrar una asignación válida")
    void testRegistrarAsignacion_Valida() {
        asignacionService.registrarAsignacion(asignacionValida);
        verify(repository, times(1)).registrarAsignacion(asignacionValida);
    }

    @Test
    @DisplayName("Debería fallar si el ID de Orden de Servicio es inválido")
    void testRegistrarAsignacion_IDOrdenInvalido() {
        asignacionValida.setIdOrdenServicio(0);

        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            asignacionService.registrarAsignacion(asignacionValida);
        });
        assertEquals("El ID del servicio es obligatorio y debe ser válido", excepcion.getMessage());
        verify(repository, never()).registrarAsignacion(any());
    }

    @Test
    @DisplayName("Debería fallar si el ID de Mecánico es inválido")
    void testRegistrarAsignacion_IDMecanicoInvalido() {
        asignacionValida.setIdMecanico(-5);

        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            asignacionService.registrarAsignacion(asignacionValida);
        });
        assertEquals("El ID del mecánico es obligatorio y debe ser válido", excepcion.getMessage());
        verify(repository, never()).registrarAsignacion(any());
    }

    @Test
    @DisplayName("Debería fallar si el ID de Especialidad es inválido")
    void testRegistrarAsignacion_IDEspecialidadInvalido() {
        asignacionValida.setIdEspecialidad(0);

        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            asignacionService.registrarAsignacion(asignacionValida);
        });
        assertEquals("La especialidad es obligatoria y debe ser válida", excepcion.getMessage());
        verify(repository, never()).registrarAsignacion(any());
    }

    @Test
    @DisplayName("Debería fallar si el Rol en Servicio es nulo o vacío")
    void testRegistrarAsignacion_RolInvalido() {
        asignacionValida.setRolEnServicio("");

        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            asignacionService.registrarAsignacion(asignacionValida);
        });
        assertEquals("El rol en el servicio es obligatorio", excepcion.getMessage());
        verify(repository, never()).registrarAsignacion(any());

        asignacionValida.setRolEnServicio(null);
        assertThrows(IllegalArgumentException.class, () -> {
            asignacionService.registrarAsignacion(asignacionValida);
        });
    }

    // -----------------------------------------------------------------
    // Tests para eliminarAsignacion(int idOrdenServicio, int idMecanico)
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería eliminar una asignación con IDs válidos")
    void testEliminarAsignacion_IDsValidos() {
        // Ejecutar (No debe lanzar excepción)
        assertDoesNotThrow(() -> asignacionService.eliminarAsignacion(ID_ORDEN_SERVICIO, ID_MECANICO));

        // Verificar
        verify(repository, times(1)).eliminarAsignacion(ID_ORDEN_SERVICIO, ID_MECANICO);
    }

    @Test
    @DisplayName("Debería lanzar excepción si el ID de Orden de Servicio no es positivo al eliminar")
    void testEliminarAsignacion_IDOrdenNoValido() {
        // Ejecutar y verificar
        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            asignacionService.eliminarAsignacion(0, ID_MECANICO);
        });
        assertEquals("Los IDs deben ser mayores que cero", excepcion.getMessage());
        verify(repository, never()).eliminarAsignacion(anyInt(), anyInt());
    }

    @Test
    @DisplayName("Debería lanzar excepción si el ID de Mecánico no es positivo al eliminar")
    void testEliminarAsignacion_IDMecanicoNoValido() {
        // Ejecutar y verificar
        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            asignacionService.eliminarAsignacion(ID_ORDEN_SERVICIO, -1);
        });
        assertEquals("Los IDs deben ser mayores que cero", excepcion.getMessage());
        verify(repository, never()).eliminarAsignacion(anyInt(), anyInt());
    }
}
