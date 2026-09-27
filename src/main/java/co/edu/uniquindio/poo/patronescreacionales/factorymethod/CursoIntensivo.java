package co.edu.uniquindio.poo.patronescreacionales.factorymethod;

import co.edu.uniquindio.poo.enums.Idioma;
import co.edu.uniquindio.poo.enums.TipoCurso;

public class CursoIntensivo extends Curso {
    private final double porcentajeRecargo;

    public CursoIntensivo(String codigo, String nombre, Idioma idioma, String descripcion,
                          int duracionMeses, double valorMensual, double porcentajeRecargo) {
        super(codigo, nombre, idioma, descripcion, duracionMeses, valorMensual);
        if (porcentajeRecargo < 0) {
            throw new IllegalArgumentException("El recargo no puede ser negativo");
        }
        this.porcentajeRecargo = porcentajeRecargo;
    }

    @Override
    protected double calcularValorMensualidades(int mesesContratados) {
        return getValorMensual() * mesesContratados * (1 + porcentajeRecargo / 100);
    }

    @Override
    public String getDetalleEspecifico() {
        return "Recargo intensivo: " + porcentajeRecargo + "%";
    }

    public double getPorcentajeRecargo() {
        return porcentajeRecargo;
    }

    @Override
    public TipoCurso getTipo() {
        return TipoCurso.INTENSIVO;
    }
}
