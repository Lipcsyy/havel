package Views;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PlayerHolder extends JPanel {

    public PlayerHolder(ArrayList<PlayerView> playerViews, int roomWidth, int roomHeight){

        setPreferredSize(new Dimension(roomWidth - 200, roomHeight / 3));
        setBackground(Color.GRAY);
        setFocusable(true);
        setLayout(new GridLayout(1, 5));

        for(PlayerView item: playerViews){
            add(item);
        }
    }
}
