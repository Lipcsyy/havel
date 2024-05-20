package Views;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ItemHolder extends JPanel {

    public ItemHolder(ArrayList<ItemView> itemViews, int roomWidth, int roomHeight){
        setPreferredSize(new Dimension(roomWidth - 400, roomHeight / 5));
        setBackground(Color.GRAY);
        setFocusable(true);
        setLayout(new GridLayout(1, 5));

        for(ItemView item: itemViews){
            add(item);
        }
    }
}
