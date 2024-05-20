package Views;

import Enums.EPlayers;
import javax.imageio.*;
import java.io.File;
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
        this.setFocusable(true);
        System.out.println("Rendering this bitch");
        repaint();
    }

}
