package dao;

import config.ConfigLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private DatabaseConnection() {}

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                ConfigLoader.getDbUrl(),
                ConfigLoader.getDbUser(),
                ConfigLoader.getDbPassword()
        );
    }
}
