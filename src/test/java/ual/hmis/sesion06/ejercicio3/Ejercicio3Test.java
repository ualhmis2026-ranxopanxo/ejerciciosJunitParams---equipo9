package ual.hmis.sesion06.ejercicio3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.NullSource;

class Ejercicio3Test {

    private final Ejercicio3 ejercicio = new Ejercicio3();

    @ParameterizedTest
    @CsvFileSource(resources = "/ejercicio3-password-cases.csv", numLinesToSkip = 1)
    void enmascararPassword_devuelveSalidaEsperada(String password, String esperado) {
        assertEquals(esperado, ejercicio.enmascararPassword(password));
    }

    @ParameterizedTest
    @NullSource
    void enmascararPassword_lanzaExcepcionConNull(String password) {
        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class, () -> ejercicio.enmascararPassword(password));
        assertEquals("El password no puede ser null", ex.getMessage());
    }
}