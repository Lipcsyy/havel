package Item;
import Logger.Logger;
import Player.*;

public class Tvsz extends Item {
    private int abilityNumber;

    public Tvsz(){
        abilityNumber = 3;
    }

    @Override
    public boolean NeedToThrow() {
        Logger.logEntry(this.getClass().getName(), "NeedToThrow", "");
        Logger.logExit(this.getClass().getName(), "NeedToThrow", abilityNumber == 0 ? "true" : "false");
        return abilityNumber == 0;
    }

    @Override
    public void Use(Player player) {
        Logger.logEntry(this.getClass().getName(), "Use", "player");
        abilityNumber --;
        Logger.logExit(this.getClass().getName(), "Use");
    }

    @Override
    public boolean CanSave(Player player){
        Logger.logEntry(this.getClass().getName(), "CanSave", "player");
        this.Use(player);
        Logger.logExit(this.getClass().getName(), "CanSave", "true");
        return true;
    }

}
