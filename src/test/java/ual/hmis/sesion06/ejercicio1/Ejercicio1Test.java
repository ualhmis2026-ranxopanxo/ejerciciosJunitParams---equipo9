package ual.hmis.sesion06.ejercicio1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class Ejercicio1Test {

    private final Ejercicio1 ejercicio = new Ejercicio1();

    @ParameterizedTest
    @CsvSource({
            "8, 1",
            "9, 1",
            "25, 1",
            "7, 7",
            "30, 1",
            "-14, -7"
    })
    void transformar_devuelveValorEsperado(int x, int esperado) {
        assertEquals(esperado, ejercicio.transformar(x));
    }

}