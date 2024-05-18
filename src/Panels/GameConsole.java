package Panels;

import Views.DoorView;
import Views.RoomView;

import javax.swing.*;
import java.awt.*;

public class GameConsole extends JPanel {

    public GameConsole(int width, int height){
        setPreferredSize( new Dimension(width, height) );
        this.setFocusable(true);
        setBackground(Color.white);
        setLayout(null);
    }

    public void addRoomView(RoomView roomView) {
        roomView.setBounds(0, 0, this.getWidth(), this.getHeight()); // Set the position and size of the RoomView within GameConsole
        add(roomView);
    }
}
