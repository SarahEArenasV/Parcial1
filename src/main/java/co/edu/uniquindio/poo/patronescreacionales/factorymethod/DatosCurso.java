package co.edu.uniquindio.poo.patronescreacionales.factorymethod;

import co.edu.uniquindio.poo.enums.Idioma;
import co.edu.uniquindio.poo.enums.NivelReferencia;
import co.edu.uniquindio.poo.enums.TipoBeneficio;

import java.util.Set;

public record DatosCurso(String codigo, String nombre, Idioma idioma, String descripcion,
                         int duracionMeses, double valorMensual, Set<TipoBeneficio> beneficios,
                         int cantidadSesiones, NivelReferencia nivelRequerido, String objetivosEstudiante) {
    public DatosCurso(String codigo, String nombre, Idioma idioma, String descripcion,
                      int duracionMeses, double valorMensual, Set<TipoBeneficio> beneficios) {
        this(codigo, nombre, idioma, descripcion, duracionMeses, valorMensual, beneficios, 0, null, null);
    }
}
