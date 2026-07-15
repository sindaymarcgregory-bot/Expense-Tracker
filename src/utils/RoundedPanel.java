package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

/*
    This custom JPanel creates rounded corners.
    We use this for modern-looking dashboard cards.
*/
public class RoundedPanel extends JPanel {

    // Controls how rounded the corners are
    private int radius = 25;

    // Stores the card background color
    private Color backgroundColor;


    /*
        Constructor:
        Receives the color that the card will use.
    */
    public RoundedPanel(Color color) {

        backgroundColor = color;

        // Disable default panel painting
        setOpaque(false);
    }


    /*
        This method draws the rounded rectangle.
        It replaces the normal square JPanel background.
    */
    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;


        // Makes the rounded corners smoother
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );


        // Set card color
        g2.setColor(backgroundColor);


        // Draw rounded rectangle
        g2.fillRoundRect(
                0,
                0,
                getWidth(),
                getHeight(),
                radius,
                radius
        );


        // Continue normal JPanel painting
        super.paintComponent(g);
    }
}