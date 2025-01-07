package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controller.PembayaranController;

import java.awt.*;

public class FormPembayaran extends JInternalFrame {
    private JTable tablePembayaran;
    private JScrollPane scrollPane;
    private JTextField txtTotal, txtBayar, txtKembalian;
    private JButton btnHitung, btnBayar;

    public FormPembayaran() {
        this("Pembayaran");
    }

    public FormPembayaran(String title) {
        super(title, true, true, true, true);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Table untuk menampilkan pesanan yang akan dibayar
        tablePembayaran = new JTable(new DefaultTableModel(
            new String[][]{},
            new String[]{"Nama Menu", "Jumlah", "Harga", "Subtotal"}
        ));
        scrollPane = new JScrollPane(tablePembayaran);

        // Panel bawah untuk informasi pembayaran
        JPanel panelInfo = new JPanel(new GridLayout(3, 2, 10, 10));
        JLabel lblTotal = new JLabel("Total:");
        JLabel lblBayar = new JLabel("Bayar:");
        JLabel lblKembalian = new JLabel("Kembalian:");

        txtTotal = new JTextField();
        txtBayar = new JTextField();
        txtKembalian = new JTextField();

        txtTotal.setEditable(false);
        txtKembalian.setEditable(false);

        panelInfo.add(lblTotal);
        panelInfo.add(txtTotal);
        panelInfo.add(lblBayar);
        panelInfo.add(txtBayar);
        panelInfo.add(lblKembalian);
        panelInfo.add(txtKembalian);

        // Panel untuk tombol aksi
        JPanel panelButtons = new JPanel();
        btnHitung = new JButton("Hitung");
        btnBayar = new JButton("Bayar");

        panelButtons.add(btnHitung);
        panelButtons.add(btnBayar);

        // Menambahkan komponen ke frame
        add(scrollPane, BorderLayout.CENTER);
        add(panelInfo, BorderLayout.SOUTH);
        add(panelButtons, BorderLayout.NORTH);

        // Action Listener untuk tombol Hitung
        btnHitung.addActionListener(e -> {
            try {
                // Menghitung kembalian jika sudah ada jumlah pembayaran
                int total = Integer.parseInt(txtTotal.getText().isEmpty() ? "0" : txtTotal.getText());
                int bayar = txtBayar.getText().isEmpty() ? 0 : Integer.parseInt(txtBayar.getText());
                if (bayar >= total) {
                    int kembalian = bayar - total;
                    txtKembalian.setText(String.valueOf(kembalian)); // Menampilkan kembalian
                } else {
                    JOptionPane.showMessageDialog(this, "Jumlah pembayaran tidak cukup.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                    txtKembalian.setText(""); // Kosongkan kembalian jika pembayaran kurang
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Masukkan angka yang valid.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Action Listener untuk tombol Bayar
        btnBayar.addActionListener(e -> {
            String metodePembayaran = JOptionPane.showInputDialog(
                this,
                "Masukkan metode pembayaran (cash/debit/kredit):",
                "Metode Pembayaran",
                JOptionPane.PLAIN_MESSAGE
            );
        
            if (metodePembayaran != null && (metodePembayaran.equals("cash") || metodePembayaran.equals("debit") || metodePembayaran.equals("kredit"))) {
                // Panggil controller untuk memproses pembayaran tanpa parameter idPemesanan
                new PembayaranController(this).prosesPembayaran(metodePembayaran);
            } else {
                JOptionPane.showMessageDialog(this, "Metode pembayaran tidak valid.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            }
        });        
    }

    // Method untuk mengisi tabel pembayaran
    public void setPembayaranData(Object[][] data) {
        DefaultTableModel model = (DefaultTableModel) tablePembayaran.getModel();
        model.setRowCount(0); // Clear existing data
        int total = 0;
        for (Object[] row : data) {
            if (row.length < 3) {
                JOptionPane.showMessageDialog(this, "Data tidak lengkap untuk baris: " + java.util.Arrays.toString(row),
                        "Error", JOptionPane.ERROR_MESSAGE);
                continue; // Lewati baris yang tidak valid
            }

            try {
                int jumlah = Integer.parseInt(row[1].toString()); // Ambil nilai jumlah
                int harga = Integer.parseInt(row[2].toString()); // Ambil nilai harga
                int subtotal = jumlah * harga; // Hitung subtotal
                Object[] lengkap = {row[0], jumlah, harga, subtotal}; // Tambahkan data lengkap
                model.addRow(lengkap);
                total += subtotal; // Hitung total
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Data jumlah atau harga tidak valid untuk baris: " + java.util.Arrays.toString(row),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        txtTotal.setText(String.valueOf(total)); // Menampilkan total ke text field
    }

    // Getter untuk komponen
    public JTable getTablePembayaran() {
        return tablePembayaran;
    }

    public JTextField getTxtTotal() {
        return txtTotal;
    }

    public JTextField getTxtBayar() {
        return txtBayar;
    }

    public JTextField getTxtKembalian() {
        return txtKembalian;
    }

    public JButton getBtnHitung() {
        return btnHitung;
    }

    public JButton getBtnBayar() {
        return btnBayar;
    }
}
