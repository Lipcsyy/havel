package Player;
import Enums.ELogger;

import GameManager.GameManager;
import Item.*;
import Logger.Logger;
import Room.*;

public class Teacher extends Player{

    static int idNumber = 1;

    public static void ResetCounter(){
        idNumber = 1;
    }

    public void setIdNumberCopySer(){idNumberCopy = idNumber;}
    public void setIdNumberSer(){idNumber = idNumberCopy;}

    public Teacher(Room startRoom, GameManager gameManager) {
        super(startRoom, gameManager, "Teacher_" + idNumber++);
    }

    @Override
    public void RemoveFromInventory(Item item) {
        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "RemoveFromInventory", "item");
        }
        items.remove(item);
        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logExit(this.getClass().getName(), "RemoveFromInventory");
        }
    }

    @Override
    public void ReactToTeacher(Player player) {
        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "ReactToTeacher", "player");
            Logger.logExit(this.getClass().getName(), "ReactToTeacher");
        }
    }

    @Override
    public void Interact(Player player) {
        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "Interact", "player");
        }

        if ( player.room != this.room ) {
            if (GameManager.loggerStatus == ELogger.INFO ) {
                Logger.logExit(this.getClass().getName(), "Interact");
            }
            return;
        }

        player.ReactToTeacher(this);
        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logExit(this.getClass().getName(), "Interact");
        }
    }
}
