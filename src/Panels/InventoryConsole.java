package Panels;

import javax.swing.*;
import java.awt.*;

public class InventoryConsole extends JPanel {

    public InventoryConsole(int width, int height){
        setPreferredSize( new Dimension(width, height) );
        this.setFocusable(true);
        try {
            image = ImageIO.read(new File("./src/Images/inventory.png"));
        } catch( Exception e){
            e.printStackTrace();
        }
        //setBackground(Color.BLACK);
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
