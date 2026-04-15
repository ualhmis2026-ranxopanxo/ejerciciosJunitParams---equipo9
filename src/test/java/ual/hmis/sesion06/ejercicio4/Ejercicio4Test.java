package ual.hmis.sesion06.ejercicio4;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

class Ejercicio4Test {

    private final Ejercicio4 ejercicio = new Ejercicio4();
    private final Ejercicio4.MezclaLineal mezclaLineal = new Ejercicio4.MezclaLineal();

    @ParameterizedTest
    @CsvSource({
            "2, true",
            "3, true",
            "17, true",
            "1, false",
            "0, false",
            "9, false",
            "21, false"
    })
    void esPrimo_evaluaCorrectamente(int numero, boolean esperado) {
        assertEquals(esperado, ejercicio.esPrimo(numero));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/ejercicio4-mezcla-cases.csv", numLinesToSkip = 1)
    void mezclar_devuelveListaOrdenadaSinDuplicados(String listaA, String listaB, String esperado) {
        assertEquals(parseLista(esperado), mezclaLineal.mezclar(parseLista(listaA), parseLista(listaB)));
    }

    @ParameterizedTest
    @CsvSource({
            "true, false",
            "false, true",
            "true, true"
    })
    void mezclar_lanzaExcepcion_siAlgunaListaEsNull(boolean nullA, boolean nullB) {
        List<Integer> listaA = nullA ? null : List.of();
        List<Integer> listaB = nullB ? null : List.of();

        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class, () -> mezclaLineal.mezclar(listaA, listaB));
        assertEquals("Las listas no pueden ser null", ex.getMessage());
    }

    private List<Integer> parseLista(String texto) {
        if (texto == null || texto.equals("EMPTY")) {
            return new ArrayList<>();
        }

        String[] elementos = texto.split("\\|");
        List<Integer> lista = new ArrayList<>();
        for (String elemento : elementos) {
            lista.add(Integer.valueOf(elemento));
        }
        return lista;
    }
}