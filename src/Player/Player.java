package Player;
import Item.*;
import Room.*;

import java.util.List;

public abstract class Player {

    boolean isAlive;
    Room room;

    protected List<Item> items;

    private int frozenForRound = 0;


    public Player( Room startRoom){
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

        if (this.HasMoreSpaceInInventory() == false) {
            return;
        }

        item.PickUpItem(this);
        this.room.RemoveItem(item);
    };

    /**
     * This function drops an item from the inventory to the floor.
     * @param item The item to be dropped.
     */
    public void DropItem(Item item) {

    };

    /**
     * This function drops all the items in the inventory to the floor.
     */
    public void DropAllItem() {

    };

    /**
     * This function removes an item from the inventory. The doesn't drop to the floor, it is removed completely.
     * @param item The item to be removed from the inventory.
     */
    public abstract void RemoveFromInventory(Item item);

    /**
     * This function return true or false based on the inventory space of the player
     */
    public boolean HasMoreSpaceInInventory() {
        return true;
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
        this.room.RemovePlayer(this);
        room.AddPlayer(this);
    };

    /**
     * This function changes the room of the player.
     * @param room The room to change to.
     */
    public void ChangeRoom(Room room) {

    };

    /**
     * This function sets the room of the player.
     * @param room The room to set.
     */
    public void SetRoom(Room room) {

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
