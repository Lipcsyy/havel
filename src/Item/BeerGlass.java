package Item;

import Player.Player;
import Logger.Logger;

public class BeerGlass extends Item{
    static int idNumber = 1;

    private int turnsLeft;

    public BeerGlass(){
        super( "BeerGlass_" + idNumber++);
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

    @Override
    public void PickUpItem(Player player) {
        Logger.logEntry(this.getClass().getName(), "PickUpItem", "player");
        player.AddItem(this);
        player.GetRoom().RemoveItem(this);
        player.DropRandomItem();
        Logger.logExit(this.getClass().getName(), "PickUpItem");
    }

}