package Panels;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    private final JButton singlePlayer = new JButton("Single Player");
    private final JButton multiPlayer = new JButton("Multiplayer");
    private final JButton loadGameButton = new JButton("Load Game");
    private final JButton exitButton = new JButton("Exit");

    public MenuPanel() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int SCREEN_WIDTH = (int) screenSize.getWidth();
        int SCREEN_HEIGHT = (int) screenSize.getHeight();
        this.setPreferredSize( new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT) );
        this.setFocusable(true);

        SetButtons();

        add(singlePlayer);
        add(multiPlayer);
        add(loadGameButton);
        add(exitButton);
    }

    private void SetButtons() {

        singlePlayer.addActionListener(event -> {
            GameFrame.layout.show(GameFrame.mainPanel, "SINGLE" );
        });

        multiPlayer.addActionListener(event -> {
            GameFrame.layout.show(GameFrame.mainPanel, "MULTI" );
        });

        loadGameButton.addActionListener( event -> {
            GameFrame.layout.show(GameFrame.mainPanel, "LOADER" );
        });

        exitButton.addActionListener( event -> System.exit(0));
    }
}
