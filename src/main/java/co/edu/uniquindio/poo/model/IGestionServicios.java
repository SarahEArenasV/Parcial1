package co.edu.uniquindio.poo.model;

import java.util.List;
import java.util.Optional;

public interface IGestionServicios {
    void registrarServicio(ServicioAdicional servicio);

    Optional<ServicioAdicional> buscarServicio(String codigo);

    void cambiarDisponibilidadServicio(String codigo, boolean disponible);

    List<ServicioAdicional> getServicios();

    List<ServicioAdicional> getServiciosDisponibles();
}
