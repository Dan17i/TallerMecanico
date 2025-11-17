package co.edu.uniquindio.co.demo;

// Importaciones de las clases reales del proyecto
import co.edu.uniquindio.tallermacanico.model.Repuesto;
import co.edu.uniquindio.tallermacanico.repository.RepuestoRepository;
// Importación de la clase a probar
import co.edu.uniquindio.tallermacanico.service.implement.RepuestoServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests Unitarios para RepuestoServiceImpl")
public class RepuestoServiceImplTest {

    // Simula la dependencia RepuestoRepository
    @Mock
    private RepuestoRepository repuestoRepository;

    // Inyecta el mock en el servicio a probar
    @InjectMocks
    private RepuestoServiceImpl repuestoService;

    private Repuesto repuestoValido;
    private final int ID_GENERADO = 10;
    private final double STOCK_INICIAL = 50.0;

    @BeforeEach
    void setUp() {
        // Inicializa un repuesto válido
        // Constructor: idRepuesto, nombre, descripcion, stockActual, unidadMedida
        repuestoValido = new Repuesto(
                0,                      // idRepuesto (0 para registro inicial)
                "Filtro de Aceite",     // nombre
                "Filtro para motor 2.0L", // descripcion
                STOCK_INICIAL,          // stockActual
                "UNIDADES"              // unidadMedida
        );
    }

    // -----------------------------------------------------------------
    // Tests para listarRepuestos()
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería retornar una lista de repuestos no vacía")
    void testListarRepuestos_Exitoso() {
        // Configurar Mock
        Repuesto repuesto2 = new Repuesto(11, "Pastillas Freno", "Juego delantero", 10.0, "JUEGOS");
        repuestoValido.setIdRepuesto(ID_GENERADO);
        List<Repuesto> listaRepuestos = Arrays.asList(repuestoValido, repuesto2);
        when(repuestoRepository.listarRepuestos()).thenReturn(listaRepuestos);

        // Ejecutar y Verificar
        List<Repuesto> resultado = repuestoService.listarRepuestos();
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(repuestoRepository, times(1)).listarRepuestos();
    }

    // -----------------------------------------------------------------
    // Tests para registrarRepuesto(Repuesto repuesto)
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería registrar un repuesto y actualizar su ID")
    void testRegistrarRepuesto_Exitoso() {
        // Configurar Mock: Simular que el repositorio devuelve el ID autogenerado
        when(repuestoRepository.registrarRepuesto(repuestoValido)).thenReturn(ID_GENERADO);

        // Ejecutar
        Repuesto repuestoRegistrado = repuestoService.registrarRepuesto(repuestoValido);

        // Verificar
        assertEquals(ID_GENERADO, repuestoRegistrado.getIdRepuesto(),
                "El ID del repuesto en el objeto devuelto debe ser actualizado");
        verify(repuestoRepository, times(1)).registrarRepuesto(repuestoValido);
    }

    // -----------------------------------------------------------------
    // Tests para obtenerPorId(int id)
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería obtener un repuesto por ID")
    void testObtenerPorId_Exitoso() {
        // Configurar Mock
        repuestoValido.setIdRepuesto(ID_GENERADO);
        when(repuestoRepository.buscarPorId(ID_GENERADO)).thenReturn(repuestoValido);

        // Ejecutar y Verificar
        Repuesto encontrado = repuestoService.obtenerPorId(ID_GENERADO);
        assertNotNull(encontrado);
        assertEquals(ID_GENERADO, encontrado.getIdRepuesto());
        verify(repuestoRepository, times(1)).buscarPorId(ID_GENERADO);
    }

    // -----------------------------------------------------------------
    // Tests para eliminarRepuesto(int id)
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería llamar al repositorio para eliminar por ID")
    void testEliminarRepuesto_Exitoso() {
        // Ejecutar
        assertDoesNotThrow(() -> repuestoService.eliminarRepuesto(ID_GENERADO));

        // Verificar
        verify(repuestoRepository, times(1)).eliminarRepuesto(ID_GENERADO);
    }

    // -----------------------------------------------------------------
    // Tests para actualizarStock(int id, double nuevoStock)
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Debería llamar al repositorio para actualizar el stock")
    void testActualizarStock_Exitoso() {
        final double NUEVO_STOCK = 75.5;

        // Ejecutar
        assertDoesNotThrow(() -> repuestoService.actualizarStock(ID_GENERADO, NUEVO_STOCK));

        // Verificar: Capturamos los argumentos pasados al repositorio
        ArgumentCaptor<Integer> idCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Double> stockCaptor = ArgumentCaptor.forClass(Double.class);

        verify(repuestoRepository, times(1)).actualizarStock(idCaptor.capture(), stockCaptor.capture());

        assertEquals(ID_GENERADO, idCaptor.getValue(), "El ID pasado al repositorio debe ser correcto");
        assertEquals(NUEVO_STOCK, stockCaptor.getValue(), 0.001, "El stock pasado al repositorio debe ser correcto");
    }
}