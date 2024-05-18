package Panels;
import javax.swing.*;
import java.awt.*;
import java.util.*;

import Controller.GameController;
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
        int roomSize = 300 / mazeGenerator.getWidth(); // Calculate Room size based on maze width and panel size

        // Set background color for the whole panel
        g.setColor(Color.green);
        g.fillRect(0, 0, getWidth(), getHeight());

        for (Room room : adjacencyList.keySet()) {
            int roomX = room.x * roomSize;
            int roomY = room.y * roomSize;

            // Draw room background
            g.setColor(Color.white);
            g.fillRect(roomX, roomY, roomSize, roomSize);

            // Draw walls for the room
            g.setColor(Color.black);
            if (!adjacencyList.get(room).contains(findCell(room.x, room.y - 1))) { // Top wall
                g.drawLine(roomX, roomY, roomX + roomSize, roomY);
            }
            if (!adjacencyList.get(room).contains(findCell(room.x, room.y + 1))) { // Bottom wall
                g.drawLine(roomX, roomY + roomSize, roomX + roomSize, roomY + roomSize);
            }
            if (!adjacencyList.get(room).contains(findCell(room.x - 1, room.y))) { // Left wall
                g.drawLine(roomX, roomY, roomX, roomY + roomSize);
            }
            if (!adjacencyList.get(room).contains(findCell(room.x + 1, room.y))) { // Right wall
                g.drawLine(roomX + roomSize, roomY, roomX + roomSize, roomY + roomSize);
            }

            // Draw players in the room
            int playerIndicatorSize = 10;
            for (Student student : gameController.studentToViews.keySet()) {
                if (student.GetRoom() == room) {
                    g.setColor(Color.RED);
                    int playerX = roomX + (roomSize - playerIndicatorSize) / 2;
                    int playerY = roomY + (roomSize - playerIndicatorSize) / 2;
                    g.fillOval(playerX, playerY, playerIndicatorSize, playerIndicatorSize);
                }
            }
        }
    }

    private Room findCell(int x, int y) {
        for (Room cell : mazeGenerator.getAdjacencyList().keySet()) {
            if (cell.x == x && cell.y == y) {
                return cell;
            }
        }
        return null;
    }

}