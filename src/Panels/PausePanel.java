package Panels;

import javax.swing.*;
import java.awt.*;

public class PausePanel extends JPanel {
    private final JButton mainMenuButton = new JButton("Return to main menu");

    public PausePanel() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int SCREEN_WIDTH = (int) screenSize.getWidth();
        int SCREEN_HEIGHT = (int) screenSize.getHeight();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));

        // Set the background color to transparent red
        setBackground(new Color(0,0,0,128));
        // Make the panel not opaque
        //setOpaque(false);

        setButtons();

        add(mainMenuButton);
    }

    private void setButtons() {
        mainMenuButton.addActionListener(event -> {
            GameFrame.layout.show(GameFrame.mainPanel, "MENU");
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
