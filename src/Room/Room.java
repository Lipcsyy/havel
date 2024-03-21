package Room;
import Item.*;
import Player.*;
import java.util.*;

public abstract class Room {
    protected int capacity;
    protected List<Item> items;
    protected List<Player> players;
    protected List<Room> neighbours;
    protected int turnsLeftForEffect;

    //-----------CONSTRUCTOR--------------------------
    public Room(int capacity, List<Item> items, List<Room> neighbours){
        this.capacity = capacity;
        this. items = items;
        this.neighbours = neighbours;
    }


    //----------ITEM FUNCTIONS------------------------
    public void AddItem(Item item) {
        items.add(item);
    }

    public void RemoveItem(Item item) {
        items.remove(item);
    }

    //-----------------ROOM FUNCTIONS----------------

    public List<Room> GetNeighbours(){
        return neighbours;
    }

    public void SetNeighbours(Room neighbour){
        this.neighbours.add(neighbour);
    }

    //-------------PLAYER FUNCTIONS--------------
    public void AddPlayer(Player player) {
        players.add(player);
    }

    public void RemovePlayer(Player player){
        players.remove(player);
    }


    //-------------TURNS LEFT FOR EFFECT FUNCTIONS--------------
    public int GetTurnsLeftForEffect() {
        return turnsLeftForEffect;
    }

    public void SetTurnsLeftForEffect(int turnsLeftForEffect) {
        this.turnsLeftForEffect = turnsLeftForEffect;
    }

    //-------------MOVING AND DOOR FUNCTIONS--------------
    public void Enter(Player player){

        //Adding the player if there is more space in the room
        if (HasMoreSpaceInRoom()) {
            AddPlayer(player);
            player.Move(this);
        } else {
            return;
        }

        if ( items.isEmpty() == false ) {
            for ( Item item : items ) {
                player.CollectItem(item);
            }

        }

        //making the players interact with eachother
        for (Player playerInRoom : players) {

            if (playerInRoom == player) {
                continue;
            }

            player.Interact(playerInRoom);
            playerInRoom.Interact(player);
        }
    }

    public boolean HasMoreSpaceInRoom(){
        return players.size() < capacity;
    }

    public abstract void ManageDoors();

}
