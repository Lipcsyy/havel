package Player;

import GameManager.GameManager;
import Item.*;
import Logger.Logger;
import Room.*;

public class Teacher extends Player{

    static int idNumber = 1;

    public void setIdNumberCopySer(){idNumberCopy = idNumber;}
    public void setIdNumberSer(){idNumber = idNumberCopy;}

    public Teacher(Room startRoom, GameManager gameManager) {
        super(startRoom, gameManager, "Teacher_" + idNumber++);
    }

    @Override
    public void RemoveFromInventory(Item item) {
        Logger.logEntry(this.getClass().getName(), "RemoveFromInventory", "item");
        items.remove(item);
        Logger.logExit(this.getClass().getName(), "RemoveFromInventory");
    }

    @Override
    public void ReactToTeacher(Player player) {
        Logger.logEntry(this.getClass().getName(), "ReactToTeacher", "player");
        Logger.logExit(this.getClass().getName(), "ReactToTeacher");
    }

    @Override
    public void Interact(Player player) {
        Logger.logEntry(this.getClass().getName(), "Interact", "player");

        if ( player.room != this.room ) {
            Logger.logExit(this.getClass().getName(), "Interact");
            return;
        }

        player.ReactToTeacher(this);
        Logger.logExit(this.getClass().getName(), "Interact");
    }
}
