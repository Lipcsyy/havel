package Panels;

import Views.DoorView;

import javax.swing.*;
import java.awt.*;

public class GameConsole extends JPanel {

    //public GridBagConstraints gbc = new GridBagConstraints();

    public GameConsole(int width, int height){
        setPreferredSize( new Dimension(width, height) );
        this.setFocusable(true);
        setBackground(Color.yellow);
        //setLayout(new GridBagLayout());
        setLayout(new BorderLayout());

    }
}
