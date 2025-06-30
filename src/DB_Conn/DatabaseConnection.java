package DB_Conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/kelompok_dfffp"; 
    private static final String USER = "root"; 
    private static final String PASSWORD = ""; 

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "MySQL JDBC Driver tidak ditemukan. Pastikan JAR sudah ditambahkan.", "Kesalahan Koneksi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal terhubung ke database: " + e.getMessage(), "Kesalahan Koneksi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Gagal menutup koneksi database: " + e.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
    
     public static int getUserId(String username) {
        String sql = "SELECT id FROM users WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            if (conn == null) {
                return -1;
            }
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println("Database error in getUserId for username " + username + ": " + e.getMessage());
            e.printStackTrace(); 
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat mencari ID pengguna. Silakan coba lagi.", "Kesalahan Database", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            System.err.println("General error in getUserId for username " + username + ": " + e.getMessage());
            e.printStackTrace(); 
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan sistem saat mencari ID pengguna. Silakan coba lagi.", "Kesalahan Sistem", JOptionPane.ERROR_MESSAGE);
        }
        return -1; 
    }

    public static Connection connect() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}