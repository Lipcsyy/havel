package Views;

import Enums.EItems;
import Player.Student;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class ItemView extends JButton {

    private EItems eItems;
    transient private BufferedImage image;
    public ItemView(EItems eItems) {

        this.eItems = eItems;
        try {
            switch (eItems) {
                case TVSZ:
                    image = ImageIO.read(new File("./src/Images/tvsz.png"));
                    break;
                case MASK:
                    image = ImageIO.read(new File("./src/Images/mask.png"));
                    break;
                case SLIDERULE:
                    image = ImageIO.read(new File("./src/Images/sliderule.png"));
                    break;
                case CAMEMBERT:
                    image = ImageIO.read(new File("./src/Images/camembert.png"));
                    break;
                case BEERGLASS:
                    image = ImageIO.read(new File("./src/Images/beerglass.png"));
                    break;
                case RAG:
                    image = ImageIO.read(new File("./src/Images/rag.png"));
                    break;
                case TRANSISTOR:
                    image = ImageIO.read(new File("./src/Images/transistor.png"));
                    break;
                case AIRFRESHENER:
                    image = ImageIO.read(new File("./src/Images/airfreshener.png"));
                    break;
            }
        } catch( Exception e){
            e.printStackTrace();
        }
        setPreferredSize( new Dimension(30, 30) );
        this.setFocusable(true);
        this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);

        //setBackground(Color.BLUE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 10, 10, 64, 64, this);
        }
    }

    public void AddClickListener(ActionListener listener) {
        this.addActionListener(listener);
    }
    public void RemoveClickListener( ActionListener listener ){ this.removeActionListener( listener ); }
    public void Render(){
        System.out.println("itemholder vagyok");
        setSize(new Dimension(64, 64));
        this.setFocusable(true);

        repaint();
    }

    public EItems geteItems(){
        return eItems;
    }

}
