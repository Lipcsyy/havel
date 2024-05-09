package Buttons;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class MenuButton extends JButton {
    private Color bgColor = new Color(63, 114, 155); // Example color: You can change this to any color you like
    private boolean hover = false;

    public MenuButton(String text) {
        super(text);
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setForeground(Color.WHITE); // Example: Text color
        setFont(new Font("Arial", Font.BOLD, 14)); // Example: Font

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hover = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hover = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        Color startColor;
        Color endColor;
        if (hover) {
            startColor = bgColor.darker();
            endColor = bgColor;
        } else {
            startColor = bgColor;
            endColor = bgColor.darker();
        }

        GradientPaint gradientPaint = new GradientPaint(0, 0, startColor, 0, height, endColor);

        g2.setPaint(gradientPaint);
        g2.fill(new RoundRectangle2D.Double(0, 0, width, height, 30, 30));

        super.paintComponent(g2);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        // No border needed
    }
}
