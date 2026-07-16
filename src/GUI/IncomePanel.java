package GUI;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Category;
import model.Transaction;
import services.CategoryService;
import services.TransactionService;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.table.DefaultTableCellRenderer;
import utils.Session;

public class IncomePanel extends javax.swing.JPanel {

    private int selectedTransactionId = -1;
    private HomeFrame homeFrame;
    
    public void setHomeFrame(HomeFrame homeFrame) {
        this.homeFrame = homeFrame;
    }

    public IncomePanel() {

        initComponents();

        loadIncomeCategories();

        loadIncomeTable();
        
        updateTotalIncome();

        styleIncomeTable();

        incomeTable.getColumnModel().getColumn(0).setMinWidth(0);
        incomeTable.getColumnModel().getColumn(0).setMaxWidth(0);
        incomeTable.getColumnModel().getColumn(0).setPreferredWidth(0);
    }
    
    private void updateTotalIncome() {
        BigDecimal total = BigDecimal.ZERO;

        for (int row = 0; row < incomeTable.getRowCount(); row++) {
            Object value = incomeTable.getValueAt(row, 1); // Amount column

            if (value == null) continue;

            String amount = value.toString()
                    .replace("₱", "")
                    .replace(",", "")
                    .trim();

            if (amount.isEmpty()) continue;

            total = total.add(new BigDecimal(amount));
        }
        lblTotalIncome.setText("Total Income: ₱" + String.format("%,.2f", total));
    }

    private void loadIncomeCategories() {
        CategoryService service = new CategoryService();
        int userId = Session.getCurrentUser().getId();
        ArrayList<Category> categories = service.getCategories(userId, "income");

        cmboIncomeCategory.removeAllItems();

        for (Category category : categories) {
            cmboIncomeCategory.addItem(category);
        }
    }

    private void loadIncomeTable() {
        DefaultTableModel model = (DefaultTableModel) incomeTable.getModel();
        model.setRowCount(0);

        TransactionService transactionService = new TransactionService();
        CategoryService categoryService = new CategoryService();

        int userId = Session.getCurrentUser().getId();

        List<Transaction> transactions = transactionService.getTransactionHistoryByType(userId, "income");

        for (Transaction transaction : transactions) {
            String categoryName
                    = categoryService.getCategoryNameById(
                            transaction.getCategoryId());

            model.addRow(new Object[]{
                transaction.getId(),
                "₱" + String.format(
                "%,.2f",
                transaction.getAmount()
                ),
                categoryName,
                transaction.getDescription(),
                transaction.getTransactionDate()
            });
        }

    }
    
    // Clears all input fields.
    private void clearFields() {
        incomeAmountField.setText("");

        incomeDescriptionTextArea.setText("");

        if (cmboIncomeCategory.getItemCount() > 0) {
            cmboIncomeCategory.setSelectedIndex(0);
        }

        incomeTable.clearSelection();

        selectedTransactionId = -1;
    }

    // Refreshes the Income Panel and Dashboard.
    private void refreshData() {

        loadIncomeTable();
        updateTotalIncome();

        if (homeFrame != null) {
            homeFrame.getDashboardPanel().refreshDashboard();
        }
        clearFields();
    }

    // Style the income table
    private void styleIncomeTable() {

        // Hide ID column
        incomeTable.getColumnModel()
                .getColumn(0)
                .setMinWidth(0);

        incomeTable.getColumnModel()
                .getColumn(0)
                .setMaxWidth(0);

        incomeTable.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(0);
        
        jScrollPane1.setPreferredSize(new Dimension(520, 335));

        // Table font
        incomeTable.setFont(
                new Font("Segoe UI", Font.PLAIN, 13)
        );

        // Header font
        incomeTable.getTableHeader()
                .setFont(
                        new Font("Segoe UI", Font.BOLD, 14)
                );

        // Row height
        incomeTable.setRowHeight(35);

        // Remove grid
        incomeTable.setShowGrid(false);

        // Remove spacing
        incomeTable.setIntercellSpacing(
                new Dimension(0, 0)
        );

        // Background
        incomeTable.setBackground(
                new Color(220, 232, 208)
        );

        // Selection
        incomeTable.setSelectionBackground(
                new Color(111, 151, 143)
        );

        incomeTable.setSelectionForeground(
                Color.WHITE
        );

        // Scroll pane design
        jScrollPane1.setBorder(null);

        // Column sizes
        incomeTable.getColumnModel()
                .getColumn(1)
                .setPreferredWidth(120);

        incomeTable.getColumnModel()
                .getColumn(2)
                .setPreferredWidth(120);

        incomeTable.getColumnModel()
                .getColumn(3)
                .setPreferredWidth(200);

        incomeTable.getColumnModel()
                .getColumn(4)
                .setPreferredWidth(120);

        DefaultTableCellRenderer incomeRenderer
                = new DefaultTableCellRenderer();

        incomeRenderer.setForeground(
                new Color(0, 170, 80)
        );

        incomeTable.getColumnModel()
                .getColumn(1)
                .setCellRenderer(incomeRenderer);

        // Center the category column
        DefaultTableCellRenderer centerRenderer
                = new DefaultTableCellRenderer();

        centerRenderer.setHorizontalAlignment(
                javax.swing.SwingConstants.CENTER
        );

        incomeTable.getColumnModel()
                .getColumn(1)
                .setCellRenderer(centerRenderer);

        incomeTable.getColumnModel()
                .getColumn(2)
                .setCellRenderer(centerRenderer);

        // Center the description column
        incomeTable.getColumnModel()
                .getColumn(3)
                .setCellRenderer(centerRenderer);

        incomeTable.getColumnModel()
                .getColumn(4)
                .setCellRenderer(centerRenderer);

        // Center the description column
        centerRenderer.setHorizontalAlignment(
                javax.swing.SwingConstants.CENTER
        );

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
        cmboIncomeCategory = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        incomeAmountField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        incomeTable = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        incomeDescriptionTextArea = new javax.swing.JTextArea();
        updateIncomeButton = new javax.swing.JButton();
        clearIncomeButton = new javax.swing.JButton();
        deleteIncomeButton = new javax.swing.JButton();
        addIncomeButton = new javax.swing.JButton();
        btnDeleteIncomeCategory = new javax.swing.JButton();
        btnAddIncomeCategory = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        lblTotalIncome = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(882, 620));

        jPanel1.setBackground(new java.awt.Color(220, 232, 208));
        jPanel1.setMaximumSize(null);
        jPanel1.setPreferredSize(new java.awt.Dimension(1020, 604));
        jPanel1.setRequestFocusEnabled(false);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(111, 151, 143));
        jLabel2.setText("Category");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(111, 151, 143));
        jLabel1.setText("Amount");

        jScrollPane1.setBackground(new java.awt.Color(220, 232, 208));
        jScrollPane1.setMaximumSize(new java.awt.Dimension(375, 100));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(375, 100));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(375, 100));

        incomeTable.setBackground(new java.awt.Color(220, 232, 208));
        incomeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Amount", "Category", "Description", "Date"
            }
        ));
        incomeTable.setMinimumSize(new java.awt.Dimension(375, 100));
        incomeTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                incomeTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(incomeTable);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(111, 151, 143));
        jLabel3.setText("Notes");

        incomeDescriptionTextArea.setColumns(20);
        incomeDescriptionTextArea.setRows(5);
        incomeDescriptionTextArea.setText("\n\n");
        jScrollPane2.setViewportView(incomeDescriptionTextArea);

        updateIncomeButton.setBackground(new java.awt.Color(111, 151, 143));
        updateIncomeButton.setForeground(new java.awt.Color(242, 242, 242));
        updateIncomeButton.setText("Update");
        updateIncomeButton.addActionListener(this::updateIncomeButtonActionPerformed);

        clearIncomeButton.setBackground(new java.awt.Color(111, 151, 143));
        clearIncomeButton.setForeground(new java.awt.Color(242, 242, 242));
        clearIncomeButton.setText("Clear");
        clearIncomeButton.addActionListener(this::clearIncomeButtonActionPerformed);

        deleteIncomeButton.setBackground(new java.awt.Color(111, 151, 143));
        deleteIncomeButton.setForeground(new java.awt.Color(242, 242, 242));
        deleteIncomeButton.setText("Delete");
        deleteIncomeButton.addActionListener(this::deleteIncomeButtonActionPerformed);

        addIncomeButton.setBackground(new java.awt.Color(111, 151, 143));
        addIncomeButton.setForeground(new java.awt.Color(242, 242, 242));
        addIncomeButton.setText("Add");
        addIncomeButton.addActionListener(this::addIncomeButtonActionPerformed);

        btnDeleteIncomeCategory.setBackground(new java.awt.Color(111, 151, 143));
        btnDeleteIncomeCategory.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnDeleteIncomeCategory.setForeground(new java.awt.Color(242, 242, 242));
        btnDeleteIncomeCategory.setText("-");
        btnDeleteIncomeCategory.addActionListener(this::btnDeleteIncomeCategoryActionPerformed);

        btnAddIncomeCategory.setBackground(new java.awt.Color(111, 151, 143));
        btnAddIncomeCategory.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnAddIncomeCategory.setForeground(new java.awt.Color(242, 242, 242));
        btnAddIncomeCategory.setText("+");
        btnAddIncomeCategory.addActionListener(this::btnAddIncomeCategoryActionPerformed);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(111, 151, 143));
        jLabel4.setText("Enter your description here...");

        lblTotalIncome.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTotalIncome.setForeground(new java.awt.Color(0, 204, 51));
        lblTotalIncome.setText("Total Income:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(55, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblTotalIncome)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(incomeAmountField, javax.swing.GroupLayout.PREFERRED_SIZE, 497, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 497, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cmboIncomeCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnAddIncomeCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDeleteIncomeCategory)
                                .addGap(6, 6, 6))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(deleteIncomeButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
                                        .addComponent(updateIncomeButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(addIncomeButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(clearIncomeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3))
                                .addContainerGap(60, Short.MAX_VALUE))))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addGap(79, 79, 79)
                        .addComponent(addIncomeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateIncomeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteIncomeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clearIncomeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnDeleteIncomeCategory)
                            .addComponent(btnAddIncomeCategory)
                            .addComponent(cmboIncomeCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(incomeAmountField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTotalIncome)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 882, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 620, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtfldIncomeAmountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtfldIncomeAmountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfldIncomeAmountActionPerformed

    private void incomeTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_incomeTableMouseClicked
        int row = incomeTable.getSelectedRow();

        if (row == -1) {
            return;
        }
        selectedTransactionId
                = Integer.parseInt(incomeTable.getValueAt(row, 0).toString());
        incomeAmountField.setText(incomeTable.getValueAt(row, 1).toString());

        String categoryName = incomeTable.getValueAt(row, 2).toString();

        for (int i = 0; i < cmboIncomeCategory.getItemCount(); i++) {
            Category category = cmboIncomeCategory.getItemAt(i);
            if (category.getName().equals(categoryName)) {
                cmboIncomeCategory.setSelectedIndex(i);
                break;
            }
        }
        incomeDescriptionTextArea.setText(
                incomeTable.getValueAt(row, 3).toString());
    }//GEN-LAST:event_incomeTableMouseClicked

    private void updateIncomeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateIncomeButtonActionPerformed
        try {

            if (selectedTransactionId == -1) {
                throw new IllegalArgumentException(
                        "Please select a transaction first."
                );
            }

            Category category
                    = (Category) cmboIncomeCategory.getSelectedItem();

            Transaction transaction = new Transaction();

            transaction.setId(selectedTransactionId);
            transaction.setUserId(Session.getCurrentUser().getId());
            transaction.setCategoryId(category.getId());
            transaction.setType("income");

            transaction.setAmount(
                    new BigDecimal(incomeAmountField.getText().trim())
            );

            transaction.setDescription(
                    incomeDescriptionTextArea.getText().trim()
            );

            TransactionService service = new TransactionService();

            service.updateTransaction(transaction);

            JOptionPane.showMessageDialog(this,
                    "Income updated successfully.");

            refreshData();

        } catch (IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(this,
                    ex.getMessage());

        } catch (RuntimeException ex) {

            JOptionPane.showMessageDialog(this,
                    ex.getMessage());

        }
    }//GEN-LAST:event_updateIncomeButtonActionPerformed

    private void clearIncomeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearIncomeButtonActionPerformed
        clearFields();
    }//GEN-LAST:event_clearIncomeButtonActionPerformed

    private void deleteIncomeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteIncomeButtonActionPerformed
        try {

            if (selectedTransactionId == -1) {
                throw new IllegalArgumentException(
                        "Please select a transaction."
                );
            }

            int option = JOptionPane.showConfirmDialog(
                    this,
                    "Delete this income?",
                    "Confirm",
                    JOptionPane.YES_NO_OPTION
            );

            if (option != JOptionPane.YES_OPTION) {
                return;
            }

            TransactionService service = new TransactionService();

            service.deleteTransaction(selectedTransactionId);

            JOptionPane.showMessageDialog(this,
                    "Income deleted.");
            refreshData();

        } catch (IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(this,
                    ex.getMessage());

        } catch (RuntimeException ex) {

            JOptionPane.showMessageDialog(this,
                    ex.getMessage());

        }
    }//GEN-LAST:event_deleteIncomeButtonActionPerformed

    private void addIncomeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addIncomeButtonActionPerformed
        try {
            Category category
                    = (Category) cmboIncomeCategory.getSelectedItem();

            if (category == null) {
                JOptionPane.showMessageDialog(this, "Please select a category.");
                return;
            }
            BigDecimal amount = new BigDecimal(incomeAmountField.getText().trim());

            String description = incomeDescriptionTextArea.getText().trim();

            Transaction transaction = new Transaction();

            transaction.setUserId(Session.getCurrentUser().getId());
            transaction.setCategoryId(category.getId());
            transaction.setType("income");
            transaction.setAmount(amount);
            transaction.setDescription(description);

            transaction.setTransactionDate(
                    java.sql.Date.valueOf(java.time.LocalDate.now()));

            TransactionService service = new TransactionService();

            if (service.addTransaction(transaction)) {
                JOptionPane.showMessageDialog(this, "Income added successfully!");

                refreshData();
            }
        } catch (Exception e) {   
            JOptionPane.showMessageDialog(
                this,
                e.getClass().getSimpleName() + "\n" + e.getMessage());
        }
    }//GEN-LAST:event_addIncomeButtonActionPerformed

    private void btnAddIncomeCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddIncomeCategoryActionPerformed
        try {

            String categoryName = JOptionPane.showInputDialog(
                    this,
                    "Enter new income category:"
            );

            if (categoryName == null) {
                return;
            }

            Category category = new Category();

            category.setUserId(Session.getCurrentUser().getId());
            category.setName(categoryName);
            category.setType("income");

            CategoryService service = new CategoryService();

            service.addCategory(category);

            JOptionPane.showMessageDialog(this,
                    "Category added successfully.");

            loadIncomeCategories();

        } catch (IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(this,
                    ex.getMessage());

        } catch (RuntimeException ex) {

            JOptionPane.showMessageDialog(this,
                    ex.getMessage());

        }
    }//GEN-LAST:event_btnAddIncomeCategoryActionPerformed

    private void btnDeleteIncomeCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteIncomeCategoryActionPerformed
          Category category = (Category) cmboIncomeCategory.getSelectedItem();

        if (category == null) {
            JOptionPane.showMessageDialog(this,
                    "Please select a category.");
            return;
        }

        CategoryService service = new CategoryService();

        // Check if the category is already used
        if (service.isCategoryInUse(category.getId())) {

            JOptionPane.showMessageDialog(
                    this,
                    "This category cannot be deleted because it is already used in one or more transactions.",
                    "Delete Category",
                    JOptionPane.WARNING_MESSAGE);

            return;
        }
        //showing confirm delete category message
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Delete \"" + category.getName() + "\"?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            //deleting the category
            if (service.deleteCategory(category.getId())) {
                
                JOptionPane.showMessageDialog(
                        this,
                        "Category deleted successfully.");

                loadIncomeCategories();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Unable to delete category.");
            }
        }
        
        
        
        
        
        
        
    }//GEN-LAST:event_btnDeleteIncomeCategoryActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addIncomeButton;
    private javax.swing.JButton btnAddIncomeCategory;
    private javax.swing.JButton btnDeleteIncomeCategory;
    private javax.swing.JButton clearIncomeButton;
    private javax.swing.JComboBox<Category> cmboIncomeCategory;
    private javax.swing.JButton deleteIncomeButton;
    private javax.swing.JTextField incomeAmountField;
    private javax.swing.JTextArea incomeDescriptionTextArea;
    private javax.swing.JTable incomeTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblTotalIncome;
    private javax.swing.JButton updateIncomeButton;
    // End of variables declaration//GEN-END:variables
}
