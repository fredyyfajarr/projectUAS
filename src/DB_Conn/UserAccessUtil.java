/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DB_Conn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane; 
/**
 *
 * @author fredy
 */
public class UserAccessUtil {
     public static boolean isAdmin(String username) {
        if (username == null || username.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Username tidak boleh kosong saat verifikasi akses.", "Validasi Akses", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        String role = "";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement("SELECT role FROM users WHERE username = ?")) {
            if (conn == null) {
                return false;
            }
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                role = rs.getString("role");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan database saat verifikasi akses: " + e.getMessage(), "Kesalahan Database", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); 
            return false;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan sistem saat verifikasi akses: " + e.getMessage(), "Kesalahan Sistem", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); 
            return false;
        }
        return "admin".equalsIgnoreCase(role);
    }
}
