package co.edu.uniquindio.co.demo;

// Importaciones de las clases reales del proyecto
import co.edu.uniquindio.tallermacanico.model.Supervision;
import co.edu.uniquindio.tallermacanico.repository.SupervisionRepository;
// Importación de la clase a probar
import co.edu.uniquindio.tallermacanico.service.implement.SupervisionServiceImpl;

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
@DisplayName("Tests Unitarios para SupervisionServiceImpl")
public class SupervisionServiceImplTest {

    // Simula la dependencia SupervisionRepository
    @Mock
    private SupervisionRepository repository;

    // Inyecta el mock en el servicio a probar
    @InjectMocks
    private SupervisionServiceImpl supervisionService;

    private Supervision supervisionValida;

    // IDs de prueba para la clave compuesta
    private final int ID_ORDEN = 10;
    private final int ID_SUPERVISOR = 1;
    private final int ID_SUPERVISADO = 2;
    private final int ID_ESPECIALIDAD = 5;

    @BeforeEach
    void setUp() {
        // Inicializa una supervisión válida (5 campos)
        supervisionValida = new Supervision(
                ID_ORDEN,           // idOrdenServicio
                ID_SUPERVISOR,      // idMecanicoSupervisor
                ID_SUPERVISADO,     // idMecanicoSupervisado
                ID_ESPECIALIDAD,    // idEspecialidad
                "Revisión de frenos conforme a norma." // observaciones
        );
    }

    // -----------------------------------------------------------------
    // Tests para listarSupervisiones()
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería retornar una lista de supervisiones")
    void testListarSupervisiones_Exitoso() {
        // Configurar Mock
        Supervision sup2 = new Supervision(11, 3, 4, 6, "Observación 2");
        List<Supervision> listaSupervisiones = Arrays.asList(supervisionValida, sup2);
        when(repository.listarSupervisiones()).thenReturn(listaSupervisiones);

        // Ejecutar y Verificar
        List<Supervision> resultado = supervisionService.listarSupervisiones();
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(repository, times(1)).listarSupervisiones();
    }

    // -----------------------------------------------------------------
    // Tests para buscarSupervision(int idOrdenServicio, int idSupervisor, int idSupervisado)
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería encontrar una supervisión por IDs válidos")
    void testBuscarSupervision_IDsValidos() {
        // Configurar Mock
        when(repository.buscarSupervision(ID_ORDEN, ID_SUPERVISOR, ID_SUPERVISADO)).thenReturn(supervisionValida);

        // Ejecutar y Verificar
        Supervision encontrado = supervisionService.buscarSupervision(ID_ORDEN, ID_SUPERVISOR, ID_SUPERVISADO);
        assertNotNull(encontrado);
        verify(repository, times(1)).buscarSupervision(ID_ORDEN, ID_SUPERVISOR, ID_SUPERVISADO);
    }

    @Test
    @DisplayName("Debería lanzar excepción si algún ID es no positivo al buscar")
    void testBuscarSupervision_IDNoValido() {
        // Caso 1: ID Orden <= 0
        Exception excepcion1 = assertThrows(IllegalArgumentException.class, () -> {
            supervisionService.buscarSupervision(0, ID_SUPERVISOR, ID_SUPERVISADO);
        });
        assertEquals("Todos los IDs deben ser mayores que cero", excepcion1.getMessage());

        // Caso 2: ID Supervisor <= 0
        Exception excepcion2 = assertThrows(IllegalArgumentException.class, () -> {
            supervisionService.buscarSupervision(ID_ORDEN, -1, ID_SUPERVISADO);
        });
        assertEquals("Todos los IDs deben ser mayores que cero", excepcion2.getMessage());

        // Caso 3: ID Supervisado <= 0
        Exception excepcion3 = assertThrows(IllegalArgumentException.class, () -> {
            supervisionService.buscarSupervision(ID_ORDEN, ID_SUPERVISOR, 0);
        });
        assertEquals("Todos los IDs deben ser mayores que cero", excepcion3.getMessage());

        verify(repository, never()).buscarSupervision(anyInt(), anyInt(), anyInt());
    }

    // -----------------------------------------------------------------
    // Tests para registrarSupervision(Supervision supervision) - Validaciones
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería registrar una supervisión válida")
    void testRegistrarSupervision_Valida() {
        supervisionService.registrarSupervision(supervisionValida);
        verify(repository, times(1)).registrarSupervision(supervisionValida);
    }

    @Test
    @DisplayName("Debería fallar si el ID de Orden de Servicio es inválido")
    void testRegistrarSupervision_IDOrdenInvalido() {
        supervisionValida.setIdOrdenServicio(0);

        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            supervisionService.registrarSupervision(supervisionValida);
        });
        assertEquals("El ID del servicio es obligatorio y debe ser válido", excepcion.getMessage());
        verify(repository, never()).registrarSupervision(any());
    }

    @Test
    @DisplayName("Debería fallar si el ID de Supervisor es inválido")
    void testRegistrarSupervision_IDSupervisorInvalido() {
        supervisionValida.setIdMecanicoSupervisor(-1);

        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            supervisionService.registrarSupervision(supervisionValida);
        });
        assertEquals("El ID del supervisor es obligatorio y debe ser válido", excepcion.getMessage());
        verify(repository, never()).registrarSupervision(any());
    }

    @Test
    @DisplayName("Debería fallar si el ID de Supervisado es inválido")
    void testRegistrarSupervision_IDSupervisadoInvalido() {
        supervisionValida.setIdMecanicoSupervisado(0);

        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            supervisionService.registrarSupervision(supervisionValida);
        });
        assertEquals("El ID del supervisado es obligatorio y debe ser válido", excepcion.getMessage());
        verify(repository, never()).registrarSupervision(any());
    }

    @Test
    @DisplayName("Debería fallar si el ID de Especialidad es inválido")
    void testRegistrarSupervision_IDEspecialidadInvalido() {
        supervisionValida.setIdEspecialidad(-5);

        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            supervisionService.registrarSupervision(supervisionValida);
        });
        assertEquals("La especialidad es obligatoria y debe ser válida", excepcion.getMessage());
        verify(repository, never()).registrarSupervision(any());
    }

    // -----------------------------------------------------------------
    // Tests para eliminarSupervision(int idOrdenServicio, int idSupervisor, int idSupervisado)
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería eliminar una supervisión con IDs válidos")
    void testEliminarSupervision_IDsValidos() {
        // Ejecutar (No debe lanzar excepción)
        assertDoesNotThrow(() -> supervisionService.eliminarSupervision(ID_ORDEN, ID_SUPERVISOR, ID_SUPERVISADO));

        // Verificar
        verify(repository, times(1)).eliminarSupervision(ID_ORDEN, ID_SUPERVISOR, ID_SUPERVISADO);
    }

    @Test
    @DisplayName("Debería lanzar excepción si algún ID es no positivo al eliminar")
    void testEliminarSupervision_IDNoValido() {
        // Caso 1: ID Orden <= 0
        Exception excepcion1 = assertThrows(IllegalArgumentException.class, () -> {
            supervisionService.eliminarSupervision(0, ID_SUPERVISOR, ID_SUPERVISADO);
        });
        assertEquals("Todos los IDs deben ser mayores que cero", excepcion1.getMessage());

        // Caso 2: ID Supervisor <= 0
        Exception excepcion2 = assertThrows(IllegalArgumentException.class, () -> {
            supervisionService.eliminarSupervision(ID_ORDEN, 0, ID_SUPERVISADO);
        });
        assertEquals("Todos los IDs deben ser mayores que cero", excepcion2.getMessage());

        // Caso 3: ID Supervisado <= 0
        Exception excepcion3 = assertThrows(IllegalArgumentException.class, () -> {
            supervisionService.eliminarSupervision(ID_ORDEN, ID_SUPERVISOR, -1);
        });
        assertEquals("Todos los IDs deben ser mayores que cero", excepcion3.getMessage());

        verify(repository, never()).eliminarSupervision(anyInt(), anyInt(), anyInt());
    }
}
