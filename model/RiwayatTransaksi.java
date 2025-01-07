package model;

import controller.Koneksi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RiwayatTransaksi {

    // Mendapatkan semua data transaksi dari database
    public static List<Object[]> getRiwayatTransaksi() {
        List<Object[]> dataTransaksi = new ArrayList<>();
        String query = "SELECT id, id_pemesanan, tanggal_transaksi, metode_pembayaran, total_bayar, kembalian FROM riwayat_transaksi";
    
        try (Connection connection = Koneksi.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
    
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int idPemesanan = resultSet.getInt("id_pemesanan");
                String tanggal = resultSet.getString("tanggal_transaksi");
                String metodePembayaran = resultSet.getString("metode_pembayaran");
                double totalBayar = resultSet.getDouble("total_bayar");
                double kembalian = resultSet.getDouble("kembalian");
    
                // Debugging log
                System.out.println("Data ditemukan: ID=" + id + ", Total=" + totalBayar);
    
                // Tambahkan data ke list
                dataTransaksi.add(new Object[]{id, idPemesanan, tanggal, metodePembayaran, totalBayar, kembalian});
            }
    
        } catch (SQLException e) {
            System.err.println("Kesalahan query: " + e.getMessage());
        }
        return dataTransaksi;
    }
}
