package Views;
import Interfaces.IObserver;

import javax.swing.*;
import java.awt.*;

public class RoomView extends JPanel {

    public RoomView() {
        this.setSize(300, 300);
        this.setBackground(Color.BLACK);
    }

    public void Render(JPanel gamePanel) {
        gamePanel.add(this);
    }
}
