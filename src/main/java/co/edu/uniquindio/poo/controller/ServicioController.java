package co.edu.uniquindio.poo.controller;

import co.edu.uniquindio.poo.enums.TipoServicio;
import co.edu.uniquindio.poo.model.IGestionServicios;
import co.edu.uniquindio.poo.model.ServicioAdicional;
import co.edu.uniquindio.poo.patronescreacionales.singleton.Academia;

import java.util.List;

public class ServicioController {
    private final IGestionServicios gestionServicios;

    public ServicioController() {
        this(Academia.getInstance());
    }

    public ServicioController(IGestionServicios gestionServicios) {
        this.gestionServicios = gestionServicios;
    }

    public ServicioAdicional crearServicio(TipoServicio tipo, String codigo, String precioTexto, String descripcion) {
        if (tipo == null) {
            throw new IllegalArgumentException("Seleccione el tipo de servicio");
        }
        ServicioAdicional servicio = new ServicioAdicional(codigo, tipo, tipo.getNombre(), descripcion,
                Conversor.aDecimal(precioTexto, "precio"), true);
        gestionServicios.registrarServicio(servicio);
        return servicio;
    }

    public void cambiarDisponibilidad(ServicioAdicional servicio, boolean disponible) {
        if (servicio == null) {
            throw new IllegalArgumentException("Seleccione un servicio");
        }
        gestionServicios.cambiarDisponibilidadServicio(servicio.getCodigo(), disponible);
    }

    public List<ServicioAdicional> listarServicios() {
        return gestionServicios.getServicios();
    }

    public List<ServicioAdicional> listarServiciosDisponibles() {
        return gestionServicios.getServiciosDisponibles();
    }
}
