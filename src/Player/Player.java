package Player;
import Item.*;
import Room.*;

public abstract class Player {

    


    //-----------INVENTORY FUNCTIONS----------------

    /**
     * This function adds an item to the inventory from the floor.
     * @param item The item to be added to the inventory from the floor.
     */
    public  void CollectItem(Item item) {

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


    //-----------MOVEMENT FUNCTIONS----------------

    /**
     * This function moves the player to a different room.
     * @param room The room to move to.
     */
    public void Move(Room room) {

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


    //-----------INTERACTION FUNCTIONS----------------

    /**
     * This function is called, when a player needs to interact with another player
     * @param player The player that we interact with
     */
    public abstract void Interact(Player player);

    public abstract void ReactToTeacher(Teacher teacher);

}
