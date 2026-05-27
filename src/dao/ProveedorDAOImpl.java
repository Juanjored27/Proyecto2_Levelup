package dao;

import model.Proveedor;
import util.LoggerUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ProveedorDAOImpl implements ProveedorDAO {
    private static final Logger LOGGER = LoggerUtil.getLogger();

    @Override
    public boolean insertar(Proveedor proveedor) {
        String sql = "INSERT INTO proveedores (nombre, direccion, email, contacto, telefono) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, proveedor.getNombre());
            stmt.setString(2, proveedor.getDireccion());
            stmt.setString(3, proveedor.getEmail());
            stmt.setString(4, proveedor.getContacto());
            stmt.setString(5, proveedor.getTelefono());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error insertando proveedor: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Proveedor obtenerPorId(int id) {
        String sql = "SELECT * FROM proveedores WHERE id_proveedor = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return map(rs);
        } catch (SQLException e) {
            LOGGER.severe("Error obteniendo proveedor: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Proveedor> listarTodos() {
        String sql = "SELECT * FROM proveedores ORDER BY id_proveedor";
        List<Proveedor> lista = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(map(rs));
        } catch (SQLException e) {
            LOGGER.severe("Error listando proveedores: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public boolean actualizar(Proveedor proveedor) {
        String sql = "UPDATE proveedores SET nombre=?, direccion=?, email=?, contacto=?, telefono=? WHERE id_proveedor=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, proveedor.getNombre());
            stmt.setString(2, proveedor.getDireccion());
            stmt.setString(3, proveedor.getEmail());
            stmt.setString(4, proveedor.getContacto());
            stmt.setString(5, proveedor.getTelefono());
            stmt.setInt(6, proveedor.getIdProveedor());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error actualizando proveedor: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM proveedores WHERE id_proveedor = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error eliminando proveedor: " + e.getMessage());
            return false;
        }
    }

    private Proveedor map(ResultSet rs) throws SQLException {
        return new Proveedor(
                rs.getInt("id_proveedor"),
                rs.getString("nombre"),
                rs.getString("direccion"),
                rs.getString("email"),
                rs.getString("contacto"),
                rs.getString("telefono")
        );
    }
}
