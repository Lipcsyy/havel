package Panels;

import Buttons.MenuButton;

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
        topPanel.setPreferredSize(new Dimension((int)Math.round(SCREEN_WIDTH* 0.95), (int)Math.round(SCREEN_HEIGHT* 0.75)));
        this.setFocusable(true);

        add(topPanel);

        setBackground(Color.GRAY);

        SetButtons();

        add(singlePlayer);
        add(multiPlayer);
        add(loadGameButton);
        add(exitButton);
    }

    private void SetButtons() {

        singlePlayer.addActionListener(event -> {
            GameFrame.layout.show(GameFrame.mainPanel, "SINGLE" );
            GameFrame.previousPanel = GameFrame.singleGamePanel;
            GameFrame.singleGamePanel.requestFocusInWindow(); // Request focus on the "SINGLE" panel
        });

        multiPlayer.addActionListener(event -> {
            GameFrame.layout.show(GameFrame.mainPanel, "MULTI" );
            GameFrame.previousPanel = GameFrame.multiGamePanel;
            GameFrame.multiGamePanel.requestFocusInWindow(); // Request focus on the "SINGLE" panel
        });

        loadGameButton.addActionListener( event -> {
            GameFrame.layout.show(GameFrame.mainPanel, "LOADER" );
        });

        exitButton.addActionListener( event -> System.exit(0));
    }
}
