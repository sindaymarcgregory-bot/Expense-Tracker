package GUI;

import DAO.TransactionDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.math.BigDecimal;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.Styler;
import model.Transaction;
import java.util.ArrayList;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import utils.Session;
import utils.ThemeManager;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;

public class DashBoardPanel extends javax.swing.JPanel {

    
    public DashBoardPanel() {
        initComponents();

        // Display the pie chart.
        loadPieChart();

        // Display the dashboard cards.
        loadSummaryCards();

        // Apply card design first
        styleSummaryCards();

        // loading the table
        loadCategoryTable(); 
      

        
    }

    // Refresh all dashboard information.
    public void refreshDashboard() {

        loadPieChart();

        loadSummaryCards();

    }

    /**
     * Loads the Income vs Expense pie chart.
     */
    /**
     * Displays the Income vs Expense pie chart.
     */
    // Set the names of the table columns
    

     private void loadPieChart() {

        try {

            // Create a DAO object.
            TransactionDAO dao = new TransactionDAO();

            // Get the logged in user's ID.
            int userId = Session.getCurrentUser().getId();

            PieChart chart = new PieChartBuilder()
                    .width(420)
                    .height(320)
                    .build();
            // Read totals from the database.
            LinkedHashMap<String, Double> categories
                    = dao.getExpenseCategoryPercentages(userId);

          
            for (Map.Entry<String, Double> entry : categories.entrySet()) {
                chart.addSeries(
                        entry.getKey() + " (" + String.format("%.1f%%", entry.getValue()) + ")",
                        entry.getValue()
                );
            }

            // Customize the appearance
            chart.getStyler().setChartTitleVisible(false);

            chart.getStyler().setLegendVisible(true);

            chart.getStyler().setPlotBorderVisible(false);

            chart.getStyler().setAnnotationDistance(1.15);
            chart.getStyler().setPlotContentSize(.9);

            chart.getStyler().setChartBackgroundColor(
                    new Color(220, 232, 208));

            chart.getStyler().setPlotBackgroundColor(
                    new Color(220, 232, 208));

            // Create the Swing component.
            XChartPanel<PieChart> xChartPanel
                    = new XChartPanel<>(chart);

            xChartPanel.setBackground(new Color(220, 232, 208));

            chartPanel.removeAll();

            chartPanel.setLayout(new BorderLayout());

            chartPanel.add(xChartPanel, BorderLayout.CENTER);

            chartPanel.revalidate();

            chartPanel.repaint();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    /**
     * Loads the values displayed inside the dashboard summary cards.
     */
    private void loadSummaryCards() {

        try {

            // Create DAO object.
            TransactionDAO dao = new TransactionDAO();

            // Get the current user's ID.
            int userId = Session.getCurrentUser().getId();

            // Retrieve totals from the database.
            BigDecimal income = dao.getTotalByType(userId, "income");

            BigDecimal expense = dao.getTotalByType(userId, "expense");

            BigDecimal balance = dao.getCurrentBalance(userId);

            // Format numbers with commas and 2 decimal places
            lblIncomeValue.setText(
                    "₱" + String.format("%,.2f", income)
            );

            lblExpenseValue.setText(
                    "₱" + String.format("%,.2f", expense)
            );

            lblBalanceValue.setText(
                    "₱" + String.format("%,.2f", balance)
            );

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    // Load recent transactions into the dashboard table

    private void loadCategoryTable() {

    TransactionDAO transactionDAO = new TransactionDAO();

    int userId = Session.getCurrentUser().getId();   // <-- Add this

    DefaultTableModel model
            = (DefaultTableModel) expenseCategoryTable.getModel();

    model.setRowCount(0);

    try {

        List<Object[]> data =
                transactionDAO.getExpenseCategorySummary(userId);

        for (Object[] row : data) {

            model.addRow(new Object[]{
                row[0],
                String.format("₱%,.2f", row[1]),
                String.format("%.1f%%", row[2]),
                row[3]
            });
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}


    /*
    This method controls the appearance
    of the dashboard summary cards.
     */
    private void styleSummaryCards() {

        // Income Card Styling
        // Light green background
        pnlIncome.setBackground(
                new Color(220, 245, 220)
        );

        // Remove default black border
        pnlIncome.setBorder(null);

        // Change title text
        lblIncomeTitle.setText(
                "💰 Total Income"
        );

        // Center the amount
        lblIncomeValue.setHorizontalAlignment(
                javax.swing.SwingConstants.CENTER
        );

        // Green text for income
        lblIncomeValue.setForeground(
                new Color(0, 150, 70)
        );

        // Expense Card Styling
        // Light red background
        pnlExpense.setBackground(
                new Color(255, 225, 225)
        );

        // Remove border
        pnlExpense.setBorder(null);

        // Change title
        lblExpenseTitle.setText(
                "🛒 Total Expense"
        );

        // Center amount
        lblExpenseValue.setHorizontalAlignment(
                javax.swing.SwingConstants.CENTER
        );

        // Red text for expenses
        lblExpenseValue.setForeground(
                new Color(220, 50, 50)
        );

        // Balance Card Styling
        // Light blue background
        pnlBalance.setBackground(
                new Color(220, 235, 255)
        );

        // Remove border
        pnlBalance.setBorder(null);

        // Change title
        lblBalanceTitle.setText(
                "💳 Current Balance"
        );

        // Center amount
        lblBalanceValue.setHorizontalAlignment(
                javax.swing.SwingConstants.CENTER
        );

        // Blue text for balance
        lblBalanceValue.setForeground(
                new Color(0, 120, 220)
        );
        // Makes the cards look rounded
        pnlIncome.setBorder(
                new LineBorder(
                        new Color(220, 245, 220),
                        2,
                        true
                )
        );

        pnlExpense.setBorder(
                new LineBorder(
                        new Color(255, 225, 225),
                        2,
                        true
                )
        );

        pnlBalance.setBorder(
                new LineBorder(
                        new Color(220, 235, 255),
                        2,
                        true
                )
        );

        // Adds spacing inside the cards
        pnlIncome.setBorder(
                new EmptyBorder(15, 15, 15, 15)
        );
    }


    /*
    Converts the normal dashboard panels
    into rounded styled cards.
     */
    private void makeCardsRounded() {

        // Change the appearance of the income card
        pnlIncome.setOpaque(false);

        // Change the appearance of the expense card
        pnlExpense.setOpaque(false);

        // Change the appearance of the balance card
        pnlBalance.setOpaque(false);

        // Remove the old borders
        pnlIncome.setBorder(null);
        pnlExpense.setBorder(null);
        pnlBalance.setBorder(null);

        // Add rounded backgrounds
        pnlIncome = new RoundedPanel(
                new Color(220, 245, 220)
        );

        pnlExpense = new RoundedPanel(
                new Color(255, 225, 225)
        );

        pnlBalance = new RoundedPanel(
                new Color(220, 235, 255)
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

        chartPanel = new javax.swing.JPanel();
        summaryPanel = new javax.swing.JPanel();
        pnlIncome = new javax.swing.JPanel();
        lblIncomeTitle = new javax.swing.JLabel();
        lblIncomeValue = new javax.swing.JLabel();
        pnlExpense = new javax.swing.JPanel();
        lblExpenseTitle = new javax.swing.JLabel();
        lblExpenseValue = new javax.swing.JLabel();
        pnlBalance = new javax.swing.JPanel();
        lblBalanceTitle = new javax.swing.JLabel();
        lblBalanceValue = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        expenseCategoryTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(220, 232, 208));
        setEnabled(false);
        setPreferredSize(new java.awt.Dimension(866, 626));

        chartPanel.setEnabled(false);

        javax.swing.GroupLayout chartPanelLayout = new javax.swing.GroupLayout(chartPanel);
        chartPanel.setLayout(chartPanelLayout);
        chartPanelLayout.setHorizontalGroup(
            chartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 420, Short.MAX_VALUE)
        );
        chartPanelLayout.setVerticalGroup(
            chartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 322, Short.MAX_VALUE)
        );

        summaryPanel.setBackground(new java.awt.Color(220, 232, 208));
        summaryPanel.setEnabled(false);

        lblIncomeTitle.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblIncomeTitle.setForeground(new java.awt.Color(102, 102, 102));
        lblIncomeTitle.setText("Total Income");

        lblIncomeValue.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        lblIncomeValue.setForeground(new java.awt.Color(0, 204, 51));
        lblIncomeValue.setText("0.00");

        javax.swing.GroupLayout pnlIncomeLayout = new javax.swing.GroupLayout(pnlIncome);
        pnlIncome.setLayout(pnlIncomeLayout);
        pnlIncomeLayout.setHorizontalGroup(
            pnlIncomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlIncomeLayout.createSequentialGroup()
                .addGroup(pnlIncomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlIncomeLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(lblIncomeTitle))
                    .addGroup(pnlIncomeLayout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addComponent(lblIncomeValue, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlIncomeLayout.setVerticalGroup(
            pnlIncomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlIncomeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblIncomeTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblIncomeValue)
                .addGap(16, 16, 16))
        );

        lblExpenseTitle.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblExpenseTitle.setForeground(new java.awt.Color(102, 102, 102));
        lblExpenseTitle.setText("Total Expense");

        lblExpenseValue.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        lblExpenseValue.setForeground(new java.awt.Color(255, 0, 51));
        lblExpenseValue.setText("0.00");

        javax.swing.GroupLayout pnlExpenseLayout = new javax.swing.GroupLayout(pnlExpense);
        pnlExpense.setLayout(pnlExpenseLayout);
        pnlExpenseLayout.setHorizontalGroup(
            pnlExpenseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlExpenseLayout.createSequentialGroup()
                .addGroup(pnlExpenseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlExpenseLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(lblExpenseTitle))
                    .addGroup(pnlExpenseLayout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(lblExpenseValue, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        pnlExpenseLayout.setVerticalGroup(
            pnlExpenseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlExpenseLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblExpenseTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblExpenseValue)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        lblBalanceTitle.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblBalanceTitle.setForeground(new java.awt.Color(102, 102, 102));
        lblBalanceTitle.setText("Current Balance");

        lblBalanceValue.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        lblBalanceValue.setForeground(new java.awt.Color(0, 153, 255));
        lblBalanceValue.setText("0.00");

        javax.swing.GroupLayout pnlBalanceLayout = new javax.swing.GroupLayout(pnlBalance);
        pnlBalance.setLayout(pnlBalanceLayout);
        pnlBalanceLayout.setHorizontalGroup(
            pnlBalanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBalanceLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lblBalanceTitle)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBalanceLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblBalanceValue, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        pnlBalanceLayout.setVerticalGroup(
            pnlBalanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBalanceLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblBalanceTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(lblBalanceValue)
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout summaryPanelLayout = new javax.swing.GroupLayout(summaryPanel);
        summaryPanel.setLayout(summaryPanelLayout);
        summaryPanelLayout.setHorizontalGroup(
            summaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(summaryPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(summaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlIncome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlExpense, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlBalance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(9, Short.MAX_VALUE))
        );
        summaryPanelLayout.setVerticalGroup(
            summaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(summaryPanelLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(pnlIncome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlExpense, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlBalance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        expenseCategoryTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Category", "Amount", "Percentage", "Transactions"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        expenseCategoryTable.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
                expenseCategoryTableAncestorMoved(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jScrollPane1.setViewportView(expenseCategoryTable);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(111, 151, 143));
        jLabel1.setText("Dashboard");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(111, 151, 143));
        jLabel2.setText("Expense Overview");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(356, 356, 356))
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 810, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chartPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(summaryPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(summaryPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chartPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(124, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void expenseCategoryTableAncestorMoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_expenseCategoryTableAncestorMoved

    }//GEN-LAST:event_expenseCategoryTableAncestorMoved


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel chartPanel;
    private javax.swing.JTable expenseCategoryTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBalanceTitle;
    private javax.swing.JLabel lblBalanceValue;
    private javax.swing.JLabel lblExpenseTitle;
    private javax.swing.JLabel lblExpenseValue;
    private javax.swing.JLabel lblIncomeTitle;
    private javax.swing.JLabel lblIncomeValue;
    private javax.swing.JPanel pnlBalance;
    private javax.swing.JPanel pnlExpense;
    private javax.swing.JPanel pnlIncome;
    private javax.swing.JPanel summaryPanel;
    // End of variables declaration//GEN-END:variables
}

