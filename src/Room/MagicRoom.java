package Room;

import Item.Item;
import Room.*;

import java.util.List;

public class MagicRoom extends Room {
    public MagicRoom(int capacity, List<Item> items, List<Room> neighbours) {
        super(capacity, items, neighbours);
    }

    @Override
    public void ManageDoors(){

    }
}
