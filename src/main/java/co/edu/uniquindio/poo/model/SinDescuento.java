package co.edu.uniquindio.poo.model;

public class SinDescuento implements IDescuento {
    @Override
    public double aplicar(double valor) {
        return valor;
    }

    @Override
    public String getDescripcion() {
        return "Sin descuento";
    }
}
