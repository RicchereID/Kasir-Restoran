import javax.swing.*;
import java.awt.*;
import view.*;
import model.*;
import controller.*;
import java.sql.Connection;

public class AplikasiKasirRestoran extends JFrame {
    private static final double SCALE = 0.85; // Skala responsif ukuran frame
    private JDesktopPane mdiDesktopPane;
    private JMenuBar menuBar;

    private JMenu masterDataMenu;
    private JMenu transaksiMenu;
    private JMenu laporanMenu;
    private JMenu userMenu;

    private JMenuItem menuItemMenu;
    private JMenuItem menuItemPemesanan;
    private JMenuItem menuItemRiwayatTransaksi;
    private JMenuItem menuItemLogout;

    private FormMenu formMenu;
    private FormPemesanan formPemesanan;
    private FormPembayaran formPembayaran;
    private FormRiwayatTransaksi formRiwayatTransaksi;

    private Connection connection;
    private UserController userController;
    private MenuController menuController;
    private PemesananController pemesananController;

    private Pemesanan pemesanan; // Deklarasikan pemesanan di sini

    public AplikasiKasirRestoran() {
        try {
            connection = Koneksi.getConnection();
            userController = new UserController(new UserModel(connection));
            menuController = new MenuController(new MenuModel(connection), new Pemesanan(), new FormMenu());
            pemesanan = new Pemesanan(); // Inisialisasi pemesanan
            formPemesanan = new FormPemesanan();
            pemesananController = new PemesananController(pemesanan, formPemesanan, new MenuModel(connection));

            if (!login()) {
                System.exit(0); // Jika login gagal, keluar dari aplikasi
            }

            initComponents();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menghubungkan ke database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    private void initComponents() {
        setTitle("Aplikasi Kasir Restoran");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Mengatur ukuran frame agar responsif
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int) (SCALE * screenSize.getWidth()), (int) (SCALE * screenSize.getHeight()));
        setLocationRelativeTo(null);

        // Mengatur Look and Feel agar sesuai dengan sistem
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            System.err.println("Gagal mengatur Nimbus LookAndFeel: " + e.getMessage());
        }        

        mdiDesktopPane = new JDesktopPane();
        menuBar = new JMenuBar();

        // Membuat menu
        masterDataMenu = new JMenu("Master Data");
        transaksiMenu = new JMenu("Transaksi");
        laporanMenu = new JMenu("Laporan");
        userMenu = new JMenu("User");

        // Membuat item menu
        menuItemMenu = new JMenuItem("Menu Makanan & Minuman");
        menuItemPemesanan = new JMenuItem("Pemesanan");
        menuItemRiwayatTransaksi = new JMenuItem("Riwayat Transaksi");
        menuItemLogout = new JMenuItem("Logout");

        // Menambahkan item menu ke menu
        setContentPane(mdiDesktopPane);
        setJMenuBar(menuBar);
        menuBar.add(masterDataMenu);
        menuBar.add(transaksiMenu);
        menuBar.add(laporanMenu);
        menuBar.add(userMenu);

        masterDataMenu.add(menuItemMenu);
        transaksiMenu.add(menuItemPemesanan);
        laporanMenu.add(menuItemRiwayatTransaksi);
        userMenu.add(menuItemLogout);

        // Menambahkan aksi untuk setiap item menu
        menuItemMenu.addActionListener(evt -> openFormMenu());
        menuItemPemesanan.addActionListener(evt -> openFormPemesanan());
        menuItemRiwayatTransaksi.addActionListener(evt -> openFormRiwayatTransaksi());
        menuItemLogout.addActionListener(evt -> logout());
    }

    // Membuka frame Menu
    private void openFormMenu() {
        if (formMenu == null || !formMenu.isVisible()) {
            // Menggunakan instance FormMenu dari menuController
            formMenu = menuController.getMenuView(); 
            mdiDesktopPane.add(formMenu);
    
            // Mengatur ukuran dan posisi frame
            formMenu.setSize((int) (mdiDesktopPane.getWidth() * 0.8), (int) (mdiDesktopPane.getHeight() * 0.8));
            formMenu.setLocation((mdiDesktopPane.getWidth() - formMenu.getWidth()) / 2, (mdiDesktopPane.getHeight() - formMenu.getHeight()) / 2);
    
            // Tampilkan data di tabel
            menuController.tampilkanMenu();
        }
        formMenu.setVisible(true);
    }

    // Membuka frame Pemesanan
    private void openFormPemesanan() {
        if (formPemesanan == null || !formPemesanan.isVisible()) {
            mdiDesktopPane.add(formPemesanan);

            formPemesanan.setSize((int) (mdiDesktopPane.getWidth() * 0.8), (int) (mdiDesktopPane.getHeight() * 0.8));
            formPemesanan.setLocation(
                (mdiDesktopPane.getWidth() - formPemesanan.getWidth()) / 2,
                (mdiDesktopPane.getHeight() - formPemesanan.getHeight()) / 2
            );

            // Menampilkan daftar menu di comboBox
            pemesananController.tampilkanMenu();
        }
        formPemesanan.setVisible(true);
    }

    // Metode untuk membuka form pembayaran
    private void openFormPembayaran() {
        if (formPembayaran == null || !formPembayaran.isVisible()) {
            formPembayaran = new FormPembayaran(); // Ganti dengan class FormPembayaran Anda
            mdiDesktopPane.add(formPembayaran);
    
            formPembayaran.setSize((int) (mdiDesktopPane.getWidth() * 0.8), (int) (mdiDesktopPane.getHeight() * 0.8));
            formPembayaran.setLocation(
                (mdiDesktopPane.getWidth() - formPembayaran.getWidth()) / 2,
                (mdiDesktopPane.getHeight() - formPembayaran.getHeight()) / 2
            );
        }
        formPembayaran.setVisible(true);
    }
    
    // Membuka frame Riwayat Transaksi
    private void openFormRiwayatTransaksi() {
        if (formRiwayatTransaksi == null || !formRiwayatTransaksi.isVisible()) {
            formRiwayatTransaksi = new FormRiwayatTransaksi(); // Form khusus dari package `view`
            mdiDesktopPane.add(formRiwayatTransaksi);

            new ControllerRiwayatTransaksi(formRiwayatTransaksi);

            formRiwayatTransaksi.setSize((int) (mdiDesktopPane.getWidth() * 0.8), (int) (mdiDesktopPane.getHeight() * 0.8));
            formRiwayatTransaksi.setLocation((mdiDesktopPane.getWidth() - formRiwayatTransaksi.getWidth()) / 2, (mdiDesktopPane.getHeight() - formRiwayatTransaksi.getHeight()) / 2);
        }
        formRiwayatTransaksi.setVisible(true);
    }

    // Dialog login sederhana
    private boolean login() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        Object[] message = {
            "Username:", usernameField,
            "Password:", passwordField
        };

        while (true) {
            int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();
                if (userController.login(username, password)) {
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "Login gagal! Username atau Password salah.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                return false;
            }
        }
    }

    // Fungsi logout
    private void logout() {
        int option = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin logout?", "Logout", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            for (JInternalFrame frame : mdiDesktopPane.getAllFrames()) {
                frame.dispose();
            }
            this.dispose();
            SwingUtilities.invokeLater(() -> new AplikasiKasirRestoran().setVisible(true));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AplikasiKasirRestoran().setVisible(true));
    }
}
