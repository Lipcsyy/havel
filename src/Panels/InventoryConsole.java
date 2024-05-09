package Panels;

import javax.swing.*;
import java.awt.*;

public class InventoryConsole extends JPanel {

    public InventoryConsole(int width, int height){
        setPreferredSize( new Dimension(width, height) );
        this.setFocusable(true);
        setBackground(Color.BLACK);
    }
}
