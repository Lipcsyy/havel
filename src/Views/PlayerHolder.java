package Views;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PlayerHolder extends JPanel {

    PlayerView studentView;
    ArrayList<PlayerView> playerViews = new ArrayList<>();

    public PlayerHolder(PlayerView _studentView, ArrayList<PlayerView> _playerViews, int holderWidth, int holderHeight, int capacity) {
        studentView = _studentView;
        playerViews = _playerViews;

        setPreferredSize(new Dimension(holderWidth - 200, holderHeight - 100));
        //setBackground(Color.RED);

        setLayout(new GridLayout(0, capacity, 0, 0));

        add(_studentView);

        for (PlayerView playerView : _playerViews) {
            add(playerView);
        }

        this.setOpaque(false);
    }

    public void Render() {
        studentView.Render();
        revalidate();
        repaint();
    }
}
