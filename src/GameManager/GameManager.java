package GameManager;
import Commander.CommandInterpreter;
import Logger.Logger;
import Room.*;
import Item.*;

import java.security.Key;
import java.util.*;
import Player.*;

public class GameManager implements java.io.Serializable{

    private Map<Room, Set<Room>> map;
    private ArrayList<Player> players;

    public GameManager() {
       map = new HashMap<Room, Set<Room>>();
       players = new ArrayList<Player>();
    }

    public ArrayList<Room> getRooms(){
        ArrayList<Room> allRooms = new ArrayList<Room >();
        allRooms.addAll( map.keySet() );
        return allRooms;
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }

    public ArrayList<Item> getItems(){
        ArrayList<Item> allItems = new ArrayList< Item >();
        ArrayList<Room> allRooms = getRooms();
        for(Room r: allRooms){

        }
        ArrayList<Player> allPlayers = getPlayers();

        return allItems;
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
        }

        //We need to set the newRoom as the neighbour of the target room's (the room we want to delete) neighbours.

        //we get here all the neighbours of the room we want to delete
        var neighboursOfTarget = targetRoom.GetNeighbours();

        //we add the new room as new neighbour for the room's neighbours that will be deleted
        for (Room r : neighboursOfTarget) {
            r.SetNeighbours(newRoom);
        }

        //we remove from the rooms neighbour list the room that will be deleted soon
        for (Room r : neighboursOfTarget) {
            r.RemoveNeighbour(targetRoom);
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

    public void AddPlayer( Player player){
        Logger.logEntry(this.getClass().getName(), "AddPlayer", "player");
        players.add( player );
        Logger.logExit(this.getClass().getName(), "AddPlayer");
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

    public Room GetRoomById(String id) {
        for (Room r : map.keySet()) {
            if (r.id.equals(id)) {
                return r;
            }
        }
        return null;
    }

    public Player GetPlayerById(String id) {
        for (Room r : map.keySet()) {
            for (Player p : r.GetPlayers()) {
                if (p.id.equals(id)) {
                    return p;
                }
            }
        }
        return null;
    }

    //-----------INFORMATION FUNCTIONS----------------

    public void PrintInfo() {

        for ( Room r : map.keySet() ) {
            r.PrintInfo();
        }

        for ( Player p : players ) {
            p.PrintInfo();
        }

    }

}
