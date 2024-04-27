package Item;
import Logger.Logger;
import Player.*;
import Room.*;
import java.io.*;

/**
 * Represents an abstract Item class that defines the basic structure and behaviors of items in the game.
 * This class serves as a base for all items, providing default implementations and expected behaviors for subclasses to override.
 */
public abstract class Item implements java.io.Serializable{



    protected static int idCounter = 1;
    public String id;

    protected int idNumberCopy = 1;
    public abstract void setIdNumberCopySer();
    public abstract void setIdNumberSer();

    public Item (String _id) {
        id = _id;
    }

    /**
     * Method to be overridden by subclasses to define the action taken when the item is used.
     * The default implementation is empty, indicating no action.
     */
    public void Use(Player player){
        Logger.logEntry(this.getClass().getName(), "Use", "player");
        Logger.logExit(this.getClass().getName(), "Use");
    }

    /**
     * Indicates how an item reacts to gas exposure.
     * By default, this method returns false, suggesting a generic reaction to gas.
     * Subclasses can override this method to provide specific reactions.
     *
     * @return boolean indicating if the item reacts to gas.
     */
    public boolean ReactToGas(Player player){
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
        Logger.logEntry(this.getClass().getName(), "CanSave", "player");
        Logger.logExit(this.getClass().getName(), "CanSave", "false");
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
        player.GetRoom().RemoveItem(this);
        Logger.logExit(this.getClass().getName(), "PickUpItem");
    }

    public void DecreaseTurnsLeft(){
        Logger.logEntry(this.getClass().getName(), "DecreaseTurnsLeft", "");
        Logger.logExit(this.getClass().getName(), "DecreaseTurnsLeft");
    }

    public boolean NeedToThrow(){
        Logger.logEntry(this.getClass().getName(), "NeedToThrow", "");
        Logger.logExit(this.getClass().getName(), "NeedToThrow", "false");
        return false;
    }


    // overridden by Transistor class to implement teleporting
    public boolean Teleport(Player player){
        Logger.logEntry(this.getClass().getName(), "NeedToThrow", "");
        Logger.logExit(this.getClass().getName(), "NeedToThrow", "false");
        return false;
    }


    // overridden in Transistor, looks for a transistor pair in the player's inventory
    public boolean Pair(Player player){
        Logger.logEntry(this.getClass().getName(), "Pair", "");
        Logger.logExit(this.getClass().getName(), "Pair", "false");
        return false; }

    public Transistor GetPair() {
        Logger.logEntry(this.getClass().getName(), "GetPair", "");
        Logger.logExit(this.getClass().getName(), "GetPair");
        return null;
    }

    // overridden in Transistor, connects a transistor to another
    public void Connect(Transistor transistor){
        Logger.logEntry(this.getClass().getName(), "Connect", "transistor");
        Logger.logExit(this.getClass().getName(), "Connect");
    }

    // overridden by transistor, deployes the first transistor at hand
    public boolean Deploy(Player player){
        Logger.logEntry(this.getClass().getName(), "Deply", "");
        Logger.logExit(this.getClass().getName(), "Deploy", "false");
        return false;
    }


    // a method used by Camembert to expicitly make gas in the given room
    public void MakeGas(Room room){
        Logger.logEntry(this.getClass().getName(), "MakeGas", "room");
        Logger.logExit(this.getClass().getName(), "MakeGas");
    }


    public void SetRoom(Room room) {

    };

    public boolean UseTransistor(Player player) {
        Logger.logEntry(this.getClass().getName(), "UseTransistor", "");
        Logger.logExit(this.getClass().getName(), "UseTransistor", "false");
        return false;
    }

    //-----------INFORMATION FUNCTIONS----------------

    public void PrintInfo() {
        System.out.println( id );
    } ;

}
