package GameManager;
import Logger.Logger;
import Room.*;
import java.util.*;
import Player.*;

public class GameManager {

    private Map<Room, Set<Room>> map;

    public GameManager() {
       map = new HashMap<Room, Set<Room>>() ;
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
        map.put(room, new HashSet<Room>());
        Logger.logExit(this.getClass().getName(), "AddRoom");
    }

    public void ConnectRoomsOneWay(Room room1, Room room2) {
        Set<Room> room1Neighbours = map.get(room1);
        room1Neighbours.add(room2);
    }

    public void ConnectRoomsTwoWay(Room room1, Room room2) {
        Set<Room> room1Neighbors = map.get(room1);
        Set<Room> room2Neighbors = map.get(room2);

        room1Neighbors.add(room2);
        room2Neighbors.add(room1);
    }

    public void DisconnectRoomsOneWay(Room room1, Room room2) {
        Set<Room> room1Neighbors = map.get(room1);
        if (room1Neighbors != null) {
            room1Neighbors.remove(room2);
        }
    }

    public void DisconnectRoomsTwoWay(Room room1, Room room2) {
        DisconnectRoomsOneWay(room1, room2);
        DisconnectRoomsOneWay(room2, room1);
    }

    public void EndGame() {

    }

    public Set<Room> GetNeighbours(Room room) {
        return map.get(room);
    }


}
