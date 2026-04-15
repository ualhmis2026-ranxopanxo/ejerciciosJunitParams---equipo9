package ual.hmis.sesion06.ejercicio6;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

class Ejercicio6Test {

    private static final double DELTA = 0.0001;
    private final Ejercicio6 ejercicio = new Ejercicio6();
    private final Ejercicio6.ConversionTemperatura conversion = new Ejercicio6.ConversionTemperatura();

    @ParameterizedTest
    @CsvSource({
            "reconocer, true",
            "Anita lava la tina, true",
            "oso, true",
            "casa, false"
    })
    void esPalindromo_evaluaCorrectamente(String texto, boolean esperado) {
        assertEquals(esperado, ejercicio.esPalindromo(texto));
    }

    @ParameterizedTest
    @NullSource
    void esPalindromo_lanzaExcepcionConNull(String texto) {
        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class, () -> ejercicio.esPalindromo(texto));
        assertEquals("El texto no puede ser null", ex.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "0, Celsius, Fahrenheit, 32",
            "100, Celsius, Fahrenheit, 212",
            "25, Celsius, Kelvin, 298.15",
            "32, Fahrenheit, Celsius, 0",
            "32, Fahrenheit, Kelvin, 273.15",
            "273.15, Kelvin, Celsius, 0",
            "273.15, Kelvin, Fahrenheit, 32",
            "15.4, Celsius, Celsius, 15.4",
            "-273.15, Celsius, Kelvin, 0",
            "0, Kelvin, Celsius, -273.15"
    })
    void convertTemperature_devuelveValorEsperado(
            double temperatura,
            String fromUnit,
            String toUnit,
            double esperado
    ) {
        assertEquals(esperado, conversion.convertTemperature(temperatura, fromUnit, toUnit), DELTA);
    }

    @ParameterizedTest
    @MethodSource("unidadesInvalidas")
    void convertTemperature_devuelveNaN_conUnidadesNoValidas(
            double temperatura,
            String fromUnit,
            String toUnit
    ) {
        assertTrue(Double.isNaN(conversion.convertTemperature(temperatura, fromUnit, toUnit)));
    }

    @SuppressWarnings("unused")
    private static Stream<Arguments> unidadesInvalidas() {
        return Stream.of(
                Arguments.of(10.0, null, "Celsius"),
                Arguments.of(10.0, "Celsius", null),
                Arguments.of(10.0, "", "Celsius"),
                Arguments.of(10.0, "Celsius", ""),
                Arguments.of(10.0, "Rankine", "Kelvin"),
                Arguments.of(10.0, "Kelvin", "Rankine")
        );
    }
}