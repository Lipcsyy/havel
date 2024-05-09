package Panels;

import javax.swing.*;
import java.awt.*;

public class LoaderPanel extends JPanel {
    private final JButton mainMenuButton = new JButton("Return to main menu");
    public LoaderPanel(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int SCREEN_WIDTH = (int) screenSize.getWidth();
        int SCREEN_HEIGHT = (int) screenSize.getHeight();
        this.setPreferredSize( new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT) );
        this.setFocusable(true);
        setBackground(Color.GRAY);

        SetButtons();

        add(mainMenuButton);
    }

    private void SetButtons() {
        mainMenuButton.addActionListener( event -> {
            GameFrame.layout.show(GameFrame.mainPanel, "MENU" );
        });
    }
}
