package controller;

import model.Menu;
import model.RiwayatTransaksi;
import view.FormRiwayatTransaksi;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ControllerRiwayatTransaksi {
    private final FormRiwayatTransaksi formRiwayatTransaksi;

    public ControllerRiwayatTransaksi(FormRiwayatTransaksi formRiwayatTransaksi) {
        this.formRiwayatTransaksi = formRiwayatTransaksi;
        loadTableData();
    }

    // Method untuk memuat data dari model ke tabel
    public void loadTableData() {
        JTable tableRiwayat = formRiwayatTransaksi.getTableRiwayat();
        DefaultTableModel tableModel = new DefaultTableModel(
            new Object[]{"ID", "ID Pemesanan", "Tanggal Transaksi", "Metode Pembayaran", "Total Bayar", "Kembalian"}, 0
        );
    
        // Ambil data dari model RiwayatTransaksi
        List<Object[]> dataTransaksi = RiwayatTransaksi.getRiwayatTransaksi();
    
        System.out.println("Jumlah data yang ditemukan: " + dataTransaksi.size()); // Debugging
    
        for (Object[] transaksi : dataTransaksi) {
            System.out.println("Menambahkan data ke tabel: " + java.util.Arrays.toString(transaksi)); // Debugging
            tableModel.addRow(transaksi); // Tambahkan data ke tabel
        }
    
        // Set model tabel ke JTable
        tableRiwayat.setModel(tableModel);
        System.out.println("Jumlah baris di tabel: " + tableRiwayat.getRowCount()); // Debugging
    }
    
}
