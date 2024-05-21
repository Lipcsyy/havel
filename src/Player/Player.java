package Player;
import Enums.ELogger;
import Item.*;
import Room.*;
import Logger.*;
import GameManager.*;

import java.util.ArrayList;
import java.util.List;
import java.io.*;

public abstract class Player implements java.io.Serializable {

    public String id;

    public GameManager gameManager;

    protected boolean isAlive;
    protected Room room;
    public List<Item> items;
    protected int frozenForRound = 0;

    protected int idNumberCopy = 1;
    public abstract void setIdNumberCopySer();
    public abstract void setIdNumberSer();
    public List<Item> getItems(){return items;}

    /**
     * This is the constructor of the player class.
     * @param startRoom The room where the player starts.
     */
    public Player( Room startRoom, GameManager gameManager, String _id  ) {

        id = _id;

        isAlive = true;
        room = startRoom;
        items = new ArrayList<>();
        frozenForRound = 0;

        this.gameManager = gameManager;

       if( startRoom != null)
          startRoom.AddPlayer( this );

       gameManager.AddPlayer( this );
    }

    /**
     * This function sets the status of the player.
     * @param isAlive The status of the player.
     */
    public void SetIsAlive(boolean isAlive){

        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "SetIsAlive", ": " + isAlive);
        }

        this.isAlive = isAlive;

        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logExit(this.getClass().getName(), "SetIsAlive");
        }
    }


    /**
     * This function returns the status of the player.
     * @return The status of the player.
     */
    public boolean GetIsAlive(){
        return isAlive;
    }

    //-----------INVENTORY FUNCTIONS----------------

    /**
     * This function adds an item to the inventory from the floor.
     * @param item The item to be added to the inventory from the floor.
     */
    public void CollectItem(Item item) {

        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "CollectItem", "item");
        }

        if (!this.HasMoreSpaceInInventory()) {
            if (GameManager.loggerStatus == ELogger.INFO ) {
                Logger.logEntry(this.getClass().getName(), "CollectItem", "item");
            }
            return;
        }


        //If the room is sticky the items can't be collected
        if( room.GetPassagesBeforeStickiness() == 0 ) {
            if (GameManager.loggerStatus == ELogger.INFO ) {
                Logger.logEntry(this.getClass().getName(), "CollectItem", "item");
            }
            return;
        }

        //he the player can collect the item we pick up it
        item.PickUpItem(this);


        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logExit(this.getClass().getName(), "CollectItem");
        }
    }

    /**
     * This function drops all the items in the inventory to the floor.
     */
    public void DropAllItem() {
        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "DropAllItem", "");
        }

        for ( int i = 0; i < items.size(); i++ ) {
            this.room.AddItem(items.get(i));
            this.RemoveFromInventory(items.get(i));
            i--;
        }

        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logExit(this.getClass().getName(), "DropAllItem");
        }
    }

    /**
     * This function removes an item from the inventory. The doesn't drop to the floor, it is removed completely.
     * @param item The item to be removed from the inventory.
     */
    public void RemoveFromInventory(Item item){
        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "RemoveFromInventory", "item");
        }
        
        this.GetInventory().remove(item);
        gameManager.GetGameController().RenderAfterDrop( this );

        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logExit( this.getClass().getName(), "RemoveFromInventory" );
        }
    }


    /**
     * This function return true or false based on the inventory space of the player
     */
    public boolean HasMoreSpaceInInventory() {
        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "HasMoreSpaceInInventory", "");
            Logger.logExit(this.getClass().getName(), "HasMoreSpaceInInventory");
        }
        return this.items.size() < 5;
    }


    /**
     * This function returns the inventory of the player.
     * @return The inventory of the player.
     */
    public List<Item> GetInventory() {
        if ( GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "GetInventory", "");
        }

        StringBuilder itemNames = new StringBuilder();
        for (Item item : this.items) {
            itemNames.append(item.getClass().getName()).append(", ");
        }

        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logExit(this.getClass().getName(), "GetInventory", itemNames.toString());
        }

        return this.items;
    }


    /**
     * This function adds an item to the inventory.
     * @param item The item to be added to the inventory.
     */
    public void AddItem(Item item) {
        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "AddItem", item.getClass().getName());
        }
        this.items.add(item);
        gameManager.GetGameController().AddActionListenerToItemView(this, item);
        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logExit(this.getClass().getName(), "AddItem");
        }
    }

    //-----------MOVEMENT FUNCTIONS----------------

    public void DecreaseItemsTurnsLeft() {}

    /**
     * This function moves the player to a different room.
     * @param room The room to move to.
     */
    public void Move(Room room) {

        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "Move", "room" );
        }

        this.room.DecreasePassagesBeforeStickiness();

        this.room.RemoveObserver(gameManager.GetGameController());
        this.room.RemovePlayer(this);

        room.AddPlayer(this);
        room.AddObserver(gameManager.GetGameController());

        this.SetRoom(room);

        for ( int i = 0; i < items.size(); i++ ) {
            items.get(i).DecreaseTurnsLeft(this);
            if (items.get(i).NeedToThrow()) {
                this.RemoveFromInventory( items.get(i) );
                i--;
            }
        }

        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logExit(this.getClass().getName(), "Move" );
        }
    }


    public boolean ChangeRoom(Room room) {

        System.out.println("Changing motherfucking roooom");
        System.out.println(frozenForRound);


        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "ChangeRoom", "room" );
        }

//        if( !this.room.GetNeighbours().contains(room) ) {
//            if (GameManager.loggerStatus == ELogger.INFO) {
//                Logger.logExit(this.getClass().getName(), "ChangeRoom", "");
//            }
//            return false;
//        }

        boolean canChangeRoom = room.Enter(this);

        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logExit(this.getClass().getName(), "ChangeRoom" );
        }

        return canChangeRoom;
    }

    /**
     * This function sets the room of the player.
     * @param room The room to set.
     */
    public void SetRoom(Room room) {

        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "SetRoom", "room");
        }

        this.room = room;

        if ( GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logExit(this.getClass().getName(), "SetRoom");
        }
    }

    /**
     * This function returns the room of the player.
     * @return The room of the player.
     */
    public Room GetRoom() {
        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "GetRoom", "");
            Logger.logExit(this.getClass().getName(), "GetRoom", "room");
        }
        return this.room;
    }


    //-----------INTERACTION FUNCTIONS----------------

    /**
     * This function is called, when a player needs to interact with another player
     * @param player The player that we interact with
     */
    public abstract void Interact(Player player);

    /**
     * This function is called, when a player needs to interact with a Teacher
     * @param teacher The teacher that we interact with
     */
    public abstract void ReactToTeacher(Player teacher);

    /**
     * This function when called freeze the player for a number of rounds
     * @param freezeForRounds The number of rounds the player needs to be frozen
     */
    public boolean Freeze( int freezeForRounds ) {
        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "Freeze", "5");
        }

        this.frozenForRound = freezeForRounds;

        this.DropAllItem();

        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logExit(this.getClass().getName(), "Freeze");
        }

        return  false;
    }

    /**
     * This function returns the number of rounds the player is frozen for
     * @return The number of rounds the player is frozen for
     */
    public int GetFrozenForRound() {
        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "GetFrozenForRound", "");
            Logger.logExit(this.getClass().getName(), "GetFrozenForRound", String.valueOf(this.frozenForRound));
        }
        return this.frozenForRound;
    }

    public void DecreaseFrozenRounds(){
        frozenForRound--;
    }

    // empty function, only overridden in Student (will apply on Students when picking up a BeerGlass)
    public void DropRandomItem() {}

    public void DropItem( Item item) {}

    public void Transistor() {}


    //-----------INFORMATION FUNCTIONS----------------

    public void PrintInfo() {
        System.out.println();
        System.out.println("Player: " + id);
        System.out.println("Room: " + room.id);
        System.out.println("Is Alive: " + isAlive);
        System.out.println("Frozen for: " + frozenForRound);

        if ( items != null ) {
            System.out.print("Items: ");
            for (Item item : items) {
                System.out.print(item.id + " ");
            }
        }
    }

    /*

    public void loadBackgroundImage(ERooms eRooms) {
        try {
            if (eRooms == ERooms.ROOM) {
                image = ImageIO.read(new File("./src/Images/room.png"));
            } else if (eRooms == ERooms.GASROOM) {
                image = ImageIO.read(new File("./src/Images/gasroom.png"));
            } else if (eRooms == ERooms.MAGICROOM) {
                image = ImageIO.read(new File("./src/Images/magicroom.png"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadBackgroundImage(Room room) {
        try {
            image = ImageIO.read(new File(room.getBackgroundImage()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ItemHolder getItemHolder() {
        return itemHolder;
    }

    public PlayerHolder getPlayerHolder() {
        return playerHolder;
    }
    */

}
