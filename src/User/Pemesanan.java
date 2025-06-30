/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package User;

import DB_Conn.DatabaseConnection;
import Dashboard.Dashboard_user;
import Dashboard.Dashboard;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel; 
import java.util.ArrayList; 
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
public class Pemesanan extends javax.swing.JFrame {
    private String loggedInUserName;
    private int loggedInUserId;
    private int currentSelectedMenuId;
    private String currentSelectedMenuName;
    private double currentSelectedMenuPrice;
    private int currentSelectedMenuStock;
    private DefaultTableModel cartTableModel;
    private ArrayList<OrderItem> currentCartItems; 
    private double grandTotal = 0.0; 
    /**
     * Creates new form Pemesanan
     */
    public Pemesanan() {
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
        initializeCartTable(); 
        loadMenusToComboBox();
        jTextField1.setText("1"); 

        jTextField1.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateRincianPemesanan();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                updateRincianPemesanan();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                updateRincianPemesanan();
            }
        });

        jButton1.setEnabled(false); 
        jButton3.setEnabled(false); 
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    
    public Pemesanan(String userName) {
        this(); 
        this.loggedInUserName = userName;
        this.loggedInUserId = DatabaseConnection.getUserId(userName); 

        if ("Guest".equalsIgnoreCase(userName)) {
            jButton1.setVisible(false); 
            jButton2.setVisible(false); 
            JOptionPane.showMessageDialog(this, "Anda harus login untuk melakukan pemesanan.", "Akses Ditolak", JOptionPane.WARNING_MESSAGE);
        } else {
            jButton1.setVisible(true);
            jButton2.setVisible(true);
        }
    }
    
    public Pemesanan(int menuId, String menuName, double menuPrice, int menuStock, String userName) {
        this(); 
        this.currentSelectedMenuId = menuId;
        this.currentSelectedMenuName = menuName;
        this.currentSelectedMenuPrice = menuPrice;
        this.currentSelectedMenuStock = menuStock;
        this.loggedInUserName = userName; 

        if ("Guest".equalsIgnoreCase(userName)) {
            jButton1.setVisible(false); 
        } else {
            jButton1.setVisible(true);
        }

        updateRincianPemesanan();
    }
    
    private void customizeComponents() {
        getContentPane().setBackground(new Color(240, 240, 240));

        jPanel2.setBackground(Color.WHITE);
        jPanel2.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 
        
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 36));
        jLabel1.setForeground(new Color(60, 60, 60));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        
        jLabel2.setFont(new Font("Segoe UI", Font.PLAIN, 18)); 
        jLabel3.setFont(new Font("Segoe UI", Font.PLAIN, 18)); 

        jComboBox1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        jComboBox1.setBackground(Color.WHITE);
        jComboBox1.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 1));

        jTextField1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        jTextField1.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        jTextField1.setBackground(Color.WHITE);

        customizeButton(jButton3, "Add to cart", new Color(33, 150, 243)); 
        customizeButton(jButton4, "Back", new Color(150, 150, 150)); 
        customizeButton(jButton2, "Clear", new Color(255, 152, 0)); 
        customizeButton(jButton1, "Order now", new Color(76, 175, 80)); 

        jTable1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jTable1.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        jTable1.setRowHeight(25);
        jTable1.setGridColor(new Color(220, 220, 220));
        jTable1.setFillsViewportHeight(true);
        jScrollPane1.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 1));
        
        jLabel4.setFont(new Font("Segoe UI", Font.BOLD, 18));
        jLabel5.setFont(new Font("Segoe UI", Font.BOLD, 18)); 
        jLabel4.setForeground(new Color(60, 60, 60));
        jLabel5.setForeground(new Color(76, 175, 80)); 
    }

    private void customizeButton(javax.swing.JButton button, String text, Color bgColor) {
        button.setText(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }
    
    private void initializeCartTable() {
        cartTableModel = new DefaultTableModel();
        cartTableModel.addColumn("ID Menu");
        cartTableModel.addColumn("Menu");
        cartTableModel.addColumn("Harga");
        cartTableModel.addColumn("Kuantitas");
        cartTableModel.addColumn("Subtotal");
        jTable1.setModel(cartTableModel);
        
        currentCartItems = new ArrayList<>(); 
        updateGrandTotal();
    }
     
    private void loadMenusToComboBox() {
        DefaultComboBoxModel<MenuItem> model = new DefaultComboBoxModel<>();
        model.addElement(new MenuItem(0, "-- Pilih Menu --", 0.0, 0));

        String sql = "SELECT id, name, price, stock FROM menu ORDER BY name";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            if (conn == null) {
                return;
            }
            while (rs.next()) {
                model.addElement(new MenuItem(rs.getInt("id"), rs.getString("name"), rs.getDouble("price"), rs.getInt("stock")));
            }
            jComboBox1.setModel(model);
            jComboBox1.setSelectedIndex(0);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error saat memuat menu: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
     
    private void updateRincianPemesanan() {
        if (currentSelectedMenuId != 0) { 
            int quantity;
            try {
                quantity = Integer.parseInt(jTextField1.getText());
                if (quantity <= 0) {
                    jButton3.setEnabled(false);
                    return;
                }
            } catch (NumberFormatException e) {
                jButton3.setEnabled(false);
                return;
            }

            if (quantity <= currentSelectedMenuStock) {
                jButton3.setEnabled(true);
            } else {
                jButton3.setEnabled(false);
                JOptionPane.showMessageDialog(this, "Stok tidak mencukupi! Stok tersedia: " + currentSelectedMenuStock, "Peringatan Stok", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            jButton3.setEnabled(false); 
        }
    }
     
    private void updateGrandTotal() {
        grandTotal = 0.0;
        for (OrderItem item : currentCartItems) {
            grandTotal += item.getItemSubtotal();
        }
        jLabel5.setText("Rp" + String.format("%.2f", grandTotal));
        jButton1.setEnabled(!currentCartItems.isEmpty());
    }
    
    private void addMenuItemToCart(int menuId, String menuName, double menuPrice, int quantity, double itemSubtotal) {
        for (OrderItem item : currentCartItems) {
            if (item.getMenuId() == menuId) {
                item.setQuantity(item.getQuantity() + quantity);
                item.setItemSubtotal(item.getItemSubtotal() + itemSubtotal);
                refreshCartTable();
                updateGrandTotal();
                return;
            }
        }
        currentCartItems.add(new OrderItem(menuId, menuName, menuPrice, quantity, itemSubtotal));
        refreshCartTable();
        updateGrandTotal();
    }
    
    private void refreshCartTable() {
        cartTableModel.setRowCount(0); 
        for (OrderItem item : currentCartItems) {
            cartTableModel.addRow(new Object[]{
                item.getMenuId(),
                item.getMenuName(),
                item.getMenuPrice(),
                item.getQuantity(),
                item.getItemSubtotal()
            });
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

        jPasswordField1 = new javax.swing.JPasswordField();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        jPasswordField1.setText("jPasswordField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(196, 225, 230));

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel1.setText("Form pemesanan");

        jLabel2.setText("Menu");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel3.setText("Quantity");

        jButton3.setText("Add to cart");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Back");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton2.setText("Clear");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

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

        jLabel4.setText("Total Keseluruhan");

        jLabel5.setText("Rp 00.0");

        jButton1.setText("Order now");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(18, 18, 18)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton2))
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(33, 33, 33)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 186, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addGap(12, 12, 12)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton4)
                    .addComponent(jButton2))
                .addContainerGap(263, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if (currentSelectedMenuId == 0) {
            JOptionPane.showMessageDialog(this, "Silakan pilih menu terlebih dahulu.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(jTextField1.getText());
            if (quantity <= 0) {
                JOptionPane.showMessageDialog(this, "Kuantitas harus lebih dari 0.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (quantity > currentSelectedMenuStock) {
                JOptionPane.showMessageDialog(this, "Stok tidak mencukupi! Stok tersedia: " + currentSelectedMenuStock, "Stok Habis", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Kuantitas harus berupa angka yang valid.", "Input Error", JOptionPane.ERROR_MESSAGE); // Menggunakan ERROR_MESSAGE
            e.printStackTrace();
            return;
        }

        double itemSubtotal = currentSelectedMenuPrice * quantity;

        addMenuItemToCart(currentSelectedMenuId, currentSelectedMenuName, currentSelectedMenuPrice, quantity, itemSubtotal);

        jComboBox1.setSelectedIndex(0); 
        jTextField1.setText("1"); 
        jButton3.setEnabled(false);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        if ("Guest".equalsIgnoreCase(loggedInUserName)) {
            Dashboard homePage = new Dashboard();
            homePage.setVisible(true);
            this.dispose();
        } else {
            Dashboard_user dashboard = new Dashboard_user(loggedInUserName);
            dashboard.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        MenuItem selectedItem = (MenuItem) jComboBox1.getSelectedItem();

        if (selectedItem != null && selectedItem.getId() != 0) {
            this.currentSelectedMenuId = selectedItem.getId();
            this.currentSelectedMenuName = selectedItem.getName();
            this.currentSelectedMenuPrice = selectedItem.getPrice();
            this.currentSelectedMenuStock = selectedItem.getStock();
            System.out.println("Menu dipilih: " + selectedItem.getName() + " (Stok: " + selectedItem.getStock() + ")");
        } else {
            currentSelectedMenuId = 0;
            currentSelectedMenuName = "";
            currentSelectedMenuPrice = 0.0;
            currentSelectedMenuStock = 0;
        }
        updateRincianPemesanan();
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        clearCartAndForm(); 
        JOptionPane.showMessageDialog(this, "Keranjang dan form telah direset.", "Info", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (currentCartItems.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Keranjang belanja kosong. Silakan tambahkan menu terlebih dahulu.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
                      "Konfirmasi pesanan dengan total Rp" + String.format("%.2f", grandTotal) + "?", 
                      "Konfirmasi Checkout", JOptionPane.YES_NO_OPTION);
        
        if (confirm != JOptionPane.YES_OPTION) {
            return; 
        }
        if (loggedInUserId == -1) {
            JOptionPane.showMessageDialog(this, "ID Pengguna tidak ditemukan. Gagal membuat pesanan. Silakan coba login ulang.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) {
                return;
            }
            conn.setAutoCommit(false); 

            int orderId = createOrder(loggedInUserId, grandTotal, conn); 
            if (orderId == -1) {
                conn.rollback();
                JOptionPane.showMessageDialog(this, "Gagal membuat pesanan utama. Terjadi kesalahan database.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            for (OrderItem item : currentCartItems) {
                boolean detailSuccess = createOrderDetail(orderId, item.getMenuId(), item.getQuantity(), item.getItemSubtotal(), conn); // Teruskan koneksi
                if (!detailSuccess) {
                    conn.rollback();
                    JOptionPane.showMessageDialog(this, "Gagal membuat detail pesanan untuk " + item.getMenuName() + ". Terjadi kesalahan database.", "Error", JOptionPane.ERROR_MESSAGE);
                    deleteOrder(orderId, conn); 
                    return;
                }
                boolean stockUpdateSuccess = updateMenuStock(item.getMenuId(), item.getQuantity(), conn); 
                if (!stockUpdateSuccess) {
                    conn.rollback();
                    JOptionPane.showMessageDialog(this, "Gagal memperbarui stok untuk " + item.getMenuName() + ". Terjadi kesalahan database.", "Error", JOptionPane.ERROR_MESSAGE);
                    deleteOrder(orderId, conn); 
                    return;
                }
            }
            conn.commit(); 
            String confirmationMessage = "Pesanan Anda berhasil dibuat!\n\n" +
                                         "Nama: " + loggedInUserName + "\n" +
                                         "Total Keseluruhan: Rp" + String.format("%.2f", grandTotal) + "\n\n" +
                                         "Nomor Pesanan Anda: " + orderId + "\n\n" +
                                         "Silakan menunggu pesanan Anda.";
            JOptionPane.showMessageDialog(this, confirmationMessage, "Pesanan Dikonfirmasi", JOptionPane.INFORMATION_MESSAGE);

            clearCartAndForm(); 
            loadMenusToComboBox(); 

        } catch (SQLException e) {
            try { if (conn != null) conn.rollback(); } catch (SQLException rbEx) { System.err.println("Rollback failed: " + rbEx.getMessage()); }
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan database saat memproses pesanan: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (Exception e) {
            try { if (conn != null) conn.rollback(); } catch (SQLException rbEx) { System.err.println("Rollback failed: " + rbEx.getMessage()); }
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan umum saat memproses pesanan: " + e.getMessage(), "Error Umum", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            try { if (conn != null) conn.setAutoCommit(true); conn.close(); } catch (SQLException closeEx) { System.err.println("Error closing connection: " + closeEx.getMessage()); }
        }
    }//GEN-LAST:event_jButton1ActionPerformed
    
    private void clearCartAndForm() {
        currentCartItems.clear(); 
        refreshCartTable();
        updateGrandTotal(); 
        jComboBox1.setSelectedIndex(0);
        jTextField1.setText("1"); 
        jButton2.setEnabled(false);
        jButton1.setEnabled(false); 
    }
    
    private int createOrder(int userId, double total, Connection conn) throws SQLException {
        String sql = "INSERT INTO orders (user_id, total, status) VALUES (?, ?, 'pending')";
        int generatedOrderId = -1;
        try (PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pst.setInt(1, userId);
            pst.setDouble(2, total);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet rs = pst.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedOrderId = rs.getInt(1);
                    }
                }
            }
        }
        return generatedOrderId;
    }
     
    private boolean createOrderDetail(int orderId, int menuId, int quantity, double subtotal, Connection conn) throws SQLException {
        String sql = "INSERT INTO order_details (order_id, menu_id, quantity, subtotal) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, orderId);
            pst.setInt(2, menuId);
            pst.setInt(3, quantity);
            pst.setDouble(4, subtotal);
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        }
    }

    private boolean updateMenuStock(int menuId, int orderedQuantity, Connection conn) throws SQLException {
        String sql = "UPDATE menu SET stock = stock - ? WHERE id = ?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, orderedQuantity);
            pst.setInt(2, menuId);
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        }
    }
      
    private void deleteOrder(int orderId, Connection conn) {
        String sql = "DELETE FROM orders WHERE id = ?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, orderId);
            pst.executeUpdate();
            System.out.println("Order (ID: " + orderId + ") dihapus karena gagalnya penyisipan detail.");
        } catch (SQLException e) {
            System.err.println("Error saat menghapus pesanan setelah detail gagal: " + e.getMessage());
            e.printStackTrace();
        }
    }
     
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
            java.util.logging.Logger.getLogger(Pemesanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pemesanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pemesanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pemesanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pemesanan().setVisible(true);
            }
        });
    }
    
    class MenuItem {
        private int id;
        private String name;
        private double price;
        private int stock;

        public MenuItem(int id, String name, double price, int stock) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.stock = stock;
        }

        public int getId() { return id; }
        public String getName() { return name; }
        public double getPrice() { return price; }
        public int getStock() { return stock; }

        @Override
        public String toString() {
            if (id == 0 && name.equals("-- Pilih Menu --")) { 
                return name;
            }
            return name + " (Rp" + String.format("%.0f", price) + ", Stok: " + stock + ")";
        }
    }
     
    class OrderItem {
        private int menuId;
        private String menuName;
        private double menuPrice; 
        private int quantity;
        private double itemSubtotal;

        public OrderItem(int menuId, String menuName, double menuPrice, int quantity, double itemSubtotal) {
            this.menuId = menuId;
            this.menuName = menuName;
            this.menuPrice = menuPrice;
            this.quantity = quantity;
            this.itemSubtotal = itemSubtotal;
        }

        public int getMenuId() { return menuId; }
        public String getMenuName() { return menuName; }
        public double getMenuPrice() { return menuPrice; }
        public int getQuantity() { return quantity; }
        public double getItemSubtotal() { return itemSubtotal; }

        public void setQuantity(int quantity) { this.quantity = quantity; }
        public void setItemSubtotal(double itemSubtotal) { this.itemSubtotal = itemSubtotal; }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
