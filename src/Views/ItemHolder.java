package Views;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ItemHolder extends JPanel {

    ArrayList<ItemView> itemViews = new ArrayList<>();

    public ItemHolder( ArrayList<ItemView> _itemViews, int size) {

        if (_itemViews != null) {
            itemViews = _itemViews;
        }

        setOpaque(false);
        setLayout(new GridLayout(1, size, 10, 10));
    }

    public ItemHolder() {
        setOpaque(false);
        setLayout(new GridLayout(1, 5, 10, 10));
    }

    public void setItemViews(ArrayList<ItemView> _itemViews) {
        if (_itemViews != null) {
            itemViews = _itemViews;
        }
    }

    public void Render() {

        setMaximumSize(new Dimension(itemViews.size() * 84, 84));
        setPreferredSize(new Dimension( itemViews.size() * 84, 84 ));
        setMinimumSize(new Dimension(itemViews.size() * 84, 84));

        removeAll();

        // Add new components
        for (ItemView itemView : itemViews) {
            if (itemView != null) {
                add(itemView);
            }
        }

        revalidate();
        repaint();
    }
}
