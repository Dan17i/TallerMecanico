package co.edu.uniquindio.tallermacanico.service;

import co.edu.uniquindio.tallermacanico.model.Especialidad;

import java.util.List;

/**
 * Interfaz para operaciones de negocio sobre Especialidad.
 */
public interface EspecialidadService {

    List<Especialidad> listarEspecialidades();

    Especialidad buscarPorId(int id);

    int registrarEspecialidad(Especialidad especialidad);

    boolean eliminarEspecialidad(int id);
}


