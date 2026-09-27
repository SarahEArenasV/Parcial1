package co.edu.uniquindio.poo.patronescreacionales.factorymethod;

import co.edu.uniquindio.poo.enums.TipoBeneficio;
import co.edu.uniquindio.poo.enums.TipoCurso;

public abstract class CreadorCurso {
    public final Curso crear(DatosCurso datos) {
        if (datos == null) {
            throw new IllegalArgumentException("Los datos del curso son obligatorios");
        }
        Curso curso = crearCurso(datos);
        if (datos.beneficios() != null) {
            for (TipoBeneficio beneficio : datos.beneficios()) {
                curso.agregarBeneficio(beneficio);
            }
        }
        return curso;
    }

    protected abstract Curso crearCurso(DatosCurso datos);

    public abstract TipoCurso getTipoCurso();
}
