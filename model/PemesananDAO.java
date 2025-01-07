package model;

import controller.Koneksi;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PemesananDAO {

    // Menyimpan pemesanan ke database dan mengembalikan id_pemesanan
    public int simpanPemesanan(int idUser, double totalHarga) {
        String query = "INSERT INTO pemesanan (id_user, total_harga, status) VALUES (?, ?, 'proses')";
        try (Connection connection = Koneksi.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
    
            preparedStatement.setInt(1, idUser);
            preparedStatement.setDouble(2, totalHarga);
    
            int affectedRows = preparedStatement.executeUpdate();
    
            if (affectedRows > 0) {
                // Ambil ID yang baru dibuat
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // ID pemesanan
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Gagal menyimpan pemesanan
    }    

    // Mengubah status pemesanan setelah pembayaran selesai
    public boolean ubahStatusPemesanan(int idPemesanan) {
        String query = "UPDATE pemesanan SET status = 'selesai' WHERE id = ?";
        try (Connection connection = Koneksi.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            if (connection == null) {
                JOptionPane.showMessageDialog(null, "Koneksi ke database gagal.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            preparedStatement.setInt(1, idPemesanan);
            int affectedRows = preparedStatement.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error SQL: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
}
