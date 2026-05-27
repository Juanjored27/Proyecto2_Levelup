package dao;

import model.Cliente;
import java.util.List;

public interface ClienteDAO {
    boolean insertar(Cliente cliente);
    Cliente obtenerPorId(int id);
    List<Cliente> listarTodos();
    boolean actualizar(Cliente cliente);
    boolean eliminar(int id);
}
