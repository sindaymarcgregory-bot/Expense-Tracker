/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.math.BigDecimal;
import java.time.LocalDate;
import model.Category;
import services.CategoryService;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.Transaction;
import services.TransactionService;
import utils.Session;

/**
 *
 * @author Arch Salon
 */
  

    public class ExpensePanel extends javax.swing.JPanel {

        /**
         * Creates new form ExpensePanel
         */
        private int selectedTransactionId = -1;
        private HomeFrame homeFrame;
        
        public void setHomeFrame(HomeFrame homeFrame) {
            this.homeFrame = homeFrame;
        }

        public ExpensePanel() {
            initComponents();

            loadExpenseCategories();

            loadExpenseTable();
            
            updateTotalExpense();
            
            styleExpenseTable();

            expenseTable.getColumnModel().getColumn(0).setMinWidth(0);
            expenseTable.getColumnModel().getColumn(0).setMaxWidth(0);
            expenseTable.getColumnModel().getColumn(0).setPreferredWidth(0);
        }
        
        private void updateTotalExpense() {
            BigDecimal total = BigDecimal.ZERO;

            for (int row = 0; row < expenseTable.getRowCount(); row++) {
                Object value = expenseTable.getValueAt(row, 1); // Amount column

                if (value == null) continue;

                String amount = value.toString()
                        .replace("₱", "")
                        .replace(",", "")
                        .trim();

                if (amount.isEmpty()) continue;

                total = total.add(new BigDecimal(amount));
            }
            lblTotalExpense.setText("Total Expense: ₱" + String.format("%,.2f", total));
        }

        private void loadExpenseTable() {
            DefaultTableModel model = (DefaultTableModel) expenseTable.getModel();
            model.setRowCount(0);
            TransactionService transactionService = new TransactionService();
            CategoryService categoryService = new CategoryService();
            int userId = Session.getCurrentUser().getId();

            List<Transaction> transactions = transactionService.getTransactionHistoryByType(userId, "expense");

            for (Transaction transaction : transactions) {
                String categoryName
                        = categoryService.getCategoryNameById(
                                transaction.getCategoryId());
                model.addRow(new Object[]{
                    transaction.getId(),
                    transaction.getAmount(),
                    categoryName,
                    transaction.getDescription(),
                    transaction.getTransactionDate()
                });
            }
        }

        private void loadExpenseCategories() {

            CategoryService service = new CategoryService();

            int userId = Session.getCurrentUser().getId();

            ArrayList<Category> categories
                    = service.getCategories(userId, "expense");

            cmboExpenseCategory.removeAllItems();

            for (Category category : categories) {

                cmboExpenseCategory.addItem(category);

            }
        }
        
        // Clears the expense form.
        private void clearFields() {
            expenseAmountField.setText("");
            expenseDescriptionTextArea.setText("");
            if (cmboExpenseCategory.getItemCount() > 0) {
                cmboExpenseCategory.setSelectedIndex(0);
            }
            expenseTable.clearSelection();
            selectedTransactionId = -1;
        }
    
        // Refresh all data after a transaction.
        private void refreshData() {

            // Refresh the expense table.
            loadExpenseTable();
            updateTotalExpense();

            // Refresh the dashboard.
            if (homeFrame != null) {
            homeFrame.getDashboardPanel().refreshDashboard();
            }

            // Clear the form.
            clearFields();
        }
        
        // Style the expense table
    private void styleExpenseTable() {

        // Hide ID column
        expenseTable.getColumnModel()
                .getColumn(0)
                .setMinWidth(0);

        expenseTable.getColumnModel()
                .getColumn(0)
                .setMaxWidth(0);

        expenseTable.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(0);
        
        jScrollPaneExpense.setPreferredSize(new Dimension(520, 335));

        // Table font
        expenseTable.setFont(
                new Font("Segoe UI", Font.PLAIN, 13)
        );

        // Header font
        expenseTable.getTableHeader()
                .setFont(
                        new Font("Segoe UI", Font.BOLD, 14)
                );

        // Row height
        expenseTable.setRowHeight(35);

        // Remove grid
        expenseTable.setShowGrid(false);

        // Remove spacing
        expenseTable.setIntercellSpacing(
                new Dimension(0, 0)
        );

        // Background
        expenseTable.setBackground(
                new Color(220, 232, 208)
        );

        // Selection
        expenseTable.setSelectionBackground(
                new Color(111, 151, 143)
        );

        expenseTable.setSelectionForeground(
                Color.WHITE
        );

        // Scroll pane design
        jScrollPaneExpense.setBorder(null);

        // Column sizes
        expenseTable.getColumnModel()
                .getColumn(1)
                .setPreferredWidth(120);

        expenseTable.getColumnModel()
                .getColumn(2)
                .setPreferredWidth(120);

        expenseTable.getColumnModel()
                .getColumn(3)
                .setPreferredWidth(200);

        expenseTable.getColumnModel()
                .getColumn(4)
                .setPreferredWidth(120);

        DefaultTableCellRenderer incomeRenderer
                = new DefaultTableCellRenderer();

        incomeRenderer.setForeground(
                new Color(0, 170, 80)
        );

        expenseTable.getColumnModel()
                .getColumn(1)
                .setCellRenderer(incomeRenderer);

        // Center the category column
        DefaultTableCellRenderer centerRenderer
                = new DefaultTableCellRenderer();

        centerRenderer.setHorizontalAlignment(
                javax.swing.SwingConstants.CENTER
        );

        expenseTable.getColumnModel()
                .getColumn(1)
                .setCellRenderer(centerRenderer);

        expenseTable.getColumnModel()
                .getColumn(2)
                .setCellRenderer(centerRenderer);

        // Center the description column
        expenseTable.getColumnModel()
                .getColumn(3)
                .setCellRenderer(centerRenderer);

        expenseTable.getColumnModel()
                .getColumn(4)
                .setCellRenderer(centerRenderer);

        // Center the description column
        centerRenderer.setHorizontalAlignment(
                javax.swing.SwingConstants.CENTER
        );

    }

        /**
         * This method is called from within the constructor to initialize the
         * form. WARNING: Do NOT modify this code. The content of this method is
         * always regenerated by the Form Editor.
         */
        @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cmboExpenseCategory = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        expenseAmountField = new javax.swing.JTextField();
        jScrollPaneExpense = new javax.swing.JScrollPane();
        expenseTable = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        expenseDescriptionTextArea = new javax.swing.JTextArea();
        updateExpenseButton = new javax.swing.JButton();
        clearExpenseButton = new javax.swing.JButton();
        deleteExpenseButton = new javax.swing.JButton();
        addExpenseButton = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        btnDeleteExpenseCategory = new javax.swing.JButton();
        btnAddExpenseCategory1 = new javax.swing.JButton();
        lblTotalExpense = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(220, 232, 208));
        jPanel1.setMaximumSize(null);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(111, 151, 143));
        jLabel2.setText("Category");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(111, 151, 143));
        jLabel1.setText("Amount");

        jScrollPaneExpense.setBackground(new java.awt.Color(220, 232, 208));

        expenseTable.setBackground(new java.awt.Color(220, 232, 208));
        expenseTable.setModel(new javax.swing.table.DefaultTableModel(
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
        expenseTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                expenseTableMouseClicked(evt);
            }
        });
        jScrollPaneExpense.setViewportView(expenseTable);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(111, 151, 143));
        jLabel3.setText("Notes");

        expenseDescriptionTextArea.setColumns(20);
        expenseDescriptionTextArea.setRows(5);
        expenseDescriptionTextArea.setText("\n\n");
        jScrollPane2.setViewportView(expenseDescriptionTextArea);

        updateExpenseButton.setBackground(new java.awt.Color(111, 151, 143));
        updateExpenseButton.setForeground(new java.awt.Color(242, 242, 242));
        updateExpenseButton.setText("Update");
        updateExpenseButton.addActionListener(this::updateExpenseButtonActionPerformed);

        clearExpenseButton.setBackground(new java.awt.Color(111, 151, 143));
        clearExpenseButton.setForeground(new java.awt.Color(242, 242, 242));
        clearExpenseButton.setText("Clear");
        clearExpenseButton.addActionListener(this::clearExpenseButtonActionPerformed);

        deleteExpenseButton.setBackground(new java.awt.Color(111, 151, 143));
        deleteExpenseButton.setForeground(new java.awt.Color(242, 242, 242));
        deleteExpenseButton.setText("Delete");
        deleteExpenseButton.addActionListener(this::deleteExpenseButtonActionPerformed);

        addExpenseButton.setBackground(new java.awt.Color(111, 151, 143));
        addExpenseButton.setForeground(new java.awt.Color(242, 242, 242));
        addExpenseButton.setText("Add");
        addExpenseButton.addActionListener(this::addExpenseButtonActionPerformed);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(111, 151, 143));
        jLabel4.setText("Enter your description here...");

        btnDeleteExpenseCategory.setBackground(new java.awt.Color(111, 151, 143));
        btnDeleteExpenseCategory.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnDeleteExpenseCategory.setForeground(new java.awt.Color(242, 242, 242));
        btnDeleteExpenseCategory.setText("-");
        btnDeleteExpenseCategory.addActionListener(this::btnDeleteExpenseCategoryActionPerformed);

        btnAddExpenseCategory1.setBackground(new java.awt.Color(111, 151, 143));
        btnAddExpenseCategory1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnAddExpenseCategory1.setForeground(new java.awt.Color(242, 242, 242));
        btnAddExpenseCategory1.setText("+");
        btnAddExpenseCategory1.addActionListener(this::btnAddExpenseCategory1ActionPerformed);

        lblTotalExpense.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTotalExpense.setForeground(new java.awt.Color(255, 0, 51));
        lblTotalExpense.setText("Total Expense:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblTotalExpense)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPaneExpense, javax.swing.GroupLayout.PREFERRED_SIZE, 497, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(expenseAmountField, javax.swing.GroupLayout.PREFERRED_SIZE, 497, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(cmboExpenseCategory, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAddExpenseCategory1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDeleteExpenseCategory)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(addExpenseButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(updateExpenseButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(deleteExpenseButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(clearExpenseButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(171, 171, 171))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addComponent(jLabel2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                        .addComponent(addExpenseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateExpenseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteExpenseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clearExpenseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(55, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAddExpenseCategory1)
                            .addComponent(btnDeleteExpenseCategory)
                            .addComponent(cmboExpenseCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(expenseAmountField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPaneExpense, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTotalExpense, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 879, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void updateExpenseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateExpenseButtonActionPerformed
        if (selectedTransactionId == -1) {

            JOptionPane.showMessageDialog(this,
                    "Please select a transaction first.");

            return;
        }

        try {
            if (cmboExpenseCategory.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Please select a category.");
                return;
            }
            Category category = (Category) cmboExpenseCategory.getSelectedItem();

            BigDecimal amount = new BigDecimal(expenseAmountField.getText());

            String description = expenseDescriptionTextArea.getText();

            Transaction transaction = new Transaction();

            transaction.setId(selectedTransactionId);
            transaction.setCategoryId(category.getId());
            transaction.setAmount(amount);
            transaction.setDescription(description);

            TransactionService service = new TransactionService();

            if (service.updateTransaction(transaction)) {
                JOptionPane.showMessageDialog(this, "Expense updated successfully!");
                refreshData();
        } else {
                JOptionPane.showMessageDialog(this, "Update failed.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid amount.");
        }
    }//GEN-LAST:event_updateExpenseButtonActionPerformed

    private void addExpenseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addExpenseButtonActionPerformed
        try {
            if (cmboExpenseCategory.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Please select a category.");
                return;
            }
            
            // Get the selected category.
            Category category = (Category) cmboExpenseCategory.getSelectedItem();
            
            // Create the service and transaction objects.
            TransactionService transactionService = new TransactionService();
            Transaction transaction = new Transaction();
            
            // Store the logged-in user's ID.
            transaction.setUserId(Session.getCurrentUser().getId());
            
             // Store the selected category.
            transaction.setCategoryId(category.getId());
            
            // Set the transaction type.
            transaction.setType("expense");
            
            // Store the entered amount.
            transaction.setAmount(new BigDecimal(expenseAmountField.getText().trim()));
            
            // Store the description.
            transaction.setDescription(expenseDescriptionTextArea.getText().trim());
            
            // Automatically use today's date.
            transaction.setTransactionDate(java.sql.Date.valueOf(LocalDate.now()));
            
            // Save the transaction.
            if (transactionService.addTransaction(transaction)) {
            JOptionPane.showMessageDialog(this, "Expense added successfully!");

            refreshData();
            }
        } catch (IllegalArgumentException e) {
            // Display validation errors.
            JOptionPane.showMessageDialog(this, e.getMessage());

        } catch (Exception e) {
            // Display unexpected errors.
            JOptionPane.showMessageDialog(this, "Unable to add expense.");
            e.printStackTrace();
        }
    }//GEN-LAST:event_addExpenseButtonActionPerformed

    private void expenseTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_expenseTableMouseClicked
        // Get the selected row.
        int row = expenseTable.getSelectedRow();
        
        // Store the selected transaction ID.
        if (row >= 0) {
            selectedTransactionId = Integer.parseInt(expenseTable.getValueAt(row, 0).toString());

            expenseAmountField.setText(expenseTable.getValueAt(row, 1).toString());

            expenseDescriptionTextArea.setText(expenseTable.getValueAt(row, 3).toString());

            // Automatically select the correct category.
            String categoryName = expenseTable.getValueAt(row, 2).toString();

        for (int i = 0; i < cmboExpenseCategory.getItemCount(); i++) {
            Category category = cmboExpenseCategory.getItemAt(i);
                if (category.getName().equals(categoryName)) {
                    cmboExpenseCategory.setSelectedIndex(i);
                    break;
                }
            }
        }
    }//GEN-LAST:event_expenseTableMouseClicked

    private void deleteExpenseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteExpenseButtonActionPerformed
        if (selectedTransactionId == -1) {
            JOptionPane.showMessageDialog(this, "Please select a transaction first.");
            return;
        }

        
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Delete this transaction?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        TransactionService service = new TransactionService();

        if (service.deleteTransaction(selectedTransactionId)) {
            JOptionPane.showMessageDialog(this, "Transaction deleted!");
            refreshData();
    }
    }//GEN-LAST:event_deleteExpenseButtonActionPerformed

    private void clearExpenseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearExpenseButtonActionPerformed
        // Clear the input fields
        expenseAmountField.setText("");
        expenseDescriptionTextArea.setText("");

        // Reset category
        if (cmboExpenseCategory.getItemCount() > 0) {
            cmboExpenseCategory.setSelectedIndex(0);
        }

        // Unselect any selected row
        expenseTable.clearSelection();

        // Reset selected transaction
        selectedTransactionId = -1;
    }//GEN-LAST:event_clearExpenseButtonActionPerformed

    private void btnDeleteExpenseCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteExpenseCategoryActionPerformed
        Category category = (Category) cmboExpenseCategory.getSelectedItem();

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

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Delete \"" + category.getName() + "\"?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {

            if (service.deleteCategory(category.getId())) {

                JOptionPane.showMessageDialog(
                        this,
                        "Category deleted successfully.");

                loadExpenseCategories();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Unable to delete category.");
            }
        }
    }//GEN-LAST:event_btnDeleteExpenseCategoryActionPerformed

    private void btnAddExpenseCategory1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddExpenseCategory1ActionPerformed
        String categoryName = JOptionPane.showInputDialog(this, "Enter new expense category:");

        // User pressed Cancel
        if (categoryName == null) {
            return;
        }

        categoryName = categoryName.trim();

        // Empty category
        if (categoryName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Category name cannot be empty.");
            return;
        }

        CategoryService service = new CategoryService();

        int userId = Session.getCurrentUser().getId();

        // Check if category already exists
        if (service.categoryExists(userId, categoryName, "expense")) {

            JOptionPane.showMessageDialog(this, "Category already exists.");
            return;
        }

        // Create Category object
        Category category = new Category();

        category.setUserId(userId);
        category.setName(categoryName);
        category.setType("expense");

        // Save category
        if (service.addCategory(category)) {
            JOptionPane.showMessageDialog(this, "Category added successfully.");

            // Reload ComboBox
            loadExpenseCategories();

            // Automatically select the newly added category
            for (int i = 0; i < cmboExpenseCategory.getItemCount(); i++) {
                Category c = cmboExpenseCategory.getItemAt(i);

                if (c.getName().equalsIgnoreCase(categoryName)) {
                    cmboExpenseCategory.setSelectedIndex(i);
                    break;
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add category.");
        }
    }//GEN-LAST:event_btnAddExpenseCategory1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addExpenseButton;
    private javax.swing.JButton btnAddExpenseCategory1;
    private javax.swing.JButton btnDeleteExpenseCategory;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton clearExpenseButton;
    private javax.swing.JComboBox<Category> cmboExpenseCategory;
    private javax.swing.JButton deleteExpenseButton;
    private javax.swing.JTextField expenseAmountField;
    private javax.swing.JTextArea expenseDescriptionTextArea;
    private javax.swing.JTable expenseTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPaneExpense;
    private javax.swing.JLabel lblTotalExpense;
    private javax.swing.JButton updateExpenseButton;
    // End of variables declaration//GEN-END:variables
}
