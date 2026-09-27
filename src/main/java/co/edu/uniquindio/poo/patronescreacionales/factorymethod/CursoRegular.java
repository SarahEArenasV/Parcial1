package co.edu.uniquindio.poo.patronescreacionales.factorymethod;

import co.edu.uniquindio.poo.enums.Idioma;
import co.edu.uniquindio.poo.enums.TipoCurso;

public class CursoRegular extends Curso {
    public CursoRegular(String codigo, String nombre, Idioma idioma, String descripcion,
                        int duracionMeses, double valorMensual) {
        super(codigo, nombre, idioma, descripcion, duracionMeses, valorMensual);
    }

    @Override
    protected double calcularValorMensualidades(int mesesContratados) {
        return getValorMensual() * mesesContratados;
    }

    @Override
    public TipoCurso getTipo() {
        return TipoCurso.REGULAR;
    }
}
