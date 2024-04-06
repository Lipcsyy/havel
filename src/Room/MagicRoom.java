package Room;

import Item.Item;
import Room.*;

import java.util.List;
import java.util.Random;
import Logger.Logger;

public class MagicRoom extends Room {
    public MagicRoom(int capacity, List<Item> items, List<Room> neighbours) {
        super(capacity, items, neighbours);
    }

    @Override
    public void ManageDoors(Room room, boolean makeDoorDisappearOrAppear) {

        Logger.logEntry(this.getClass().getName(), "ManageDoors", makeDoorDisappearOrAppear ? "true" : "false");

        //Disappear
        if ( makeDoorDisappearOrAppear ) {
            //Remove a random neighbour
            Room removedRoom = this.neighbours.remove(new Random().nextInt(this.neighbours.size()));
            removedRoom.neighbours.remove(this);
        }
        else //Make two rooms neighbours
        {
            this.SetNeighbours(room);
        }

        Logger.logExit(this.getClass().getName(), "ManageDoors");

    }
}
