package dao;

import model.Categoria;
import java.util.List;

public interface CategoriaDAO {
    boolean insertar(Categoria categoria);
    Categoria obtenerPorId(int id);
    List<Categoria> listarTodas();
    boolean actualizar(Categoria categoria);
    boolean eliminar(int id);
}
