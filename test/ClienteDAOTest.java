import dao.ClienteDAO;
import dao.ClienteDAOImpl;
import model.Cliente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ClienteDAOTest {
    @Test
    void testInstanciarClienteDao() {
        ClienteDAO dao = new ClienteDAOImpl();
        Cliente cliente = new Cliente("Laura", "Pérez", "Calle Mayor 1", "laura@test.com", "600111222");
        Assertions.assertNotNull(dao);
        Assertions.assertNotNull(cliente);
    }
}
