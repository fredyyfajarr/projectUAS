/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Admin;

import DB_Conn.DatabaseConnection;
import DB_Conn.UserAccessUtil;
import Dashboard.Dashboard_admin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JFrame; 
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.SwingConstants; 
/**
 *
 * @author fredy
 */
public class Update_menu extends javax.swing.JFrame {
    private int menuId; 
    private String loggedInUserName;
    /**
     * Creates new form Update_menu
     */
    public Update_menu() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            System.err.println("Failed to set Nimbus Look and Feel: " + ex.getMessage());
        }
        initComponents();
        customizeComponents(); 
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        jTextField4.setEditable(false);
    }
    
    public Update_menu(String userName){
        this();
        this.loggedInUserName = userName;
        if (!UserAccessUtil.isAdmin(userName)) { 
            JOptionPane.showMessageDialog(this, "Akses Ditolak: Anda tidak memiliki izin Admin untuk membuka halaman ini.", "Akses Ditolak", JOptionPane.WARNING_MESSAGE);
            new Dashboard_admin(userName).setVisible(true);
            this.dispose();
            return;
        }
    }
    
    private void customizeComponents() {
        getContentPane().setBackground(new Color(240, 240, 240));
        jPanel1.setBackground(Color.WHITE);
        jPanel1.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30)); 

        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 36));
        jLabel1.setForeground(new Color(60, 60, 60));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER); 

        jLabel6.setFont(new Font("Segoe UI", Font.PLAIN, 18)); 
        
        jTextField4.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        jTextField4.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        jTextField4.setBackground(new Color(230, 230, 230)); 
        
        jLabel2.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        jLabel3.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        jLabel4.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        jLabel5.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        Border lineBorder = BorderFactory.createLineBorder(new Color(180, 180, 180), 1);
        Border emptyBorder = BorderFactory.createEmptyBorder(10, 15, 10, 15);

        jTextField1.setFont(new Font("Segoe UI", Font.PLAIN, 18)); 
        jTextField1.setBorder(BorderFactory.createCompoundBorder(lineBorder, emptyBorder));
        jTextField1.setBackground(Color.WHITE);

        jTextField2.setFont(new Font("Segoe UI", Font.PLAIN, 18)); 
        jTextField2.setBorder(BorderFactory.createCompoundBorder(lineBorder, emptyBorder));
        jTextField2.setBackground(Color.WHITE);

        jTextField3.setFont(new Font("Segoe UI", Font.PLAIN, 18)); 
        jTextField3.setBorder(BorderFactory.createCompoundBorder(lineBorder, emptyBorder));
        jTextField3.setBackground(Color.WHITE);

        jTextArea1.setFont(new Font("Segoe UI", Font.PLAIN, 18)); 
        jTextArea1.setBorder(BorderFactory.createCompoundBorder(lineBorder, emptyBorder));
        jTextArea1.setBackground(Color.WHITE);
        jScrollPane1.setBorder(null); 

        jButton1.setBackground(new Color(33, 150, 243)); 
        jButton1.setForeground(Color.WHITE);
        jButton1.setFont(new Font("Segoe UI", Font.BOLD, 18));
        jButton1.setFocusPainted(false);
        jButton1.setBorder(BorderFactory.createEmptyBorder(15, 40, 15, 40));

        jButton2.setBackground(new Color(150, 150, 150)); 
        jButton2.setForeground(Color.WHITE);
        jButton2.setFont(new Font("Segoe UI", Font.BOLD, 18));
        jButton2.setFocusPainted(false);
        jButton2.setBorder(BorderFactory.createEmptyBorder(15, 40, 15, 40));
    }

    Update_menu(int id, String name, double price, int stock, String description, String userName) {
        this();
        this.menuId = id;
        this.loggedInUserName = userName;
        
        if (!UserAccessUtil.isAdmin(userName)) {
            JOptionPane.showMessageDialog(this, "Akses Ditolak: Anda tidak memiliki izin Admin untuk membuka halaman ini.", "Akses Ditolak", JOptionPane.WARNING_MESSAGE);
            new Dashboard_admin(userName).setVisible(true);
            this.dispose();
            return;
        }
        
        jTextField4.setText(String.valueOf(id)); 
        jTextField1.setText(name);
        jTextField2.setText(String.valueOf(price));
        jTextField3.setText(String.valueOf(stock)); 
        jTextArea1.setText(description);        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(196, 223, 223));

        jButton1.setText("Update");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel1.setText("Form update menu");

        jLabel2.setText("Nama");

        jLabel6.setText("ID");

        jLabel3.setText("Harga");

        jButton2.setText("Back");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel4.setText("Stock");

        jLabel5.setText("Deskripsi");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE)
                            .addComponent(jTextField2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabel4))
                            .addComponent(jTextField1))
                        .addGap(40, 40, 40)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(170, 222, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(76, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String newName = jTextField1.getText().trim();
        String newDescription = jTextArea1.getText().trim();
        String newPriceText = jTextField2.getText().trim();
        String newStockText = jTextField3.getText().trim();

        if (newName.isEmpty() || newPriceText.isEmpty() || newStockText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama, Harga, dan Stock wajib diisi.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            double newPrice = Double.parseDouble(newPriceText.replace(",", "."));
            int newStock = Integer.parseInt(newStockText);

            if (newPrice <= 0) {
                JOptionPane.showMessageDialog(this, "Harga harus lebih dari 0.", "Validasi Input", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (newStock < 0) {
                JOptionPane.showMessageDialog(this, "Stock tidak boleh negatif.", "Validasi Input", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this, 
                          "Apakah Anda yakin ingin menyimpan perubahan untuk menu '" + newName + "'?", 
                          "Konfirmasi Update", JOptionPane.YES_NO_OPTION);

            if (confirm != JOptionPane.YES_OPTION) {
                return; 
            }
            
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pst = conn.prepareStatement(
                         "UPDATE menu SET name = ?, price = ?, stock = ?, description = ? WHERE id = ?")) {
                if (conn == null) {
                    return;
                }
                pst.setString(1, newName);
                pst.setDouble(2, newPrice);
                pst.setInt(3, newStock);
                pst.setString(4, newDescription);
                pst.setInt(5, this.menuId);

                int rowsAffected = pst.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Data menu berhasil diupdate.", "Update Sukses", JOptionPane.INFORMATION_MESSAGE);
                    List_menu_admin listMenuAdmin = new List_menu_admin(loggedInUserName);
                    listMenuAdmin.setVisible(true);
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal mengupdate data menu. Mungkin ID tidak ditemukan atau tidak ada perubahan.", "Update Gagal", JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Harga harus berupa angka (gunakan titik untuk desimal), dan Stock berupa bilangan bulat.", "Format Input Salah", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error database saat mengupdate menu: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan umum saat mengupdate menu: " + e.getMessage(), "Error Umum", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        Dashboard_admin dashboard = new Dashboard_admin(loggedInUserName); 
        dashboard.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Update_menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Update_menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Update_menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Update_menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Update_menu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
