package Views;

import javax.swing.*;
import java.awt.*;

public class ItemView extends JPanel {

    public ItemView() {
        setPreferredSize( new Dimension(20, 20) );
        this.setFocusable(true);
        setBackground(Color.BLUE);
    }

    public void Render(JPanel gamePanel) {
        gamePanel.add(this);
    }
}
