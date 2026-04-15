package ual.hmis.sesion06.ejercicio6;

import java.util.Locale;

public class Ejercicio6 {

    public boolean esPalindromo(String texto) {
        if (texto == null) {
            throw new IllegalArgumentException("El texto no puede ser null");
        }
        String normalizado = texto.replaceAll("\\s+", "").toLowerCase();
        String invertido = new StringBuilder(normalizado).reverse().toString();
        return normalizado.equals(invertido);
    }

    public static class ConversionTemperatura {

        public double convertTemperature(double temperature, String fromUnit, String toUnit) {
            if (fromUnit == null || toUnit == null) {
                return Double.NaN;
            }

            String origen = fromUnit.trim().toLowerCase(Locale.ROOT);
            String destino = toUnit.trim().toLowerCase(Locale.ROOT);

            if (!esUnidadValida(origen) || !esUnidadValida(destino)) {
                return Double.NaN;
            }

            if (origen.equals(destino)) {
                return temperature;
            }

            return switch (origen) {
                case "celsius" -> convertirDesdeCelsius(temperature, destino);
                case "fahrenheit" -> convertirDesdeFahrenheit(temperature, destino);
                case "kelvin" -> convertirDesdeKelvin(temperature, destino);
                default -> Double.NaN;
            };
        }

        private boolean esUnidadValida(String unidad) {
            return unidad.equals("celsius") || unidad.equals("fahrenheit") || unidad.equals("kelvin");
        }

        private double convertirDesdeCelsius(double temperatura, String destino) {
            return switch (destino) {
                case "fahrenheit" -> (temperatura * 9 / 5) + 32;
                case "kelvin" -> temperatura + 273.15;
                default -> Double.NaN;
            };
        }

        private double convertirDesdeFahrenheit(double temperatura, String destino) {
            return switch (destino) {
                case "celsius" -> (temperatura - 32) * 5 / 9;
                case "kelvin" -> ((temperatura - 32) * 5 / 9) + 273.15;
                default -> Double.NaN;
            };
        }

        private double convertirDesdeKelvin(double temperatura, String destino) {
            return switch (destino) {
                case "celsius" -> temperatura - 273.15;
                case "fahrenheit" -> ((temperatura - 273.15) * 9 / 5) + 32;
                default -> Double.NaN;
            };
        }
    }
}