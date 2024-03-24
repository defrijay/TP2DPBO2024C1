import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Menu extends JFrame {
    private int selectedIndex = -1;
    private ArrayList<Mahasiswa> listMahasiswa;
    private Database database;

    private JPanel mainPanel;
    private JTextField nimField;
    private JTextField namaField;
    private JTable mahasiswaTable;
    private JButton addUpdateButton;
    private JButton cancelButton;
    private JComboBox<String> jenisKelaminComboBox;
    private JButton deleteButton;
    private JLabel titleLabel;
    private JLabel nimLabel;
    private JLabel namaLabel;
    private JLabel jenisKelaminLabel;
    private JLabel lulusanLabel;
    private JComboBox<String> lulusanComboBox;

    public static void main(String[] args) {
        Menu window = new Menu();
        window.setSize(480, 560);
        window.setLocationRelativeTo(null);
        window.setContentPane(window.mainPanel);
        window.getContentPane().setBackground(Color.white);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public Menu() {
//        listMahasiswa = new ArrayList<>();
        database = new Database();
        mahasiswaTable.setModel(setTable());
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));
        String[] jenisKelaminData = {"-Pilih Jenis Kelamin-", "Laki-laki", "Perempuan"};
        jenisKelaminComboBox.setModel(new DefaultComboBoxModel<>(jenisKelaminData));
        String[] lulusanData = {"-Pilih Lulusan-", "SMA", "SMK"};
        lulusanComboBox.setModel(new DefaultComboBoxModel<>(lulusanData));
        deleteButton.setVisible(false);

        addUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex == -1) {
                    insertData();
                } else {
                    updateData();
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex >= 0) {
                    deleteData();
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        // saat salah satu baris tabel ditekan
        mahasiswaTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // ubah selectedIndex menjadi baris tabel yang diklik
                selectedIndex = mahasiswaTable.getSelectedRow();

                // simpan value textfield dan combo box
                String selectedNim = mahasiswaTable.getModel().getValueAt(selectedIndex, 1).toString();
                String selectedNama = mahasiswaTable.getModel().getValueAt(selectedIndex, 2).toString();
                String selectedJenisKelamin = mahasiswaTable.getModel().getValueAt(selectedIndex, 3).toString();

                // ubah isi textfield dan combo box
                nimField.setText(selectedNim);
                namaField.setText(selectedNama);
                jenisKelaminComboBox.setSelectedItem(selectedJenisKelamin);

                // ubah button "Add" menjadi "Update"
                addUpdateButton.setText("Update");
                // tampilkan button delete
                deleteButton.setVisible(true);

                // set nilai combo box lulusan
                String selectedLulusan = mahasiswaTable.getModel().getValueAt(selectedIndex, 4).toString();
                lulusanComboBox.setSelectedItem(selectedLulusan);

                // Set nilai combo box jenis kelamin
                if (selectedJenisKelamin.equals("laki-laki")) {
                    jenisKelaminComboBox.setSelectedItem("Laki-laki");
                } else if (selectedJenisKelamin.equals("Perempuan")) {
                    jenisKelaminComboBox.setSelectedItem("Perempuan");
                }
            }
        });
    }

    public final DefaultTableModel setTable() {
        Object[] column = {"No", "NIM", "Nama", "Jenis Kelamin", "Lulusan"};
        DefaultTableModel temp = new DefaultTableModel(null, column);
        try {
            ResultSet resultSet = database.selectQuery("SELECT * FROM mahasiswa");
            int i = 0;
            while (resultSet.next()) {
                Object[] row = new Object[5]; // Ubah jumlah elemen menjadi 5
                row[0] = i + 1;
                row[1] = resultSet.getString("nim");
                row[2] = resultSet.getString("nama");
                row[3] = resultSet.getString("jenis_kelamin");
                row[4] = resultSet.getString("lulusan"); // Simpan data lulusan ke row[4]
                temp.addRow(row);
                i++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return temp;
    }


    public void insertData() {
        String nim = nimField.getText();
        String nama = namaField.getText();
        String jenisKelamin = jenisKelaminComboBox.getSelectedItem().toString();
        String lulusan = lulusanComboBox.getSelectedItem().toString();

        // Validasi input kosong
        if (nim.isEmpty() || nama.isEmpty() || jenisKelamin.equals("-Pilih Jenis Kelamin-") || lulusan.equals("-Pilih Lulusan-")) {
            JOptionPane.showMessageDialog(null, "Harap lengkapi semua input!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Cek apakah NIM sudah ada di database
        if (isNIMExists(nim)) {
            JOptionPane.showMessageDialog(null, "NIM sudah ada dalam database!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String sql = "INSERT INTO mahasiswa VALUES (null, '" + nim + "', '" + nama + "', '" + jenisKelamin + "', '" + lulusan + "');";
        database.insertUpdateDeleteQuery(sql);
        mahasiswaTable.setModel(setTable());
        clearForm();
        JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan");
    }

    private boolean isNIMExists(String nim) {
        try {
            ResultSet resultSet = database.selectQuery("SELECT COUNT(*) AS count FROM mahasiswa WHERE nim = '" + nim + "'");
            resultSet.next();
            int count = resultSet.getInt("count");
            return count > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void updateData() {
        String nim = nimField.getText();
        String nama = namaField.getText();
        String jenisKelamin = jenisKelaminComboBox.getSelectedItem().toString();
        String lulusan = lulusanComboBox.getSelectedItem().toString();

        // Validasi input kosong
        if (nim.isEmpty() || nama.isEmpty() || jenisKelamin.equals("-Pilih Jenis Kelamin-") || lulusan.equals("-Pilih Lulusan-")) {
            JOptionPane.showMessageDialog(null, "Harap lengkapi semua input!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String sql = "UPDATE mahasiswa SET nim = '" + nim + "', nama = '" + nama + "', jenis_kelamin = '" + jenisKelamin + "', jenis_kelamin = '" + lulusan + "' WHERE nim = '" + nim + "'";
        database.insertUpdateDeleteQuery(sql);
        mahasiswaTable.setModel(setTable());
        clearForm();
        JOptionPane.showMessageDialog(null, "Data berhasil diubah!");
    }

    public void deleteData() {
        String nim = nimField.getText();
        String sql = "DELETE FROM mahasiswa WHERE nim = '" + nim + "'";
        database.insertUpdateDeleteQuery(sql);
        mahasiswaTable.setModel(setTable());
        clearForm();
        JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
    }

    public void clearForm() {
        nimField.setText("");
        namaField.setText("");
        jenisKelaminComboBox.setSelectedItem("");
        addUpdateButton.setText("Add");
        deleteButton.setVisible(false);
        selectedIndex = -1;
    }
}
