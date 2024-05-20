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

    public void Render(){
        System.out.println("itemholder vagyok");
        setSize(10, 10);
        this.setFocusable(true);
        setBackground(Color.BLUE);
        repaint();
    }

}
