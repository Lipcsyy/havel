package Views;

import Panels.GamePanel;

import javax.swing.*;
import java.awt.*;

public class PlayerView extends JPanel {

    public PlayerView() {
        setPreferredSize( new Dimension(20, 20) );
        this.setFocusable(true);
        setBackground(Color.RED);
    }

    public void Render(JPanel gamePanel) {
        gamePanel.add(this, BorderLayout.CENTER);
    }

}
