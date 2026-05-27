package dao;

import model.Producto;
import util.LoggerUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ProductoDAOImpl implements ProductoDAO {
    private static final Logger LOGGER = LoggerUtil.getLogger();

    @Override
    public boolean insertar(Producto producto) {
        String sql = "INSERT INTO productos (id_proveedor, id_categoria, nombre, descripcion, precio, stock) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, producto.getIdProveedor());
            stmt.setInt(2, producto.getIdCategoria());
            stmt.setString(3, producto.getNombre());
            stmt.setString(4, producto.getDescripcion());
            stmt.setDouble(5, producto.getPrecio());
            stmt.setInt(6, producto.getStock());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error insertando producto: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Producto obtenerPorId(int id) {
        String sql = "SELECT * FROM productos WHERE id_producto = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return map(rs);
        } catch (SQLException e) {
            LOGGER.severe("Error obteniendo producto: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Producto> listarTodos() {
        String sql = "SELECT * FROM productos ORDER BY id_producto";
        List<Producto> lista = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(map(rs));
        } catch (SQLException e) {
            LOGGER.severe("Error listando productos: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public boolean actualizar(Producto producto) {
        String sql = "UPDATE productos SET id_proveedor=?, id_categoria=?, nombre=?, descripcion=?, precio=?, stock=? WHERE id_producto=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, producto.getIdProveedor());
            stmt.setInt(2, producto.getIdCategoria());
            stmt.setString(3, producto.getNombre());
            stmt.setString(4, producto.getDescripcion());
            stmt.setDouble(5, producto.getPrecio());
            stmt.setInt(6, producto.getStock());
            stmt.setInt(7, producto.getIdProducto());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error actualizando producto: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM productos WHERE id_producto = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error eliminando producto: " + e.getMessage());
            return false;
        }
    }

    private Producto map(ResultSet rs) throws SQLException {
        return new Producto(
                rs.getInt("id_producto"),
                rs.getInt("id_proveedor"),
                rs.getInt("id_categoria"),
                rs.getString("nombre"),
                rs.getString("descripcion"),
                rs.getDouble("precio"),
                rs.getInt("stock")
        );
    }
}
