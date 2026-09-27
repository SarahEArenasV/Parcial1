package co.edu.uniquindio.poo.patronescreacionales.factorymethod;

import co.edu.uniquindio.poo.enums.TipoCurso;

public class CreadorCursoRegular extends CreadorCurso {
    @Override
    protected Curso crearCurso(DatosCurso datos) {
        return new CursoRegular(datos.codigo(), datos.nombre(), datos.idioma(), datos.descripcion(),
                datos.duracionMeses(), datos.valorMensual());
    }

    @Override
    public TipoCurso getTipoCurso() {
        return TipoCurso.REGULAR;
    }
}
