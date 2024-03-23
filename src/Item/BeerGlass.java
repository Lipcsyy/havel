package Item;

import Player.Player;
import Logger.Logger;

public class BeerGlass extends Item{

    private int turnsLeft;

    public BeerGlass(){
        turnsLeft = 5;
    }

    public void Use(Player player){
        Logger.logEntry(this.getClass().getName(), "Use", "player");

        player.Freeze(3);

        Logger.logExit(this.getClass().getName(), "Use");
    }

    @Override
    public boolean CanSave(Player player){
        Logger.logEntry(this.getClass().getName(), "CanSave", "player");
        this.Use(player);
        Logger.logExit(this.getClass().getName(), "CanSave", "true");

        return true;
    }

    @Override
    public void DecreaseTurnsLeft(){
        Logger.logEntry(this.getClass().getName(), "DecreaseTurnsLeft", "");

        turnsLeft --;

        Logger.logExit(this.getClass().getName(), "DecreaseTurnsLeft");
    }

    @Override
    public boolean NeedToThrow(){
        Logger.logEntry(this.getClass().getName(), "NeedToThrow", "");
        Logger.logExit(this.getClass().getName(), "NeedToThrow",  turnsLeft == 0 ? "true" : "false");

        return turnsLeft == 0;
    }
}