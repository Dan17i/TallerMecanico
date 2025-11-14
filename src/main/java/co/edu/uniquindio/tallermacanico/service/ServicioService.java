package co.edu.uniquindio.tallermacanico.service;


import co.edu.uniquindio.tallermacanico.model.Servicio;

import java.util.List;

public interface ServicioService {

    List<Servicio> listarServicios();

    Servicio buscarPorId(int id);

    void registrarServicio(Servicio servicio);

    void eliminarServicio(int id);
}
