package Views;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ItemHolder extends JPanel {

    ArrayList<ItemView> itemViews = new ArrayList<>();

    public ItemHolder(ArrayList<ItemView> _itemViews, int roomWidth, int roomHeight, int size){
        itemViews = _itemViews;

        int holderWidth = roomWidth - 100;  // Width of the ItemHolder
        int holderHeight = roomHeight - 400; // Height of the ItemHolder

        // Calculate the position to center the PlayerHolder
        int x = (roomWidth - holderWidth) / 2;
        int y = (int)((roomHeight - holderHeight) * (4.0 / 5.0));

        setBounds(x, y, holderWidth, holderHeight);
        setPreferredSize(new Dimension(holderWidth, holderHeight));
        setBackground(Color.GREEN);
        setLayout(new GridLayout(1, size, 10, 10)); // Absolute positioning

        System.out.println("Size:" + size);

        for (ItemView itemView : _itemViews) {
            add(itemView);
        }
    }

    public void Render() {

        for(ItemView itemView: itemViews){
            itemView.Render();
        }

        revalidate();
        repaint();
    }
}
