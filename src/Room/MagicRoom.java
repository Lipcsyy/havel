package Room;

import GameManager.GameManager;
import Item.Item;
import Room.*;

import java.util.List;
import java.util.Random;
import java.util.Set;

import Logger.Logger;

public class MagicRoom extends Room {
    public MagicRoom(int capacity, List<Item> items, List<Room> neighbours, GameManager gameManager) {
        super(capacity, items, neighbours, gameManager);
    }

    @Override
    public void ManageDoors(Room room, boolean makeDoorDisappearOrAppear) {

        Logger.logEntry(this.getClass().getName(), "ManageDoors", makeDoorDisappearOrAppear ? "true" : "false");

        //Disappear
        if ( makeDoorDisappearOrAppear ) {
            //Remove a random neighbour

            Set<Room> neighboursOfRoom = gameManager.GetNeighbours(this);

            if(neighboursOfRoom.size() == 0)
                return;

            gameManager.DisconnectRoomsTwoWay(this, neighboursOfRoom.stream().toList().get(new Random().nextInt(neighboursOfRoom.size())));

        }
        else //Make two rooms neighbours
        {
            this.SetNeighbours(room);
        }

        Logger.logExit(this.getClass().getName(), "ManageDoors");

    }

    @Override
    public void CleanRoom() {
        Logger.logEntry(this.getClass().getName(), "CleanRoom", "");
        this.turnsLeftForEffect = 0;
        Logger.logExit(this.getClass().getName(), "CleanRoom" );
    }
}
