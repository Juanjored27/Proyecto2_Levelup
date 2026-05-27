package dao;

import model.Categoria;
import util.LoggerUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CategoriaDAOImpl implements CategoriaDAO {
    private static final Logger LOGGER = LoggerUtil.getLogger();

    @Override
    public boolean insertar(Categoria categoria) {
        String sql = "INSERT INTO categorias (nombre, descripcion) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categoria.getNombre());
            stmt.setString(2, categoria.getDescripcion());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error insertando categoría: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Categoria obtenerPorId(int id) {
        String sql = "SELECT * FROM categorias WHERE id_categoria = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return map(rs);
        } catch (SQLException e) {
            LOGGER.severe("Error obteniendo categoría: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Categoria> listarTodas() {
        String sql = "SELECT * FROM categorias ORDER BY id_categoria";
        List<Categoria> lista = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(map(rs));
        } catch (SQLException e) {
            LOGGER.severe("Error listando categorías: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public boolean actualizar(Categoria categoria) {
        String sql = "UPDATE categorias SET nombre=?, descripcion=? WHERE id_categoria=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categoria.getNombre());
            stmt.setString(2, categoria.getDescripcion());
            stmt.setInt(3, categoria.getIdCategoria());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error actualizando categoría: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM categorias WHERE id_categoria = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error eliminando categoría: " + e.getMessage());
            return false;
        }
    }

    private Categoria map(ResultSet rs) throws SQLException {
        return new Categoria(rs.getInt("id_categoria"), rs.getString("nombre"), rs.getString("descripcion"));
    }
}
