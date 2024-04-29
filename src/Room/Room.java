package Room;
import Item.*;
import Player.*;
import java.util.*;
import Logger.*;
import GameManager.*;
import Enums.*;

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

    public static void ResetCounter(){
        idNumber = 1;
    }



    //public int getIdNumber(){return idNumber;}
    //public void setIdNumber(int idValue){idNumber = idValue;}
    protected int idNumberCopy = 1;
    public void setIdNumberCopySer(){idNumberCopy = idNumber;}
    public void setIdNumberSer(){idNumber = idNumberCopy;}
    public List<Item> getItems(){return items;}

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

        gameManager.AddRoom(this);
    }

    //Copy constructor
    public Room( Room room, GameManager gameManager ) {

        this.gameManager = gameManager;

        gameManager.AddRoom(this);

        this.id = room.id;
        this.capacity = room.capacity;

        this.items = new ArrayList<Item>();
        this.players = new ArrayList<Player>();
        this.neighbours = new ArrayList<Room>();

        for ( var i : room.getItems() ) {
            this.AddItem(i);
        }

        for ( var p : room.GetPlayers() ){
            this.AddPlayer(p);
        }
        var targetNeighbours = gameManager.GetNeighbours(room);

        for ( var r : targetNeighbours ) {
            this.SetNeighbours(r);
        }

    }

    public Room(){}

    //----------ITEM FUNCTIONS------------------------

    /**
     * This function adds an item to the room.
     * @param item The item to add.
     */
    public void AddItem(Item item) {
        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "AddItem", "item");
        }
        items.add(item);
        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logExit(this.getClass().getName(), "AddItem");
        }
    }

    /**
     * This function removes an item from the room.
     * @param item The item to remove.
     */
    public void RemoveItem(Item item) {
        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "RemoveItem", "item");
        }
        items.remove(item);
        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logExit(this.getClass().getName(), "RemoveItem");
        }
    }

    //-----------------ROOM FUNCTIONS----------------

    /**
     * This function returns the neighbours of the room.
     */
    public Set<Room> GetNeighbours(){
        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "GetNeighbours", "");
        }

        StringBuilder neighboursString = new StringBuilder();

        Set<Room> neighbours = gameManager.GetNeighbours(this);

        for (Room neighbour : neighbours) {
            neighboursString.append(neighbour.getClass().getName()).append(" ");
        }

        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logExit(this.getClass().getName(), "GetNeighbours", neighboursString.toString() );
        }

        return neighbours;
    }


    /**
     * This function sets the neighbours of the room.
     * @param neighbour The neighbour to set.
     */
    public void SetNeighbours(Room neighbour){
        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "SetNeighbours", "neighbour");
        }

        gameManager.ConnectRoomsTwoWay(this, neighbour);

        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logExit(this.getClass().getName(), "SetNeighbours");
        }
    }

    public void RemoveNeighbour( Room neighbour ) {
        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "RemoveNeighbour", "room");
        }
        gameManager.DisconnectRoomsTwoWay(this, neighbour);
        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logExit(this.getClass().getName(), "RemoveNeighbour");
        }
    }

    public void CleanRoom() {
        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "CleanRoom", "");
        }
        this.turnsLeftForEffect = 0;
        this.SetRoomNumberOfPassagesBeforeStickiness(5);
        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logExit(this.getClass().getName(), "CleanRoom");
        }
    }

    //-------------PLAYER FUNCTIONS--------------

    /**
     * This function adds a player to the room.
     * @param player The player to add.
     */
    public void AddPlayer(Player player) {
        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "AddPlayer", "player");
        }

        players.add(player);

        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logExit(this.getClass().getName(), "AddPlayer");
        }
    }


    /**
     * This function removes a player from the room.
     * @param player The player to remove.
     */
    public void RemovePlayer(Player player){
        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "RemovePlayer", "player");
        }

        players.remove(player);

        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logExit(this.getClass().getName(), "RemovePlayer");
        }
    }

    public List<Player> GetPlayers() {

        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "GetPlayers", "");
        }

        StringBuilder playerNames = new StringBuilder();
        for (Player player : this.players) {
            playerNames.append(player.getClass().getName()).append(", ");
        }

        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logExit(this.getClass().getName(), playerNames.toString());
        }

        return this.players;
    }

    //-------------TURNS LEFT FOR EFFECT FUNCTIONS--------------

    /**
     * This function returns the turns left for the effect.
     */
    public int GetTurnsLeftForEffect() {
        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "GetTurnsLeftForEffect", "");
            Logger.logExit(this.getClass().getName(), "GetTurnsLeftForEffect", String.valueOf(turnsLeftForEffect));
        }
        return turnsLeftForEffect;
    }

    /**
     * This function sets the turns left for the effect.
     * @param turnsLeftForEffect The number of turns left for the effect.
     */
    public void SetTurnsLeftForEffect(int turnsLeftForEffect) {

        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "SetTurnsLeftForEffect", String.valueOf(turnsLeftForEffect));
        }
        this.turnsLeftForEffect = turnsLeftForEffect;

        System.out.println(players.size());

        for ( Player player : this.players ) {
            player.Freeze(turnsLeftForEffect);
        }
        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logExit(this.getClass().getName(), "SetTurnsLeftForEffect");
        }
    }

    //-------------MOVING AND DOOR FUNCTIONS--------------

    /**
     * This function is called when a player enters the room.
     * @param player The player that enters the room.
     */
    public void Enter(Player player){

        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "Enter", "player");
        }

        //The player can't move is he is frozen
        if( player.GetFrozenForRound() != 0 ) {
            if (GameManager.loggerStatus == ELogger.INFO) {
                Logger.logExit(this.getClass().getName(), "Enter");
            }
            return;
        }

        //Adding the player if there is more space in the room
        if (HasMoreSpaceInRoom()) {
            player.Move(this);
        } else {
            if (GameManager.loggerStatus == ELogger.INFO) {
                Logger.logExit(this.getClass().getName(), "Enter");
            }
            return;
        }

        if (!items.isEmpty()) {
            player.CollectItem(items.get(0));
        }

        if(turnsLeftForEffect > 0){
            player.Freeze(turnsLeftForEffect);
        }

        //making the players interact with each-other
        for(int i = 0; i<players.size();i++){

            int siz = players.size();

            Player playerInRoom = players.get(i);

            if (playerInRoom == player) {
                continue;
            }

            player.Interact(playerInRoom);
            playerInRoom.Interact(player);
            int newsiz = players.size();

            //if we removed a player
            if(newsiz != siz){
                i--;
            }
        }

        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logExit(this.getClass().getName(), "Enter");
        }
    }

    /**
     * This function checks if there is more space in the room.
     */
    public boolean HasMoreSpaceInRoom(){
        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "HasMoreSpaceInRoom", "");
            Logger.logExit(this.getClass().getName(), "HasMoreSpaceInRoom", players.size() < capacity ? "true" : "false");
        }
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
        if ( GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "GetPassagesBeforeStickiness", String.valueOf(passagesBeforeStickiness));
            Logger.logExit(this.getClass().getName(), "GetPassagesBeforeStickiness");
        }
        return passagesBeforeStickiness;
    }

    public void DecreasePassagesBeforeStickiness() {

        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "DecreasePassagesBeforeStickiness", "");
        }

        //Only decrease the stickiness if the room was cleaned before.
        //We know that the room was cleared, because the passagesLeftForStickiness is not -1
        if( passagesBeforeStickiness > 0 ) {
            passagesBeforeStickiness--;
        }

        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logExit(this.getClass().getName(), "DecreasePassagesBeforeStickiness");
        }

    }
    
    public void SetRoomNumberOfPassagesBeforeStickiness(int numberOfPlayersBefore) {
        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(),"SetRoomNumberOfPassagesBeforeStickiness", "" );
        }
        this.passagesBeforeStickiness = numberOfPlayersBefore;
        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logExit(this.getClass().getName(), "SetRoomNumberOfPassagesBeforeStickiness");
        }
    }

    //-----------INFORMATION FUNCTIONS----------------

    public void PrintInfo() {

        System.out.println("\n");

        System.out.println("Room id: " + id);
        System.out.println("Room capacity: " + capacity);

        if ( items != null ) {
            System.out.print("Room items: ");
            for (Item item : items) {
                System.out.print(item.id + " ");
            }
            System.out.println();
        }

        if ( players != null ) {
            System.out.print("Room players: ");
            for (Player player : players) {
                System.out.print(player.id  + " ");
            }
            System.out.println();
        }

        var neighbours = gameManager.GetNeighbours(this);

        if ( neighbours != null ) {
            System.out.print("Room neighbours: ");
            for (Room neighbour : neighbours) {
                System.out.print(neighbour.id + " ");
            }
            System.out.println();
        }

        System.out.println("Room turns left for effect: " + turnsLeftForEffect);
        System.out.print("Room passages before stickiness: " + passagesBeforeStickiness);

    }

}
