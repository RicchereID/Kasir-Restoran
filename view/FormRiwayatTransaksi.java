package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FormRiwayatTransaksi extends JInternalFrame {
    private JTable tableRiwayat;
    private JScrollPane scrollPane;
    private DefaultTableModel tableModel;

    public FormRiwayatTransaksi() {
        super("Riwayat Transaksi", true, true, true, true);
        setSize(800, 600);

        // Inisialisasi model tabel
        tableModel = new DefaultTableModel(
            new Object[]{"ID", "ID Pemesanan", "Tanggal Transaksi", "Metode Pembayaran", "Total Bayar", "Kembalian"}, 0
        );

        // Inisialisasi tabel dengan model
        tableRiwayat = new JTable(tableModel);

        // Tambahkan tabel ke dalam JScrollPane
        scrollPane = new JScrollPane(tableRiwayat);

        // Atur layout dan tambahkan scroll pane ke frame
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
    }

    // Getter untuk tabel
    public JTable getTableRiwayat() {
        return tableRiwayat;
    }

    // Getter untuk model tabel
    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    // Method untuk menyegarkan tabel setelah data diubah
    public void refreshTable() {
        tableModel.fireTableDataChanged();
    }
}
