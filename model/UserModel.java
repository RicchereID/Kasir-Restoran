package model;

import java.sql.*;

public class UserModel {
    private Connection connection;

    public UserModel(Connection connection) {
        this.connection = connection;
    }

    public boolean authenticate(String username, String password) {
        try {
            String query = "SELECT * FROM users WHERE username = ? AND password = SHA2(?, 256)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            return rs.next(); // Jika ada hasil, login berhasil
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
