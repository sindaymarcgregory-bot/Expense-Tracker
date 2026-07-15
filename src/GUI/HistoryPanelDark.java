/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import java.util.List;
import javax.swing.table.DefaultTableModel;
import model.Transaction;
import services.CategoryService;
import services.TransactionService;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import utils.Session;

/**
 *
 * @author Arch Salon
 */
public class HistoryPanelDark extends javax.swing.JPanel {

    /**
     * Creates new form HistoryPanel
     */
    private HomeFrame homeFrame;

    public void setHomeFrame(HomeFrame homeFrame) {
        this.homeFrame = homeFrame;
    }

    public HistoryPanelDark() {

        initComponents();

        loadHistoryTable();

        styleHistoryTable();
        colorHistoryType();
    }

    private class TableCellPaddingRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column) {

            Component cell = super.getTableCellRendererComponent(
                    table,
                    value,
                    isSelected,
                    hasFocus,
                    row,
                    column);

            setBorder(
                    BorderFactory.createEmptyBorder(
                            0,
                            10,
                            0,
                            10
                    )
            );

            return cell;
        }
    }

    private void loadHistoryTable() {
        String keyword = historySearchField.getText().trim();
        String filter = cmboHistoryFilter.getSelectedItem().toString();
        DefaultTableModel model = (DefaultTableModel) historyTable.getModel();
        model.setRowCount(0);
        TransactionService transactionService = new TransactionService();

        CategoryService categoryService = new CategoryService();

        int userId = Session.getCurrentUser().getId();

        List<Transaction> transactions;

        if (keyword.isEmpty()) {
            if (filter.equalsIgnoreCase("All")) {
                transactions = transactionService.getTransactionHistory(userId);
            } else {
                transactions = transactionService.getTransactionHistoryByType(
                        userId,
                        filter.toLowerCase());
            }
        } else {
            transactions = transactionService.searchTransactionHistory(
                    userId,
                    filter,
                    keyword);
        }

        for (Transaction transaction : transactions) {
            String categoryName
                    = categoryService.getCategoryNameById(
                            transaction.getCategoryId());
            model.addRow(new Object[]{
                transaction.getId(),
                transaction.getTransactionDate(),
                transaction.getType(),
                categoryName,
                "₱" + String.format(
                "%,.2f",
                transaction.getAmount()
                ),
                transaction.getDescription()
            });
        }
    }

    // Style the history table
    private void styleHistoryTable() {

        historyTable.setAutoResizeMode(
                javax.swing.JTable.AUTO_RESIZE_OFF
        );

        historyTable.getColumnModel()
                .getColumn(0)
                .setMinWidth(0);

        historyTable.getColumnModel()
                .getColumn(0)
                .setMaxWidth(0);

        historyTable.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(0);

        historyTable.setFont(
                new Font("Segoe UI", Font.PLAIN, 13)
        );

        historyTable.getTableHeader()
                .setFont(
                        new Font("Segoe UI", Font.BOLD, 14)
                );

        historyTable.setRowHeight(35);

        historyTable.setShowGrid(false);

        historyTable.setIntercellSpacing(
                new Dimension(0, 0)
        );

        historyTable.setBackground(
                new Color(153, 153, 153)
        );
        
        historyTable.setForeground(Color.WHITE);
        historyTable.setGridColor(new Color(120, 120, 120));
        historyTable.getTableHeader().setBackground(
                new Color(153, 153, 153)
        );

        historyTable.getTableHeader().setForeground(Color.WHITE);

        historyTable.setSelectionBackground(
                new Color(120, 120, 120)
        );

        historyTable.setSelectionForeground(
                Color.WHITE
        );

        jScrollPane1.setBorder(null);
        jScrollPane1.getViewport().setBackground(
                new Color(153, 153, 153)
        );

        historyTable.getColumnModel()
                .getColumn(1)
                .setPreferredWidth(100); // Date

        historyTable.getColumnModel()
                .getColumn(2)
                .setPreferredWidth(100); // Type

        historyTable.getColumnModel()
                .getColumn(3)
                .setPreferredWidth(130); // Category

        historyTable.getColumnModel()
                .getColumn(4)
                .setPreferredWidth(150); // Amount

        historyTable.getColumnModel()
                .getColumn(5)
                .setPreferredWidth(300); // Description
        TableCellPaddingRenderer renderer
                = new TableCellPaddingRenderer();

        historyTable.setDefaultRenderer(
                Object.class,
                renderer
        );

        renderer.setHorizontalAlignment(
                javax.swing.SwingConstants.LEFT
        );

        DefaultTableCellRenderer amountRenderer
                = new TableCellPaddingRenderer();

        amountRenderer.setHorizontalAlignment(
                javax.swing.SwingConstants.RIGHT
        );

        historyTable.getColumnModel()
                .getColumn(4)
                .setCellRenderer(amountRenderer);

        DefaultTableCellRenderer descriptionRenderer
                = new DefaultTableCellRenderer();

        descriptionRenderer.setHorizontalAlignment(
                javax.swing.SwingConstants.CENTER
        );

        historyTable.getColumnModel()
                .getColumn(5)
                .setCellRenderer(descriptionRenderer);
        historyTable.getColumnModel()
                .getColumn(4)
                .setCellRenderer(descriptionRenderer);
        historyTable.getColumnModel()
                .getColumn(3)
                .setCellRenderer(descriptionRenderer);
        historyTable.getColumnModel()
                .getColumn(2)
                .setCellRenderer(descriptionRenderer);
        historyTable.getColumnModel()
                .getColumn(1)
                .setCellRenderer(descriptionRenderer);

    }

    private void colorHistoryType() {

        DefaultTableCellRenderer typeRenderer
                = new DefaultTableCellRenderer() {

            @Override
            public java.awt.Component getTableCellRendererComponent(
                    javax.swing.JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {

                java.awt.Component c
                        = super.getTableCellRendererComponent(
                                table,
                                value,
                                isSelected,
                                hasFocus,
                                row,
                                column
                        );

                if (!isSelected) {

                    if ("income".equalsIgnoreCase(value.toString())) {

                        c.setForeground(
                                new Color(0, 170, 80)
                        );

                    } else if ("expense".equalsIgnoreCase(value.toString())) {

                        c.setForeground(
                                new Color(220, 70, 70)
                        );

                    }

                }

                return c;
            }
        };

        historyTable.getColumnModel()
                .getColumn(2)
                .setCellRenderer(typeRenderer);

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
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        historyTable = new javax.swing.JTable();
        historySearchField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        cmboHistoryFilter = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.setMinimumSize(new java.awt.Dimension(895, 600));
        jPanel1.setPreferredSize(new java.awt.Dimension(917, 361));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(111, 151, 143));
        jLabel2.setText("History");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 20, 90, 30));

        jScrollPane1.setBackground(new java.awt.Color(102, 102, 102));

        historyTable.setBackground(new java.awt.Color(102, 102, 102));
        historyTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Date", "Type", "Category", "Amount", "Description"
            }
        ));
        jScrollPane1.setViewportView(historyTable);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, 810, 440));

        historySearchField.setBackground(new java.awt.Color(102, 102, 102));
        historySearchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                historySearchFieldKeyReleased(evt);
            }
        });
        jPanel1.add(historySearchField, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 100, 160, -1));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(111, 151, 143));
        jLabel1.setText("Search:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 100, -1, -1));

        cmboHistoryFilter.setBackground(new java.awt.Color(102, 102, 102));
        cmboHistoryFilter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Income", "Expense" }));
        cmboHistoryFilter.addActionListener(this::cmboHistoryFilterActionPerformed);
        jPanel1.add(cmboHistoryFilter, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, 100, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(111, 151, 143));
        jLabel3.setText("Filter:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 895, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmboHistoryFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmboHistoryFilterActionPerformed
        loadHistoryTable();
    }//GEN-LAST:event_cmboHistoryFilterActionPerformed

    private void historySearchFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_historySearchFieldKeyReleased
        loadHistoryTable();
    }//GEN-LAST:event_historySearchFieldKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmboHistoryFilter;
    private javax.swing.JTextField historySearchField;
    private javax.swing.JTable historyTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
