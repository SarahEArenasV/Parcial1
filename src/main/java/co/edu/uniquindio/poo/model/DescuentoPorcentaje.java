package co.edu.uniquindio.poo.model;

public class DescuentoPorcentaje implements IDescuento {
    private final double porcentaje;

    public DescuentoPorcentaje(double porcentaje) {
        if (porcentaje < 0 || porcentaje > 100) {
            throw new IllegalArgumentException("El porcentaje de descuento debe estar entre 0 y 100");
        }
        this.porcentaje = porcentaje;
    }

    @Override
    public double aplicar(double valor) {
        return valor - (valor * porcentaje / 100);
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    @Override
    public String getDescripcion() {
        return "Descuento del " + porcentaje + "%";
    }
}
