package ual.hmis.sesion06.ejercicio5;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ejercicio5 {

    public int maximo(int[] numeros) {
        if (numeros == null || numeros.length == 0) {
            throw new IllegalArgumentException("El array no puede ser null ni vacio");
        }
        int max = numeros[0];
        for (int i = 1; i < numeros.length; i++) {
            if (numeros[i] > max) {
                max = numeros[i];
            }
        }
        return max;
    }

    public static class ContadorDePalabras {

        static final String RUTA_RELATIVA_ARCHIVO = "src/main/resources/ejercicio5/entrada-palabras.txt";
        private static final Path ARCHIVO_ENTRADA = Paths.get(RUTA_RELATIVA_ARCHIVO);

        public List<String> obtenerPalabrasOrdenAlfabetico() throws IOException {
            List<String> palabras = new ArrayList<>(leerPalabras());
            palabras.sort(Comparator.naturalOrder());
            return palabras;
        }

        public List<String> obtenerPalabrasOrdenPorOcurrencias() throws IOException {
            Map<String, Integer> frecuencias = new HashMap<>();
            for (String palabra : leerPalabras()) {
                frecuencias.put(palabra, frecuencias.getOrDefault(palabra, 0) + 1);
            }

            return frecuencias.entrySet().stream()
                    .sorted(
                            Comparator.<Map.Entry<String, Integer>>comparingInt(Map.Entry::getValue)
                                    .reversed()
                                    .thenComparing(Map.Entry::getKey)
                    )
                    .map(Map.Entry::getKey)
                    .toList();
        }

        private List<String> leerPalabras() throws IOException {
            String contenido = Files.readString(ARCHIVO_ENTRADA, StandardCharsets.UTF_8);
            if (contenido.isBlank()) {
                return List.of();
            }

            String[] tokens = contenido.toLowerCase().split("[^\\p{L}\\p{N}]+", -1);
            List<String> palabras = new ArrayList<>();
            for (String token : tokens) {
                if (!token.isBlank()) {
                    palabras.add(token);
                }
            }
            return palabras;
        }
    }
}