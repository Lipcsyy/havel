package Item;

import Logger.Logger;
import Player.Player;

public class Rag extends Item{
    private int turnsLeft;

    public Rag(){
        turnsLeft = 5;
    }

    @Override
    public void Use(Player player){
        Logger.logEntry(this.getClass().getName(), "Use", "player");
        Logger.logExit(this.getClass().getName(), "Use");
        player.Freeze(3);
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
        Logger.logExit(this.getClass().getName(), "DecreaseTurnsLeft", "turnsLeft --");
    }

    @Override
    public boolean NeedToThrow(){
        Logger.logEntry(this.getClass().getName(), "NeedToThrow", "");
        Logger.logExit(this.getClass().getName(), "NeedToThrow");
        return turnsLeft == 0;
    }
}
