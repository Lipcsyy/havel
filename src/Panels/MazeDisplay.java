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

    public void Render() {
        paintComponent(getGraphics());
    }

    private void drawMaze(Graphics g) {
        HashMap<Room, Set<Room>> adjacencyList = mazeGenerator.getAdjacencyList();
        int roomSize = 300 / mazeGenerator.getWidth(); // Calculate Room size based on maze width and panel size

        // Set background color for the whole panel
        g.setColor(Color.green);
        g.fillRect(0, 0, getWidth(), getHeight());

        int lineThickness = 10;
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(lineThickness));

        for (Room room : adjacencyList.keySet()) {
            boolean studentInRoom = false;
            int roomX = room.x * roomSize;
            int roomY = room.y * roomSize;

            // Draw room background
            g2d.setColor(Color.white);
            g2d.fillRect(roomX, roomY, roomSize, roomSize);

            Set<Room> neighbors = adjacencyList.get(room);

            for (Student student : gameController.studentToViews.keySet()) {
                if (student.GetRoom() == room) {
                    studentInRoom = true;
                    Set<Room> rooomneighbors = adjacencyList.get(room);
                    for (Room neighbour : rooomneighbors) {
                    }
                }
            }

            // Draw walls for the room
            if((room.GetX() + room.GetY()) % 2 == 0)
                g2d.setColor(Color.black);
            else
                g2d.setColor(Color.blue);


            int a = lineThickness/2 + 4;
            if (! neighbors.contains(findCell(room.x, room.y - 1))) { // Top wall
                g2d.drawLine(roomX+a, roomY+a, roomX + roomSize-a, roomY+a);
                if(studentInRoom) {
                    //System.out.println("Top wall drawn");
                }
            }
            if (!neighbors.contains(findCell(room.x, room.y + 1))) { // Bottom wall
                g2d.drawLine(roomX+a, roomY + roomSize-a, roomX + roomSize-a, roomY + roomSize-a);
                if(studentInRoom) {
                    //System.out.println("Bottom wall drawn");
                }
            }
            if (!neighbors.contains(findCell(room.x - 1, room.y))) { // Left wall
                g2d.drawLine(roomX+a, roomY+a, roomX+a, roomY + roomSize-a);
                if(studentInRoom) {
                    //System.out.println("Left wall drawn");
                }
            }
            if (!neighbors.contains(findCell(room.x + 1, room.y))) { // Right wall
                g2d.drawLine(roomX + roomSize-a, roomY+a, roomX + roomSize-a, roomY + roomSize-a);
                if(studentInRoom) {
                    //System.out.println("Right wall drawn");
                }
            }

            // Draw players in the room
            int playerIndicatorSize = 10;
            for (Student student : gameController.studentToViews.keySet()) {
                if (student.GetRoom() == room) {
                    g2d.setColor(Color.RED);
                    int playerX = roomX + (roomSize - playerIndicatorSize) / 2;
                    int playerY = roomY + (roomSize - playerIndicatorSize) / 2;
                    g2d.fillOval(playerX, playerY, playerIndicatorSize, playerIndicatorSize);

//                    Set<Room> rooomneighbors = adjacencyList.get(room);
//                    System.out.println("\nneighbours size (from mazedisplay): " + rooomneighbors.size());
//                    for(Room neighbour : rooomneighbors) {
//                        System.out.println("(md) neighbour: " + neighbour.GetX() + " " + neighbour.GetY());
//                    }
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



    /*
    //masik feketitett mazedisplay
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
            roomPanel.setBackground(Color.green);
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

     */

}