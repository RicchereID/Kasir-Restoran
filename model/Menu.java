package model;

public class Menu {
    private int id;
    private String namaMenu;
    private String kategori;
    private double harga;
    private int stok;

    // Constructor
    public Menu(int id, String namaMenu, String kategori, double harga, int stok) {
        this.id = id;
        this.namaMenu = namaMenu;
        this.kategori = kategori;
        this.harga = harga;
        this.stok = stok;
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public String getNamaMenu() {
        return namaMenu;
    }

    public String getKategori() {
        return kategori;
    }

    public double getHarga() {
        return harga;
    }

    public int getStok() {
        return stok;
    }

    // Setter methods
    public void setId(int id) {
        this.id = id;
    }

    public void setNamaMenu(String namaMenu) {
        this.namaMenu = namaMenu;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }
}
