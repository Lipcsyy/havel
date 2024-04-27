package Room;
import Item.*;
import Player.*;
import java.util.*;
import Logger.*;
import GameManager.*;

public class Room implements java.io.Serializable{


    GameManager gameManager;

    public String id;

    protected int capacity;
    protected List<Item> items;
    protected List<Player> players;
    protected List<Room> neighbours;
    protected int turnsLeftForEffect;

    protected int passagesBeforeStickiness = -1;

    static int idNumber = 1;
    //public int getIdNumber(){return idNumber;}
    //public void setIdNumber(int idValue){idNumber = idValue;}
    protected int idNumberCopy = 1;
    public void setIdNumberCopySer(){idNumberCopy = idNumber;}
    public void setIdNumberSer(){idNumber = idNumberCopy;}

    //-----------CONSTRUCTOR--------------------------

    /**
     * This is the constructor of the room class.
     * @param capacity The capacity of the room.
     * @param items The items in the room.
     * @param neighbours The neighbours of the room.
     */
    public Room(int capacity, List<Item> items, List<Room> neighbours, GameManager gameManager){

        this.capacity = capacity;
        this.items = items;
        this.players = new ArrayList<>();
        this.gameManager = gameManager;
        this.id = "Room_" + idNumber++;

        System.out.println("Adding the room to the gameManager");

        gameManager.AddRoom(this);
    }

    //Copy constructor
    public Room( Room room, GameManager gameManager ) {

        this.id = room.id;
        this.capacity = room.capacity;
        this.items = room.items;
        this.players = room.GetPlayers();
        this.gameManager = gameManager;

        gameManager.AddRoom(this);

    }

    //----------ITEM FUNCTIONS------------------------

    /**
     * This function adds an item to the room.
     * @param item The item to add.
     */
    public void AddItem(Item item) {
        Logger.logEntry(this.getClass().getName(), "AddItem", "item");
        items.add(item);
        Logger.logExit(this.getClass().getName(), "AddItem");
    }

    /**
     * This function removes an item from the room.
     * @param item The item to remove.
     */
    public void RemoveItem(Item item) {
        Logger.logEntry(this.getClass().getName(), "RemoveItem", "item");
        items.remove(item);
        Logger.logExit(this.getClass().getName(), "RemoveItem");
    }

    //-----------------ROOM FUNCTIONS----------------

    /**
     * This function returns the neighbours of the room.
     */
    public Set<Room> GetNeighbours(){
        Logger.logEntry(this.getClass().getName(), "GetNeighbours", "");
        StringBuilder neighboursString = new StringBuilder();

        Set<Room> neighbours = gameManager.GetNeighbours(this);

        for (Room neighbour : neighbours) {
            neighboursString.append(neighbour.getClass().getName()).append(" ");
        }
        Logger.logExit(this.getClass().getName(), "GetNeighbours", neighboursString.toString() );

        return neighbours;
    }


    /**
     * This function sets the neighbours of the room.
     * @param neighbour The neighbour to set.
     */
    public void SetNeighbours(Room neighbour){
        Logger.logEntry(this.getClass().getName(), "SetNeighbours", "neighbour");

        gameManager.ConnectRoomsTwoWay(this, neighbour);

        Logger.logExit(this.getClass().getName(), "SetNeighbours");
    }

    public void RemoveNeighbour( Room neighbour ) {
        Logger.logEntry(this.getClass().getName(), "RemoveNeighbour", "room");
        gameManager.DisconnectRoomsTwoWay(this, neighbour);
        Logger.logExit(this.getClass().getName(), "RemoveNeighbour");
    }

    public void CleanRoom() {
        Logger.logEntry(this.getClass().getName(), "CleanRoom", "");
        this.turnsLeftForEffect = 0;
        this.SetRoomNumberOfPassagesBeforeStickiness(5);
        Logger.logExit(this.getClass().getName(), "CleanRoom");
    }

    //-------------PLAYER FUNCTIONS--------------

    /**
     * This function adds a player to the room.
     * @param player The player to add.
     */
    public void AddPlayer(Player player) {
        Logger.logEntry(this.getClass().getName(), "AddPlayer", "player");

        players.add(player);

        Logger.logExit(this.getClass().getName(), "AddPlayer");
    }


    /**
     * This function removes a player from the room.
     * @param player The player to remove.
     */
    public void RemovePlayer(Player player){
        Logger.logEntry(this.getClass().getName(), "RemovePlayer", "player");

        players.remove(player);

        Logger.logExit(this.getClass().getName(), "RemovePlayer");
    }

    public List<Player> GetPlayers() {

        Logger.logEntry(this.getClass().getName(), "GetPlayers", "");

        StringBuilder playerNames = new StringBuilder();
        for (Player player : this.players) {
            playerNames.append(player.getClass().getName()).append(", ");
        }

        Logger.logExit(this.getClass().getName(), playerNames.toString());

        return this.players;
    }

    //-------------TURNS LEFT FOR EFFECT FUNCTIONS--------------

    /**
     * This function returns the turns left for the effect.
     */
    public int GetTurnsLeftForEffect() {
        Logger.logEntry(this.getClass().getName(), "GetTurnsLeftForEffect", "");
        Logger.logExit(this.getClass().getName(), "GetTurnsLeftForEffect", String.valueOf(turnsLeftForEffect));
        return turnsLeftForEffect;
    }

    /**
     * This function sets the turns left for the effect.
     * @param turnsLeftForEffect The number of turns left for the effect.
     */
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

    /**
     * This function is called when a player enters the room.
     * @param player The player that enters the room.
     */
    public void Enter(Player player){

        Logger.logEntry(this.getClass().getName(), "Enter", "player");

        //The player can't move is he is frozen
        if( player.GetFrozenForRound() != 0 ) {
            return;
        }

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

        if(turnsLeftForEffect > 0){
            player.Freeze(turnsLeftForEffect);
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

    /**
     * This function checks if there is more space in the room.
     */
    public boolean HasMoreSpaceInRoom(){
        Logger.logEntry(this.getClass().getName(), "HasMoreSpaceInRoom", "");
        Logger.logExit(this.getClass().getName(), "HasMoreSpaceInRoom", players.size() < capacity ? "true" : "false");
        return players.size() < capacity;
    }


    /**
     * This function manages the doors of the room.
     * @param room The room to manage the doors of.
     * @param makeDoorDisappearOrAppear If true, the door will disappear, if false, the door will appear.
     */
    public void ManageDoors(Room room, boolean makeDoorDisappearOrAppear) {

    }

    public int GetPassagesBeforeStickiness() {
        return passagesBeforeStickiness;
    }

    public void DecreasePassagesBeforeStickiness() {

        Logger.logEntry(this.getClass().getName(), "DecreasePassagesBeforeStickiness", "");

        //Only decrease the stickiness if the room was cleaned before.
        //We know that the room was cleared, because the passagesLeftForStickiness is not -1
        if( passagesBeforeStickiness > 0 ) {
            passagesBeforeStickiness--;
        }

        Logger.logExit(this.getClass().getName(), "DecreasePassagesBeforeStickiness");

    }
    
    public void SetRoomNumberOfPassagesBeforeStickiness(int numberOfPlayersBefore) {
        Logger.logEntry(this.getClass().getName(),"SetRoomNumberOfPassagesBeforeStickiness", "" );
        this.passagesBeforeStickiness = numberOfPlayersBefore;
        Logger.logExit(this.getClass().getName(), "SetRoomNumberOfPassagesBeforeStickiness");
    }

    //-----------INFORMATION FUNCTIONS----------------

    public void PrintInfo() {
        System.out.println("Room id: " + id);
        System.out.println("Room capacity: " + capacity);
        System.out.println("Room items: ");
        for (Item item : items) {
            System.out.print(item.id + " ");
        }
        System.out.println("Room players: ");
        for (Player player : players) {
            System.out.print(player.id + " ");
        }
        System.out.println("Room neighbours: ");
        for (Room neighbour : neighbours) {
            System.out.println(neighbour.id + " ");
        }
        System.out.println("Room turns left for effect: " + turnsLeftForEffect);
        System.out.println("Room passages before stickiness: " + passagesBeforeStickiness);
    }

}
