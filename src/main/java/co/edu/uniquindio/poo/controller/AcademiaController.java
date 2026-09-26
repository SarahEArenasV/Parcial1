package co.edu.uniquindio.poo.controller;

import co.edu.uniquindio.poo.patronescreacionales.singleton.Academia;

public class AcademiaController {
    private final Academia academia;

    public AcademiaController() {
        this(Academia.getInstance());
    }

    public AcademiaController(Academia academia) {
        this.academia = academia;
    }

    public String obtenerDatosAcademia() {
        return academia.getNombreComercial() + "  |  NIT: " + academia.getNit()
                + "  |  " + academia.getDireccion() + "  |  Tel: " + academia.getTelefono()
                + "  |  " + academia.getCorreo() + "  |  " + academia.getPaginaWeb();
    }
}
