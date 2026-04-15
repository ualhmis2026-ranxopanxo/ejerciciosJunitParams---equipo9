package ual.hmis.sesion06.ejercicio5;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

class Ejercicio5Test {

    private static final Path ARCHIVO_ENTRADA = Paths.get(Ejercicio5.ContadorDePalabras.RUTA_RELATIVA_ARCHIVO);
    private final Ejercicio5 ejercicio = new Ejercicio5();
    private final Ejercicio5.ContadorDePalabras contador = new Ejercicio5.ContadorDePalabras();

    @Test
    void maximo_devuelveMayorValor() {
        assertEquals(9, ejercicio.maximo(new int[] {3, 9, 1, 4}));
    }

    @Test
    void maximo_funcionaConNegativos() {
        assertEquals(-2, ejercicio.maximo(new int[] {-5, -2, -7}));
    }

    @Test
    void maximo_lanzaExcepcionConArrayVacio() {
        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class, () -> ejercicio.maximo(new int[] {}));
        assertEquals("El array no puede ser null ni vacio", ex.getMessage());
    }

    @Test
    void maximo_lanzaExcepcionConArrayNull() {
        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class, () -> ejercicio.maximo(null));
        assertEquals("El array no puede ser null ni vacio", ex.getMessage());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/ejercicio5-contador-cases.csv", numLinesToSkip = 1, delimiter = ';')
    void obtenerPalabrasOrdenAlfabetico_devuelveListaEsperada(
            String contenido,
            String esperadoAlfabetico,
            String esperadoOcurrencias
    ) throws IOException {
        escribirContenido(contenido);
        assertEquals(parsearLista(esperadoAlfabetico), contador.obtenerPalabrasOrdenAlfabetico());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/ejercicio5-contador-cases.csv", numLinesToSkip = 1, delimiter = ';')
    void obtenerPalabrasOrdenPorOcurrencias_devuelveListaEsperada(
            String contenido,
            String esperadoAlfabetico,
            String esperadoOcurrencias
    ) throws IOException {
        escribirContenido(contenido);
        assertEquals(parsearLista(esperadoOcurrencias), contador.obtenerPalabrasOrdenPorOcurrencias());
    }

    private void escribirContenido(String tokenContenido) throws IOException {
        Files.createDirectories(ARCHIVO_ENTRADA.getParent());
        String contenido = tokenContenido.equals("EMPTY") ? "" : tokenContenido.replace('|', ' ');
        Files.writeString(ARCHIVO_ENTRADA, contenido, StandardCharsets.UTF_8);
    }

    private List<String> parsearLista(String tokenLista) {
        if (tokenLista.equals("EMPTY")) {
            return List.of();
        }
        return Arrays.asList(tokenLista.split("\\|"));
    }
}