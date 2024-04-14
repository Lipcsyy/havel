package GameManager;
import Logger.Logger;
import Room.*;
import java.util.*;
import Player.*;

public class GameManager {

    private List<Room> map;

    public GameManager() {
        map = new ArrayList<Room>() ;
    }

    public List<Room> GetMap() {
        return map;
    }

    public void ChangeRoomToNormalInList( Room targetRoom )
    {

        Logger.logEntry(this.getClass().getName(), "ChangeRoomToNormalInList", "targetRoom");

        //Copying the room we want to change -> we make it into a normal room no matter what
        Room newRoom = new Room(targetRoom, this);

        //We need remove the players from the target room and add them to the new room
        Iterator<Player> it = targetRoom.GetPlayers().iterator();
        while (it.hasNext()) {

            Player p = it.next();

            // Remove player from the current room
            it.remove();
            p.SetRoom(newRoom);

            // Move items to the new room
            p.GetInventory().forEach((item) -> {
                item.SetRoom(newRoom);
            });

            System.out.println("HELO2");
        }

        //We need to set the newRoom as the neighbour of the target room's (the room we want to delete) neighbours.

        var neighboursOfTarget = targetRoom.GetNeighbours();
        for (Room r : neighboursOfTarget) {
            r.RemoveNeighbour(targetRoom);
        }

        for (Room r : neighboursOfTarget) {
            r.SetNeighbours(newRoom);
        }

        //There are no references left for the targetRoom, so it should be deleted
        map.remove(targetRoom);

        Logger.logExit(this.getClass().getName(), "ChangeRoomToNormalInList");

    }

    public void AddRoom( Room room ) {
        Logger.logEntry(this.getClass().getName(), "AddRoom", "room");
        map.add(room);
        Logger.logExit(this.getClass().getName(), "AddRoom");
    }

    public void MakeRoomsNeighbours(Room room1, Room room2) {
        Logger.logEntry(this.getClass().getName(), "MakeRoomsNeighbours", "room1, room2");
        room1.SetNeighbours(room2);
        Logger.logExit(this.getClass().getName(), "MakeRoomsNeighbours");
    }

}
