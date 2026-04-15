package ual.hmis.sesion06.ejercicio3;

public class Ejercicio3 {

    public String enmascararPassword(String password) {
        if (password == null) {
            throw new IllegalArgumentException("El password no puede ser null");
        }

        int longitud = password.length();

        if (longitud < 5) {
            return "password demasiado corto";
        }

        if (longitud <= 8) {
            return "********";
        }

        if (longitud > 40) {
            return "password demasiado largo";
        }

        return "************";
    }
}