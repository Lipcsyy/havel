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

        // Set the color for the item
        g.setColor(Color.pink);

        //the item
        g.fillRect(0, 0, 40,40);

    }
    public void Render(JPanel inventoryConsole) {
        inventoryConsole.add(this);
    }

}
