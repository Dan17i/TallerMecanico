package co.edu.uniquindio.co.demo;

// Importaciones de las clases reales del proyecto
import co.edu.uniquindio.tallermacanico.model.Especialidad;
import co.edu.uniquindio.tallermacanico.repository.EspecialidadRepository;
// Importación de la clase a probar
import co.edu.uniquindio.tallermacanico.service.implement.EspecialidadServiceImpl;

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
@DisplayName("Tests Unitarios para EspecialidadServiceImpl")
public class EspecialidadServiceImplTest {

    // Simula la dependencia EspecialidadRepository
    @Mock
    private EspecialidadRepository repository;

    // Inyecta el mock en el servicio a probar
    @InjectMocks
    private EspecialidadServiceImpl especialidadService;

    private Especialidad especialidadValida;

    @BeforeEach
    void setUp() {
        // Inicializa una especialidad válida usando el AllArgsConstructor (2 campos)
        especialidadValida = new Especialidad(
                0,                  // idEspecialidad (0 para registro inicial)
                "Electricidad"      // nombre
        );
    }

    // -----------------------------------------------------------------
    // Tests para listarEspecialidades()
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería retornar una lista de especialidades no vacía")
    void testListarEspecialidades_Exitoso() {
        // Configurar Mock
        Especialidad esp1 = new Especialidad(1, "Frenos");
        Especialidad esp2 = new Especialidad(2, "Suspensión");
        List<Especialidad> listaEspecialidades = Arrays.asList(esp1, esp2);
        when(repository.listarEspecialidades()).thenReturn(listaEspecialidades);

        // Ejecutar y Verificar
        List<Especialidad> resultado = especialidadService.listarEspecialidades();
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(repository, times(1)).listarEspecialidades();
    }

    // -----------------------------------------------------------------
    // Tests para buscarPorId(int id)
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería encontrar una especialidad por ID válido")
    void testBuscarPorId_IDValido() {
        // Configurar Mock
        Especialidad espEncontrada = new Especialidad(1, "Electricidad");
        when(repository.buscarPorId(1)).thenReturn(espEncontrada);

        // Ejecutar y Verificar
        Especialidad encontrado = especialidadService.buscarPorId(1);
        assertNotNull(encontrado);
        verify(repository, times(1)).buscarPorId(1);
    }

    @Test
    @DisplayName("Debería lanzar excepción para ID no positivo al buscar")
    void testBuscarPorId_IDNoValido() {
        // Ejecutar y verificar
        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            especialidadService.buscarPorId(0);
        });
        assertEquals("El ID debe ser mayor que cero", excepcion.getMessage());
        verify(repository, never()).buscarPorId(anyInt());
    }

    // -----------------------------------------------------------------
    // Tests para registrarEspecialidad(Especialidad especialidad)
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería registrar una especialidad válida y retornar su ID")
    void testRegistrarEspecialidad_Valido() {
        final int ID_GENERADO = 10;

        // Configurar Mock: El repositorio retorna el ID generado
        when(repository.registrarEspecialidad(especialidadValida)).thenReturn(ID_GENERADO);

        // Ejecutar y Verificar
        int idResultado = especialidadService.registrarEspecialidad(especialidadValida);

        assertEquals(ID_GENERADO, idResultado, "El ID retornado debe ser el generado por el repositorio");
        verify(repository, times(1)).registrarEspecialidad(especialidadValida);
    }

    @Test
    @DisplayName("Debería fallar si el nombre de la especialidad es nulo o vacío")
    void testRegistrarEspecialidad_NombreInvalido() {
        // Nombre nulo
        Especialidad espNula = new Especialidad(0, null);

        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            especialidadService.registrarEspecialidad(espNula);
        });
        assertEquals("El nombre de la especialidad es obligatorio", excepcion.getMessage());
        verify(repository, never()).registrarEspecialidad(any());
    }

    // -----------------------------------------------------------------
    // Tests para eliminarEspecialidad(int id)
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería eliminar una especialidad con ID válido y retornar true")
    void testEliminarEspecialidad_Exitoso() {
        final int ID_A_ELIMINAR = 5;
        // Configurar Mock: El repositorio retorna true
        when(repository.eliminarEspecialidad(ID_A_ELIMINAR)).thenReturn(true);

        // Ejecutar y Verificar
        boolean resultado = especialidadService.eliminarEspecialidad(ID_A_ELIMINAR);

        assertTrue(resultado, "Debe retornar true si la eliminación fue exitosa");
        verify(repository, times(1)).eliminarEspecialidad(ID_A_ELIMINAR);
    }

    @Test
    @DisplayName("Debería eliminar una especialidad con ID válido y retornar false (no encontrado)")
    void testEliminarEspecialidad_NoEncontrado() {
        final int ID_A_ELIMINAR = 99;
        // Configurar Mock: El repositorio retorna false (ej. no existe)
        when(repository.eliminarEspecialidad(ID_A_ELIMINAR)).thenReturn(false);

        // Ejecutar y Verificar
        boolean resultado = especialidadService.eliminarEspecialidad(ID_A_ELIMINAR);

        assertFalse(resultado, "Debe retornar false si no se eliminó ninguna fila");
        verify(repository, times(1)).eliminarEspecialidad(ID_A_ELIMINAR);
    }

    @Test
    @DisplayName("Debería lanzar excepción para ID no positivo al eliminar")
    void testEliminarEspecialidad_IDNoValido() {
        // Ejecutar y verificar
        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            especialidadService.eliminarEspecialidad(-1);
        });

        assertEquals("El ID debe ser mayor que cero", excepcion.getMessage());
        verify(repository, never()).eliminarEspecialidad(anyInt());
    }
}
