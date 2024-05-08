package Panels;

import javax.swing.*;
import java.awt.*;

public class GameConsole extends JPanel {

    public GameConsole(int width, int height){
        setPreferredSize( new Dimension(width, height) );
        this.setFocusable(true);
        setBackground(Color.WHITE);
    }
}
