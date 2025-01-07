package model;

public class Pembayaran {
    private int total;
    private int bayar;
    private int kembalian;

    public Pembayaran(int total, int bayar) {
        this.total = total;
        this.bayar = bayar;
    }

    // Menghitung kembalian
    public void hitungKembalian() {
        if (bayar < total) {
            throw new IllegalArgumentException("Pembayaran tidak cukup");
        }
        kembalian = bayar - total;
    }

    public int getTotal() {
        return total;
    }

    public int getKembalian() {
        return kembalian;
    }
}
