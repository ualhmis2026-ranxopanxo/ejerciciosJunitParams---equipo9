package ual.hmis.sesion06.ejercicio2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;

class Ejercicio2Test {

    private final Ejercicio2 ejercicio = new Ejercicio2();

    @ParameterizedTest
    @NullSource
    void login_devuelveFalse_siUsernameEsNull(String username) {
        assertEquals(false, ejercicio.login(username, "Pass1"));
    }

    @ParameterizedTest
    @NullSource
    void login_devuelveFalse_siPasswordEsNull(String password) {
        assertEquals(false, ejercicio.login("user", password));
    }

    @ParameterizedTest
    @CsvSource({
            "'', Pass1",
            "user, ''"
    })
    void login_devuelveFalse_siHayCadenasVacias(String username, String password) {
        assertEquals(false, ejercicio.login(username, password));
    }

    @ParameterizedTest
    @CsvSource({
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa, Pass1",
            "user, Aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa1"
    })
    void login_devuelveFalse_siLongitudEs30OMas(String username, String password) {
        assertEquals(false, ejercicio.login(username, password));
    }

    @ParameterizedTest
    @CsvSource({
            "user, pass1",
            "user, PASS1",
            "user, Pass"
    })
    void login_devuelveFalse_siPasswordNoCumpleComplejidad(String username, String password) {
        assertEquals(false, ejercicio.login(username, password));
    }

    @ParameterizedTest
    @CsvSource({
            "user, Pass1"
    })
    void login_devuelveTrue_siDatosSonValidosYExistenEnBd(String username, String password) {
        assertEquals(true, ejercicio.login(username, password));
    }

    @ParameterizedTest
    @CsvSource({
            "user2, Pass1",
            "user, Pass2",
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaa, Aaaaaaaaaaaaaaaaaaaaaaaaaaa1"
    })
    void login_devuelveFalse_siFormatoEsValidoPeroNoExisteEnBd(String username, String password) {
        assertEquals(false, ejercicio.login(username, password));
    }
}