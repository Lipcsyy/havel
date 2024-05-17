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

        this.setLayout(null); // Set the layout manager to null for absolute positioning

        for (Room room : adjacencyList.keySet()) {
            int roomX = room.x * roomSize;
            int roomY = room.y * roomSize;

            // We need to add a JPanel for each room
            JPanel roomPanel = new JPanel();
            roomPanel.setBounds(roomX, roomY, roomSize, roomSize);
            roomPanel.setBackground(Color.BLACK);
            roomPanel.setLayout(null); // Set layout manager to null for absolute positioning of the red dot

            ArrayList<JPanel> playerIndicators = new ArrayList<>();

            for (Student student : gameController.studentToViews.keySet()) {
                if (student.GetRoom() == room) {
                    JPanel playerPanel = gameController.studentToViews.get(student);

                    // Create a copy of the playerPanel
                    JPanel playerIndicator = new JPanel();
                    playerIndicator.setBackground(Color.RED);
                    int indicatorSize = 10;

                    playerIndicator.setSize(indicatorSize, indicatorSize);
                    playerIndicators.add(playerIndicator);
                }
            }

            int playerCount = playerIndicators.size();

            if (playerCount == 1) {
                JPanel playerIndicator = playerIndicators.get(0);
                int indicatorSize = playerIndicator.getWidth();
                int indicatorX = (roomSize - indicatorSize) / 2;
                int indicatorY = (roomSize - indicatorSize) / 2;
                playerIndicator.setBounds(indicatorX, indicatorY, indicatorSize, indicatorSize);
                roomPanel.add(playerIndicator);
            } else if (playerCount > 1) {
                int totalWidth = playerCount * 10 + (playerCount - 1) * 5;
                int startX = (roomSize - totalWidth) / 2;

                for (int i = 0; i < playerCount; i++) {
                    JPanel playerIndicator = playerIndicators.get(i);
                    int indicatorSize = playerIndicator.getWidth();
                    int indicatorX = startX + i * (indicatorSize + 5);
                    int indicatorY = (roomSize - indicatorSize) / 2;
                    playerIndicator.setBounds(indicatorX, indicatorY, indicatorSize, indicatorSize);
                    roomPanel.add(playerIndicator);
                }
            }

            if (playerCount > 0) {
                roomPanel.setBackground(Color.WHITE);
            }

            this.add(roomPanel);
        }
    }

}