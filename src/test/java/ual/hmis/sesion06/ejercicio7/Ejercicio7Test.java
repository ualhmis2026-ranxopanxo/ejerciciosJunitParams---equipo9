package ual.hmis.sesion06.ejercicio7;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Ejercicio7Test {

    @ParameterizedTest
    @MethodSource("casosValidos")
    void aleatorizacion_mantieneLosMismosElementos(String methodName, int[] original) throws Exception {
        int[] entrada = original.clone();
        int[] esperadoOrdenado = ordenarCopia(original);

        invocarMetodoPrivado(methodName, entrada);

        assertArrayEquals(esperadoOrdenado, ordenarCopia(entrada));
        assertEquals(original.length, entrada.length);

        for (int valor : entrada) {
            assertTrue(valor >= 0 && valor <= 100);
        }

        if (original.length <= 1) {
            assertArrayEquals(original, entrada);
        }
    }

    @ParameterizedTest
    @MethodSource("casosInvalidos")
    void aleatorizacion_lanzaExcepcion_enEntradasNoValidas(String methodName, int[] entrada, String mensaje) {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> invocarMetodoPrivado(methodName, entrada)
        );
        assertEquals(mensaje, ex.getMessage());
    }

    @SuppressWarnings("unused")
    private static Stream<Arguments> casosValidos() {
        return Stream.of(
                Arguments.of("aleatorizarArrayFY", new int[] {}),
                Arguments.of("aleatorizarArrayFY", new int[] {42}),
                Arguments.of("aleatorizarArrayFY", new int[] {0, 100, 50, 20, 80}),
                Arguments.of("aleatorizarArrayFY", generarArrayTamano(100)),
                Arguments.of("aleatorizarArray", new int[] {}),
                Arguments.of("aleatorizarArray", new int[] {7}),
                Arguments.of("aleatorizarArray", new int[] {0, 1, 2, 3, 4, 5}),
                Arguments.of("aleatorizarArray", generarArrayTamano(100))
        );
    }

    @SuppressWarnings("unused")
    private static Stream<Arguments> casosInvalidos() {
        return Stream.of(
                Arguments.of("aleatorizarArrayFY", null, "El array no puede ser null"),
                Arguments.of("aleatorizarArray", null, "El array no puede ser null"),
                Arguments.of("aleatorizarArrayFY", generarArrayTamano(101), "El tamano maximo del array es 100"),
                Arguments.of("aleatorizarArray", generarArrayTamano(101), "El tamano maximo del array es 100"),
                Arguments.of("aleatorizarArrayFY", new int[] {-1, 0, 1}, "Todos los valores deben estar entre 0 y 100"),
                Arguments.of("aleatorizarArray", new int[] {0, 101}, "Todos los valores deben estar entre 0 y 100")
        );
    }

    private static int[] generarArrayTamano(int tamano) {
        int[] array = new int[tamano];
        for (int i = 0; i < tamano; i++) {
            array[i] = i % 101;
        }
        return array;
    }

    private static int[] ordenarCopia(int[] array) {
        int[] copia = array.clone();
        Arrays.sort(copia);
        return copia;
    }

    private static void invocarMetodoPrivado(String methodName, int[] entrada) throws Exception {
        try {
            Method method = Ejercicio7.class.getDeclaredMethod(methodName, int[].class);
            method.setAccessible(true);
            method.invoke(null, (Object) entrada);
        } catch (InvocationTargetException ex) {
            Throwable causa = ex.getCause();
            if (causa instanceof IllegalArgumentException iae) {
                throw iae;
            }
            throw ex;
        }
    }
}