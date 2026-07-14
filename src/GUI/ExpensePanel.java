/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import java.math.BigDecimal;
import java.time.LocalDate;
import model.Category;
import services.CategoryService;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Transaction;
import services.TransactionService;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.table.DefaultTableCellRenderer;
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
    private DashBoardPanel dashboard;

    public ExpensePanel(DashBoardPanel dashboard) {
        initComponents();
        this.dashboard = dashboard;
    }

    public ExpensePanel() {

        initComponents();

        this.dashboard = dashboard;

        loadExpenseCategories();

        loadExpenseTable();

        styleExpenseTable();

    }

    private void loadExpenseTable() {
        DefaultTableModel model = (DefaultTableModel) expenseTable.getModel();
        model.setRowCount(0);
        TransactionService transactionService = new TransactionService();
        CategoryService categoryService = new CategoryService();
        int userId = Session.getCurrentUser().getId();

        List<Transaction> transactions = transactionService.getTransactionsByType(userId, "expense");

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
    // Style the expense table

    private void styleExpenseTable() {

        expenseTable.getColumnModel()
                .getColumn(0)
                .setMinWidth(0);

        expenseTable.getColumnModel()
                .getColumn(0)
                .setMaxWidth(0);

        expenseTable.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(0);

        expenseTable.setFont(
                new Font("Segoe UI", Font.PLAIN, 13)
        );

        expenseTable.getTableHeader()
                .setFont(
                        new Font("Segoe UI", Font.BOLD, 14)
                );

        expenseTable.setRowHeight(35);

        expenseTable.setShowGrid(false);

        expenseTable.setIntercellSpacing(
                new Dimension(0, 0)
        );

        expenseTable.setBackground(
                new Color(220, 232, 208)
        );

        expenseTable.setSelectionBackground(
                new Color(111, 151, 143)
        );

        expenseTable.setSelectionForeground(
                Color.WHITE
        );

        jScrollPane1.setBorder(null);

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

        DefaultTableCellRenderer expenseRenderer
                = new DefaultTableCellRenderer();

        expenseRenderer.setForeground(
                new Color(220, 70, 70)
        );

        expenseTable.getColumnModel()
                .getColumn(1)
                .setCellRenderer(expenseRenderer);

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
        expenseTable.getColumnModel()
                .getColumn(3)
                .setCellRenderer(centerRenderer);

// Center the description column
        expenseTable.getColumnModel()
                .getColumn(4)
                .setCellRenderer(centerRenderer);

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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
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
        jScrollPane1 = new javax.swing.JScrollPane();
        expenseTable = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        expenseDescriptionTextArea = new javax.swing.JTextArea();
        updateExpenseButton = new javax.swing.JButton();
        clearExpenseButton = new javax.swing.JButton();
        deleteExpenseButton = new javax.swing.JButton();
        addExpenseButton = new javax.swing.JButton();
        btnAddExpenseCategory = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(220, 232, 208));
        jPanel1.setMaximumSize(null);
        jPanel1.setPreferredSize(new java.awt.Dimension(1020, 604));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(111, 151, 143));
        jLabel2.setText("Category");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(111, 151, 143));
        jLabel1.setText("Amount");

        jScrollPane1.setBackground(new java.awt.Color(220, 232, 208));
        jScrollPane1.setEnabled(false);

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
        expenseTable.setEnabled(false);
        expenseTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                expenseTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(expenseTable);

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

        btnAddExpenseCategory.setBackground(new java.awt.Color(111, 151, 143));
        btnAddExpenseCategory.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnAddExpenseCategory.setForeground(new java.awt.Color(242, 242, 242));
        btnAddExpenseCategory.setText("+");
        btnAddExpenseCategory.addActionListener(this::btnAddExpenseCategoryActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 497, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(expenseAmountField, javax.swing.GroupLayout.PREFERRED_SIZE, 497, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cmboExpenseCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 446, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddExpenseCategory)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
                    .addComponent(addExpenseButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(updateExpenseButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(clearExpenseButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(deleteExpenseButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(201, 201, 201))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmboExpenseCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAddExpenseCategory))
                        .addGap(34, 34, 34)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(expenseAmountField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(addExpenseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(updateExpenseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(deleteExpenseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(clearExpenseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 883, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 620, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddExpenseCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddExpenseCategoryActionPerformed
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
    }//GEN-LAST:event_btnAddExpenseCategoryActionPerformed

    private void addExpenseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addExpenseButtonActionPerformed
        try {
            Category category = (Category) cmboExpenseCategory.getSelectedItem();

            if (category == null) {
                JOptionPane.showMessageDialog(this, "Please select a category.");
                return;
            }

            String amountText = expenseAmountField.getText().trim();

            if (amountText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter an amount.");
                return;
            }

            BigDecimal amount = new BigDecimal(amountText);

            String description = expenseDescriptionTextArea.getText().trim();

            Transaction transaction = new Transaction();

            transaction.setUserId(Session.getCurrentUser().getId());
            transaction.setCategoryId(category.getId());
            transaction.setType("expense");
            transaction.setAmount(amount);
            transaction.setDescription(description);

            // Automatically use today's date
            transaction.setTransactionDate(java.sql.Date.valueOf(LocalDate.now()));

            TransactionService service = new TransactionService();

            if (service.addTransaction(transaction)) {
                //dashboard.loadPieChart();

                JOptionPane.showMessageDialog(this, "Expense added successfully!");

                // Refresh the JTable
                loadExpenseTable();

                // Clear the form
                expenseAmountField.setText("");
                expenseDescriptionTextArea.setText("");

                if (cmboExpenseCategory.getItemCount() > 0) {
                    cmboExpenseCategory.setSelectedIndex(0);
                }

                // Reset selection
                expenseTable.clearSelection();
                selectedTransactionId = -1;
            } else {
                JOptionPane.showMessageDialog(this, "Unable to save expense.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount.");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while saving the expense.");
        }
    }//GEN-LAST:event_addExpenseButtonActionPerformed

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

            loadExpenseTable();

            expenseAmountField.setText("");
            expenseDescriptionTextArea.setText("");
            cmboExpenseCategory.setSelectedIndex(0);

            selectedTransactionId = -1;
        } else {
            JOptionPane.showMessageDialog(this, "Unable to delete.");
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

    private void updateExpenseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateExpenseButtonActionPerformed
        if (selectedTransactionId == -1) {

            JOptionPane.showMessageDialog(this,
                    "Please select a transaction first.");

            return;
        }

        try {
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

                loadExpenseTable();

                expenseAmountField.setText("");
                expenseDescriptionTextArea.setText("");
                cmboExpenseCategory.setSelectedIndex(0);

                selectedTransactionId = -1;
            } else {
                JOptionPane.showMessageDialog(this, "Update failed.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid amount.");
        }
    }//GEN-LAST:event_updateExpenseButtonActionPerformed

    private void expenseTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_expenseTableMouseClicked
        int row = expenseTable.getSelectedRow();
        if (row == -1) {
            return;
        }

        // Save the selected transaction ID
        selectedTransactionId = Integer.parseInt(expenseTable.getValueAt(row, 0).toString());

        // Fill the form
        expenseAmountField.setText(expenseTable.getValueAt(row, 1).toString());

        String categoryName = expenseTable.getValueAt(row, 2).toString();

        for (int i = 0; i < cmboExpenseCategory.getItemCount(); i++) {
            Category category = cmboExpenseCategory.getItemAt(i);
            if (category.getName().equals(categoryName)) {
                cmboExpenseCategory.setSelectedIndex(i);
                break;
            }
        }
        expenseDescriptionTextArea.setText(expenseTable.getValueAt(row, 3).toString());
    }//GEN-LAST:event_expenseTableMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addExpenseButton;
    private javax.swing.JButton btnAddExpenseCategory;
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton updateExpenseButton;
    // End of variables declaration//GEN-END:variables
}
