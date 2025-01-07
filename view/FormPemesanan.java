package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import model.PemesananDAO;

import java.awt.*;
import java.util.List;

public class FormPemesanan extends JInternalFrame {
    private JTable tablePemesanan;
    private JScrollPane scrollPane;
    private JComboBox<String> comboMenu;
    private JSpinner spinnerJumlah;
    private JButton btnTambahPesanan, btnHapusPesanan, btnProsesPesanan;
    private Runnable onProsesPesanan; // Runnable untuk proses pesanan

    public FormPemesanan() {
        this("Pemesanan");
    }

    public FormPemesanan(String title) {
        super(title, true, true, true, true);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Table untuk menampilkan pesanan
        tablePemesanan = new JTable(new DefaultTableModel(
            new String[][]{},
            new String[]{"Nama Menu", "Jumlah", "Subtotal"}
        ));
        scrollPane = new JScrollPane(tablePemesanan);

        // Panel atas untuk input pemesanan
        JPanel panelInput = new JPanel(new GridLayout(1, 3, 10, 10));
        comboMenu = new JComboBox<>(); // Data menu akan diisi dari controller
        spinnerJumlah = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        btnTambahPesanan = new JButton("Tambah");

        panelInput.add(comboMenu);
        panelInput.add(spinnerJumlah);
        panelInput.add(btnTambahPesanan);

        // Panel bawah untuk aksi pesanan
        JPanel panelButtons = new JPanel();
        btnHapusPesanan = new JButton("Hapus");
        btnProsesPesanan = new JButton("Proses Pesanan");

        panelButtons.add(btnHapusPesanan);
        panelButtons.add(btnProsesPesanan);

        // Menambahkan komponen ke frame
        add(scrollPane, BorderLayout.CENTER);
        add(panelInput, BorderLayout.NORTH);
        add(panelButtons, BorderLayout.SOUTH);

        btnHapusPesanan.addActionListener(e -> {
            try {
                // Ambil model dari tabel
                DefaultTableModel model = (DefaultTableModel) tablePemesanan.getModel();
        
                // Ambil indeks baris yang dipilih
                int selectedRow = tablePemesanan.getSelectedRow();
        
                // Validasi apakah ada baris yang dipilih
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(this, "Pilih pesanan yang ingin dihapus.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                    return;
                }
        
                // Hapus baris yang dipilih dari tabel
                model.removeRow(selectedRow);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });        

        // Tambahkan action listener untuk tombol Proses Pesanan
        btnProsesPesanan.addActionListener(e -> {
        try {
            // Validasi jika tabel pesanan kosong
            if (tablePemesanan.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Pesanan masih kosong, tambahkan pesanan terlebih dahulu.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Hitung total harga dari tabel
            DefaultTableModel model = (DefaultTableModel) tablePemesanan.getModel();
            double totalHarga = 0; // Tambahkan deklarasi variabel totalHarga
            for (int i = 0; i < model.getRowCount(); i++) {
                totalHarga += Double.parseDouble(model.getValueAt(i, 2).toString());
            }

            // Simpan data ke database
            PemesananDAO pemesananDAO = new PemesananDAO();
            int idUser = 1; // Contoh ID pengguna (ganti sesuai konteks aplikasi)
            int idPemesanan = pemesananDAO.simpanPemesanan(idUser, totalHarga);

            // Periksa apakah data berhasil disimpan
            if (idPemesanan > 0) {
                // Menyiapkan data pesanan untuk form pembayaran
                Object[][] dataPembayaran = new Object[model.getRowCount()][3];
                for (int i = 0; i < model.getRowCount(); i++) {
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        dataPembayaran[i][j] = model.getValueAt(i, j);
                    }
                }

                // Buat form pembayaran dan set data pesanan
                FormPembayaran formPembayaran = new FormPembayaran();
                formPembayaran.setPembayaranData(dataPembayaran);

                // Menampilkan form pembayaran
                getDesktopPane().add(formPembayaran);
                formPembayaran.setVisible(true);

                // Reset tabel setelah berhasil disimpan
                model.setRowCount(0);
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal menyimpan pemesanan ke database.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        // Tambahkan action listener untuk tombol Tambah Pesanan
        btnTambahPesanan.addActionListener(e -> {
            try {
                // Validasi input
                String menu = (String) comboMenu.getSelectedItem();
                int jumlah = (int) spinnerJumlah.getValue();

                if (menu == null || menu.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Silakan pilih menu terlebih dahulu.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Tambahkan data ke tabel
                DefaultTableModel model = (DefaultTableModel) tablePemesanan.getModel();
                model.addRow(new Object[]{menu, jumlah, jumlah * 10000}); // Subtotal contoh: harga tetap 10000
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    // Setter untuk Runnable onProsesPesanan
    public void setOnProsesPesanan(Runnable onProsesPesanan) {
        this.onProsesPesanan = onProsesPesanan;
    }

    // Setter untuk mengisi daftar menu
    public void setMenuList(List<String> menuList) {
        comboMenu.removeAllItems(); // Hapus data lama
        for (String menu : menuList) {
            comboMenu.addItem(menu); // Tambahkan menu baru
        }
    }

    // Getter untuk komponen
    public JTable getTablePemesanan() {
        return tablePemesanan;
    }

    public JComboBox<String> getComboMenu() {
        return comboMenu;
    }

    public JSpinner getSpinnerJumlah() {
        return spinnerJumlah;
    }

    public JButton getBtnTambahPesanan() {
        return btnTambahPesanan;
    }

    public JButton getBtnHapusPesanan() {
        return btnHapusPesanan;
    }

    public JButton getBtnProsesPesanan() {
        return btnProsesPesanan;
    }
}
