package dao;

import model.Proveedor;
import java.util.List;

public interface ProveedorDAO {
    boolean insertar(Proveedor proveedor);
    Proveedor obtenerPorId(int id);
    List<Proveedor> listarTodos();
    boolean actualizar(Proveedor proveedor);
    boolean eliminar(int id);
}
