package Panels;
import javax.security.auth.Refreshable;
import javax.swing.*;
import java.awt.*;
import java.util.*;

import Controller.GameController;
import Enums.EGameMode;
import Map.GameMap;
import Room.Room;
import Player.Student;

public class MazeDisplay extends JPanel {
    private final GameMap mazeGenerator;
    private final GameController gameController;

    public MazeDisplay(GameMap mazeGenerator, GameController gameController) {
        this.mazeGenerator = mazeGenerator;
        this.gameController = gameController;
        setPreferredSize(new Dimension(300, 300)); // Set the window size
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawMaze(g);
    }

    private void drawMaze(Graphics g) {
        HashMap<Room, Set<Room>> adjacencyList = mazeGenerator.getAdjacencyList();
        int RoomSize = 300 / mazeGenerator.getWidth(); // Calculate Room size based on maze width and panel size

        for (Room room : adjacencyList.keySet()) {
            int RoomX = room.x * RoomSize;
            int RoomY = room.y * RoomSize;

            // Determine if there are walls based on connectivity in the adjacency list
            boolean hasNorth = adjacencyList.get(room).stream().anyMatch(c -> c.y == room.y - 1);
            boolean hasSouth = adjacencyList.get(room).stream().anyMatch(c -> c.y == room.y + 1);
            boolean hasEast = adjacencyList.get(room).stream().anyMatch(c -> c.x == room.x + 1);
            boolean hasWest = adjacencyList.get(room).stream().anyMatch(c -> c.x == room.x - 1);

            if (!hasNorth) {
                g.drawLine(RoomX, RoomY, RoomX + RoomSize, RoomY);
            }
            if (!hasSouth) {
                g.drawLine(RoomX, RoomY + RoomSize, RoomX + RoomSize, RoomY + RoomSize);
            }
            if (!hasEast) {
                g.drawLine(RoomX + RoomSize, RoomY, RoomX + RoomSize, RoomY + RoomSize);
            }
            if (!hasWest) {
                g.drawLine(RoomX, RoomY, RoomX, RoomY + RoomSize);
            }

            boolean hasStudent = false;
            for (Student student : gameController.studentViews.keySet()){
                if(student.GetRoom() == room){
                    hasStudent = true;
                    break;
                }
            }
            if(hasStudent){
                // Draw a filled rectangle around the room
                g.setColor(Color.BLACK);
                g.fillRect(RoomX, RoomY, RoomSize, RoomSize);
            } else {
                g.setColor(Color.RED);
                int pointSize = 2; // You can adjust the size of the point as needed
                g.fillOval(RoomX, RoomY, pointSize, pointSize);
            }
        }
    }
}