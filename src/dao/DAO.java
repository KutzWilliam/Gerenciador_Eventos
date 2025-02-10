package dao;

import java.sql.*;

public class DAO {
    protected Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/eventos";
        String user = "postgres";
        String password = "1234";
        return DriverManager.getConnection(url, user, password);
    }
}
