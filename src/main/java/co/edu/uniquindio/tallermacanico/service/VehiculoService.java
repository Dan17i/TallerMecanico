package co.edu.uniquindio.tallermacanico.service;


import co.edu.uniquindio.tallermacanico.model.Vehiculo;

import java.util.List;

/**
 * Interfaz que define las operaciones del servicio para la entidad Vehiculo.
 * Permite gestionar vehículos registrados en el sistema.
 */
public interface VehiculoService {

    /**
     * Lista todos los vehículos registrados.
     * @return lista de vehículos
     */
    List<Vehiculo> listarVehiculos();

    /**
     * Busca un vehículo por su ID.
     * @param id identificador del vehículo
     * @return vehículo encontrado
     */
    Vehiculo buscarPorId(int id);

    /**
     * Registra un nuevo vehículo en el sistema.
     * @param vehiculo objeto con los datos del vehículo
     */
    void registrarVehiculo(Vehiculo vehiculo);

    /**
     * Elimina un vehículo por su ID.
     * @param id identificador del vehículo
     */
    void eliminarVehiculo(int id);
}
