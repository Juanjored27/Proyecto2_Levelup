package dao;

import model.Cliente;
import util.LoggerUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ClienteDAOImpl implements ClienteDAO {
    private static final Logger LOGGER = LoggerUtil.getLogger();

    @Override
    public boolean insertar(Cliente cliente) {
        String sql = "INSERT INTO clientes (nombre, apellidos, direccion, email, telefono) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellidos());
            stmt.setString(3, cliente.getDireccion());
            stmt.setString(4, cliente.getEmail());
            stmt.setString(5, cliente.getTelefono());
            boolean ok = stmt.executeUpdate() > 0;
            LOGGER.info("Cliente insertado: " + cliente.getEmail());
            return ok;
        } catch (SQLException e) {
            LOGGER.severe("Error insertando cliente: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Cliente obtenerPorId(int id) {
        String sql = "SELECT * FROM clientes WHERE id_cliente = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return map(rs);
        } catch (SQLException e) {
            LOGGER.severe("Error obteniendo cliente: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Cliente> listarTodos() {
        String sql = "SELECT * FROM clientes ORDER BY id_cliente";
        List<Cliente> lista = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(map(rs));
        } catch (SQLException e) {
            LOGGER.severe("Error listando clientes: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public boolean actualizar(Cliente cliente) {
        String sql = "UPDATE clientes SET nombre=?, apellidos=?, direccion=?, email=?, telefono=? WHERE id_cliente=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellidos());
            stmt.setString(3, cliente.getDireccion());
            stmt.setString(4, cliente.getEmail());
            stmt.setString(5, cliente.getTelefono());
            stmt.setInt(6, cliente.getIdCliente());
            boolean ok = stmt.executeUpdate() > 0;
            LOGGER.info("Cliente actualizado: " + cliente.getIdCliente());
            return ok;
        } catch (SQLException e) {
            LOGGER.severe("Error actualizando cliente: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM clientes WHERE id_cliente = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            boolean ok = stmt.executeUpdate() > 0;
            LOGGER.info("Cliente eliminado: " + id);
            return ok;
        } catch (SQLException e) {
            LOGGER.severe("Error eliminando cliente: " + e.getMessage());
            return false;
        }
    }

    private Cliente map(ResultSet rs) throws SQLException {
        Cliente c = new Cliente();
        c.setIdCliente(rs.getInt("id_cliente"));
        c.setNombre(rs.getString("nombre"));
        c.setApellidos(rs.getString("apellidos"));
        c.setDireccion(rs.getString("direccion"));
        c.setEmail(rs.getString("email"));
        c.setTelefono(rs.getString("telefono"));
        Timestamp ts = rs.getTimestamp("fecha_registro");
        if (ts != null) c.setFechaRegistro(ts.toLocalDateTime());
        return c;
    }
}
