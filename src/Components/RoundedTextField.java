package Components;

import java.awt.*;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class RoundedTextField extends JTextField {
    public RoundedTextField() {

        setOpaque(false);

        setBorder(new EmptyBorder(8,12,8,12));

    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2=(Graphics2D)g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.WHITE);

        g2.fillRoundRect(0,0,getWidth(),getHeight(),20,20);

        super.paintComponent(g);

        g2.dispose();

    }
}
