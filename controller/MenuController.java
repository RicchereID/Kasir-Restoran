package controller;

import model.*;
import view.*;
import javax.swing.*;
import java.util.List;
import java.util.Scanner;

public class MenuController {
    private MenuModel menuModel;
    private Pemesanan pemesanan;
    private FormMenu menuView;

    public MenuController(MenuModel menuModel, Pemesanan pemesanan, FormMenu menuView) {
        this.menuModel = menuModel;
        this.pemesanan = pemesanan;
        this.menuView = menuView;

        menuView.addListeners(this);
    }

    public void tampilkanMenu() {
        List<Menu> menuList = menuModel.getMenuItems();
        if (menuList == null || menuList.isEmpty()) {
            JOptionPane.showMessageDialog(menuView, "Tidak ada data menu yang ditemukan atau koneksi bermasalah.",
                "Informasi", JOptionPane.INFORMATION_MESSAGE);
        } else {
            menuView.menuTable(menuList);
        }
    }     

    public void refreshMenu() {
        tampilkanMenu(); // Memperbarui data di tabel
    }

    // Fungsi untuk menambah menu baru
    public void tambahMenu() {
        String namaMenu = JOptionPane.showInputDialog(menuView, "Masukkan Nama Menu:");
        String kategori = JOptionPane.showInputDialog(menuView, "Masukkan Kategori:");
        String hargaStr = JOptionPane.showInputDialog(menuView, "Masukkan Harga:");
        String stokStr = JOptionPane.showInputDialog(menuView, "Masukkan Stok:");

        try {
            double harga = Double.parseDouble(hargaStr);
            int stok = Integer.parseInt(stokStr);
            Menu menuBaru = new Menu(0, namaMenu, kategori, harga, stok); // ID akan dihasilkan otomatis
            menuModel.tambahMenu(menuBaru); // Menyimpan menu baru ke database
            refreshMenu();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(menuView, "Input tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Fungsi untuk mengedit menu yang ada
    public void editMenu() {
        try {
            String idStr = JOptionPane.showInputDialog(menuView, "Masukkan ID menu yang ingin diedit:");
            int id = Integer.parseInt(idStr);

            Menu menuDipilih = menuModel.getMenuById(id);
            if (menuDipilih != null) {
                String namaMenu = JOptionPane.showInputDialog(menuView, "Masukkan Nama Menu:", menuDipilih.getNamaMenu());
                String kategori = JOptionPane.showInputDialog(menuView, "Masukkan Kategori:", menuDipilih.getKategori());
                String hargaStr = JOptionPane.showInputDialog(menuView, "Masukkan Harga:", menuDipilih.getHarga());
                String stokStr = JOptionPane.showInputDialog(menuView, "Masukkan Stok:", menuDipilih.getStok());

                double harga = Double.parseDouble(hargaStr);
                int stok = Integer.parseInt(stokStr);

                menuDipilih.setNamaMenu(namaMenu);
                menuDipilih.setKategori(kategori);
                menuDipilih.setHarga(harga);
                menuDipilih.setStok(stok);

                menuModel.updateMenu(menuDipilih); // Update menu ke database
                refreshMenu();
            } else {
                JOptionPane.showMessageDialog(menuView, "Menu tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(menuView, "Input tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Fungsi untuk menghapus menu
    public void deleteMenu() {
        try {
            String idStr = JOptionPane.showInputDialog(menuView, "Masukkan ID menu yang ingin dihapus:");
            int id = Integer.parseInt(idStr);

            Menu menuDipilih = menuModel.getMenuById(id);
            if (menuDipilih != null) {
                int konfirmasi = JOptionPane.showConfirmDialog(menuView, 
                    "Apakah Anda yakin ingin menghapus menu " + menuDipilih.getNamaMenu() + "?", 
                    "Konfirmasi", JOptionPane.YES_NO_OPTION);

                if (konfirmasi == JOptionPane.YES_OPTION) {
                    menuModel.deleteMenu(id); // Menghapus menu dari database
                    refreshMenu();
                }
            } else {
                JOptionPane.showMessageDialog(menuView, "Menu tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(menuView, "Input tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public FormMenu getMenuView() {
        return menuView;
    }
}
