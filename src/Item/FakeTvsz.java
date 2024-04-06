package Item;
import Logger.Logger;
import Player.*;

public class FakeTvsz extends Item {

    public FakeTvsz(){}

    // These functions are implemented in Item

    @Override
    public boolean NeedToThrow() {
        Logger.logEntry(this.getClass().getName(), "NeedToThrow", "");
        Logger.logExit(this.getClass().getName(), "NeedToThrow", "false");
        return false;
    }

    @Override
    public void Use(Player player) {
        Logger.logEntry(this.getClass().getName(), "Use", "player");
        Logger.logExit(this.getClass().getName(), "Use");
    }

    @Override
    public boolean CanSave(Player player){
        Logger.logEntry(this.getClass().getName(), "CanSave", "player");
        Logger.logExit(this.getClass().getName(), "CanSave", "false");
        return false;
    }
}
