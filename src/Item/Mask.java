package Item;

import Logger.Logger;
import Player.Player;

public class Mask extends Item{

    private int abilityNumber;

    public Mask(){abilityNumber = 3;}

    @Override
    public boolean ReactToGas(Player player){
        Logger.logEntry(this.getClass().getName(), "ReactToGas", "");
        Logger.logExit(this.getClass().getName(), "ReactToGas", "true");
        return true;
    }

    @Override
    public void DecreaseTurnsLeft(){
        Logger.logEntry(this.getClass().getName(), "DecreaseTurnsLeft", "");
        abilityNumber --;
        Logger.logExit(this.getClass().getName(), "DecreaseTurnsLeft", "abilityNumber --");
    }

    @Override
    public boolean NeedToThrow(){
        Logger.logEntry(this.getClass().getName(), "NeedToThrow", "");
        Logger.logExit(this.getClass().getName(), "NeedToThrow", "abilityNumber == 0");
        return abilityNumber == 0;
    }
}
