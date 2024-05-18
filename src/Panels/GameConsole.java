package Panels;

import Views.DoorView;
import Views.RoomView;

import javax.swing.*;
import java.awt.*;

public class GameConsole extends JPanel {

    int width;
    int height;
    public GameConsole(int width, int height){
        this.width = width;
        this.height = height;
        setPreferredSize( new Dimension(width, height) );
        this.setFocusable(true);
        setBackground(Color.white);
        setLayout(null);
    }

    public int getConsoleWidth(){
        return width;
    }
    public int getConsoleHeight(){
        return height;
    }


    public void addRoomView(RoomView roomView) {
        roomView.setBounds(0, 0, this.getWidth(), this.getHeight()); // Set the position and size of the RoomView within GameConsole
        add(roomView);
    }
}
