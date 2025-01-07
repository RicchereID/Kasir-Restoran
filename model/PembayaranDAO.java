package model;

import controller.Koneksi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class PembayaranDAO {

    // Cek apakah ID pemesanan ada di tabel pemesanan
    public boolean cekIdPemesananExist(int idPemesanan) {
        String query = "SELECT COUNT(*) AS total FROM pemesanan WHERE id = ?";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, idPemesanan);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total") > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }       
    
    // Metode untuk mendapatkan id_pemesanan terbaru
    public int getIdPemesananTerbaru() {
        String query = "SELECT id FROM pemesanan ORDER BY id DESC LIMIT 1";
        try (Connection conn = Koneksi.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("id"); // Kembalikan id_pemesanan terbaru
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat mengambil ID pemesanan terbaru: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return -1; // Kembalikan -1 jika tidak ada id_pemesanan
    }

    // Simpan data ke tabel riwayat_transaksi
    public boolean simpanRiwayatTransaksi(int id, int idPemesanan, double totalBayar, String metodePembayaran, double kembalian) {
        String query = "INSERT INTO riwayat_transaksi (id, id_pemesanan, total_bayar, metode_pembayaran, kembalian) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id); // ID transaksi
            ps.setInt(2, idPemesanan); // ID pemesanan
            ps.setDouble(3, totalBayar); // Total bayar
            ps.setString(4, metodePembayaran); // Metode pembayaran
            ps.setDouble(5, kembalian); // Kembalian
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat menyimpan transaksi: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }        

    // Menyimpan detail pemesanan
    public boolean simpanDetailPemesanan(int idPemesanan, int idMenu, int jumlah, double hargaSatuan, double subtotal) {
        String query = "INSERT INTO detail_pemesanan (id_pemesanan, id_menu, jumlah, harga_satuan, subtotal) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = Koneksi.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, idPemesanan);
            preparedStatement.setInt(2, idMenu);
            preparedStatement.setInt(3, jumlah);
            preparedStatement.setDouble(4, hargaSatuan);
            preparedStatement.setDouble(5, subtotal);

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean prosesPembayaran(int idPemesanan, String metodePembayaran) {
        // Tidak perlu menyimpan metode pembayaran di tabel pemesanan, hanya update status
        String query = "UPDATE pemesanan SET status = 'selesai' WHERE id = ?";
        try (Connection connection = Koneksi.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
    
            stmt.setInt(1, idPemesanan);  // Update status menjadi 'selesai'
    
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0; // Mengembalikan true jika pembayaran berhasil diproses
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Mengembalikan false jika ada kesalahan dalam memproses pembayaran
    }    
}