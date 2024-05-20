package Views;

import Enums.EPlayers;
import javax.imageio.*;
import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PlayerView extends JPanel {

    transient private BufferedImage image;

    public PlayerView(EPlayers ePlayers) {

        try {
            if (ePlayers == EPlayers.STUDENT) {
                image = ImageIO.read(new File("./src/Images/student.png"));
            } else if ( ePlayers == EPlayers.TEACHER){
                image = ImageIO.read(new File("./src/Images/teacher.png"));
            } else if ( ePlayers == EPlayers.CLEANER){
                image = ImageIO.read(new File("./src/Images/cleaner.png"));
            }
        } catch( Exception e){

        }


        this.setFocusable(true);
        setBackground(Color.RED);
    }

    public void Render() {

        JLabel picLabel = new JLabel(new ImageIcon(image));
        picLabel.setPreferredSize(new Dimension(64, 128) );
        this.setPreferredSize( new Dimension(64, 128) );
        this.setSize(64,128);
        add(picLabel);

        System.out.println("Rendering this shit");
        this.setFocusable(true);
        System.out.println("Rendering this bitch");
        repaint();
    }
}
