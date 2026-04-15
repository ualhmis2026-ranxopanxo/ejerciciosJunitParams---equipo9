package ual.hmis.sesion06.ejercicio4;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Ejercicio4 {

    public boolean esPrimo(int numero) {
        if (numero <= 1) {
            return false;
        }
        if (numero == 2) {
            return true;
        }
        if (numero % 2 == 0) {
            return false;
        }
        for (int i = 3; i * i <= numero; i += 2) {
            if (numero % i == 0) {
                return false;
            }
        }
        return true;
    }
    public static class MezclaLineal {

        public <T extends Comparable<? super T>> List<T> mezclar(List<T> listaA, List<T> listaB) {
            if (listaA == null || listaB == null) {
                throw new IllegalArgumentException("Las listas no pueden ser null");
            }

            List<T> resultado = new ArrayList<>();
            int i = 0;
            int j = 0;

            while (i < listaA.size() && j < listaB.size()) {
                T elementoA = listaA.get(i);
                T elementoB = listaB.get(j);
                int comparacion = elementoA.compareTo(elementoB);

                if (comparacion < 0) {
                    addIfDifferent(resultado, elementoA);
                    i++;
                } else if (comparacion > 0) {
                    addIfDifferent(resultado, elementoB);
                    j++;
                } else {
                    addIfDifferent(resultado, elementoA);
                    i++;
                    j++;
                }
            }

            while (i < listaA.size()) {
                addIfDifferent(resultado, listaA.get(i));
                i++;
            }

            while (j < listaB.size()) {
                addIfDifferent(resultado, listaB.get(j));
                j++;
            }

            return resultado;
        }

        private <T> void addIfDifferent(List<T> destino, T valor) {
            if (destino.isEmpty() || !Objects.equals(destino.get(destino.size() - 1), valor)) {
                destino.add(valor);
            }
        }
    }
}