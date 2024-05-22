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

        setMaximumSize(new Dimension(400, 148));
        setMinimumSize(new Dimension(400, 148));
        //setBackground(Color.RED);

        setLayout(new GridLayout(0, capacity, 0, 0));

        add(_studentView);

        for (PlayerView playerView : _playerViews) {
            add(playerView);
        }

        this.setOpaque(false);
    }

    public PlayerHolder(){

    }

    public void Render() {
        if ( studentView != null ) {
            studentView.Render();
        }

        revalidate();
        repaint();
    }
}
