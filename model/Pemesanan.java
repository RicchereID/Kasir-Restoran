package model;

import java.util.ArrayList;
import java.util.List;

public class Pemesanan {
    private List<ItemPemesanan> daftarMenu = new ArrayList<>();
    private double totalHarga;
    private String status;

    public Pemesanan() {
        this.status = "proses";  // Default status adalah proses
    }

    // Menambah menu ke dalam pemesanan
    public void tambahMenu(Menu menu, int jumlah) {
        boolean ditemukan = false;
        for (ItemPemesanan item : daftarMenu) {
            if (item.getMenu().getId() == menu.getId()) {
                item.setJumlah(item.getJumlah() + jumlah);
                ditemukan = true;
                break;
            }
        }

        if (!ditemukan) {
            daftarMenu.add(new ItemPemesanan(menu, jumlah));
        }

        totalHarga += menu.getHarga() * jumlah;
    }

    // Getter untuk total harga
    public double getTotalHarga() {
        return totalHarga;
    }

    // Getter untuk daftar menu
    public List<ItemPemesanan> getDaftarMenu() {
        return daftarMenu;
    }

    // Getter untuk status
    public String getStatus() {
        return status;
    }

    // Setter untuk status
    public void setStatus(String status) {
        this.status = status;
    }

    // Reset pemesanan
    public void resetPesanan() {
        daftarMenu.clear();
        totalHarga = 0.0;
        status = "proses";  // Kembalikan status ke "proses" setelah reset
    }
}
