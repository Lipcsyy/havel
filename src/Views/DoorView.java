package Views;

import javax.swing.*;
import java.awt.*;

public class DoorView extends JPanel {

    int direction = 0;
    public GridBagConstraints gbc;
    public DoorView(int direction) {
        this.direction = direction;
        //this.gbc = gbc;
        if(direction == 0){
            this.setPreferredSize(new Dimension(10, 50));
//            gbc.gridx = 1;
//            gbc.gridy = 0;
//            gbc.gridwidth = 1;
//            gbc.gridheight = 1;
//            gbc.weightx = 1.0;
//            gbc.weighty = 0.0;
//            gbc.fill = GridBagConstraints.HORIZONTAL;
//            gbc.anchor = GridBagConstraints.NORTH;
        }
        else if(direction == 1) {
            this.setPreferredSize(new Dimension(50, 10));
        }
        else if(direction == 2){
            this.setPreferredSize(new Dimension(10, 50));
        }
        else if(direction == 3){
            this.setPreferredSize(new Dimension(50, 10));
        }
        setBackground(new Color(139, 69, 19));
    }

    /*
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Set the color for the doors
        g.setColor(new Color(139, 69, 19)); // Brown color

        // Door dimensions
        int doorWidth = 50;
        int doorHeight = 10;

        // Top door (centered horizontally)
        g.fillRect((this.getWidth() - doorWidth) / 2, 0, doorWidth, doorHeight);

        // Bottom door (centered horizontally)
        g.fillRect((this.getWidth() - doorWidth) / 2, this.getHeight() - doorHeight, doorWidth, doorHeight);

        // Left door (centered vertically)
        g.fillRect(0, (this.getHeight() - doorWidth) / 2, doorHeight, doorWidth);

        // Right door (centered vertically)
        g.fillRect(this.getWidth() - doorHeight, (this.getHeight() - doorWidth) / 2, doorHeight, doorWidth);
    }
     */
    public void Render(JPanel gamePanel) {
        //this.setPreferredSize();
        if(direction == 0){
            gamePanel.add(this, BorderLayout.NORTH);
        }
        else if(direction == 1) {
            gamePanel.add(this, BorderLayout.EAST);
        }
        else if(direction == 2){
            gamePanel.add(this, BorderLayout.SOUTH);
        }
        else if(direction == 3){
            gamePanel.add(this, BorderLayout.WEST);
        }

    }
}
