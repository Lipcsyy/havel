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
        return abilityNumber == 0;
    }

    @Override
    public void Use(Player player) {
        Logger.logEntry(this.getClass().getName(), "Use");
        abilityNumber --;
        Logger.logExit(this.getClass().getName(), "Use");
    }

    @Override
    public boolean CanSave(Player player){
        Logger.logEntry(this.getClass().getName(), "CanSave");
        Logger.logExit(this.getClass().getName(), "CanSave");
        return true;
    }

}
