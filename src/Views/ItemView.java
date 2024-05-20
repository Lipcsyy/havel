package Views;

import Player.Student;

import javax.swing.*;
import java.awt.*;

public class ItemView extends JPanel {

    public ItemView() {
        setPreferredSize( new Dimension(30, 30) );
        this.setFocusable(true);
        setBackground(Color.BLUE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Set the color for the item
        g.setColor(Color.pink);

        //the item
        g.fillRect(0, 0, 20,20);

    }
    public void Render(){
        paintComponent(getGraphics());
    }

}
