package Panels;

import Views.ItemView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class InventoryConsole extends JPanel {

    transient private BufferedImage image;
    public InventoryConsole(int width, int height){
        setMaximumSize( new Dimension(380, 84) );
        setPreferredSize( new Dimension(380, 84));
        setMinimumSize( new Dimension(380, 84) );

        this.setFocusable(true);
        try {
            image = ImageIO.read(new File("./src/Images/inventory.png"));
        } catch( Exception e){
            e.printStackTrace();
        }

        setLayout(new GridLayout(1, 5, 10, 10));

        Render();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, 380, 84, this);
        }
    }

    public void Render(){
        revalidate();
        repaint();
    }

}
