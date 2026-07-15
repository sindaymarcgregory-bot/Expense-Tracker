package GUI;

import DAO.TransactionDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.XChartPanel;
import javax.swing.table.DefaultTableModel;
import utils.Session;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;
import java.util.Random;
import org.knowm.xchart.style.Styler;

public class DashBoardPanelDark extends javax.swing.JPanel {

    public DashBoardPanelDark() {
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
            // Get data and user info
            TransactionDAO dao = new TransactionDAO();
            int userId = Session.getCurrentUser().getId();

            // Set expanded chart size
            PieChart chart = new PieChartBuilder()
                    .width(490)
                    .height(390)
                    .build();

            // Get category data from database
            LinkedHashMap<String, Double> categories = dao.getExpenseCategoryPercentages(userId);

            // Add each category to the chart
            for (Map.Entry<String, Double> entry : categories.entrySet()) {
                chart.addSeries(
                        entry.getKey() + " (" + String.format("%.1f%%", entry.getValue()) + ")",
                        entry.getValue()
                );
            }

            // Generate different random colors for each category
            List<Color> colorList = new ArrayList<>();
            Random rand = new Random();
            int totalCategories = categories.size();

            // Create a unique bright color for every category
            for (int i = 0; i < totalCategories; i++) {
                int r = 50 + rand.nextInt(180);
                int g = 50 + rand.nextInt(180);
                int b = 50 + rand.nextInt(180);
                colorList.add(new Color(r, g, b));
            }

            // Apply the generated colors to the chart
            chart.getStyler().setSeriesColors(colorList.toArray(new Color[0]));

            // Chart appearance settings
            chart.getStyler().setChartTitleVisible(false);
            chart.getStyler().setLegendVisible(true);
            chart.getStyler().setPlotBorderVisible(false);
            chart.getStyler().setAnnotationDistance(0.85);
            chart.getStyler().setPlotContentSize(0.92);
            chart.getStyler().setLabelsVisible(true);
            chart.getStyler().setCircular(true);

            // Set background color
            Color bgColor = new Color(51, 51, 51);
            chart.getStyler().setChartBackgroundColor(bgColor);
            chart.getStyler().setPlotBackgroundColor(bgColor);
            chart.getStyler().setLegendBackgroundColor(bgColor);
            chart.getStyler().setChartFontColor(Color.WHITE);
            chart.getStyler().setLegendBorderColor(bgColor);

            // Show chart on your panel
            XChartPanel<PieChart> xChartPanel = new XChartPanel<>(chart);
            xChartPanel.setBackground(bgColor);

            chartPanel.removeAll();
            chartPanel.setLayout(new java.awt.BorderLayout());
            chartPanel.add(xChartPanel, java.awt.BorderLayout.CENTER);

            chartPanel.revalidate();
            chartPanel.repaint();

        } catch (Exception e) {
            System.out.println("Error loading pie chart: " + e.getMessage());
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

            List<Object[]> data
                    = transactionDAO.getExpenseCategorySummary(userId);

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
        pnlIncome.setBackground(new Color(70, 70, 70));

        // Remove default black border
        pnlIncome.setBorder(null);

        // Change title text
        lblIncomeTitle.setText(
                "💰 Total Income"
        );
        lblIncomeTitle.setForeground(Color.WHITE);

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
        pnlExpense.setBackground(new Color(70, 70, 70));

        // Remove border
        pnlExpense.setBorder(null);

        // Change title
        lblExpenseTitle.setText(
                "🛒 Total Expense"
        );
        lblExpenseTitle.setForeground(Color.WHITE);

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
        pnlBalance.setBackground(new Color(70, 70, 70));

        // Remove border
        pnlBalance.setBorder(null);

        // Change title
        lblBalanceTitle.setText(
                "💳 Current Balance"
        );
        lblBalanceTitle.setForeground(Color.WHITE);

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
                        new Color(90,90,90),
                        2,
                        true
                )
        );

        pnlExpense.setBorder(
                new LineBorder(
                        new Color(90,90,90),
                        2,
                        true
                )
        );

        pnlBalance.setBorder(
                new LineBorder(
                        new Color(90,90,90),
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

        setBackground(new java.awt.Color(51, 51, 51));
        setEnabled(false);
        setPreferredSize(new java.awt.Dimension(866, 626));

        chartPanel.setEnabled(false);

        javax.swing.GroupLayout chartPanelLayout = new javax.swing.GroupLayout(chartPanel);
        chartPanel.setLayout(chartPanelLayout);
        chartPanelLayout.setHorizontalGroup(
            chartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 422, Short.MAX_VALUE)
        );
        chartPanelLayout.setVerticalGroup(
            chartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 334, Short.MAX_VALUE)
        );

        summaryPanel.setBackground(new java.awt.Color(51, 51, 51));
        summaryPanel.setEnabled(false);

        pnlIncome.setBackground(new java.awt.Color(153, 153, 153));

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
                .addGap(15, 15, 15)
                .addComponent(lblIncomeTitle)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlIncomeLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblIncomeValue, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
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

        pnlExpense.setBackground(new java.awt.Color(153, 153, 153));

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
                .addContainerGap(8, Short.MAX_VALUE))
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

        pnlBalance.setBackground(new java.awt.Color(153, 153, 153));

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
                .addContainerGap())
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
                .addContainerGap()
                .addGroup(summaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(pnlExpense, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlIncome, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlBalance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(58, Short.MAX_VALUE))
        );
        summaryPanelLayout.setVerticalGroup(
            summaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(summaryPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(pnlIncome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlExpense, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlBalance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jScrollPane1.setBackground(new java.awt.Color(153, 153, 153));

        expenseCategoryTable.setBackground(new java.awt.Color(153, 153, 153));
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
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 810, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(chartPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(summaryPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(252, 252, 252)
                        .addComponent(jLabel1)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(summaryPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chartPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56))
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
