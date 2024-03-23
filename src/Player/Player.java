package Player;
import Item.*;
import Room.*;
import Logger.*;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {

    boolean isAlive;
    Room room;
    protected List<Item> items;
    private int frozenForRound = 0;


    public Player( Room startRoom ) {
        isAlive = true;
        room = startRoom;
        items = new ArrayList<Item>();
        frozenForRound = 0;

       if( startRoom != null)
          startRoom.AddPlayer( this );
    }

    public void setIsAlive(boolean isAlive){
        this.isAlive = isAlive;
    }

    public boolean GetIsAlive(){
        return isAlive;
    }

    //-----------INVENTORY FUNCTIONS----------------

    /**
     * This function adds an item to the inventory from the floor.
     * @param item The item to be added to the inventory from the floor.
     */
    public void CollectItem(Item item) {

        if (!this.HasMoreSpaceInInventory()) {
            return;
        }

        item.PickUpItem(this);
        this.room.RemoveItem(item);
    };

    /**
     * This function drops all the items in the inventory to the floor.
     */
    public void DropAllItem() {
        for(Item i: items){
            this.room.AddItem(i);
            this.RemoveFromInventory(i);
        }
    };

    /**
     * This function removes an item from the inventory. The doesn't drop to the floor, it is removed completely.
     * @param item The item to be removed from the inventory.
     */
    public void RemoveFromInventory(Item item){
        this.items.remove(item);
    }

    /**
     * This function return true or false based on the inventory space of the player
     */
    public boolean HasMoreSpaceInInventory() {
        return this.items.size() < 5;
    };

    public List<Item> GetInventory() {
        return this.items;
    }

    public void AddItem(Item item) {
        this.items.add(item);}

    //-----------MOVEMENT FUNCTIONS----------------

    /**
     * This function moves the player to a different room.
     * @param room The room to move to.
     */
    public void Move(Room room) {
        Logger.logEntry(this.getClass().getName(), "Move" );

        this.room.RemovePlayer(this);
        room.AddPlayer(this);
        this.SetRoom(room);

        Logger.logExit(this.getClass().getName(), "Move" );
    };

    /**
     * This function changes the room of the player.
     * @param room The room to change to.
     */
    public void ChangeRoom(Room room) {
        Logger.logEntry(this.getClass().getName(), "ChangeRoom" );

        room.Enter(this);

        Logger.logExit(this.getClass().getName(), "ChangeRoom" );
    };

    /**
     * This function sets the room of the player.
     * @param room The room to set.
     */
    public void SetRoom(Room room) {

        Logger.logEntry(this.getClass().getName(), "SetRoom");

        this.room = room;

        Logger.logExit(this.getClass().getName(), "SetRoom");
    }

    /**
     * This function returns the room of the player.
     * @return The room of the player.
     */
    public Room GetRoom() {
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
        this.frozenForRound = freezeForRounds;
    }

    /**
     * This function returns the number of rounds the player is frozen for
     * @return The number of rounds the player is frozen for
     */
    public int GetFrozenForRound() {
        return this.frozenForRound;
    }

}
