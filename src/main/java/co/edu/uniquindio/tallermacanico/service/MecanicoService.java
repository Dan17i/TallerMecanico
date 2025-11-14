package co.edu.uniquindio.tallermacanico.service;

import co.edu.uniquindio.tallermacanico.model.Mecanico;

import java.util.List;

/**
 * Interfaz que define las operaciones del servicio para la entidad Mecanico.
 * Permite gestionar los mecánicos registrados en el sistema.
 */
public interface MecanicoService {

    /**
     * Lista todos los mecánicos registrados.
     * @return lista de mecánicos
     */
    List<Mecanico> listarMecanicos();

    /**
     * Busca un mecánico por su ID.
     * @param id identificador del mecánico
     * @return mecánico encontrado
     * @throws IllegalArgumentException si el ID es inválido
     */
    Mecanico buscarPorId(int id);

    /**
     * Registra un nuevo mecánico en el sistema.
     * @param mecanico objeto con los datos del mecánico
     * @throws IllegalArgumentException si los datos son inválidos
     */
    void registrarMecanico(Mecanico mecanico);

    /**
     * Elimina un mecánico por su ID.
     * @param id identificador del mecánico
     * @throws IllegalArgumentException si el ID es inválido
     */
    void eliminarMecanico(int id);
}

