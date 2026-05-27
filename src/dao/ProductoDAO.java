package dao;

import model.Producto;
import java.util.List;

public interface ProductoDAO {
    boolean insertar(Producto producto);
    Producto obtenerPorId(int id);
    List<Producto> listarTodos();
    boolean actualizar(Producto producto);
    boolean eliminar(int id);
}
