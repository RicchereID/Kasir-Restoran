package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Koneksi {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/restoran_kasir";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static Connection connection;

    // Singleton: hanya satu instance koneksi yang digunakan
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // Load JDBC Driver
                Class.forName(DRIVER);

                // Buat koneksi baru
                connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
                System.out.println("Koneksi ke database berhasil!");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Gagal terhubung ke database: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Tidak dapat terhubung ke database. Periksa koneksi.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return connection;
    }

    // Metode untuk menutup koneksi
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("Koneksi berhasil ditutup.");
            } catch (SQLException e) {
                System.err.println("Gagal menutup koneksi: " + e.getMessage());
                JOptionPane.showMessageDialog(null, "Gagal menutup koneksi dengan database.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
