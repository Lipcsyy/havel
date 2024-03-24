package Room;
import Item.*;
import Player.*;
import java.util.*;
import Logger.*;
import static Logger.Logger.callDepth;

public class Room {
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
        this.players = new ArrayList<>();
    }



    //----------ITEM FUNCTIONS------------------------
    public void AddItem(Item item) {
        Logger.logEntry(this.getClass().getName(), "AddItem", "item");
        items.add(item);
        Logger.logExit(this.getClass().getName(), "AddItem");
    }

    public void RemoveItem(Item item) {
        Logger.logEntry(this.getClass().getName(), "RemoveItem", "item");
        items.remove(item);
        Logger.logExit(this.getClass().getName(), "RemoveItem");
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
        Logger.logEntry(this.getClass().getName(), "AddPlayer", "player");

        players.add(player);

        Logger.logExit(this.getClass().getName(), "AddPlayer");
    }

    public void RemovePlayer(Player player){
        Logger.logEntry(this.getClass().getName(), "RemovePlayer", "player");

        players.remove(player);

        Logger.logExit(this.getClass().getName(), "RemovePlayer");
    }


    //-------------TURNS LEFT FOR EFFECT FUNCTIONS--------------
    public int GetTurnsLeftForEffect() {
        Logger.logEntry(this.getClass().getName(), "GetTurnsLeftForEffect", "");
        Logger.logExit(this.getClass().getName(), "GetTurnsLeftForEffect", String.valueOf(turnsLeftForEffect));
        return turnsLeftForEffect;
    }

    public void SetTurnsLeftForEffect(int turnsLeftForEffect) {
        Logger.logEntry(this.getClass().getName(), "SetTurnsLeftForEffect", String.valueOf(turnsLeftForEffect));
        this.turnsLeftForEffect = turnsLeftForEffect;

        System.out.println(players.size());

        for ( Player player : this.players ) {
            player.Freeze(turnsLeftForEffect);
        }
        Logger.logExit(this.getClass().getName(), "SetTurnsLeftForEffect");
    }

    //-------------MOVING AND DOOR FUNCTIONS--------------
    public void Enter(Player player){

        Logger.logEntry(this.getClass().getName(), "Enter", "player");

        //Adding the player if there is more space in the room
        if (HasMoreSpaceInRoom()) {
            player.Move(this);
        } else {
            Logger.logExit(this.getClass().getName(), "Enter");
            return;
        }

        if (!items.isEmpty()) {
            player.CollectItem(items.get(0));
        }

        //making the players interact with each-other
        for (Player playerInRoom : players) {

            if (playerInRoom == player) {
                continue;
            }

            player.Interact(playerInRoom);
            playerInRoom.Interact(player);
        }

        Logger.logExit(this.getClass().getName(), "Enter");
    }

    public boolean HasMoreSpaceInRoom(){
        Logger.logEntry(this.getClass().getName(), "HasMoreSpaceInRoom", "");
        Logger.logExit(this.getClass().getName(), "HasMoreSpaceInRoom", players.size() < capacity ? "true" : "false");
        return players.size() < capacity;
    }

    public void ManageDoors() {

    }

}
