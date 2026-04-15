package ual.hmis.sesion06.ejercicio7;

import java.util.Random;

public class Ejercicio7 {

    private static final Random RANDOM = new Random();

    @SuppressWarnings("unused")
    private static void aleatorizarArrayFY(int[] array) {
        validarArray(array);

        for (int i = array.length - 1; i > 0; i--) {
            int j = RANDOM.nextInt(i + 1);
            intercambiar(array, i, j);
        }
    }

    @SuppressWarnings("unused")
    private static void aleatorizarArray(int[] array) {
        validarArray(array);

        for (int i = 0; i < array.length; i++) {
            int j = RANDOM.nextInt(array.length);
            intercambiar(array, i, j);
        }
    }

    private static void validarArray(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("El array no puede ser null");
        }
        if (array.length > 100) {
            throw new IllegalArgumentException("El tamano maximo del array es 100");
        }
        for (int valor : array) {
            if (valor < 0 || valor > 100) {
                throw new IllegalArgumentException("Todos los valores deben estar entre 0 y 100");
            }
        }
    }

    private static void intercambiar(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}