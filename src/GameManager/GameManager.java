package GameManager;
import Logger.Logger;
import Room.*;
import java.util.*;

public class GameManager {

    public List<Room> map;

    public GameManager() {
        map = new ArrayList<Room>() ;
    }

    public void ChangeRoomToNormalInList( Room targetRoom )
    {

        Logger.logEntry(this.getClass().getName(), "ChangeRoomToNormalInList", "targetRoom");

        //Copying the room we want to change -> we make it into a normal room no matter what
        Room newRoom = new Room(targetRoom, this);

        //Now setting the room new room as the room of each player in the previous room
        targetRoom.GetPlayers().forEach((p) -> {

            System.out.println("HELO1");

            targetRoom.RemovePlayer(p);
            p.SetRoom(newRoom);

            p.GetInventory().forEach((item) -> {
                item.SetRoom(newRoom);
            });

            System.out.println("HELO2");

        });

        //We need to set the newRoom as the neighbour of the target room's (the room we want to delete) neighbours.
        System.out.println("SZOSZI");
        targetRoom.GetNeighbours().forEach((r) -> {
            r.RemoveNeighbour(targetRoom);
            r.SetNeighbours(newRoom);
        });

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
