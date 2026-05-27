package dao;

import model.Usuario;
import util.LoggerUtil;

import java.sql.*;
import java.util.logging.Logger;

public class UsuarioDAOImpl implements UsuarioDAO {
    private static final Logger LOGGER = LoggerUtil.getLogger();

    @Override
    public Usuario login(String email, String passwordHash) {
        String sql = "SELECT u.*, r.nombre AS rol_nombre FROM usuarios u JOIN roles r ON u.id_rol = r.id_rol WHERE (u.email = ? OR u.nombre = ?) AND u.password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, email);
            stmt.setString(3, passwordHash);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setIdRol(rs.getInt("id_rol"));
                u.setRolNombre(rs.getString("rol_nombre"));
                u.setNombre(rs.getString("nombre"));
                u.setApellidos(rs.getString("apellidos"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                Timestamp ts = rs.getTimestamp("fecha_creacion");
                if (ts != null) u.setFechaCreacion(ts.toLocalDateTime());
                return u;
            }
        } catch (SQLException e) {
            LOGGER.severe("Error en login DAO: " + e.getMessage());
        }
        return null;
    }
}
