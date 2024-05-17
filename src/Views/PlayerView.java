package Views;

import Panels.GamePanel;

import javax.swing.*;
import java.awt.*;

public class PlayerView extends JPanel {
    public void Render(GamePanel gamePanel) {
        setPreferredSize( new Dimension(20, 20) );
        this.setFocusable(true);
        setBackground(Color.RED);
        gamePanel.AddPlayerView(this);
    }
}
