package Panels;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

import Map.GameMap;
import Room.Room;

public class MazeDisplay extends JPanel {
    private final GameMap mazeGenerator;

    public MazeDisplay(GameMap mazeGenerator) {
        this.mazeGenerator = mazeGenerator;
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

        for (Room Room : adjacencyList.keySet()) {
            int RoomX = Room.x * RoomSize;
            int RoomY = Room.y * RoomSize;

            // Determine if there are walls based on connectivity in the adjacency list
            boolean hasNorth = adjacencyList.get(Room).stream().anyMatch(c -> c.y == Room.y - 1);
            boolean hasSouth = adjacencyList.get(Room).stream().anyMatch(c -> c.y == Room.y + 1);
            boolean hasEast = adjacencyList.get(Room).stream().anyMatch(c -> c.x == Room.x + 1);
            boolean hasWest = adjacencyList.get(Room).stream().anyMatch(c -> c.x == Room.x - 1);

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
        }
    }

    public static void createAndShowGUI(GameMap mazeGenerator) {
        JFrame frame = new JFrame("Maze");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new MazeDisplay(mazeGenerator));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        GameMap maze = new GameMap(20, 20); // Adjust size as needed
        maze.generateMaze();

        createAndShowGUI(maze);
    }
}