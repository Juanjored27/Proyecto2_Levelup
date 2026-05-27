package util;

public class InputValidator {
    private InputValidator() {}

    public static boolean isNotBlank(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public static boolean isValidEmail(String email) {
        return isNotBlank(email) && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    public static boolean isPositiveInt(int value) {
        return value >= 0;
    }

    public static boolean isPositiveDouble(double value) {
        return value >= 0;
    }

    public static int parseInt(String value) {
        if (!isNotBlank(value)) {
            throw new IllegalArgumentException("Debes introducir un número entero.");
        }
        return Integer.parseInt(value.trim());
    }

    public static double parseDoubleFlexible(String value) {
        if (!isNotBlank(value)) {
            throw new IllegalArgumentException("Debes introducir un número decimal.");
        }
        String normalizado = value.trim().replace(',', '.');
        return Double.parseDouble(normalizado);
    }
}
