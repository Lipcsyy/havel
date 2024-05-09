package Panels;

import Controller.GameController;
import GameManager.GameManager;
import Map.GameMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    private ArrayList<GameConsole> gameConsoles = new ArrayList<GameConsole>();
    private ArrayList<InventoryConsole> inventoryConsoles = new ArrayList<InventoryConsole>();
    private MazeDisplay mazeDisplay;
    private JPanel mazeDisplayArea;
    private JPanel containerPanel;
    private GameController gameController;

    public GamePanel(){

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int SCREEN_WIDTH = (int) screenSize.getWidth();
        int SCREEN_HEIGHT = (int) screenSize.getHeight();
        this.setPreferredSize( new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT) );
        this.setFocusable(true); // Enable keyboard focus for the panel
        requestFocusInWindow(); // Request keyboard focus when the panel is shown
        setBackground(Color.GRAY);

        SetKeyListener();

        // Initialize containerPanel and set its preferred size to one third of the screen width
        containerPanel = new JPanel();
        containerPanel.setPreferredSize(new Dimension((int)Math.round((float)SCREEN_WIDTH * (2.0f / 3.0f)), SCREEN_HEIGHT));
        containerPanel.setBackground(Color.GRAY);

        // Initialize mazeDisplayArea and set its preferred size to one third of the screen width
        mazeDisplayArea = new JPanel();
        mazeDisplayArea.setPreferredSize(new Dimension((int)Math.round((float) SCREEN_WIDTH / 3), SCREEN_HEIGHT));
        mazeDisplayArea.setBackground(Color.GRAY);
        mazeDisplay = new MazeDisplay(new GameMap(10,10));
        mazeDisplayArea.add(mazeDisplay);

        // Set the layout of the GamePanel to BorderLayout
        setLayout(new BorderLayout());

        add(containerPanel, BorderLayout.WEST);
        add(mazeDisplayArea, BorderLayout.EAST);

        // Initialize the GameController

    }


    private void SetKeyListener() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Check if the pressed key is ESC (key code 27)
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    // Handle ESC key press here
                    GameFrame.layout.show(GameFrame.mainPanel, "PAUSE");
                }
            }
        });
    }

    public void AddGameConsole(GameConsole gameConsole){
        gameConsoles.add(gameConsole);
        containerPanel.add(gameConsoles.get(gameConsoles.size()-1)); // Add the console to containerPanel
    }

    public void AddInventoryConsole(InventoryConsole inventoryConsole){
        inventoryConsoles.add(inventoryConsole);
        containerPanel.add(inventoryConsoles.get(inventoryConsoles.size()-1)); // Add the console to containerPanel
    }

    public void SetMazeDisplay(GameMap maze){

        mazeDisplayArea.remove(mazeDisplay);
        mazeDisplay = new MazeDisplay(maze);
        mazeDisplayArea.add(mazeDisplay);

    }
}