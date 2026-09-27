package co.edu.uniquindio.poo.patronescreacionales.factorymethod;

import co.edu.uniquindio.poo.enums.TipoCurso;

public class CreadorCursoPersonalizado extends CreadorCurso {
    @Override
    protected Curso crearCurso(DatosCurso datos) {
        return new CursoPersonalizado(datos.codigo(), datos.nombre(), datos.idioma(), datos.descripcion(),
                datos.duracionMeses(), datos.valorMensual(), datos.cantidadSesiones(),
                datos.nivelRequerido(), datos.objetivosEstudiante());
    }

    @Override
    public TipoCurso getTipoCurso() {
        return TipoCurso.PERSONALIZADO;
    }
}
