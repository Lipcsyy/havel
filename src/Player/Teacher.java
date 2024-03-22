package Player;

import Item.*;
import Logger.Logger;
import Room.*;

public class Teacher extends Player{
    public Teacher(Room startRoom) {
        super(startRoom);
    }

    @Override
    public void RemoveFromInventory(Item item) {

    }

    @Override
    public void ReactToTeacher(Player teacher) {
        Logger.logEntry(this.getClass().getName(), "ReactToTeacher");
    }

    @Override
    public void Interact(Player player) {
        Logger.logEntry(this.getClass().getName(), "Interact");
        player.ReactToTeacher(this);
        Logger.logExit(this.getClass().getName(), "Interact");
    }
}
