package controller;

import model.Menu;
import model.MenuModel;
import model.Pemesanan;
import view.FormPemesanan;

import java.util.List;

public class PemesananController {
    private Pemesanan pemesanan;
    private FormPemesanan formPemesanan;
    private MenuModel menuModel;

    // Konstruktor yang benar sesuai dengan kebutuhan
    public PemesananController(Pemesanan pemesanan, FormPemesanan formPemesanan, MenuModel menuModel) {
        this.pemesanan = pemesanan;
        this.formPemesanan = formPemesanan;
        this.menuModel = menuModel;

        // Ambil daftar menu dari model dan tampilkan di form
        tampilkanMenu();
    }

    // Metode untuk menampilkan menu di FormPemesanan
    public void tampilkanMenu() {
        // Ambil daftar menu dari MenuModel
        List<Menu> menuList = menuModel.getMenuItems();

        // Pastikan comboBox dibersihkan terlebih dahulu sebelum menambahkan item baru
        formPemesanan.getComboMenu().removeAllItems();

        // Masukkan daftar menu ke ComboBox di FormPemesanan
        for (Menu menu : menuList) {
            formPemesanan.getComboMenu().addItem(menu.getNamaMenu()); // Menambahkan nama menu
        }
    }

    // Tambahkan metode untuk menambahkan pesanan
    public void tambahPesanan(Menu menu, int jumlah) {
        pemesanan.tambahMenu(menu, jumlah);
    }
}
