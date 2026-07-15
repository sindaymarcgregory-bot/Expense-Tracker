package GUI;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;


/*
    Changes the appearance of the transaction type column.
    Income will be green and expense will be red.
*/
public class TypeBadgeRenderer extends DefaultTableCellRenderer {


    @Override
    public Component getTableCellRendererComponent(
            JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column) {


        JLabel label = (JLabel) super.getTableCellRendererComponent(
                table,
                value,
                isSelected,
                hasFocus,
                row,
                column
        );


        String type = value.toString();


        // Center the text
        label.setHorizontalAlignment(
                SwingConstants.CENTER
        );


        if (type.equalsIgnoreCase("income")) {

            label.setForeground(
                    new Color(0, 150, 70)
            );

            label.setText(
                    "Income"
            );


        } else if (type.equalsIgnoreCase("expense")) {

            label.setForeground(
                    new Color(220, 50, 50)
            );

            label.setText(
                    "Expense"
            );

        }


        return label;
    }
}