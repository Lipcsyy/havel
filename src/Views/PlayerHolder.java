package Views;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PlayerHolder extends JPanel {

    public PlayerHolder(PlayerView studentView, ArrayList<PlayerView> playerViews, int roomWidth, int roomHeight){

        setPreferredSize(new Dimension(roomWidth - 200, roomHeight / 3));
        setBackground(Color.GRAY);
        setFocusable(true);
        setLayout(new GridLayout(1, 5));

        add(studentView);
        for(PlayerView playerView: playerViews){
            add(playerView);
        }
    }
}
