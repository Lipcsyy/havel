package Item;

import Logger.Logger;
import Player.Player;
import Enums.ELogger;
import GameManager.GameManager;

public class Rag extends Item{

    static int idNumber = 1;

    public static void ResetCounter(){
        idNumber = 1;
    }

    public void setIdNumberCopySer(){idNumberCopy = idNumber;}
    public void setIdNumberSer(){idNumber = idNumberCopy;}

    private int turnsLeft;

    public Rag(){
        super( "Rag_" + idNumber++ );
        turnsLeft = 5;
    }

    @Override
    public void Use(Player player){
        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logEntry(this.getClass().getName(), "Use", "player");
            Logger.logExit(this.getClass().getName(), "Use");
        }
        player.Freeze(3);
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
    public void DecreaseTurnsLeft(Player player){
        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logEntry(this.getClass().getName(), "DecreaseTurnsLeft", "");
        }
        turnsLeft --;
        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logExit(this.getClass().getName(), "DecreaseTurnsLeft", "turnsLeft --");
        }
    }

    @Override
    public boolean NeedToThrow(){
        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logEntry(this.getClass().getName(), "NeedToThrow", "");
            Logger.logExit(this.getClass().getName(), "NeedToThrow");
        }
        return turnsLeft == 0;
    }
}
