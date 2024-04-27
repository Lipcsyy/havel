package Player;
import Item.*;
import Room.*;
import Logger.*;
import GameManager.*;

import java.util.ArrayList;
import java.util.List;
import java.io.*;

public abstract class Player implements java.io.Serializable {

    public String id;

    protected GameManager gameManager;

    protected boolean isAlive;
    protected Room room;
    protected List<Item> items;
    protected int frozenForRound = 0;

    protected int idNumberCopy = 1;
    public abstract void setIdNumberCopySer();
    public abstract void setIdNumberSer();

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
        this.isAlive = isAlive;
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

        Logger.logEntry(this.getClass().getName(), "CollectItem", "item");

        if (!this.HasMoreSpaceInInventory()) {
            return;
        }


        //If the room is sticky the items can't be collected
        if( room.GetPassagesBeforeStickiness() == 0 ) {
            return;
        }

        item.PickUpItem(this);

        Logger.logExit(this.getClass().getName(), "CollectItem");
    };

    /**
     * This function drops all the items in the inventory to the floor.
     */
    public void DropAllItem() {
        Logger.logEntry(this.getClass().getName(), "DropAllItem", "");
        for(Item i: items){
            this.room.AddItem(i);
            this.RemoveFromInventory(i);
        }
        Logger.logExit(this.getClass().getName(), "DropAllItem");
    };

    /**
     * This function removes an item from the inventory. The doesn't drop to the floor, it is removed completely.
     * @param item The item to be removed from the inventory.
     */
    public void RemoveFromInventory(Item item){
        Logger.logEntry(this.getClass().getName(), "RemoveFromInventory", "item");
        this.GetInventory().remove(item);
        Logger.logExit(this.getClass().getName(), "RemoveFromInventory");
    }


    /**
     * This function return true or false based on the inventory space of the player
     */
    public boolean HasMoreSpaceInInventory() {
        Logger.logEntry(this.getClass().getName(), "HasMoreSpaceInInventory", "");
        Logger.logExit(this.getClass().getName(), "HasMoreSpaceInInventory");
        return this.items.size() < 5;
    };


    /**
     * This function returns the inventory of the player.
     * @return The inventory of the player.
     */
    public List<Item> GetInventory() {
        Logger.logEntry(this.getClass().getName(), "GetInventory", "");

        StringBuilder itemNames = new StringBuilder();
        for (Item item : this.items) {
            itemNames.append(item.getClass().getName()).append(", ");
        }

        Logger.logExit(this.getClass().getName(), "GetInventory", itemNames.toString());

        return this.items;
    }

    /**
     * This function adds an item to the inventory.
     * @param item The item to be added to the inventory.
     */
    public void AddItem(Item item) {
        Logger.logEntry(this.getClass().getName(), "AddItem", item.getClass().getName());
        this.items.add(item);
        Logger.logExit(this.getClass().getName(), "AddItem");
    }


    //-----------MOVEMENT FUNCTIONS----------------

    /**
     * This function moves the player to a different room.
     * @param room The room to move to.
     */
    public void Move(Room room) {
        Logger.logEntry(this.getClass().getName(), "Move", "room" );

        this.room.RemovePlayer(this);
        room.AddPlayer(this);
        this.SetRoom(room);

        //When we go into a room we decrease the passages that are left before stickiness
        //This only decreases if the room was cleared before
        this.room.DecreasePassagesBeforeStickiness();

        Logger.logExit(this.getClass().getName(), "Move" );
    };

    /**
     * This function changes the room of the player.
     * @param room The room to change to.
     */
    public void ChangeRoom(Room room) {

        Logger.logEntry(this.getClass().getName(), "ChangeRoom", "room" );

        if( !room.GetNeighbours().contains(this.room) ) {
            Logger.logExit(this.getClass().getName(), "ChangeRoom", "");
            return;
        }

        room.Enter(this);

        Logger.logExit(this.getClass().getName(), "ChangeRoom" );
    };

    /**
     * This function sets the room of the player.
     * @param room The room to set.
     */
    public void SetRoom(Room room) {

        Logger.logEntry(this.getClass().getName(), "SetRoom", "room");

        this.room = room;

        Logger.logExit(this.getClass().getName(), "SetRoom");
    }

    /**
     * This function returns the room of the player.
     * @return The room of the player.
     */
    public Room GetRoom() {
        Logger.logEntry(this.getClass().getName(), "GetRoom", "");
        Logger.logExit(this.getClass().getName(), "GetRoom", "room");
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
    public void Freeze( int freezeForRounds ) {
        Logger.logEntry(this.getClass().getName(), "Freeze", "5");
        this.frozenForRound = freezeForRounds;
        Logger.logExit(this.getClass().getName(), "Freeze");
    }

    /**
     * This function returns the number of rounds the player is frozen for
     * @return The number of rounds the player is frozen for
     */
    public int GetFrozenForRound() {
        Logger.logEntry(this.getClass().getName(), "GetFrozenForRound", "");
        Logger.logExit(this.getClass().getName(), "GetFrozenForRound", String.valueOf(this.frozenForRound));
        return this.frozenForRound;
    }

    // empty function, only overridden in Student (will apply on Students when picking up a BeerGlass)
    public void DropRandomItem() {}

    public void DropItem() {}

    public void Transistor() {};


    //-----------INFORMATION FUNCTIONS----------------

    public void PrintInfo() {
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

}
