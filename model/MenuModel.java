package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import controller.Koneksi;

public class MenuModel {
    private Connection connection;

    public MenuModel(Connection connection) {
        this.connection = connection;
    }

    // Ambil semua menu dari database
    public List<Menu> getMenuItems() {
        List<Menu> menuList = new ArrayList<>();
        try {
            if (connection == null || connection.isClosed()) {
                connection = Koneksi.getConnection();
            }

            String query = "SELECT * FROM menu";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                menuList.add(new Menu(
                    rs.getInt("id"),
                    rs.getString("nama_menu"),
                    rs.getString("kategori"),
                    rs.getDouble("harga"),
                    rs.getInt("stok")
                ));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error saat mengambil data menu: " + e.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        return menuList;
    }

    // Fungsi untuk menambah menu baru
    public void tambahMenu(Menu menu) {
        try {
            String query = "INSERT INTO menu (nama_menu, kategori, harga, stok) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, menu.getNamaMenu());
            stmt.setString(2, menu.getKategori());
            stmt.setDouble(3, menu.getHarga());
            stmt.setInt(4, menu.getStok());
            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error saat menambah menu: " + e.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Fungsi untuk mendapatkan menu berdasarkan ID
    public Menu getMenuById(int id) {
        Menu menu = null;
        try {
            String query = "SELECT * FROM menu WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                menu = new Menu(
                    rs.getInt("id"),
                    rs.getString("nama_menu"),
                    rs.getString("kategori"),
                    rs.getDouble("harga"),
                    rs.getInt("stok")
                );
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error saat mencari menu: " + e.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        return menu;
    }

    // Fungsi untuk update menu
    public void updateMenu(Menu menu) {
        try {
            String query = "UPDATE menu SET nama_menu = ?, kategori = ?, harga = ?, stok = ? WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, menu.getNamaMenu());
            stmt.setString(2, menu.getKategori());
            stmt.setDouble(3, menu.getHarga());
            stmt.setInt(4, menu.getStok());
            stmt.setInt(5, menu.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error saat memperbarui menu: " + e.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Fungsi untuk menghapus menu
    public void deleteMenu(int id) {
        try {
            String query = "DELETE FROM menu WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error saat menghapus menu: " + e.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Fungsi untuk mengurangi stok menu setelah pemesanan
    public void kurangiStokMenu(int idMenu, int jumlah) {
        try {
            String query = "UPDATE menu SET stok = stok - ? WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, jumlah);
            stmt.setInt(2, idMenu);
            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error saat mengurangi stok menu: " + e.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
