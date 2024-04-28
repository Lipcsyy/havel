package Item;

import Player.Player;
import Logger.Logger;
import Enums.ELogger;
import GameManager.GameManager;

public class BeerGlass extends Item{
    static int idNumber = 1;

    private int turnsLeft;

    public static void ResetCounter(){
        idNumber = 1;
    }

    public void setIdNumberCopySer(){idNumberCopy = idNumber;}
    public void setIdNumberSer(){idNumber = idNumberCopy;}

    public BeerGlass(){
        super( "BeerGlass_" + idNumber++);
        turnsLeft = 5;
    }

    @Override
    public void Use(Player player){
        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logEntry(this.getClass().getName(), "Use", "player");
        }

        player.Freeze(3);

        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logExit(this.getClass().getName(), "Use");
        }
    }

    @Override
    public boolean CanSave(Player player){
        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logEntry(this.getClass().getName(), "CanSave", "player");
        }

        this.Use(player);

        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logExit(this.getClass().getName(), "CanSave", "true");
        }

        return true;
    }

    @Override
    public void DecreaseTurnsLeft( Player player ){

        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logEntry(this.getClass().getName(), "DecreaseTurnsLeft", "");
        }

        turnsLeft --;

        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logExit(this.getClass().getName(), "DecreaseTurnsLeft");
        }
    }

    @Override
    public boolean NeedToThrow(){
        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logEntry(this.getClass().getName(), "NeedToThrow", "");
            Logger.logExit(this.getClass().getName(), "NeedToThrow",  turnsLeft == 0 ? "true" : "false");
        }

        return turnsLeft == 0;
    }

    @Override
    public void PickUpItem(Player player) {
        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logEntry(this.getClass().getName(), "PickUpItem", "player");
        }

        player.AddItem(this);
        player.GetRoom().RemoveItem(this);
        player.DropRandomItem();

        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logExit(this.getClass().getName(), "PickUpItem");
        }
    }

    @Override
    public void PrintInfo() {
        System.out.println();
        System.out.println(  "Item: " + id );
        System.out.println( "Durability: " + turnsLeft);
    };

}