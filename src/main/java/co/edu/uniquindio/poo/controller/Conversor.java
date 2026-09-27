package co.edu.uniquindio.poo.controller;

final class Conversor {
    private Conversor() {
    }

    static int aEntero(String texto, String campo) {
        try {
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException | NullPointerException e) {
            throw new IllegalArgumentException("El campo " + campo + " debe ser un número entero");
        }
    }

    static double aDecimal(String texto, String campo) {
        try {
            return Double.parseDouble(texto.trim().replace(",", "."));
        } catch (NumberFormatException | NullPointerException e) {
            throw new IllegalArgumentException("El campo " + campo + " debe ser un número");
        }
    }

    static boolean estaVacio(String texto) {
        return texto == null || texto.isBlank();
    }
}
