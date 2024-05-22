package Panels;

import Buttons.MenuButton;
import Enums.EGameMode;

import javax.swing.*;
import java.awt.*;

public class PausePanel extends JPanel {
    private final MenuButton mainMenuButton = new MenuButton("Return to main menu");
    private final MenuButton restartButton = new MenuButton("Restart the game");
    private final MenuButton cancelButton = new MenuButton("Cancel");

    public PausePanel() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int SCREEN_WIDTH = (int) screenSize.getWidth();
        int SCREEN_HEIGHT = (int) screenSize.getHeight();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));

        // Set the background color to half transparent
        setBackground(new Color(0,0,0,128));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Set layout to BoxLayout

        setButtons();

        add(Box.createVerticalGlue()); // Add vertical glue before buttons
        add(restartButton);
        add(Box.createRigidArea(new Dimension(0, 10))); // Add space between buttons
        add(mainMenuButton);
        add(Box.createRigidArea(new Dimension(0, 10))); // Add space between buttons
        add(cancelButton);
        add(Box.createVerticalGlue()); // Add vertical glue after buttons
    }

    private void setButtons() {
        mainMenuButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Align button to center
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Align button to center
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Align button to center

        mainMenuButton.addActionListener(event -> {
            GameFrame.layout.show(GameFrame.mainPanel, "MENU");
        });

        restartButton.addActionListener(event -> {
            if(GameFrame.previousPanel == GameFrame.singleGamePanel){
                GameFrame.layout.show(GameFrame.mainPanel, "SINGLE" );
                GameFrame.singleGamePanel.requestFocusInWindow();
                GameFrame.singleGamePanel.InitializeGame(EGameMode.SINGLEPLAYER);
            }
            else {
                GameFrame.layout.show(GameFrame.mainPanel, "MULTI" );
                GameFrame.multiGamePanel.requestFocusInWindow();
                GameFrame.multiGamePanel.InitializeGame(EGameMode.MULTIPLAYER);
            }
        });

        cancelButton.addActionListener(event -> {
            if(GameFrame.previousPanel == GameFrame.singleGamePanel){
                GameFrame.layout.show(GameFrame.mainPanel, "SINGLE" );
                GameFrame.singleGamePanel.requestFocusInWindow();
            }
            else {
                GameFrame.layout.show(GameFrame.mainPanel, "MULTI" );
                GameFrame.singleGamePanel.requestFocusInWindow();
            }
        });
    }

    // Override paintComponent to draw the background
    @Override
    protected void paintComponent(Graphics g) {
        GameFrame.previousPanel.paint(g.create());
        super.paintComponent(g);
        // Draw the previous panel, assuming it's stored in GameFrame.previousPanel
    }
}