package co.edu.uniquindio.tallermacanico.service;


import co.edu.uniquindio.tallermacanico.model.Servicio;

import java.util.List;

public interface ServicioService {

    List<Servicio> listarServicios();

    Servicio buscarPorId(int id);

    void registrarServicio(Servicio servicio);

    void eliminarServicio(int id);

    /**
     * Actualiza el precio base de un servicio existente.
     *
     * @param idServicio identificador del servicio
     * @param nuevoPrecio nuevo valor del precio base; debe ser mayor que cero
     */
    void actualizarPrecioBase(int idServicio, double nuevoPrecio);
}
