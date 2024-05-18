package Views;

import javax.swing.*;
import java.awt.*;

public class ItemView extends JPanel {

    public ItemView() {
        //setPreferredSize( new Dimension(20, 20) );
        this.setFocusable(true);
        //setBackground(Color.BLUE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Set the color for the doors
        g.setColor(Color.LIGHT_GRAY);

        // Door dimensions
        //int doorWidth = 20;
        //int doorHeight = 20;

        // Top door (centered horizontally)
        g.fillRect(0, 0, getParent().getWidth(), getParent().getHeight()/4);

    }

    public void Render(JPanel gamePanel) {
        gamePanel.add(this);
    }
}
