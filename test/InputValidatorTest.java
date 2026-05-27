import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import util.InputValidator;

public class InputValidatorTest {

    @Test
    void parseaDoubleConPunto() {
        Assertions.assertEquals(59.99, InputValidator.parseDoubleFlexible("59.99"), 0.0001);
    }

    @Test
    void parseaDoubleConComa() {
        Assertions.assertEquals(59.99, InputValidator.parseDoubleFlexible("59,99"), 0.0001);
    }

    @Test
    void emailValido() {
        Assertions.assertTrue(InputValidator.isValidEmail("admin@levelup.com"));
    }

    @Test
    void emailInvalido() {
        Assertions.assertFalse(InputValidator.isValidEmail("adminlevelup.com"));
    }
}
