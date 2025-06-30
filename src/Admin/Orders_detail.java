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
import javax.swing.table.DefaultTableModel;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants; 
/**
 *
 * @author fredy
 */
public class Orders_detail extends javax.swing.JFrame {
    private String loggedInAdminName; 
    /**
     * Creates new form Orders_detail
     */
    public Orders_detail() {
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
        loadAllOrders();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    
    public Orders_detail(String adminName) {
        this(); 
        this.loggedInAdminName = adminName;
        if (!UserAccessUtil.isAdmin(adminName)) {
            JOptionPane.showMessageDialog(this, "Akses Ditolak: Anda tidak memiliki izin Admin untuk membuka halaman ini.", "Akses Ditolak", JOptionPane.WARNING_MESSAGE);
            new Dashboard_admin(adminName).setVisible(true);
            this.dispose();
            return;
        }
    }
    
    private void customizeComponents() {
        getContentPane().setBackground(new Color(240, 240, 240));

        jPanel1.setBackground(Color.WHITE);
        jPanel1.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 

        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 36));
        jLabel1.setForeground(new Color(60, 60, 60));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER); 
        
        jTable1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jTable1.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        jTable1.setRowHeight(25);
        jTable1.setGridColor(new Color(220, 220, 220));
        jTable1.setFillsViewportHeight(true);
        jScrollPane1.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 1));

        jButton1.setBackground(new Color(76, 175, 80)); 
        jButton1.setForeground(Color.WHITE);
        jButton1.setFont(new Font("Segoe UI", Font.BOLD, 16));
        jButton1.setFocusPainted(false);
        jButton1.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        jButton2.setBackground(new Color(150, 150, 150)); 
        jButton2.setForeground(Color.WHITE);
        jButton2.setFont(new Font("Segoe UI", Font.BOLD, 16));
        jButton2.setFocusPainted(false);
        jButton2.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }
    
     private void loadAllOrders() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Order ID");
        model.addColumn("Tanggal Pesanan");
        model.addColumn("Customer");
        model.addColumn("Menu");
        model.addColumn("Harga Satuan");
        model.addColumn("Kuantitas");
        model.addColumn("Subtotal Item");
        model.addColumn("Total Pesanan");
        model.addColumn("Status");

        String sql = "SELECT o.id AS order_id, o.order_date, o.total AS order_total, o.status, " +
                     "u.name AS customer_name, " + 
                     "m.name AS menu_name, m.price AS menu_price, od.quantity, od.subtotal AS item_subtotal " +
                     "FROM orders o " +
                     "JOIN users u ON o.user_id = u.id " + 
                     "JOIN order_details od ON o.id = od.order_id " +
                     "JOIN menu m ON od.menu_id = m.id " +
                     "ORDER BY o.order_date DESC, o.id ASC"; 

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            if (conn == null) {
                return;
            }

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("order_id"),
                    rs.getTimestamp("order_date"),
                    rs.getString("customer_name"),
                    rs.getString("menu_name"),
                    rs.getDouble("menu_price"),
                    rs.getInt("quantity"),
                    rs.getDouble("item_subtotal"),
                    rs.getDouble("order_total"),
                    rs.getString("status")
                });
            }
            jTable1.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error saat memuat semua pesanan: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
     
     private void confirmOrder(int orderId) {
        String sql = "UPDATE orders SET status = 'completed' WHERE id = ? AND status = 'pending'";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            if (conn == null) {
                return;
            }

            pst.setInt(1, orderId);
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Pesanan ID " + orderId + " berhasil dikonfirmasi.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                loadAllOrders(); 
            } else {
                JOptionPane.showMessageDialog(this, "Gagal mengkonfirmasi pesanan ID " + orderId + ". Mungkin sudah 'completed' atau tidak ditemukan.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error saat mengkonfirmasi pesanan: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator1 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(800, 500));

        jPanel1.setBackground(new java.awt.Color(196, 223, 223));

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel1.setText("Orders Detail");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("Confirm Order");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Back");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 776, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(18, 18, 18)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(30, 30, 30))
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
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow >= 0) {
            int orderId = (int) jTable1.getValueAt(selectedRow, 0);
            String currentStatus = (String) jTable1.getValueAt(selectedRow, 8); // Status ada di kolom ke-9 (indeks 8)

            if ("pending".equalsIgnoreCase(currentStatus)) {
                int confirm = JOptionPane.showConfirmDialog(this,
                              "Apakah Anda yakin ingin mengkonfirmasi pesanan ID " + orderId + " menjadi 'Completed'?",
                              "Konfirmasi Pesanan", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    confirmOrder(orderId);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Pesanan ID " + orderId + " sudah berstatus: " + currentStatus, "Informasi Status", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih pesanan yang ingin dikonfirmasi.", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        Dashboard_admin dashboard = new Dashboard_admin(loggedInAdminName);
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
            java.util.logging.Logger.getLogger(Orders_detail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Orders_detail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Orders_detail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Orders_detail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Orders_detail().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
