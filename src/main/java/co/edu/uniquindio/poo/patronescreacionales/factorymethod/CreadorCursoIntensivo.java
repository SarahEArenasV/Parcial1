package co.edu.uniquindio.poo.patronescreacionales.factorymethod;

import co.edu.uniquindio.poo.enums.TipoCurso;

public class CreadorCursoIntensivo extends CreadorCurso {
    public static final double RECARGO_POR_DEFECTO = 20;

    private final double porcentajeRecargo;

    public CreadorCursoIntensivo() {
        this(RECARGO_POR_DEFECTO);
    }

    public CreadorCursoIntensivo(double porcentajeRecargo) {
        this.porcentajeRecargo = porcentajeRecargo;
    }

    @Override
    protected Curso crearCurso(DatosCurso datos) {
        return new CursoIntensivo(datos.codigo(), datos.nombre(), datos.idioma(), datos.descripcion(),
                datos.duracionMeses(), datos.valorMensual(), porcentajeRecargo);
    }

    @Override
    public TipoCurso getTipoCurso() {
        return TipoCurso.INTENSIVO;
    }
}
