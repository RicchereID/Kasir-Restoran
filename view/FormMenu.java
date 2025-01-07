package view;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

import controller.MenuController;

import java.util.List;  // Pastikan ini ada untuk mendeklarasikan List<Menu>
import model.Menu;

public class FormMenu extends JInternalFrame {
    private JTable menuTable;
    private JScrollPane scrollPane;
    private JButton btnAdd, btnEdit, btnDelete, btnRefresh;

    public FormMenu() {
        super("Menu Makanan & Minuman", true, true, true, true);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Table untuk menampilkan data menu
        menuTable = new JTable(new DefaultTableModel(new Object[]{"ID", "Nama Menu", "Kategori", "Harga", "Stok"}, 0));
        scrollPane = new JScrollPane(menuTable);

        // Panel bawah untuk tombol
        JPanel panelButtons = new JPanel();
        btnAdd = new JButton("Tambah");
        btnEdit = new JButton("Edit");
        btnDelete = new JButton("Hapus");
        btnRefresh = new JButton("Refresh");

        panelButtons.add(btnAdd);
        panelButtons.add(btnEdit);
        panelButtons.add(btnDelete);
        panelButtons.add(btnRefresh);

        // Menambahkan komponen ke frame
        add(scrollPane, BorderLayout.CENTER);
        add(panelButtons, BorderLayout.SOUTH);
    }

    // Method untuk mengisi data ke dalam tabel
    public void menuTable(List<Menu> menuList) {
        DefaultTableModel tableModel = (DefaultTableModel) menuTable.getModel();
        tableModel.setRowCount(0);  // Clear existing data

        // Mengisi tabel dengan data dari list menu
        for (Menu menu : menuList) {
            Object[] rowData = {
                menu.getId(),
                menu.getNamaMenu(),
                menu.getKategori(),
                menu.getHarga(),
                menu.getStok()
            };
            tableModel.addRow(rowData);
        }
    }

    // Getter untuk komponen tombol dan tabel
    public JTable getMenuTable() {
        return menuTable;
    }

    public JButton getBtnAdd() {
        return btnAdd;
    }

    public JButton getBtnEdit() {
        return btnEdit;
    }

    public JButton getBtnDelete() {
        return btnDelete;
    }

    public JButton getBtnRefresh() {
        return btnRefresh;
    }

    // Method untuk menampilkan total harga di pemesanan
    public void tampilkanPesanan(double totalHarga) {
        JOptionPane.showMessageDialog(this, "Total Harga: " + totalHarga);
    }

    public void addListeners(MenuController controller) {
        btnAdd.addActionListener(evt -> controller.tambahMenu());
        btnEdit.addActionListener(evt -> controller.editMenu());
        btnDelete.addActionListener(evt -> controller.deleteMenu());
        btnRefresh.addActionListener(evt -> controller.refreshMenu());
    }
    

}
