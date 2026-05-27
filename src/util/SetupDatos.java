package util;

import dao.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SetupDatos {
    private SetupDatos() {}

    public static void cargarDatosIniciales() {
        insertarRoles();
        insertarUsuarios();
        insertarCategorias();
        insertarProveedores();
        insertarProductos();
    }

    private static void insertarRoles() {
        String sql = "INSERT IGNORE INTO roles (id_rol, nombre) VALUES (1, 'ADMINISTRADOR'), (2, 'EMPLEADO')";
        ejecutar(sql);
    }

    private static void insertarUsuarios() {
        String deleteSql = "DELETE FROM usuarios WHERE id_usuario IN (1,2)";
        String sql = "INSERT INTO usuarios (id_usuario, id_rol, nombre, apellidos, email, password) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection()) {
            try (PreparedStatement del = conn.prepareStatement(deleteSql)) {
                del.executeUpdate();
            }
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, 1);
                stmt.setInt(2, 1);
                stmt.setString(3, "admin");
                stmt.setString(4, "Administrador");
                stmt.setString(5, "admin@levelup.com");
                stmt.setString(6, HashUtil.sha256("admin"));
                stmt.executeUpdate();

                stmt.setInt(1, 2);
                stmt.setInt(2, 2);
                stmt.setString(3, "usuario");
                stmt.setString(4, "Empleado");
                stmt.setString(5, "usuario@levelup.com");
                stmt.setString(6, HashUtil.sha256("usuario"));
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error insertando usuarios iniciales: " + e.getMessage());
        }
    }

    private static void insertarCategorias() {
        String sql = "INSERT IGNORE INTO categorias (id_categoria, nombre, descripcion) VALUES " +
                "(1, 'Videojuegos', 'Juegos para distintas plataformas')," +
                "(2, 'Merchandising', 'Figuras, camisetas y accesorios')," +
                "(3, 'Consolas', 'Hardware y consolas')";
        ejecutar(sql);
    }

    private static void insertarProveedores() {
        String sql = "INSERT IGNORE INTO proveedores (id_proveedor, nombre, direccion, email, contacto, telefono) VALUES " +
                "(1, 'Nintendo Distribución', 'Calle Japón 1', 'nintendo@proveedor.com', 'Laura', '900111111')," +
                "(2, 'Bandai Merch', 'Avenida Anime 2', 'bandai@proveedor.com', 'Sergio', '900222222')";
        ejecutar(sql);
    }

    private static void insertarProductos() {
        String sql = "INSERT IGNORE INTO productos (id_producto, id_proveedor, id_categoria, nombre, descripcion, precio, stock) VALUES " +
                "(1, 1, 1, 'Zelda TOTK', 'Juego para Nintendo Switch', 59.99, 10)," +
                "(2, 2, 2, 'Figura Link', 'Figura coleccionable', 34.95, 5)";
        ejecutar(sql);
    }

    private static void ejecutar(String sql) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error en carga inicial: " + e.getMessage());
        }
    }
}
