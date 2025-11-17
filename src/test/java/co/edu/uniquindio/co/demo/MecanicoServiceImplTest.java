package co.edu.uniquindio.co.demo;

// Importaciones de las clases reales del proyecto
import co.edu.uniquindio.tallermacanico.model.Mecanico;
import co.edu.uniquindio.tallermacanico.repository.MecanicoRepository;
// Importación de la clase a probar
import co.edu.uniquindio.tallermacanico.service.implement.MecanicoServiceImpl;

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
@DisplayName("Tests Unitarios para MecanicoServiceImpl")
public class MecanicoServiceImplTest {

    // Simula la dependencia MecanicoRepository
    @Mock
    private MecanicoRepository mecanicoRepository;

    // Inyecta el mock en el servicio a probar
    @InjectMocks
    private MecanicoServiceImpl mecanicoService;

    private Mecanico mecanicoValido;

    @BeforeEach
    void setUp() {
        // Inicializa un mecánico válido usando el AllArgsConstructor (5 campos)
        mecanicoValido = new Mecanico(
                1,                  // idMecanico
                "Ricardo",          // nombre
                "Pérez",            // apellido
                "3001234567",       // telefono
                10                  // experienciaAnios
        );
    }

    // -----------------------------------------------------------------
    // Tests para listarMecanicos()
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería retornar una lista de mecánicos no vacía")
    void testListarMecanicos_Exitoso() {
        // Configurar Mock
        Mecanico mec2 = new Mecanico(2, "Ana", "Ruiz", "3119876543", 5);
        List<Mecanico> listaMecanicos = Arrays.asList(mecanicoValido, mec2);
        when(mecanicoRepository.listarMecanicos()).thenReturn(listaMecanicos);

        // Ejecutar y Verificar
        List<Mecanico> resultado = mecanicoService.listarMecanicos();
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(mecanicoRepository, times(1)).listarMecanicos();
    }

    // -----------------------------------------------------------------
    // Tests para buscarPorId(int id)
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería encontrar un mecánico por ID válido")
    void testBuscarPorId_IDValido() {
        // Configurar Mock
        when(mecanicoRepository.buscarPorId(1)).thenReturn(mecanicoValido);

        // Ejecutar y Verificar
        Mecanico encontrado = mecanicoService.buscarPorId(1);
        assertNotNull(encontrado);
        verify(mecanicoRepository, times(1)).buscarPorId(1);
    }

    @Test
    @DisplayName("Debería lanzar excepción para ID no positivo al buscar")
    void testBuscarPorId_IDNoValido() {
        // Ejecutar y verificar
        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            mecanicoService.buscarPorId(0);
        });
        assertEquals("El ID del mecánico debe ser mayor que cero", excepcion.getMessage());
        verify(mecanicoRepository, never()).buscarPorId(anyInt());
    }

    // -----------------------------------------------------------------
    // Tests para registrarMecanico(Mecanico mecanico) - Validaciones
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería registrar un mecánico válido")
    void testRegistrarMecanico_Valido() {
        mecanicoService.registrarMecanico(mecanicoValido);
        verify(mecanicoRepository, times(1)).registrarMecanico(mecanicoValido);
    }

    @Test
    @DisplayName("Debería fallar si el nombre es nulo o vacío")
    void testRegistrarMecanico_NombreInvalido() {
        // Nombre nulo
        Mecanico mecanicoInvalido = new Mecanico(1, null, "Pérez", "300", 5);

        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            mecanicoService.registrarMecanico(mecanicoInvalido);
        });
        assertEquals("El nombre del mecánico es obligatorio", excepcion.getMessage());
        verify(mecanicoRepository, never()).registrarMecanico(any());
    }

    @Test
    @DisplayName("Debería fallar si el apellido está nulo o vacío")
    void testRegistrarMecanico_ApellidoInvalido() {
        // Apellido vacío
        Mecanico mecanicoInvalido = new Mecanico(1, "Ricardo", "", "300", 5);

        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            mecanicoService.registrarMecanico(mecanicoInvalido);
        });
        assertEquals("El apellido del mecánico es obligatorio", excepcion.getMessage());
        verify(mecanicoRepository, never()).registrarMecanico(any());
    }

    @Test
    @DisplayName("Debería fallar si la experiencia es menor a 0")
    void testRegistrarMecanico_ExperienciaMenorCero() {
        // Experiencia -1
        Mecanico mecanicoInvalido = new Mecanico(1, "Ricardo", "Pérez", "300", -1);

        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            mecanicoService.registrarMecanico(mecanicoInvalido);
        });
        assertEquals("La experiencia debe estar entre 0 y 99 años", excepcion.getMessage());
        verify(mecanicoRepository, never()).registrarMecanico(any());
    }

    @Test
    @DisplayName("Debería fallar si la experiencia es mayor a 99")
    void testRegistrarMecanico_ExperienciaMayorNoventaYNueve() {
        // Experiencia 100
        Mecanico mecanicoInvalido = new Mecanico(1, "Ricardo", "Pérez", "300", 100);

        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            mecanicoService.registrarMecanico(mecanicoInvalido);
        });
        assertEquals("La experiencia debe estar entre 0 y 99 años", excepcion.getMessage());
        verify(mecanicoRepository, never()).registrarMecanico(any());
    }

    // -----------------------------------------------------------------
    // Tests para eliminarMecanico(int id)
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería eliminar un mecánico con ID válido y relaciones")
    void testEliminarMecanico_Exitoso() {
        // Configurar Mock: El repositorio retorna 'true' indicando que la eliminación fue exitosa
        when(mecanicoRepository.eliminarMecanicoConRelaciones(5)).thenReturn(true);

        // Ejecutar (No debe lanzar excepción)
        assertDoesNotThrow(() -> mecanicoService.eliminarMecanico(5));

        // Verificar
        verify(mecanicoRepository, times(1)).eliminarMecanicoConRelaciones(5);
    }

    @Test
    @DisplayName("Debería lanzar excepción si el ID no es positivo al eliminar")
    void testEliminarMecanico_IDNoValido() {
        // Ejecutar y verificar
        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            mecanicoService.eliminarMecanico(-1);
        });

        assertEquals("El ID del mecánico debe ser mayor que cero", excepcion.getMessage());
        verify(mecanicoRepository, never()).eliminarMecanicoConRelaciones(anyInt());
    }

    @Test
    @DisplayName("Debería lanzar excepción si el repositorio falla al eliminar relaciones")
    void testEliminarMecanico_FalloRepositorio() {
        // Configurar Mock: El repositorio retorna 'false' indicando un fallo
        when(mecanicoRepository.eliminarMecanicoConRelaciones(5)).thenReturn(false);

        // Ejecutar y verificar
        Exception excepcion = assertThrows(IllegalStateException.class, () -> {
            mecanicoService.eliminarMecanico(5);
        });

        assertEquals("No se pudo eliminar el mecánico ni sus relaciones", excepcion.getMessage());
        verify(mecanicoRepository, times(1)).eliminarMecanicoConRelaciones(5);
    }
}
