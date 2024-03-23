package Item;
import Player.*;
import Room.*;
import Logger.*;

/**
 * Represents an abstract Item class that defines the basic structure and behaviors of items in the game.
 * This class serves as a base for all items, providing default implementations and expected behaviors for subclasses to override.
 */
public abstract class Item {
    /**
     * Method to be overridden by subclasses to define the action taken when the item is used.
     * The default implementation is empty, indicating no action.
     */
    public void Use(Player player){ }

    // overridden by Transistor class to implement teleporting
    public void Teleport(Player player){ }

    /**
     * Indicates how an item reacts to gas exposure.
     * By default, this method returns true, suggesting a generic reaction to gas.
     * Subclasses can override this method to provide specific reactions.
     *
     * @return boolean indicating if the item reacts to gas.
     */
    public boolean ReactToGas(){
        return false;
    }

    /**
     * Indicates how an item reacts when hit by a teacher.
     * This method returns true by default, suggesting a generic reaction to being hit.
     * Subclasses are expected to override this method with specific behaviors.
     *
     * @param player The Teacher object interacting with the item.
     * @return boolean indicating if the item reacts when hit by a teacher.
     */
    public boolean CanSave(Player player){
        return false;
    }

    /**
     * Defines the behavior when a player picks up the item.
     * The default implementation is empty, as specific behavior is expected to be defined in subclasses.
     *
     * @param player The Player object picking up the item.
     */
    public void PickUpItem(Player player) {
        Logger.logEntry(this.getClass().getName(), "PickUpItem", "player");
        player.AddItem(this);
        Logger.logExit(this.getClass().getName(), "PickUpItem");
    }

    public void DecreaseTurnsLeft(){ }

    public boolean NeedToThrow(){
        return false;
    }

    // overridden in Transistor, looks for a transistor pair in the player's inventory
    public boolean Pair(Player player){ return false; }

    // overridden in Transistor, connects a transistor to another
    public void Connect(Transistor transistor){}
    // overridden by transistor, deployes the first transistor at hand
    public void Deploy(Player player){}

    // a method used by Camembert to implicitely make gas in the given room
    public void MakeGas(Room room){}


}
