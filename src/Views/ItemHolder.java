package Views;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ItemHolder extends JPanel {

    ArrayList<ItemView> itemViews = new ArrayList<>();

    public ItemHolder(ArrayList<ItemView> _itemViews, int holderWidth, int holderHeight, int size) {

        if (_itemViews != null) {
            itemViews = _itemViews;
        }

        setPreferredSize(new Dimension(holderWidth - 200, holderHeight - 100));
        setOpaque(false);
        setLayout(new GridLayout(1, size, 10, 10));
    }

    public void Render() {

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
