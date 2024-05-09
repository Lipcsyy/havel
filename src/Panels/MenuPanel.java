package Panels;

import Buttons.MenuButton;
import Map.GameMap;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    private final JButton singlePlayer = new MenuButton("Single Player");
    private final JButton multiPlayer = new MenuButton("Multiplayer");
    private final JButton loadGameButton = new MenuButton("Load Game");
    private final JButton exitButton = new MenuButton("Exit");

    public MenuPanel() {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int SCREEN_WIDTH = (int) screenSize.getWidth();
        int SCREEN_HEIGHT = (int) screenSize.getHeight();
        this.setPreferredSize( new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT) );
        this.setFocusable(true);

        JPanel topPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIcon = new ImageIcon("SlideRuleGame.jpg");
                Image image = imageIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        topPanel.setPreferredSize(new Dimension((int)Math.round(SCREEN_WIDTH * 0.8), (int)Math.round(SCREEN_HEIGHT* 0.5)));
        this.setFocusable(true);

        setBackground(Color.GRAY);

        SetButtons();

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS)); // Set layout to BoxLayout
        buttonPanel.setBackground(Color.GRAY);
        buttonPanel.add(Box.createHorizontalGlue()); // Add horizontal glue before buttons
        buttonPanel.add(singlePlayer);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Add space between buttons
        buttonPanel.add(multiPlayer);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Add space between buttons
        buttonPanel.add(loadGameButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Add space between buttons
        buttonPanel.add(exitButton);
        buttonPanel.add(Box.createHorizontalGlue()); // Add horizontal glue after buttons

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Set layout to BoxLayout
        add(Box.createVerticalGlue()); // Add vertical glue before topPanel
        add(topPanel);
        add(Box.createVerticalGlue()); // Add vertical glue between topPanel and buttonPanel
        add(buttonPanel);
        add(Box.createVerticalGlue()); // Add vertical glue after buttonPanel
    }

    private void SetButtons() {

        singlePlayer.setAlignmentX(Component.CENTER_ALIGNMENT); // Align button to center
        multiPlayer.setAlignmentX(Component.CENTER_ALIGNMENT); // Align button to center
        loadGameButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Align button to center
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Align button to center

        singlePlayer.addActionListener(event -> {

            // make the maze and add it to the panel
            GameMap maze = new GameMap(20, 20);
            maze.generateMaze();
            GameFrame.singleGamePanel.SetMazeDisplay(maze);

            // set the previous panel and set focus
            GameFrame.previousPanel = GameFrame.singleGamePanel;

            GameFrame.layout.show(GameFrame.mainPanel, "SINGLE" );
            GameFrame.singleGamePanel.requestFocusInWindow(); // Request focus on the "SINGLE" panel
        });

        multiPlayer.addActionListener(event -> {

            // make the maze and add it to the panel
            GameMap maze = new GameMap(20, 20);
            maze.generateMaze();
            GameFrame.multiGamePanel.SetMazeDisplay(maze);

            // set the previous panel and set focus
            GameFrame.previousPanel = GameFrame.multiGamePanel;

            GameFrame.layout.show(GameFrame.mainPanel, "MULTI" );
            GameFrame.multiGamePanel.requestFocusInWindow(); // Request focus on the "MULTI" panel
        });

        loadGameButton.addActionListener( event -> {
            GameFrame.layout.show(GameFrame.mainPanel, "LOADER" );
        });

        exitButton.addActionListener( event -> System.exit(0));
    }
}