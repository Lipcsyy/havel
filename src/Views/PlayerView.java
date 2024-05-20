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

    public void Render() {
        System.out.println("Rendering this shit");
        setBounds(100, 100 / 2, 10, 10);
        setSize(10, 10);
        this.setFocusable(true);
        setBackground(Color.RED);
        paintComponent(getGraphics());
    }

}
