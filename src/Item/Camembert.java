package Item;

import Logger.Logger;
import Player.Player;
import Room.*;
import Enums.ELogger;
import GameManager.GameManager;

public class Camembert extends Item{


    static int idNumber = 1;

    int abilityNumber;

    public static void ResetCounter(){
        idNumber = 1;
    }

    public void setIdNumberCopySer(){idNumberCopy = idNumber;}
    public void setIdNumberSer(){idNumber = idNumberCopy;}

    public Camembert(){
       super( "Camembert_" + idNumber++ );
       abilityNumber = 1;
    }

    @Override
    public boolean NeedToThrow(){
        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logEntry(this.getClass().getName(), "NeedToThrow", "");
            Logger.logExit(this.getClass().getName(), "NeedToThrow", abilityNumber == 0 ? "true" : "false");
        }
        return abilityNumber == 0;
    }

    @Override
    public void Use(Player player) {

        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logEntry(this.getClass().getName(), "Use", "player");
        }

        this.MakeGas(player.GetRoom());
        abilityNumber--;

        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logExit(this.getClass().getName(), "Use");
        }
    }

    public void MakeGas(Room room){
        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logEntry(this.getClass().getName(), "MakeGas", "room");
        }
        room.SetTurnsLeftForEffect(5);
        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logExit(this.getClass().getName(), "MakeGas");
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
}
