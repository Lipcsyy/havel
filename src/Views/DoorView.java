package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class DoorView extends JButton {

    public DoorView() {
        setBackground(new Color(60, 0, 0, 30));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(139, 69, 19)); // Brown color
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    public void AddClickListener(ActionListener listener) {
        this.addActionListener(listener);
    }

    public void RemoveClickListener(ActionListener listener) {
        this.removeActionListener(listener);
    }

    public void Render() {
        paintComponent(getGraphics());
    }
}
