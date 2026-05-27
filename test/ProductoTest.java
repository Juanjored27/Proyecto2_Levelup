import model.Producto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProductoTest {

    @Test
    void precioSeGuardaComoDouble() {
        Producto producto = new Producto(1, 1, "Producto test", "Descripción test", 39.95, 7);
        Assertions.assertEquals(39.95, producto.getPrecio(), 0.0001);
    }

    @Test
    void stockSeGuardaCorrectamente() {
        Producto producto = new Producto(1, 1, "Producto test", "Descripción test", 39.95, 7);
        Assertions.assertEquals(7, producto.getStock());
    }
}
