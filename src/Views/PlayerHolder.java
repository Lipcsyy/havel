package Views;
import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class PlayerHolder extends JPanel {

    PlayerView studentView;

    ArrayList<PlayerView> playerViews = new ArrayList<>();


    public PlayerHolder(PlayerView _studentView, ArrayList<PlayerView> _playerViews, int roomWidth, int roomHeight, int capacity) {
        studentView = _studentView;
        playerViews = _playerViews;

        int holderWidth = roomWidth - 100;  // Width of the PlayerHolder
        int holderHeight = roomHeight - 150; // Height of the PlayerHolder

        // Calculate the position to center the PlayerHolder
        int x = (roomWidth - holderWidth) / 2;
        int y = (roomHeight - holderHeight) / 2;

        setBounds(x, y, holderWidth, holderHeight);
        setPreferredSize(new Dimension(holderWidth, holderHeight));
        setBackground(Color.RED);

        System.out.println(capacity);

        setLayout(new GridLayout(0, capacity, 10, 10));

        add(_studentView);
        for (PlayerView playerView : _playerViews) {
            add(playerView);
        }
    }

    public void Render() {

        studentView.Render();

        revalidate();
        repaint();
    }

}
