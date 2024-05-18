package Views;

import javax.swing.*;
import java.awt.*;

public class DoorView extends JPanel {

    //int direction = 0;
    public DoorView() {
        setBackground(new Color(60, 0, 0, 30));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Set the color for the doors
        g.setColor(new Color(139, 69, 19)); // Brown color

        // Fill the door area
        g.fillRect(0, 0, getWidth(), getHeight());
    }

}
