package Panels;

import Buttons.MenuButton;
import Enums.EGameMode;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    private final JButton singlePlayer = new MenuButton("Single Player");
    private final JButton multiPlayer = new MenuButton("Multiplayer");
    private final JButton loadGameButton = new MenuButton("Load Game");
    private final JButton exitButton = new MenuButton("Exit");
    private Image backgroundImage;

    public MenuPanel() {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int SCREEN_WIDTH = (int) screenSize.getWidth();
        int SCREEN_HEIGHT = (int) screenSize.getHeight();
        this.setPreferredSize( new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT) );
        this.setFocusable(true);

        backgroundImage = new ImageIcon("background.jpg").getImage();

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

        //setBackground(Color.GRAY);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS)); // Set layout to BoxLayout
        //buttonPanel.setBackground(Color.GRAY);
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

        SetButtons();
    }

    private void SetButtons() {

        singlePlayer.setAlignmentX(Component.CENTER_ALIGNMENT); // Align button to center
        multiPlayer.setAlignmentX(Component.CENTER_ALIGNMENT); // Align button to center
        loadGameButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Align button to center
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Align button to center

        singlePlayer.addActionListener(event -> {

            System.out.println("Single Player ACTION LISTENER");

            // set the previous panel and set focus and start the game
            GameFrame.previousPanel = GameFrame.singleGamePanel;
            GameFrame.layout.show(GameFrame.mainPanel, "SINGLE" );
            GameFrame.singleGamePanel.requestFocusInWindow(); // Request focus on the "SINGLE" panel
            System.out.println("Single Player");
            GameFrame.singleGamePanel.InitializeGame(EGameMode.SINGLEPLAYER);
        });

        multiPlayer.addActionListener(event -> {

            System.out.println("Multi Player ACTION LISTENER");

            // set the previous panel and set focus and start the game
            GameFrame.previousPanel = GameFrame.multiGamePanel;
            GameFrame.layout.show(GameFrame.mainPanel, "MULTI" );
            GameFrame.multiGamePanel.requestFocusInWindow(); // Request focus on the "MULTI" panel
            GameFrame.multiGamePanel.InitializeGame(EGameMode.MULTIPLAYER);
        });

        loadGameButton.addActionListener( event -> {
            GameFrame.layout.show(GameFrame.mainPanel, "LOADER" );
            //GameFrame.loaderPanel.makeModel();  // Load the saved games
        });

        exitButton.addActionListener( event -> System.exit(0));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}