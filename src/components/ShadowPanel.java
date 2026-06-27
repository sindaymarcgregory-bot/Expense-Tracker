package components;

import java.awt.*;
import javax.swing.JPanel;

public class ShadowPanel extends JPanel {
   public ShadowPanel() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2=(Graphics2D)g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(0,0,0,30));

        g2.fillRoundRect(6,6,getWidth()-6,getHeight()-6,30,30);

        g2.setColor(getBackground());

        g2.fillRoundRect(0,0,getWidth()-6,getHeight()-6,30,30);

        g2.dispose();

        super.paintComponent(g);

    }

}
