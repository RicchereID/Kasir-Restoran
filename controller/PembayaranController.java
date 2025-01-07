package controller;

import model.PembayaranDAO;
import view.FormPembayaran;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PembayaranController {

    private PembayaranDAO pembayaranDAO = new PembayaranDAO();
    private FormPembayaran formPembayaran;

    public PembayaranController(FormPembayaran formPembayaran) {
        this.formPembayaran = formPembayaran;
    }

    // Menambahkan metode untuk mendapatkan idMenu berdasarkan nama menu
    public int getIdMenuByNama(String namaMenu) {
        String query = "SELECT id FROM menu WHERE nama_menu = ?";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, namaMenu);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat mengambil ID menu: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return -1;
    }

    // Metode untuk memproses pembayaran
    public void prosesPembayaran(String metodePembayaran) {
        try {
            // Ambil id_pemesanan terbaru
            int idPemesanan = pembayaranDAO.getIdPemesananTerbaru();
            if (idPemesanan == -1) {
                JOptionPane.showMessageDialog(formPembayaran, "Tidak ada pesanan yang dapat diproses.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            // Validasi input total dan bayar
            String totalText = formPembayaran.getTxtTotal().getText();
            String bayarText = formPembayaran.getTxtBayar().getText();
    
            if (totalText.isEmpty() || bayarText.isEmpty()) {
                JOptionPane.showMessageDialog(formPembayaran, "Total dan pembayaran tidak boleh kosong.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
    
            double total = Double.parseDouble(totalText);
            double bayar = Double.parseDouble(bayarText);
    
            if (bayar < total) {
                JOptionPane.showMessageDialog(formPembayaran, "Jumlah pembayaran tidak cukup.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
    
            double kembalian = bayar - total;
    
            // Proses pembayaran
            boolean pembayaranBerhasil = pembayaranDAO.prosesPembayaran(idPemesanan, metodePembayaran);
            if (!pembayaranBerhasil) {
                JOptionPane.showMessageDialog(formPembayaran, "Pembayaran gagal!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            // Simpan data ke tabel riwayat_transaksi
            boolean riwayatBerhasil = pembayaranDAO.simpanRiwayatTransaksi(
                idPemesanan, 
                idPemesanan, 
                total, 
                metodePembayaran, 
                kembalian
            );
    
            // Ambil data dari tabel pembayaran untuk disimpan di detail_pemesanan
            JTable table = formPembayaran.getTablePembayaran();
            boolean detailBerhasil = true;
    
            for (int i = 0; i < table.getRowCount(); i++) {
                String namaMenu = table.getValueAt(i, 0).toString();
                int jumlah = Integer.parseInt(table.getValueAt(i, 1).toString());
                double hargaSatuan = Double.parseDouble(table.getValueAt(i, 2).toString());
                double subtotal = jumlah * hargaSatuan;
    
                int idMenu = getIdMenuByNama(namaMenu);
    
                if (idMenu == -1) {
                    JOptionPane.showMessageDialog(formPembayaran, "Menu " + namaMenu + " tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
                    detailBerhasil = false;
                    break;
                }
    
                boolean hasil = pembayaranDAO.simpanDetailPemesanan(idPemesanan, idMenu, jumlah, hargaSatuan, subtotal);
                if (!hasil) {
                    detailBerhasil = false;
                    break;
                }
            }
    
            if (riwayatBerhasil && detailBerhasil) {
                JOptionPane.showMessageDialog(formPembayaran, "Pembayaran berhasil dan data pesanan tersimpan!", "Informasi", JOptionPane.INFORMATION_MESSAGE);
    
                formPembayaran.getTxtBayar().setText("");
                formPembayaran.getTxtKembalian().setText("");
                ((DefaultTableModel) table.getModel()).setRowCount(0);
                formPembayaran.getTxtTotal().setText("");
            } else {
                JOptionPane.showMessageDialog(formPembayaran, "Terjadi kesalahan saat menyimpan transaksi atau detail pesanan.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(formPembayaran, "Masukkan angka yang valid untuk Total atau Bayar.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(formPembayaran, "Terjadi kesalahan: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }    
}
